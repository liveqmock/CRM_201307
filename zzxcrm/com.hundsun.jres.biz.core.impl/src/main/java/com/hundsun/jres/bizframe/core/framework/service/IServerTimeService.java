package com.hundsun.jres.bizframe.core.framework.service;
/**
 * 
 * @author huhl
 * 
 * 获取服务器当前时间
 * 
 */
public interface IServerTimeService {

	/**
	 * 获取服务器当前时间
	 * @return
	 *       具有一定格式的时间字符串
	 */
	public String getServerTime() throws Exception;
}
