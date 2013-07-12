/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : RightService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2010-12-28    胡海亮               完善了当session失效时，轮询请求处理
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserMessage;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessException;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
/**
 * 功能说明: 消息服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-3<br>
 * 修改记录：
 * ===============================================================
 * 2013-04-15     zhangsu   BUG #4242 目前消息收件箱中多选无法删除
 */
public class SysMessageService implements IService
{

	private static final String	TABLE_NAME					= "tsys_user_message";
	/**
	 * 日志句柄
	 */
	private BizLog				log							= LoggerSupport
																	.getBizLogger(SysMessageService.class);

	/** 交易代码 */
	private static final String	INBOX_RESOCODE				= "bizEmailInbox";

	/** 交易代码 */
	private static final String	OUT_RESOCODE				= "bizEmailOutbox";

	/** 用户发送消息子交易代码 */
	private static final String	OPERCODE_MSGSEND			= "bizMsgSend";

	/** 用户接收消息交易代码 */
	private static final String	OPERCODE_MSGRECEIVE			= "bizSetMsgReceive";

	/** 用户删除消息子交易代码 */
	private static final String	OPERCODE_MSGDELETE			= "bizMsgDel";

	/** 用户收箱查询 */

	private static final String	OPERCODE_EMAIL_INBOX_FIND	= "bizEmailInboxFind";

	/** 用户发信箱查询 */
	private static final String	OPERCODE_EMAIL_OUTBOX_FIND	= "bizEmailOutboxFind";

	/** 用户查看邮件 */
	private static final String	OPERCODE_EMAIL_VIEW			= "bizEmailView";

	/** 用户即时消息记录 */
	private static final String	OPERCODE_IM_INBOX_FIND		= "bizIMBoxFind";

	/** 用户轮询消息 */
	private static final String	OPERCODE_EMAIL_POLL			= "bizEmailPoll";

	/** 用户邮件消息类型代码 */
	public static final String	MSG_TYPE_EMAIL				= "1";

	/** 用户即时消息类型代码 */
	public static final String	MSG_TYPE_IM					= "2";

	/** 消息已读类型代码 */
	public static final String	MSG_ISREAD					= "1";

	/** 消息未读类型代码 */
	public static final String	MSG_UNREAD					= "0";

	/** 用户通知消息类型代码 */
	public static final String	MSG_TYPE_NOTICE				= "0000003";

	/** 收件箱类型 */
	@SuppressWarnings("unused")
	private static final String	TYPE_INBOX					= "inbox";

	/** 发件箱类型 */
	@SuppressWarnings("unused")
	private static final String	TYPE_OUTBOX					= "outbox";

	private UserMessageCache	messageCache				= UserMessageCache.getInstance();

	/**
	 * 当前交易码
	 */
	private String				resoCode					= "";

	/**
	 * 当前子交易码
	 */
	private String				operCode					= "";

	public IDataset action(IContext context) throws Exception
	{

		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString(REQUEST_RESCODE);

		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;

		if (INBOX_RESOCODE.equals(resoCode) || OUT_RESOCODE.equals(resoCode)) {
			if (OPERCODE_MSGDELETE.equals(operCode)) {
				deleteMessage(context);
			} else if (OPERCODE_MSGRECEIVE.equals(operCode)) {
				recevieMessage(context);
			} else if (OPERCODE_MSGSEND.equals(operCode)) {
				sendMessage(context);
			} else if (OPERCODE_EMAIL_VIEW.equals(operCode)) {
				viewMessage(context);
			} else if (isFindService(operCode)) {
				resultDataset = findMessage(context, operCode);
			} else if (OPERCODE_EMAIL_POLL.equals(operCode)) {
				resultDataset = pollFindMessage(context, operCode);
			}
		} else {
			throw new BizBussinessException(BizframeException.ERROR_DEFAULT, "资源:" + resoCode
					+ "配置不存在!");
		}
		if (resultDataset == null) {// 无返回值情况
			MapWriter mw = new MapWriter();
			mw.put("result", "success");
			resultDataset = mw.getDataset();
		}
		context.setResult("result", resultDataset);

		return resultDataset;
	}

