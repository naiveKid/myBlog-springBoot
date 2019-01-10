package com.core.interceptor;

import com.base.pojo.Page;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;


/**
 * myBatis分页拦截器
 */
// 首先通过注解注册(定义该拦截器的切入点，对那个类的哪个方法进行拦截，防止方法重载需要声明参数类型以及个数)
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {
	private String sqlIdByPageRegex;// 这则表达式用了筛选所有分页的sql语句

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
		// 通过拦截器得到被拦截的对象,就是上面配置的注解的对象
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		// 为了获取以及设置某些对象的属性值（某些对象的属性是没有getter/setter的），mybatis提供的快捷的通过反射设置获取属性值的工具类，当然也可以通过自己写反射完成
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

		// 得到当前的mapper对象信息,即为各种select，update，delete，insert语句的映射配置信息，通过上面的工具类获取属性对象
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

		// 配置文件中SQL语句的Id
		String id = mappedStatement.getId();
		// 符合sqlIdByPageRegex正则表达式的方法名才被拦截
		if (id.matches(sqlIdByPageRegex)) {
			// sql语句在对象BoundSql对象中，这个对象有get方法可以直接获取
			BoundSql boundSql = statementHandler.getBoundSql();
			// 获取原始sql，该sql是预处理的，有参数还没有被设置，被问号代替
			String sql = boundSql.getSql();
			// 改造后带分页查询的SQL语句
			String pageSql = sql + " limit " + Page.getOffset() + "," + Page.getPageSize();
			// 通过反射将原来的sql给换成加入分页的sql
			metaObject.setValue("delegate.boundSql.sql", pageSql);
		}
		// 连接器是链式结构的。完成拦截处理以后，需要保证接下来其他拦截器或代码继续执行
		return invocation.proceed();
	}

	@Override
    public Object plugin(Object target) {
		// 表示给一个目标对象织入一个拦截器，该代码织入的的拦截器对象就是本身this对象
		return Plugin.wrap(target, this);
	}

	@Override
    public void setProperties(Properties properties) {
		// 可读取到配置文件中定义的属性以及属性值
		sqlIdByPageRegex = (String) properties.get("sqlIdByPageRegex");
	}
}
