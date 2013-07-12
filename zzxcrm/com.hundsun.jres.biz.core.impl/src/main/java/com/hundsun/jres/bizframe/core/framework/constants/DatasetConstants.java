package com.hundsun.jres.bizframe.core.framework.constants;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-8-25<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：DatasetConstants.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class DatasetConstants {

	public static final String OUTPUT_TYPE_KEY="_type";
	/**
	 * IDataset转化为treeJson格式的类型
	 */
	public static final String OUTPUT_TREE_TYPE="treeOutput";
	/**
	 * 请求是否做数据权限检测
	 */
	public static final String CHECK_ORG_PERMISSION="checkOrgPermission";
	/**
	 * IDataset转化为collectionJson格式的类型
	 */
	public static final String OUTPUT_COLLECTION_TYPE="collectionOutput";
	
	/**
	 * 当前请求用户ID
	 */
	public static final String USER_ID="$_USER_ID";
	
	/**
	 * 当前请求客户端IP
	 */
	public static final String CLIENT_IP="$_CLIENT_IP";
	
	/**
	 * 当前请求服务端IP
	 */
	public static final String SERVICE_IP="$_SERVICE_IP";
	
	/**
	 * 当前请求服务端端口
	 */
	public static final String SERVICE_PORT="$_SERVICE_PORT";
	
	/**
	 * 当前请求服务端端口
	 */
	public static final String ORG_ID="$_ORG_ID";
	
	/**
	 * http接入cep中的请求头
	 */
	public static final String EVENT_DATASET_HEADER="$_HEAD_DATASET";
}
