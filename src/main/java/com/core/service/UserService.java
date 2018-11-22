package com.core.service;

import com.base.model.UserInfo;
import com.base.util.RequestContextHolderUtil;
import com.core.dao.UserInfoMapper;
import com.core.redis.JedisUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
	@Autowired
	UserInfoMapper userDao;

    @Autowired
    JedisUtil jedisUtil;

	/**
	 * 根据Id获取账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserName(Integer userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	/**
	 * 根据用户名获取账户信息
	 * 
	 * @param userName
	 * @return
	 */
	public UserInfo getUser(String userName) {
		UserInfo user = new UserInfo();
		user.setUserName(userName);
		List<UserInfo> list = userDao.checkUserName(user);
		if (null == list || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 注册检查是否用户名可用
	 * 
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName) {
		boolean flag = false;
		UserInfo user = this.getUser(userName);
		if (null == user) {
			flag = true;
		}
		return flag;
	}

    /**
     * 判断当前人员是否登录以及其角色权限
     * @param userName
     * @return
     */
	public int role(String userName) {
       UserInfo user = this.getUser(userName);
       if (user != null) {
           if (user.getIsAdmin() == 1) {// 1:管理员
               return 1;
           } else {
               return 0;//普通用户
           }
       }
        return -1;//未找到对应的用户
	}

	/**
	 * 判断验证码是否输入正确
	 * 
	 * @param checkCode
	 * @return
	 */
	public boolean checkYZM(String checkCode) {
		boolean flag = false;
		HttpSession session = RequestContextHolderUtil.getSession();
		// 从Session对象中读取出保存在其中的图片验证码字符串
		String piccode = (String) session.getAttribute("piccode");
		if (checkCode != null && checkCode != "" && piccode != null
				&& piccode != "") {
			// 将用户的输入字符串全部改成大写
			checkCode = checkCode.toUpperCase();
			// 将验证码字符串全部改成大写
			piccode = piccode.toUpperCase();
		}
		if (checkCode.equals(piccode)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 添加用户记录
	 * 
	 * @param user
	 */
	public void addUser(UserInfo user) {
        // 密码使用 salt 盐处理
	    String password=user.getPassword();
        Md5Hash md5Hash = new Md5Hash(password, "salt");
        user.setPassword(md5Hash.toString());
		userDao.insertSelective(user);
	}

}