package com.hundsun.jres.bizframe.core.authority.service.api;

import java.util.Date;
import java.util.List;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.JsonUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserMessage;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.constants.MessageConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.service.MessageService;
import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-16<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：MessageServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 * 
 * ======== ====== ============================================ <br>
 * 
 * 此实例中的消息格式是：
 * $消息唯一标识$消息标题$发送者ID$接收者ID$发送日期时间$消息内容$消息类型$已读标识$扩展字段$
 * "[{"msgId":"id1",
 *    "msgTitle":"title1",
 *    "sendUserId":"sendUserId1",
 *    "receiveUserId":"receiveUserId1",
 *    "sendDate":"sendDate1",
 *    "msgContent":"msgContent1",
 *    "msgType":"msgType1",
 *    "msgIsRed":"msgIsRed1"
 *    },{"msgId":"id2",
 *    "msgTitle":"title2",
 *    "sendUserId":"sendUserId2",
 *    "receiveUserId":"receiveUserId2",
 *    "sendDate":"sendDate2",
 *    "msgContent":"msgContent2",
 *    "msgType":"msgType2",
 *    "msgIsRed":"msgIsRed2"
 *    }...]"
 */
public class MessageServiceHandler extends ServiceHandler implements MessageService {

	/**
	 * 
	 */
	public    String formatMessage(String oldMsg, String modelName)
			throws Exception {
//		SysUserMessage userMessage=JsonUtil.json2object(oldMsg, SysUserMessage.class);
		if(MessageConstants.MSG_BIZ_MODEL.equals(modelName)){
			return oldMsg;
		}
		return null;
	}

	public    String receiveMessage() throws Exception {
		UserMessageCache messageCache=UserMessageCache.getInstance();
		CommonRequestDTP  request=this.getCurrentRequest();
		String userId=request.getUserId();
		List<SysUserMessage> msgs=messageCache.getMessages(userId);
		String res=JsonUtil.collection2Json(msgs);
		return res;
	}

	/**
	 * 只支持发送一段
	 *    {"msgId":"id1",
	 *    "msgTitle":"title1",
	 *    "sendUserId":"sendUserId1",
	 *    "receiveUserId":"receiveUserId1",
	 *    "sendDate":"sendDate1",
	 *    "msgContent":"msgContent1",
	 *    "msgType":"msgType1",
	 *    "msgIsRed":"msgIsRed1"}格式的数据
	 */
	@SuppressWarnings("unchecked")
	public   void sendMessage(String message) throws Exception {
		UserMessageCache messageCache=UserMessageCache.getInstance();
		SysUserMessage userMessage=JsonUtil.json2object(message, SysUserMessage.class);
		String receiveUserId=userMessage.getReceiveUserId();
		if(!InputCheckUtils.notNull(receiveUserId))
			throw new BizframeException("1021");
		
		String sendUserId=userMessage.getSendUserId();
		if(!InputCheckUtils.notNull(sendUserId))
			throw new BizframeException("1021");
		
		String msgTitle=userMessage.getMsgTitle();
		if(!InputCheckUtils.notNull(msgTitle))
			throw new BizframeException("1021");
		
		String msgContent=userMessage.getMsgContent();
		if(!InputCheckUtils.notNull(msgContent))
			throw new BizframeException("1021");
		
		String msgType=userMessage.getMsgType();
		if(!InputCheckUtils.notNull(msgType))
			throw new BizframeException("1021");
		String msgId=getMessageID(32,sendUserId,receiveUserId);
		userMessage.setMsgId(msgId);
		if(messageCache.containsUserID(receiveUserId)){
			messageCache.putMessage(receiveUserId, userMessage);
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		BizframeDao dao=new BizframeDao("tsys_user_message",new String[]{"msg_id"},SysUserMessage.class,session);
		try{
			session.beginTransaction();
			dao.create(userMessage);
			session.endTransaction();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}


	}

	
	
	/**
	 * 获取唯一消息ID
	 * 			获取逻辑:根据发送者ID、接受者ID和消息发送时间的long值来获取
	 * @param length
	 * 			数据库中消息字段的长度
	 * @param sender
	 *          发送者ID
	 * @param geter
	 * 			接收者ID
	 * @return
	 * 		    唯一消息ID
	 */
	public static String getMessageID(int length,String sender,String geter){
		int le=(length-13)/2;
		String start=sender.substring(sender.length()<le?0:sender.length()-le, sender.length());
		String end=geter.substring(geter.length()<le?0:geter.length()-le, geter.length());
		String messageID=start+new Date().getTime()+end;
		return messageID;
	}
	
	
}