	/**
	 * 轮询查询 策略：查询查询缓存，不查询数据库
	 * @param context
	 * @param operCode2
	 * @return
	 */
	private IDataset pollFindMessage(IContext context, String operCode2)
	{
		IDataset request = context.getRequestDataset();
		// UserInfo userInfo = HttpUtil.getUserInfo(request);
		String userId = request.getString(DatasetConstants.USER_ID);// 获得当前用户ID
		if (null == userId) {
			throw new BizframeException("4001");
		}

		List<SysUserMessage> msgs = messageCache.getMessages(userId);
		if (null == msgs) {
			msgs = new ArrayList<SysUserMessage>();
		}
		return DataSetConvertUtil.collection2DataSet(msgs, SysUserMessage.class);
	}

	/**
	 * @param context
	 */
	private void viewMessage(IContext context)
	{
		IDataset request = context.getRequestDataset();
		String msgId = request.getString("msgId");
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("msg_id", msgId);
		values.put("msg_isred", MSG_ISREAD);
		try {
			updateMsgByMsgId(values, msgId);
			// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 begin
			String userId = request.getString(DatasetConstants.USER_ID);
			removeReadMsgFromCache(msgId, userId);
			// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 end
		} catch (Exception e) {
			e.printStackTrace();
			log.error("viewMessage()方法执行失败", e.fillInStackTrace());
		}
	}

	@SuppressWarnings("static-access")
	private boolean isFindService(String operCode)
	{
		boolean res = (this.OPERCODE_EMAIL_INBOX_FIND.equals(operCode)
				|| this.OPERCODE_EMAIL_OUTBOX_FIND.equals(operCode) || this.OPERCODE_IM_INBOX_FIND
				.equals(operCode));
		return res;
	}

