package com.hundsun.crm.common.plugin;

import org.apache.commons.lang.StringUtils;

import com.hundsun.jres.interfaces.share.dataset.IDataset;


public class IdentityUtils {

	public static final String getUser(IDataset request) throws RuntimeException{
		String user = request.getString("userId");
		if(StringUtils.isNotEmpty(user)){
			return user;
		}
		user = request.getString("$_USER_ID");
		if(StringUtils.isNotEmpty(user)){
			return user;
		} else {
			throw new RuntimeException("无法获取用户信息，请重新登陆！");
		}
	}
	
	/**
	 * 如果key获取到的值是空的，抛出50002异常
	 * @param request
	 * @param key
	 * @return
	 */
	public static final String getString(IDataset request, String key){
		String value = request.getString(key);
		if(StringUtils.isEmpty(value)){
			throw new RuntimeException("50002:"+key);
		}
		return value;
	}
}
