package com.core.shiro;

import com.base.model.UserInfo;
import com.core.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义 Realm
 **/
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 设置realm的名称
     * 
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("myRealm");
    }

    /**
     * 得到身份和权限信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        // 获取当前用户的角色身份
        Set<String> rolesSet = getRolesByUserName(username);
        // 获取当前用户的操作权限
        Set<String> permissionsSet = getPermissionByUsename(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(rolesSet);
        authorizationInfo.setStringPermissions(permissionsSet);
        return authorizationInfo;
    }

    /**
     * 得到验证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        // 从数据库中获取用户密码
        String password = getPasswordByUserName(username);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, this.getName());
        // 设置加盐策略值
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
        return authenticationInfo;
    }

    /**
     * 模拟从数据库中获取权限信息
     *
     * @param username
     * @return
     */
    private Set<String> getPermissionByUsename(String username) {
        Set<String> permissionsSet = new HashSet<String>();
        permissionsSet.add("essay:update");
        permissionsSet.add("essay:publish");
        return permissionsSet;
    }

    /**
     * 根据用户名获取角色信息
     *
     * @param username
     * @return
     */
    private Set<String> getRolesByUserName(String username) {
        Set<String> rolesSet = new HashSet<String>();
        Integer roleValue=userService.role(username);
        if(roleValue==0){
            rolesSet.add("user");
        }else if(roleValue==1){
            rolesSet.add("admin");
        }
        return rolesSet;
    }
    
    /**
     * 根据用户名获取密码
     * 
     * @param username
     * @return
     */
    private String getPasswordByUserName(String username) {
        UserInfo user=userService.getUser(username);
        if(user==null){
            return null;
        }
        return user.getPassword();
    }


    public static void main(String[] args) {
        // 密码使用 salt 盐处理
        Md5Hash md5Hash = new Md5Hash("123456", "salt");
        System.out.println(md5Hash.toString());
    }
}