	/**
	 * 收发件箱查询
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private IDataset findMessage(IContext context, String operCode) throws Exception
	{
		IDataset request = context.getRequestDataset();
		String userId = request.getString(DatasetConstants.USER_ID);
		StringBuffer sb = new StringBuffer(
				" select u.*,sendUser.user_name send_user_name, receiveUser.user_name receive_user_name ");
		sb.append(" from tsys_user_message u  inner join tsys_user sendUser on sendUser.user_id=u.send_user_id");
		sb.append(" inner join tsys_user receiveUser on receiveUser.user_id=u.receive_user_id where 1=1 ");
		Map<String, Object> param = new HashMap<String, Object>();

		if (this.OPERCODE_EMAIL_INBOX_FIND.equals(operCode)) {
			sb.append(" and u.msg_type = @msgType");
			param.put("msgType", this.MSG_TYPE_EMAIL);// 消息类型是邮件
			sb.append(" and receive_user_id = @receiveUserId");
			param.put("receiveUserId", userId);// 收件人是自己
		} else if (this.OPERCODE_EMAIL_OUTBOX_FIND.equals(operCode)) {
			sb.append(" and u.msg_type = @msgType");
			param.put("msgType", this.MSG_TYPE_EMAIL);// 消息类型是邮件
			sb.append(" and send_user_id = @sendUserId");
			param.put("sendUserId", userId);// 发件人是自己
		} else if (this.OPERCODE_IM_INBOX_FIND.equals(operCode)) {
			sb.append(" and ( receive_user_id = @receiveUserId");
			sb.append(" or send_user_id = @sendUserId )");
			param.put("receiveUserId", userId);// 收件人是自己
			param.put("sendUserId", userId);// 收件人是自己
			sb.append("and u.msg_type = @msgType");
			param.put("msgType", this.MSG_TYPE_IM);// 消息类型是邮件
		}

		if (InputCheckUtils.notNull(request.getString("msgTitle"))) {// 消息标题
			sb.append(" and u.msg_title like @msgTitle");
			param.put("msgTitle", request.getString("msgTitle"));
		}
		if (InputCheckUtils.notNull(request.getString("msgId"))) {// 消息ID
			sb.append(" and u.msg_id = @msgId");
			param.put("msgId", request.getString("msgId"));
		}
		if (InputCheckUtils.notNull(request.getString("sendDate"))) {// 等值匹配发送时间
			sb.append(" and u.send_date = @sendDate");
			param.put("sendDate", request.getString("sendDate"));
		}
		if (InputCheckUtils.notNull(request.getString("sendDateStart"))) {// 大于发送时间
			sb.append(" and u.send_date >= @sendDateStart");
			param.put("sendDateStart", request.getString("sendDateStart"));
		}
		if (InputCheckUtils.notNull(request.getString("sendDateEnd"))) {// 小于发送时间
			sb.append(" and u.send_date <= @sendDateEnd");
			param.put("sendDateEnd", request.getString("sendDateEnd"));
		}
		if (InputCheckUtils.notNull(request.getString("msgContent"))) {// 消息内容
			sb.append(" and u.msg_content like @msgContent");
			param.put("msgContent", request.getString("msgContent"));
		}
		if (InputCheckUtils.notNull(request.getString("msgType"))) {// msg_type消息类型
			sb.append(" and u.msg_type = @msgType");
			param.put("msgType", request.getString("msgType"));
		}
		if (InputCheckUtils.notNull(request.getString("msgIsred"))) {// msg_isred消息已读标志
			sb.append(" and u.msg_isred = @msgIsred");
			param.put("msgIsred", request.getString("msgIsred"));
		}
		sb.append(" order by u.send_date desc");
		IDBSession session = DBSessionAdapter.getSession();
		String start = request.getString("start");
		String limit = request.getString("limit");
		IDataset resultDataset = null;
		if (InputCheckUtils.notNull(start, limit)) {
			// 分页
			resultDataset = session.getDataSetByMapHasTotalCount(sb.toString(),
					Integer.valueOf(start), Integer.valueOf(limit), param);

		} else {
			resultDataset = session.getDataSetByMapHasTotalCount(sb.toString(), param);
		}
		DataSetUtil.addDictDisplayColumns(resultDataset, new String[] { "BIZ_MSG_TYPE",
				"BIZ_IS_READ" }, new String[] { "msg_type", "msg_isred" }, new String[] {
				"msg_type_display", "msg_isred_display" });
		return resultDataset;
	}

	/**
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	private void deleteMessage(IContext context) throws Exception
	{
		IDataset request = context.getRequestDataset();
		//遍历获取msgIds
		List<String> msgIds = new ArrayList<String>();
		request.beforeFirst();
		while(request.hasNext()){
			request.next();
			String msgId = request.getString("msgIds");
			msgIds.add(msgId);
		}
		//String[] msgIds = request.getStringArray("msgIds");
		IDBSession dBSession = DBSessionAdapter.getSession();
		String userId = request.getString(DatasetConstants.USER_ID);
		try {
			dBSession.beginTransaction();
			for (String id : msgIds) {
				HsSqlString hss = new HsSqlString(TABLE_NAME, HsSqlString.TypeDelete);
				hss.setWhere("msg_id", id);
				dBSession.executeByList(hss.getSqlString(), hss.getParamList());
				// 从消息缓存中删除未读消息
				// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 begin
				removeReadMsgFromCache(id, userId);
				// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 end

			}
			dBSession.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			dBSession.rollback();
			throw new BizframeException("1005");
		} catch (Exception e) {
			e.printStackTrace();
			dBSession.rollback();
			throw new BizframeException("1005");
		}

	}

	/**
	 * @param context
	 */
	@SuppressWarnings("static-access")
	private void sendMessage(IContext context) throws Exception
	{
		IDataset request = context.getRequestDataset();
		// 20120522 xujin BUG #2923::收件箱中将“未读”消息直接回复后，该消息仍为未读 begin
		String msgId = request.getString("srcMsgId");
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			if (null != msgId && !"".equals(msgId.trim())) {
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("msg_id", msgId);
				values.put("msg_isred", MSG_ISREAD);

				updateMsgByMsgId(values, msgId);
				// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 begin
				String userId = request.getString(DatasetConstants.USER_ID);
				removeReadMsgFromCache(msgId, userId);
				// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符 end

			}
			// 20120522 xujin BUG #2923::收件箱中将“未读”消息直接回复后，该消息仍为未读 end
			String msgType = request.getString("msgType");
			if (InputCheckUtils.notNull(msgType)) {
				if (this.MSG_TYPE_EMAIL.equals(msgType)) {
					sendEmailMessage(context);
				} else if (this.MSG_TYPE_IM.equals(msgType)) {
					sendImMesaage(context);
				} else if (this.MSG_TYPE_NOTICE.equals(msgType)) {
					sendNoticeMessage(context);
				}

			} else {
				throw new BizframeException("1021");
			}
			//关闭事务
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			log.error("sendMessage()方法执行失败", e.fillInStackTrace());
		}

	}

	/**
	 * 从未读邮件缓存中移除已读邮件
	 */
	private void removeReadMsgFromCache(String msgId, String userIds)
	{

		String[] receiveUserIdArray = userIds.split(",");

		for (String receiveUserId : receiveUserIdArray) {

			if (receiveUserId != null) {
				if (messageCache.containsUserID(receiveUserId)) {
					messageCache.removeMessage(receiveUserId, msgId);
				}

			}

		}

	}

	/**
	 * @param message
	 * @throws Exception
	 */
	private void sendImMesaage(IContext context) throws Exception
	{
		// UserMessageCache messageCache= UserMessageCache.getInstance();
		// String receiveUserId=message.getReceiveUserId();
		// if(messageCache.getMsgCache().containsKey(receiveUserId)){
		// //持续到DB中
		// }else{
		// messageCache.putMessage(receiveUserId, message);
		// }
	}

	/**
	 * @param message
	 * @throws Exception
	 */
	private void sendNoticeMessage(IContext context) throws Exception
	{

	}

	/**
	 * @param message
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private void sendEmailMessage(IContext context) throws Exception
	{
		IDataset request = context.getRequestDataset();

		String receiveUserIds = request.getString("receiveUserIds");
		if (!InputCheckUtils.notNull(receiveUserIds))
			throw new BizframeException("1021");

		String sendUserId = request.getString("sendUserId");
		if (!InputCheckUtils.notNull(sendUserId))
			throw new BizframeException("1021");

		String msgTitle = request.getString("msgTitle");
		if (!InputCheckUtils.notNull(msgTitle))
			throw new BizframeException("1021");

		String msgContent = request.getString("msgContent");
		if (!InputCheckUtils.notNull(msgContent))
			throw new BizframeException("1021");

		String msgType = request.getString("msgType");
		if (!InputCheckUtils.notNull(msgType))
			throw new BizframeException("1021");

		String[] receiveUserIdArray = receiveUserIds.split(",");
		Set<String> receiveSet = new HashSet<String>();
		for (String temp : receiveUserIdArray) {
			receiveSet.add(temp);
		}
			for (String receiveUserId : receiveSet) {
				Map<String, Object> values = new HashMap<String, Object>();
				String msgId = getMessageID(32, sendUserId, receiveUserId);
				long sendDate = new Date().getTime();
				values.put("msg_id", msgId);
				values.put("msg_title", msgTitle);
				values.put("send_user_id", sendUserId);
				values.put("receive_user_id", receiveUserId);
				values.put("send_date", sendDate);
				values.put("msg_content", msgContent);
				values.put("msg_type", msgType);
				values.put("msg_isred", this.MSG_UNREAD);
				HsSqlTool.insert(TABLE_NAME, values);

				if (messageCache.containsUserID(receiveUserId)) {
					SysUserMessage msg = new SysUserMessage();
					msg.setMsgContent(msgContent);
					msg.setMsgId(msgId);
					msg.setMsgIsRed(this.MSG_UNREAD);
					msg.setMsgTitle(msgTitle);
					msg.setMsgType(msgType);
					msg.setReceiveUserId(receiveUserId);
					msg.setSendDate(sendDate);
					msg.setSendUserId(sendUserId);
					messageCache.putMessage(receiveUserId, msg);
				}

			}

		// 插入数据库
	}

	/**
	 * @param context
	 */
	private IDataset recevieMessage(IContext context)
	{
		IDataset request = context.getRequestDataset();
		UserMessageCache messageCache = UserMessageCache.getInstance();
		// 请求人的id
		String userId = request.getString("userId");
		List<SysUserMessage> onlineMessages = messageCache.getMessages(userId);
		onlineMessages = (null == onlineMessages) ? new ArrayList<SysUserMessage>()
				: onlineMessages;
		return DataSetConvertUtil.collection2DataSet(onlineMessages, SysUserMessage.class);
	}

	/**
	 * 根据消息ID更新
	 * @param param
	 *            待设置值
	 * @param msgId
	 *            消息ID
	 * @return
	 * @throws Exception
	 */
	public int updateMsgByMsgId(Map<String, Object> setParam, String msgId) throws Exception
	{
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("msg_id", msgId);
		return updateMsg(where, setParam);
	}

	/**
	 * 更新消息
	 * @param where
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public int updateMsg(Map<String, Object> where, Map<String, Object> values,
			String... additionalParam) throws Exception
	{
		return HsSqlTool.update(TABLE_NAME, where, values);

	}

	/**
	 * 插入消息
	 * @param msg
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public void insertMessage(SysUserMessage msg) throws Exception
	{
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("msg_id", this.getMessageID(32, msg));
		values.put("msg_title", msg.getMsgTitle());
		values.put("send_user_id", msg.getSendUserId());
		values.put("receive_user_id", msg.getReceiveUserId());
		values.put("send_date", msg.getSendDate());
		values.put("msg_content", msg.getMsgContent());
		values.put("msg_type", msg.getMsgType());
		values.put("msg_isred", msg.getMsgIsRed());
		insertMessage(values);
	}

	/**
	 * 插入消息
	 * @param param
	 * @throws SQLException
	 */
	public void insertMessage(Map<String, Object> param) throws Exception
	{

		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			HsSqlTool.insert(TABLE_NAME, param);
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			throw e;
		}
	}

	/**
	 * 获取唯一消息ID 获取逻辑:根据发送者ID、接受者ID和消息发送时间的long值来获取
	 * @param length
	 *            数据库中消息字段的长度
	 * @param msg
	 *            消息对象
	 * @return 唯一消息ID
	 */
	public static String getMessageID(int length, SysUserMessage msg)
	{
		return getMessageID(length, msg.getSendUserId(), msg.getReceiveUserId());
	}

	/**
	 * 获取唯一消息ID 获取逻辑:根据发送者ID、接受者ID和消息发送时间的long值来获取
	 * @param length
	 *            数据库中消息字段的长度
	 * @param sender
	 *            发送者ID
	 * @param geter
	 *            接收者ID
	 * @return 唯一消息ID
	 */
	public static String getMessageID(int length, String sender, String geter)
	{
		int le = (length - 13) / 2;
		String start = sender.substring(sender.length() < le ? 0 : sender.length() - le,
				sender.length());
		String end = geter.substring(geter.length() < le ? 0 : geter.length() - le, geter.length());
		String messageID = start + new Date().getTime() + end;
		return messageID;
	}

}
