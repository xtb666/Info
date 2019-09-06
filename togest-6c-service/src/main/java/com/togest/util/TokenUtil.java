package com.togest.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.togest.common.util.core.SpringContextHolder;
import com.togest.common.util.string.StringUtil;
import com.togest.service.impl.RedisService;

public class TokenUtil {
	public static final String TOKEN = "token";
	public static final String TOKEN_CACHE = "token:";
	public static final String USER_CACHE = TOKEN_CACHE + "user:";
	public static final String DATASCOPE_CACHE = TOKEN_CACHE + "dataScope:";
	private static final String KEY = "AES";

	private static RedisService redisService = SpringContextHolder
			.getBean("redisService");

	public static UserInfo getUser(HttpServletRequest request) {
		String token = getUserId(request);
		if (StringUtil.isNotBlank(token)) {
			return redisService.getJson(USER_CACHE +token, UserInfo.class);
		}
		return null;
	}

	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN);
		if(StringUtil.isBlank(token)){
			token = request.getParameter(TOKEN);
		}
		return token;
	}

	public static String getUserId(HttpServletRequest request) {
		String token =getToken(request);
		if(StringUtil.isNotBlank(token)){
			return token.substring(token.lastIndexOf(":")+1, token.length());
		}
		return null;
		
	}

	public static List getDataScopeToken(HttpServletRequest request) {
		String token =getUserId(request);
		if (StringUtil.isNotBlank(token)) {
			return redisService.getListJson(DATASCOPE_CACHE + token,
					Object.class);
		}
		return null;
	}

	public static String getUserTokenData(HttpServletRequest request,
			String property) {
		Object obj = getUser(request);
		if (StringUtil.isNotEmpty(obj)) {
			JSONObject jsonObject = JSONObject.fromObject(obj);
			return jsonObject.getString(property);
		}
		return null;
	}

	public static String getDataScopeTokenData(HttpServletRequest request,
			String code) {
		List list = getDataScopeToken(request);
		if (StringUtil.isNotEmpty(list) && StringUtil.isNotBlank(code)) {
			for (Object obj : list) {
				JSONObject jsonObject = JSONObject.fromObject(obj);
				if (code.equals(jsonObject.getString("code"))) {
					return jsonObject.getString("dataScope");
				}
			}
		}
		return null;
	}

	public static boolean isToken(String token) {
		if (StringUtil.isBlank(token)) {
			return false;
		}
		return redisService.hasKey(TOKEN_CACHE +token);
	}

	public static void freToken(String token) {
		if (isToken(token)) {
			redisService.set(TOKEN_CACHE +token, 1, 3600L);
		}
	}


}
