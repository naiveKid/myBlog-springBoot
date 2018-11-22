package com.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token生成工具类
 *
 */
public class TokenUtils {
	// 版本
	public static String TOKEN_VERSION = "1";
	// 设置发行人
	public static String ISSUER = "huangxingzhi";
	// 设置抽象主题
	public static String SUBJECT = "subject";

	// HS256 私钥
	public static String HS256KEY = "hxz";

	public static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	public static Key signingKey = new SecretKeySpec(Base64.decodeBase64(HS256KEY), signatureAlgorithm.getJcaName());

	/**
	 * 生成token
	 * 
	 * @param claims
	 * @return
	 */
	public static String getJWTString(Map<String, Object> claims) {

		long nowMillis = System.currentTimeMillis();
		claims.put(Claims.ISSUER, ISSUER);
		claims.put(Claims.SUBJECT, SUBJECT);
		claims.put(Claims.EXPIRATION, new Date(nowMillis + (12 * 60 * 60 * 1000)));
		claims.put(Claims.ISSUED_AT, new Date(nowMillis));
		JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);
		jwtBuilder.signWith(signatureAlgorithm, signingKey);
		return jwtBuilder.compact();
	}

	/**
	 * 得到申请人token
	 * 
	 * @param id
	 * @param userType
	 * @param className
	 * @return
	 */
	public static String getUserJWTString(Integer id, String userType, String className) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userType", userType);
		map.put("className", className);
		return TokenUtils.getJWTString(map);
	}

	/**
	 * 生成token（传入对象）
	 * 
	 * @param claims
	 * @return
	 */
	public static String getJWTString(Object obj) {

		Map<String, Object> claims = new HashMap<String, Object>();
		long nowMillis = System.currentTimeMillis();
		claims.put(Claims.ISSUER, ISSUER);
		claims.put(Claims.SUBJECT, SUBJECT);
		claims.put(Claims.EXPIRATION, new Date(nowMillis + (10 * 60 * 1000)));
		claims.put(Claims.ISSUED_AT, new Date(nowMillis));
		// 通过反射将对象中的属性填入map中
		Class<? extends Object> clz = obj.getClass();
		// 存入全类名
		System.out.println(clz.getName());
		claims.put("className", clz.getName());
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			// 设置field为可读
			field.setAccessible(true);
			try {
				claims.put(field.getName(), field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);
		jwtBuilder.signWith(signatureAlgorithm, signingKey);
		return jwtBuilder.compact();
	}

	/**
	 * 判断token是否过期
	 * 
	 * @param token
	 * @return
	 */
	public static boolean isValid(String token) {
		try {
			Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.trim());
			Long exp = (Long) jwsClaims.getBody().get(Claims.EXPIRATION);
			return exp - System.currentTimeMillis() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 解码token
	 * 
	 * @param token
	 * @return
	 */
	public static Map<String, Object> parseJWTtoMap(String token) {
		Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.trim()).getBody();
		return claims;
	}

	/**
	 * 解码token（验证是否过期）
	 * 
	 * @param token
	 * @return
	 */
	public static Map<String, Object> parseJWTtoMapForExpire(String token) {
		// 首相验证是否过期
		if (isValid(token)) {
			Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.trim()).getBody();
			return claims;
		} else {
			return null;
		}
	}

	/**
	 * 解码token（验证是否过期，并生成相应的对象）
	 * 
	 * @param token
	 * @return
	 */
	public static Object parseJWTtoMapForExpireAndObject(String token) {
		// 首相验证是否过期
		if (isValid(token)) {
			Map<String, Object> map = parseJWTtoMapForExpire(token);
			try {
				Class<?> clz = Class.forName((String) map.get("className"));
				// 实例化一个对象
				Object obj = clz.newInstance();
				// 通过反射为其赋值
				Field[] fields = clz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					field.set(obj, map.get(field.getName()));
				}
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static String getHS512Key() {
		Key key = MacProvider.generateKey(SignatureAlgorithm.HS512);
		String keyStr = Base64.encodeBase64String(key.getEncoded());
		return keyStr;
	}

}
