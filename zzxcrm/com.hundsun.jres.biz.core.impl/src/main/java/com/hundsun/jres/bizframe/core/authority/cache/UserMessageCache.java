package com.hundsun.jres.bizframe.core.authority.cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserMessage;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 功能说明: 用户消息缓存<br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hudnsun.com <br>
 * 开发时间: 2010-12-01<br>
 * <br>
 */
public class UserMessageCache
{

	private Map<String, List<SysUserMessage>>	msgCache	= new HashMap<String, List<SysUserMessage>>();

	private static UserMessageCache				instance	= null;

	/**
	 * 单例化UserMessageCache
	 * @return
	 */
	synchronized public static UserMessageCache getInstance()
	{
		if (null == instance) {
			try {
				instance = new UserMessageCache();
			} catch (Exception e) {
				e.printStackTrace();
				throw new java.lang.RuntimeException("初始化用户消息缓存失败");
			}
		}
		return instance;
	}

	/**
	 * @throws Exception
	 */
	private UserMessageCache() throws Exception
	{
		this.init();
	}

	/**
	 * 
	 */
	private void init()
	{

	}

	/**
	 * 为用户在缓存中开辟一个空间,并初始加载未读信息缓存
	 * @param userId
	 */
	public void registerUserCache(String userId)
	{
		if (!msgCache.containsKey(userId)) {
			List<SysUserMessage> messages = new ArrayList<SysUserMessage>();
			try {
				messages.addAll(getUnReadMsgs(userId));
			} catch (SQLException e) {
			  throw new BizframeException(e,"用户："+userId+"初始化未读消息缓存失败");
			}
			msgCache.put(userId, messages);
		}
	}

	/**
	 * method comments here
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	private Collection<? extends SysUserMessage> getUnReadMsgs(String userId) throws SQLException
	{
		IDBSession session = DBSessionAdapter.getSession();
		StringBuffer sb = new StringBuffer(
				" select u.*,sendUser.user_name send_user_name, receiveUser.user_name receive_user_name ");
		sb.append(" from tsys_user_message u  inner join tsys_user sendUser on sendUser.user_id=u.send_user_id");
		sb.append(" inner join tsys_user receiveUser on receiveUser.user_id=u.receive_user_id where 1=1 ");
		sb.append(" and receive_user_id = @receiveUserId");
		sb.append(" and u.msg_isred = @msgIsred");
		Map params = new HashMap();
		params.put("receiveUserId", userId);
		params.put("msgIsred", "0");

		return session.getObjectListByMap(sb.toString(), SysUserMessage.class, params);
	}

	/**
	 * 为用户在缓存中注销缓存一个空间
	 * @param userId
	 *            用户ID
	 */
	public void destroyUserCache(String userId)
	{
		msgCache.remove(userId);
	}

	/**
	 * 从缓存中得到用户的消息列表(不移除消息列表)
	 * @param userId
	 *            用户ID
	 * @return 用户的消息列表List<SysUserMessage>
	 */
	synchronized public List<SysUserMessage> getMessages(String userId)
	{
		return msgCache.get(userId);
	}

	/**
	 * 从缓存中得到用户的消息列表(移除消息列表)
	 * @param userId
	 *            用户ID
	 * @return 用户的消息列表List<SysUserMessage>
	 */
	synchronized public List<SysUserMessage> removeMessages(String userId)
	{
		return msgCache.remove(userId);
	}

	/**
	 * 向缓存中加入用户的消息列表
	 * @param userId
	 *            用户ID
	 * @param message
	 *            消息SysUserMessage
	 */
	synchronized public void putMessage(String userId, SysUserMessage message)
	{
		List<SysUserMessage> messages = msgCache.get(userId);
		if (null == messages) {
			messages = new ArrayList<SysUserMessage>();
			msgCache.put(userId, messages);
		}
		messages.add(message);
	}

	// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 增加根据msgId进行删除消息缓存
	synchronized public void removeMessage(String userId, String msgId)
	{
		List<SysUserMessage> messages = msgCache.get(userId);
		if (null == messages) {
			messages = new ArrayList<SysUserMessage>();
			msgCache.put(userId, messages);
		} else {
			if (messages.size() > 0) {
				SysUserMessage existMessage = null;
				for (SysUserMessage sysUserMessage : messages) {
					if (sysUserMessage != null) {
						if (sysUserMessage.getMsgId().equals(msgId)) {
							existMessage = sysUserMessage;
							break;
						}
					}
				}
				if (existMessage != null) {
					messages.remove(existMessage);
				}
			}
		}

	}

	/**
	 * 判断缓存中是否含有此用户的消息
	 * @param userId
	 *            用户ID
	 */
	synchronized public boolean containsUserID(String userId)
	{
		return msgCache.containsKey(userId);
	}

	/**
	 * 刷新缓存
	 */
	synchronized public void refresh()
	{
		try {
			this.msgCache.clear();
			init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("刷新内存失败");
		}
	}

}
