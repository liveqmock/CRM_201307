package com.hundsun.jres.bizframe.core.authority.constants;

import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-10<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserGroupConstants.java
 * 修改日期 修改人员 修改说明 <br>
 * 20111110  huhl@hundsun.com  用户群组API修改
 * ======== ====== ============================================ <br>
 *
 */
public class UserGroupConstants {

	public static final String USERGROUP_ROOT="bizroot";
	
	/**
	 * 机构类型
	 */
	public static final String ORG_TYPE="org";
	
	/**
	 * 机构类型
	 */
	public static final String ORG_NAME="组织机构";
	
	/**
	 * 机构类型
	 */
	public static final String BRANCH_TYPE="branch";
	
	/**
	 * 机构类型
	 */
	public static final String BRANCH_NAME="机构";
	
	/**
	 * 部门类型
	 */
	public static final String DEP_TYPE="dep";
	
	/**
	 * 部门类型
	 */
	public static final String DEP_NAME="部门";
	/**
	 * 岗位类型
	 */
	public static final String OFFICE_TYPE="office";
	/**
	 * 岗位类型
	 */
	public static final String OFFICE_NAME="岗位";
	
	/**
	 * 角色类型
	 */
	public static final String ROLE_TYPE="role";
	
	/**
	 * 角色类型
	 */
	public static final String ROLE_NAME="角色";
	
	/**
	 * 团队类型
	 */
	public static final String TEAM_TYPE="team";
	
	/**
	 * 团队类型
	 */
	public static final String TEAM_NAME="团队";
	
	/**
	 * 岗位类型
	 */
	public static final String POSITION_TYPE="pos";
	/**
	 * 岗位类型
	 */
	public static final String POSITION_NAME="岗位";
	
	/**
	 * 岗位名称扩展名
	 * 新增组织时主管岗位的扩展名
	 */
	public static final String MANAGER_POSITION_NAME_SUFFIX=SysParameterUtil.getProperty("managerPositionNameSuffix", "主管");
	
	/**
	 * 岗位编码扩展名
	 * 新增组织时主管岗位编码的扩展名
	 */
	public static final String MANAGER_POSITION_CODE_SUFFIX=SysParameterUtil.getProperty("managerPositionCodeSuffix", "head");

	/**
	 * 岗位名称扩展名
	 * 新增组织时普通岗位的扩展名
	 */
	public static final String COMMON_POSITION_NAME_SUFFIX=SysParameterUtil.getProperty("commonPositionNameSuffix", "普通岗");
	
	/**
	 * 岗位编码扩展名
	 * 新增组织时普通岗位编码的扩展名
	 */
	public static final String COMMON_POSITION_CODE_SUFFIX=SysParameterUtil.getProperty("commonPositionCodeSuffix", "common");

	
	/**
	 * 组织编码是否唯一<br>
	 * false:不唯一，不同纬度可以能重复，同一纬度不能重复<br>
	 * true :唯一  ，不同纬度组织编码不能重复<br>
	 */
	public static final String UNIQUE_ORG_CODE=SysParameterUtil.getProperty("uniqueOrgCode", "false");
	
	
	public static final String KEY_ORG_DIMENSION="dimension";
	
	
	public static final String KEY_ORG_CODE="org_code";
	
	
}
