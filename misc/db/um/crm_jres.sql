/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : crm_jres

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2013-07-17 22:53:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cst_activity`
-- ----------------------------
DROP TABLE IF EXISTS `cst_activity`;
CREATE TABLE `cst_activity` (
  `atv_id` bigint(20) NOT NULL default '0',
  `atv_cust_no` varchar(17) default NULL,
  `atv_cust_name` varchar(100) default NULL,
  `atv_date` datetime NOT NULL,
  `atv_place` varchar(200) NOT NULL,
  `atv_title` varchar(500) NOT NULL,
  `atv_desc` varchar(2000) default NULL,
  PRIMARY KEY  (`atv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_activity
-- ----------------------------

-- ----------------------------
-- Table structure for `cst_customer`
-- ----------------------------
DROP TABLE IF EXISTS `cst_customer`;
CREATE TABLE `cst_customer` (
  `cust_no` char(17) NOT NULL,
  `cust_name` varchar(100) NOT NULL,
  `cust_region` varchar(50) default '',
  `cust_manager_id` bigint(20) default '0',
  `cust_manager_name` varchar(50) default '',
  `cust_level` int(11) default '1',
  `cust_level_label` varchar(50) default '',
  `cust_satisfy` int(11) default '3',
  `cust_credit` int(11) default '3',
  `cust_addr` varchar(300) default '',
  `cust_zip` char(10) default '',
  `cust_tel` varchar(50) default '',
  `cust_fax` varchar(50) default '',
  `cust_website` varchar(50) default '',
  `cust_licence_no` varchar(50) default '',
  `cust_chieftain` varchar(50) default '',
  `cust_bankroll` bigint(20) default '0',
  `cust_turnover` bigint(20) default '0',
  `cust_bank` varchar(200) default '',
  `cust_bank_account` varchar(50) default '',
  `cust_local_tax_no` varchar(50) default '',
  `cust_national_tax_no` varchar(50) default '',
  `cust_status` char(1) default '1',
  PRIMARY KEY  (`cust_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `cst_linkman`
-- ----------------------------
DROP TABLE IF EXISTS `cst_linkman`;
CREATE TABLE `cst_linkman` (
  `lkm_id` bigint(20) NOT NULL,
  `cust_no` varchar(17) NOT NULL,
  `cust_name` varchar(100) default NULL,
  `lkm_name` varchar(50) NOT NULL,
  `lkm_sex` varchar(5) default NULL,
  `lkm_postion` varchar(50) default NULL,
  `lkm_tel` varchar(50) NOT NULL,
  `lkm_mobile` varchar(50) default NULL,
  `lkm_memo` varchar(300) default NULL,
  PRIMARY KEY  (`lkm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_linkman
-- ----------------------------

-- ----------------------------
-- Table structure for `cst_sal_chance`
-- ----------------------------
DROP TABLE IF EXISTS `cst_sal_chance`;
CREATE TABLE `cst_sal_chance` (
  `chc_id` bigint(20) NOT NULL,
  `chc_source` varchar(50) default NULL,
  `chc_cust_name` varchar(100) NOT NULL,
  `chc_title` varchar(200) NOT NULL,
  `chc_rate` int(11) NOT NULL default '0',
  `chc_linkman` varchar(50) default NULL,
  `chc_tel` varchar(50) default NULL,
  `chc_desc` varchar(2000) NOT NULL,
  `chc_create_id` bigint(20) NOT NULL,
  `chc_create_by` varchar(50) NOT NULL,
  `chc_create_date` datetime NOT NULL,
  `chc_due_id` bigint(20) default NULL,
  `chc_due_to` varchar(50) default NULL,
  `chc_due_date` datetime default NULL,
  `chc_status` char(10) NOT NULL default '1',
  PRIMARY KEY  (`chc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_sal_chance
-- ----------------------------

-- ----------------------------
-- Table structure for `cst_sal_plan`
-- ----------------------------
DROP TABLE IF EXISTS `cst_sal_plan`;
CREATE TABLE `cst_sal_plan` (
  `pla_id` bigint(20) NOT NULL,
  `pla_chc_id` bigint(20) NOT NULL,
  `pla_date` datetime NOT NULL,
  `pla_todo` varchar(500) NOT NULL,
  `pla_result` varchar(500) default NULL,
  PRIMARY KEY  (`pla_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_sal_plan
-- ----------------------------

-- ----------------------------
-- Table structure for `jres_subsystem_rc`
-- ----------------------------
DROP TABLE IF EXISTS `jres_subsystem_rc`;
CREATE TABLE `jres_subsystem_rc` (
  `subsystem_name` varchar(32) NOT NULL,
  `subsystem_ver` varchar(32) NOT NULL,
  `begin_time` date NOT NULL,
  `end_time` date default NULL,
  `remark` varchar(200) default NULL,
  `trace_info` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jres_subsystem_rc
-- ----------------------------
INSERT INTO `jres_subsystem_rc` VALUES ('bizframe', '1.0.12.6', '2013-07-09', '2013-07-09', '新建基础业务框架V1.0.12.6版', null);
INSERT INTO `jres_subsystem_rc` VALUES ('bizframe', '1.0.18', '2013-07-09', '2013-07-09', '基础业务框架V1.0.18版', null);
INSERT INTO `jres_subsystem_rc` VALUES ('bizframe', '1.0.19', '2013-07-09', '2013-07-09', '基础业务框架V1.0.19版', null);
INSERT INTO `jres_subsystem_rc` VALUES ('bizframe', '1.0.24', '2013-07-09', '2013-07-09', '基础业务框架V1.0.24版', null);
INSERT INTO `jres_subsystem_rc` VALUES ('bizframe', '1.0.25', '2013-07-09', '2013-07-09', '基础业务框架V1.0.25版', null);

-- ----------------------------
-- Table structure for `tsys_biz_type`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_biz_type`;
CREATE TABLE `tsys_biz_type` (
  `biz_type_code` varchar(16) NOT NULL,
  `biz_type_name` varchar(32) default NULL,
  `def_value_code` varchar(16) default NULL,
  `std_type_code` varchar(16) default NULL,
  `type_length` bigint(20) default NULL,
  `type_prec` bigint(20) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`biz_type_code`),
  KEY `FK_BIZTYPE_DEFV` (`def_value_code`),
  KEY `FK_BIZTYPE_STD` (`std_type_code`),
  CONSTRAINT `FK_BIZTYPE_DEFV` FOREIGN KEY (`def_value_code`) REFERENCES `tsys_def_value` (`def_value_code`),
  CONSTRAINT `FK_BIZTYPE_STD` FOREIGN KEY (`std_type_code`) REFERENCES `tsys_std_type` (`std_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_biz_type
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_db_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_db_keyword`;
CREATE TABLE `tsys_db_keyword` (
  `db_keyword` varchar(16) NOT NULL,
  `std_type_code` varchar(16) default NULL,
  `db_type` varchar(8) NOT NULL,
  `db_version` varchar(16) NOT NULL,
  `def_flag` varchar(8) default NULL,
  PRIMARY KEY  (`db_keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_db_keyword
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_db_source`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_db_source`;
CREATE TABLE `tsys_db_source` (
  `db_type` varchar(8) NOT NULL,
  `db_version` varchar(16) NOT NULL,
  `db_dialect` varchar(256) default NULL,
  `db_driver` varchar(256) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`db_type`,`db_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_db_source
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_def_value`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_def_value`;
CREATE TABLE `tsys_def_value` (
  `def_value_code` varchar(16) NOT NULL,
  `def_value_name` varchar(32) default NULL,
  `def_value` varchar(1024) default NULL,
  PRIMARY KEY  (`def_value_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_def_value
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_dict_entry`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dict_entry`;
CREATE TABLE `tsys_dict_entry` (
  `dict_entry_code` varchar(16) NOT NULL,
  `kind_code` varchar(16) NOT NULL,
  `dict_entry_name` varchar(32) NOT NULL,
  `ctrl_flag` varchar(8) default NULL,
  `lifecycle` varchar(8) default NULL,
  `platform` varchar(8) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`dict_entry_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_dict_entry
-- ----------------------------
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_ALLOT_TYPE', 'BIZ_DICT', '分配分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_AUTH_TYPE', 'BIZ_DICT', '授权分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_DB_TYPE', 'BIZ_DICT', '数据库分类', '', '1', '1', 'zxz');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_DICT_TYPE', 'BIZ_DICT', '数据字典类型', '1', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_IS_READ', 'BIZ_DICT', '已读标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_KIND_DIMEN', 'BIZ_DICT', '分类维度', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_LIFECYCLE', 'BIZ_DICT', '生命周期', '', '1', '0', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_LOCK_STATUS', 'BIZ_DICT', '锁定状态', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_LOGIN_FLAG', 'BIZ_DICT', '登录标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_MODEL', 'BIZ_DICT', '模块', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_MSG_TYPE', 'BIZ_DICT', '消息类型', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_ORG_CATE', 'BIZ_DICT', '组织分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_ORG_DIMEN', 'BIZ_DICT', '组织维度', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_ORG_LEVEL', 'BIZ_DICT', '组织级别', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_PLATFORM', 'BIZ_DICT', '平台标志', '', '1', '0', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_RIGHT_CATE', 'BIZ_DICT', '权限分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_RIGHT_ENABLE', 'BIZ_DICT', '授权禁止标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_RIGHT_FLAG', 'BIZ_DICT', '授权标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_RIGHT_TYPE', 'BIZ_DICT', '权限类别分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_SERV_CATE', 'BIZ_DICT', '服务分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_SUB_SYSTEM', 'BIZ_DICT', '子系统', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_SYN_AUTH', 'BIZ_DICT', '同步授权', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_TIME_CATE', 'BIZ_DICT', '时间单位', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_USER_CATE', 'BIZ_DICT', '用户分类', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_USER_STATUS', 'BIZ_DICT', '用户状态', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_WINDOW_CATE', 'BIZ_DICT', '窗口类型', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_WINDOW_MODEL', 'BIZ_DICT', '窗口模式', '', '1', '1', '单例窗口/多例窗口');
INSERT INTO `tsys_dict_entry` VALUES ('BIZ_YES_OR_NO', 'BIZ_DICT', '是否标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('ENABLE', 'BIZ_DICT', '开闭标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('FIN_DICT_FLAG', 'BIZ_DICT', '条目标志', '', '1', '1', '');
INSERT INTO `tsys_dict_entry` VALUES ('SYS_BRANCH_LEVEL', 'BIZ_DICT', '机构级别', '', '1', '1', '机构级别');

-- ----------------------------
-- Table structure for `tsys_dict_item`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dict_item`;
CREATE TABLE `tsys_dict_item` (
  `dict_item_code` varchar(256) NOT NULL,
  `dict_entry_code` varchar(16) NOT NULL,
  `dict_item_name` varchar(60) NOT NULL,
  `lifecycle` varchar(8) default NULL,
  `platform` varchar(8) default NULL,
  `dict_item_order` bigint(20) default NULL,
  `rel_code` varchar(16) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_dict_item
-- ----------------------------
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_MODEL', '系统模块', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_MODEL', '用户模块', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_SUB_SYSTEM', '系统管理', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_SUB_SYSTEM', '工作流', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_SYN_AUTH', '同步授权', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_SYN_AUTH', '无', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_DICT_TYPE', '个性化数据字典', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_DICT_TYPE', '核心数据字典', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_LOGIN_FLAG', '登录', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_LOGIN_FLAG', '无', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('BIZFRAME', 'BIZ_SUB_SYSTEM', '基础业务框架', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('5', 'BIZ_KIND_DIMEN', '子系统', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'SYS_BRANCH_LEVEL', '级别3', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'SYS_BRANCH_LEVEL', '级别1', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'SYS_BRANCH_LEVEL', '级别2', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_WINDOW_CATE', '子窗口', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_RIGHT_CATE', '用户', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_RIGHT_CATE', '角色', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_RIGHT_CATE', '组织', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_SERV_CATE', '服务', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_SERV_CATE', '请求转发', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_ORG_DIMEN', '行政维度', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_ORG_DIMEN', '岗位维度', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_ORG_CATE', '机构', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_ORG_CATE', '部门', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_ORG_LEVEL', '总部', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_ORG_LEVEL', '分行', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_ORG_LEVEL', '支行', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_ORG_LEVEL', '网点', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_USER_CATE', '柜员', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_USER_CATE', '用户', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_USER_STATUS', '正常', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_USER_STATUS', '注销', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_USER_STATUS', '锁定', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_LOCK_STATUS', '正常', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_LOCK_STATUS', '登录', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_LOCK_STATUS', '非正常签退', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_LOCK_STATUS', '锁定', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('4', 'BIZ_LOCK_STATUS', '密码锁定', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_YES_OR_NO', '否', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_YES_OR_NO', '是', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_TIME_CATE', '天', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_TIME_CATE', '周', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_TIME_CATE', '月', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_TIME_CATE', '年', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_KIND_DIMEN', '数据字典', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_KIND_DIMEN', '系统参数', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_KIND_DIMEN', '标准字段', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_KIND_DIMEN', '系统资源', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('4', 'BIZ_KIND_DIMEN', '系统菜单', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_DB_TYPE', 'Oracle', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_DB_TYPE', 'DB2', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_DB_TYPE', 'SQL Server', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_DB_TYPE', 'MySQL', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('4', 'BIZ_DB_TYPE', 'Infomix', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('5', 'BIZ_DB_TYPE', 'Sybase', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'FIN_DICT_FLAG', '核心', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'FIN_DICT_FLAG', '周边', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_RIGHT_FLAG', '操作', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_RIGHT_ENABLE', '禁止', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_RIGHT_ENABLE', '可用', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_RIGHT_FLAG', '授权', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_MSG_TYPE', '站内邮件', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_MSG_TYPE', '站内消息', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_IS_READ', '未读', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_IS_READ', '已读', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_RIGHT_TYPE', '操作权限', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_RIGHT_TYPE', '权限授权', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'ENABLE', '关闭', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'ENABLE', '开启', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_AUTH_TYPE', '未授权', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_AUTH_TYPE', '已授权', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('2', 'BIZ_WINDOW_CATE', '单页模式窗口', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('3', 'BIZ_WINDOW_CATE', '非单页模式窗口', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_ALLOT_TYPE', '未分配', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_ALLOT_TYPE', '已分配', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('0', 'BIZ_PLATFORM', '应用', '1', '1', '0', '');
INSERT INTO `tsys_dict_item` VALUES ('1', 'BIZ_PLATFORM', '平台', '1', '1', '1', '');

-- ----------------------------
-- Table structure for `tsys_kind`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_kind`;
CREATE TABLE `tsys_kind` (
  `kind_code` varchar(16) NOT NULL,
  `kind_type` varchar(8) NOT NULL,
  `kind_name` varchar(32) NOT NULL,
  `parent_code` varchar(16) default NULL,
  `mnemonic` varchar(16) NOT NULL,
  `tree_idx` varchar(256) default NULL,
  `ext_flag` varchar(8) default NULL,
  `lifecycle` varchar(8) default NULL,
  `platform` varchar(8) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`kind_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_kind
-- ----------------------------
INSERT INTO `tsys_kind` VALUES ('0001', '0', '数据字典', 'bizroot', '0', '#bizroot#0001#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('0002', '1', '系统参数', 'bizroot', '1', '#bizroot#0002#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('0003', '2', '标准字段', 'bizroot', '2', '#bizroot#0003#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('0004', '3', '系统资源', 'bizroot', '3', '#bizroot#0004#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('0005', '4', '系统菜单', 'bizroot', '4', '#bizroot#0005#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('0006', '5', '子系统', 'bizroot', '5', '#bizroot#0006#', '1', '', '1', '');
INSERT INTO `tsys_kind` VALUES ('BIZFRAME', '5', '基础业务框架', '0006', '123', '#bizroot#0006#BIZFRAME#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('BIZ_DICT', '0', '基础数据字典', '0001', 'bizDict', '#bizroot#0001#BIZ_DICT#', '', '1', '1', '');
INSERT INTO `tsys_kind` VALUES ('BIZ_PARAM', '1', '基础参数', '0002', 'param', '#bizroot#0002#BIZ_PARAM#', '', '1', '1', '');

-- ----------------------------
-- Table structure for `tsys_log`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_log`;
CREATE TABLE `tsys_log` (
  `log_id` varchar(32) NOT NULL,
  `org_id` varchar(40) default NULL,
  `org_name` varchar(128) default NULL,
  `user_id` varchar(32) default NULL,
  `user_name` varchar(32) default NULL,
  `access_date` bigint(20) NOT NULL,
  `access_time` bigint(20) NOT NULL,
  `sub_trans_code` varchar(32) default NULL,
  `trans_code` varchar(32) default NULL,
  `oper_contents` varchar(1024) default NULL,
  `ip_add` varchar(64) default NULL,
  `host_name` varchar(64) default NULL,
  PRIMARY KEY  (`log_id`),
  KEY `index_access_d` (`access_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_log
-- ----------------------------
INSERT INTO `tsys_log` VALUES ('01a9d802-2fcc-4c42-ad6d-582733b', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '204717', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('0553051a-4894-4de6-9ecf-a335c27', '0_000000', '组织根', 'admin', '系统管理员', '20130709', '211047', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('06488da0-fbda-499b-8284-73d4183', '0_000000', '组织根', 'admin', '系统管理员', '20130714', '155219', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('0744684c-a92d-4f7f-aef2-281da0f', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '122322', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('09274b7d-2cb8-4838-8b6e-0b070ff', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '230004', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('0b8bb62c-f7ea-4c3e-afbb-b4f574a', '0_000000', '组织根', 'admin', '系统管理员', '20130714', '100147', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('0b944fe9-db61-4668-a40c-4afca93', '0_000000', '组织根', 'admin', '系统管理员', '20130714', '155339', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('0e308715-5614-47eb-8531-2cad43d', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '104148', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('11163da2-2a51-4251-8044-6bce3a9', '0_000000', '组织根', 'admin', '系统管理员', '20130709', '210915', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('153e617f-da19-4c57-8065-9a192ef', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '142537', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('15d867e3-ae53-48cb-a0bf-b3b7188', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '122633', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('17467c31-e461-4bd6-81aa-68ddc35', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '80939', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('180e7e5b-a5a5-45ff-aff8-530987a', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '82957', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('1862c883-3d0a-45c9-94c0-3cf7102', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '185048', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('1a0afec4-01dd-4279-b9c5-224a23b', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '124618', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('1d570d1e-dec3-415e-a3d4-79af99e', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '222240', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('2200323e-da5e-46f1-aa70-a3fa50b', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '223110', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('23237bd0-64d6-4ee1-92ec-a649343', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '221630', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('2399dcb6-edfe-455b-b303-7fc40ec', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '101922', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('24a737d9-7353-4535-b2f7-9098e55', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '143723', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('2816fb7d-55c3-46f1-9e6b-d10a6a9', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '200918', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('2a5387d3-498b-4aaf-b3f2-3206770', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '211253', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('38e33c4c-9aee-468b-96ee-4bc9673', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '153604', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('3cdc96fe-4dc9-44e0-81b7-fa30636', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '220429', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('3dac1c76-ff64-43c6-adec-0bb2669', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '143355', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('40bffd58-c0e5-4a16-9126-11156ea', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '230844', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('417789c2-aece-4828-837a-ba45dcf', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '123900', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('431487bf-8f5a-4307-914f-99aae89', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '123103', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('43dc5f6f-73a9-49cd-9f14-5092126', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '213424', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('47961ef8-8c58-4895-b13d-58368f3', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '122308', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('4caa7193-013a-45c5-b2b3-b1f1360', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '132847', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('4f58b01b-a742-48ca-b5d9-28a1dc7', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '222533', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('522f4966-622f-4782-a4f7-2e8e8ad', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '211405', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('52c7c359-f7e7-4f78-9517-4e994ee', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '214006', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('533ed02c-f9ae-4661-9e1a-0be58dd', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '214559', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('53bef688-f44a-4c58-bba7-e102490', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '132233', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('590d55a4-ca44-481f-ba02-f881e03', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '134356', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('59333cec-3e7a-4bd5-97d5-2add2b0', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '132428', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('5b75df4f-d02f-4892-9cb5-ba9fa4b', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '222429', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('602c1839-dccb-4928-8c4a-3e10014', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '85215', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('61f10bb0-e900-4458-8063-79334e8', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '202744', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('6418c2cc-5619-4661-80fa-68266d5', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '215146', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('6ac9983f-c64a-4854-a595-621ca59', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '104056', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('6d814009-6c78-450b-8658-99a3b75', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '162953', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('6db99377-e309-426d-988f-e13ec54', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '70835', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('7647f478-8acf-4f4c-965d-7dfc147', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '103759', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('795a5f6d-9e3a-4784-843a-93fbde8', '0_000000', '组织根', 'admin', '系统管理员', '20130714', '155045', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('7a65bcfc-1508-4b56-8045-21b7a10', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '123437', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('7dd28738-3b61-4111-a8a5-4bbbefe', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '222441', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('81a57ebd-6fc4-48c2-a257-1d953a9', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '124017', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('82ddd94e-6280-4e19-956b-8dd49f5', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '124914', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('8365974b-931a-4bbc-92af-ab407fc', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '144046', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('846e6b05-2613-40b4-8ae7-2d76f39', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '102618', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('85b061ae-2b8b-43b9-bd8d-fe9d168', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '155929', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('8625393f-1751-4e29-bb3d-3cf4958', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '112643', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('88378f58-b55b-4693-93be-db37d1d', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '130810', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('8edf7bef-4f80-45db-8589-50d2497', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '221958', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('91991376-d35b-409d-be48-8a66972', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '122908', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('91cee1ef-59c7-4d1b-bacc-e0d68c2', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '73047', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('92b04eb6-93a4-47c9-9b21-e4bdcdf', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '162429', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('99a40b33-527c-4537-9477-f4f025a', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '1616', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('9f822983-18df-4fa5-9b1c-8e81251', '0_000000', '组织根', 'system', '系统用户', '20130709', '213645', 'bizSignIn2', 'bizSign', 'ID为[system]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('a7a3e74a-5b50-4b8e-82b6-b4806b2', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '160549', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('a7c5905c-c442-448b-9321-47006b3', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '222103', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ab0def04-f5ab-4dd7-a5bd-fc7ece8', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '215232', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ac547639-9726-4ffd-a817-d90aafe', '0_000000', '组织根', '111111', '授权用户', '20130709', '211205', 'bizSignIn2', 'bizSign', 'ID为[111111]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ac6fccc7-60f1-405b-af92-4e0e11c', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '210040', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('b0ba6de5-0cb3-444f-a7fe-ffb0bbf', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '104354', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('bbcc0176-4463-401e-a34f-f087e75', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '81824', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('bd815d02-fd7e-4823-b095-a7c8a92', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '220708', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('bf1d1503-cea8-4c19-a493-b6be04d', '0_000000', '组织根', 'admin', '系统管理员', '20130714', '155138', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('c00d725b-dd1b-482a-a6a3-b755b9d', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '223542', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('c1f80889-8420-4fee-9aee-bfff875', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '234107', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('c2ec55d0-99a2-4655-9d23-8c158a3', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '222102', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('c743bac2-8e57-4ee6-a190-8bf0eff', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '130557', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ca1b3343-8886-417d-9a5e-984b9c7', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '101016', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ca377ac0-0e1f-40a5-a4b5-c45b50c', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '936', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('cb1366cb-7d70-4b98-84c3-46ded1b', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '81620', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ce9c4036-b405-47bf-96ce-ebc45d0', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '161313', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('d227b0c8-2c4a-4396-bf98-73ef33b', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '112037', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('d3073338-524c-4ca1-b9a6-fc8e2e9', '0_jingl_a', '经理A', '001', '张三', '20130710', '135124', 'bizSignOut', 'bizSign', 'ID为[001]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('e25684a3-e21f-4518-86a8-96ed393', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '105309', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('e758e985-c3b8-42f6-b45b-23e67f9', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '74720', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('e80f543d-136e-4ed2-8489-fa05a54', '0_jingl_a', '经理A', '001', '张三', '20130710', '135015', 'bizSignIn2', 'bizSign', 'ID为[001]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('e8a05787-3517-47ef-870b-05146b7', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '211530', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ed1ae592-57c6-4f17-ab4d-5681ca9', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '230943', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('ef684585-955a-434f-a314-f51a571', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '223529', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f23d8a94-f4cb-4fb3-8ee3-7a3a32d', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '123740', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f4838d42-3d48-41dd-a674-b44166c', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '132244', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f5c93d77-c286-4b3a-bf8c-3e4432a', '0_000000', '组织根', 'admin', '系统管理员', '20130710', '124358', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f6fab538-f6bb-43e6-8923-7a6b241', '0_000000', '组织根', 'admin', '系统管理员', '20130712', '213611', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f93ee40a-7a58-40c0-a738-8b9dd07', '0_000000', '组织根', 'admin', '系统管理员', '20130716', '116', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('f9de35ec-49ee-4eba-b7b2-b41b36c', '0_000000', '组织根', 'admin', '系统管理员', '20130713', '93137', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('fa986e50-6377-41d2-a147-9790a3f', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '232604', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('fc3aeac5-330f-4841-841c-d717331', '0_000000', '组织根', 'admin', '系统管理员', '20130715', '162115', 'bizSignIn2', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1登陆了系统', '127.0.0.1', '');
INSERT INTO `tsys_log` VALUES ('fe0f5a64-4fa8-440f-bee8-48748c5', '0_000000', '组织根', 'admin', '系统管理员', '20130717', '223040', 'bizSignOut', 'bizSign', 'ID为[admin]的用户在IP为：127.0.0.1签出了系统', '127.0.0.1', '');

-- ----------------------------
-- Table structure for `tsys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_menu`;
CREATE TABLE `tsys_menu` (
  `menu_code` varchar(32) NOT NULL,
  `kind_code` varchar(16) NOT NULL,
  `trans_code` varchar(32) default NULL,
  `sub_trans_code` varchar(32) default NULL,
  `menu_name` varchar(32) NOT NULL,
  `menu_arg` varchar(256) default NULL,
  `menu_icon` varchar(256) default NULL,
  `menu_url` varchar(256) default NULL,
  `window_type` varchar(8) default NULL,
  `window_model` varchar(8) default NULL,
  `tip` varchar(256) default NULL,
  `hot_key` varchar(8) default NULL,
  `parent_code` varchar(32) default NULL,
  `order_no` bigint(20) default NULL,
  `open_flag` varchar(8) default NULL,
  `tree_idx` varchar(256) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`menu_code`,`kind_code`),
  KEY `FK_MENU_SOURCE` (`trans_code`,`sub_trans_code`),
  KEY `FK_SYSMENU_KIND` (`kind_code`),
  KEY `INDX_BIZ_MENU_CODE` (`menu_code`),
  KEY `INDX_BIZ_MENU_NAME` (`menu_name`),
  KEY `INDX_BIZ_MENU_PCODE` (`parent_code`),
  CONSTRAINT `FK_MENU_SOURCE` FOREIGN KEY (`trans_code`, `sub_trans_code`) REFERENCES `tsys_subtrans` (`trans_code`, `sub_trans_code`),
  CONSTRAINT `FK_SYSMENU_KIND` FOREIGN KEY (`kind_code`) REFERENCES `tsys_kind` (`kind_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_menu
-- ----------------------------
INSERT INTO `tsys_menu` VALUES ('bizCustomer', 'BIZFRAME', 'bizCustomer', 'bizCustomer', '客户信息', '', 'bizframe/images/bizSetRole.png', 'crm/customer/customerManage.mw', '3', '', '', '', 'bizCustomerManager', '0', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#bizCustomer#', '');
INSERT INTO `tsys_menu` VALUES ('bizCustomerManager', 'BIZFRAME', 'bizCustomerManager', 'bizCustomerManager', '客户管理', '', 'bizframe/images/bizUserManager.png', '', '0', '', '', '', 'bizMenu', '0', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#', '');
INSERT INTO `tsys_menu` VALUES ('bizEmailInbox', 'BIZFRAME', 'bizEmailInbox', 'bizEmailInbox', '消息收件箱', '', 'bizframe/images/bizEmailInbox.png', 'bizframe/message/emailInbox.mw', '3', null, '', '', 'bizMsgManager', '0', '', '#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailInbox#', '');
INSERT INTO `tsys_menu` VALUES ('bizEmailOutbox', 'BIZFRAME', 'bizEmailOutbox', 'bizEmailOutbox', '消息发件箱', '', 'bizframe/images/bizEmailOutbox.png', 'bizframe/message/emailOutbox.mw', '3', null, '', '', 'bizMsgManager', '1', '', '#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailOutbox#', '');
INSERT INTO `tsys_menu` VALUES ('BIZFRAME', 'BIZFRAME', 'BIZFRAME', 'BIZFRAME', '基础业务框架', '', 'bizframe/images/BIZFRAME.png', '', '0', null, '', '', 'bizroot', '0', '', '#bizroot#BIZFRAME#', '');
INSERT INTO `tsys_menu` VALUES ('bizLinkMan', 'BIZFRAME', 'bizLinkMan', 'bizLinkMan', '联系人信息', '', 'bizframe/images/bizSetRole.png', 'crm/customer/orgManage.mw', '3', '', '', '', 'bizCustomerManager', '1', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#bizLinkMan#', '');
INSERT INTO `tsys_menu` VALUES ('bizMainCustomer', 'BIZFRAME', 'bizMainCustomer', 'bizMainCustomer', '潜在客户', '', 'bizframe/images/bizSetRole.png', 'crm/customer/customerManage.mw', '3', '', '', '', 'bizCustomerManager', '2', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#bizMainCustomer#', '');
INSERT INTO `tsys_menu` VALUES ('bizMenu', 'BIZFRAME', 'bizMenu', 'bizMenu', '系统菜单', '', 'bizframe/images/bizMenu.png', '', '0', null, '', '', 'BIZFRAME', '0', '', '#bizroot#BIZFRAME#bizMenu#', '');
INSERT INTO `tsys_menu` VALUES ('bizMonitorMenu', 'BIZFRAME', 'bizMonitorMenu', 'bizMonitorMenu', '平台管理', '', 'bizframe/images/bizMonitorMenu.png', '', '0', null, '', '', 'bizMenu', '1', '', '#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#', '');
INSERT INTO `tsys_menu` VALUES ('bizMsgManager', 'BIZFRAME', 'bizMsgManager', 'bizMsgManager', '消息管理', '', 'bizframe/images/bizEmailManager.png', '', '0', null, '', '', 'bizMenu', '2', '', '#bizroot#BIZFRAME#bizMenu#bizMsgManager#', '');
INSERT INTO `tsys_menu` VALUES ('bizOnlineMonitor', 'BIZFRAME', 'bizOnlineMonitor', 'bizOnlineMonitor', '在线监控', '', 'bizframe/images/bizOnlineMonitor.png', '', '0', null, '', '', 'bizMonitorMenu', '0', '', '#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#', '');
INSERT INTO `tsys_menu` VALUES ('bizOrgSet', 'BIZFRAME', 'bizOrgSet', 'bizOrgSet', '组织机构设置', '', 'bizframe/images/bizOrgSet.png', 'bizframe/authority/orgManage.mw', '3', null, '', '', 'bizUserManager', '0', '', '#bizroot#BIZFRAME#bizMenu#bizUserManager#bizOrgSet#', '');
INSERT INTO `tsys_menu` VALUES ('bizPageCacheSet', 'BIZFRAME', 'bizPageCacheSet', 'bizPageCacheSet', '页面缓存设置', '', 'bizframe/images/bizPageCacheSet.png', 'bizframe/cacheRefresh/refreshPage.mw', '0', null, '', '', 'bizSysManager', '0', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizPageCacheSet#', '');
INSERT INTO `tsys_menu` VALUES ('bizPosSet', 'BIZFRAME', 'bizPosSet', 'bizPosSet', '岗位设置', '', 'bizframe/images/bizPosSet.png', 'bizframe/authority/positionManage.mw', '3', null, '', '', 'bizUserManager', '1', '', '#bizroot#BIZFRAME#bizMenu#bizUserManager#bizPosSet#', '');
INSERT INTO `tsys_menu` VALUES ('bizSallChange', 'BIZFRAME', 'bizSallChange', 'bizSallChange', '销售目标', '', 'bizframe/images/bizSetRole.png', 'crm/customer/customerManage.mw', '3', '', '', '', 'bizCustomerManager', '3', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#bizSallChange#', '');
INSERT INTO `tsys_menu` VALUES ('bizSallPlan', 'BIZFRAME', 'bizSallPlan', 'bizSallPlan', '营销计划', '', 'bizframe/images/bizSetRole.png', 'crm/customer/customerManage.mw', '3', '', '', '', 'bizCustomerManager', '4', '', '#bizroot#BIZFRAME#bizMenu#bizCustomerManager#bizSallPlan#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetCache', 'BIZFRAME', 'bizSetCache', 'bizSetCache', '系统缓存设置', '', 'bizframe/images/bizSetCache.png', 'bizframe/common/cacheFresh.mw', '3', null, '', '', 'bizSysManager', '1', '1', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetCache#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetDict', 'BIZFRAME', 'bizSetDict', 'bizSetDict', '数据字典设置', '', 'bizframe/images/bizSetDict.png', 'bizframe/dictionary/dictManage.mw', '3', null, '', '', 'bizSysManager', '2', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetDict#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetKind', 'BIZFRAME', 'bizSetKind', 'bizSetKind', '系统类别设置', '', 'bizframe/images/bizSetKind.png', 'bizframe/kind/kindManagement.mw', '3', null, '', '', 'bizSysManager', '3', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetKind#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetLog', 'BIZFRAME', 'bizSetLog', 'bizSetLog', '业务日志设置', '', 'bizframe/images/bizSetLog.png', 'bizframe/businessLog/loggerManage.mw', '3', null, '', '', 'bizSysManager', '4', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetLog#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetMenu', 'BIZFRAME', 'bizSetMenu', 'bizSetMenu', '系统菜单设置', '', 'bizframe/images/bizSetMenu.png', 'bizframe/authority/menuManage.mw', '3', null, '', '', 'bizSysManager', '5', '1', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetMenu#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetParam', 'BIZFRAME', 'bizSetParam', 'bizSetParam', '系统参数设置', '', 'bizframe/images/bizSetParam.png', 'bizframe/parameter/sysParameter.mw', '3', null, '', '', 'bizSysManager', '6', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetParam#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetRole', 'BIZFRAME', 'bizSetRole', 'bizSetRole', '角色设置', '', 'bizframe/images/bizSetRole.png', 'bizframe/authority/roleManagement.mw', '3', null, '', '', 'bizUserManager', '2', '', '#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetRole#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetTrans', 'BIZFRAME', 'bizSetTrans', 'bizSetTrans', '系统交易设置', '', 'bizframe/images/bizSetTrans.png', 'bizframe/authority/sysTransManage.mw', '3', null, '', '', 'bizSysManager', '7', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetTrans#', '');
INSERT INTO `tsys_menu` VALUES ('bizSetUser', 'BIZFRAME', 'bizSetUser', 'bizSetUser', '用户设置', '', 'bizframe/images/bizSetUser.png', 'bizframe/authority/userManage.mw', '3', null, '', '', 'bizUserManager', '3', '', '#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetUser#', '');
INSERT INTO `tsys_menu` VALUES ('bizSysManager', 'BIZFRAME', 'bizSysManager', 'bizSysManager', '系统管理', '', 'bizframe/images/bizSysManager.png', '', '0', null, '', '', 'bizMenu', '3', '', '#bizroot#BIZFRAME#bizMenu#bizSysManager#', '');
INSERT INTO `tsys_menu` VALUES ('bizSysStatus', 'BIZFRAME', 'bizSysStatus', 'bizSysStatus', '系统状态', '', 'bizframe/images/bizSysStatus.png', 'monitor/sysStatus/sysMain.mw', '3', null, '', '', 'bizOnlineMonitor', '0', '', '#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#', '');
INSERT INTO `tsys_menu` VALUES ('bizUserManager', 'BIZFRAME', 'bizUserManager', 'bizUserManager', '用户管理', '', 'bizframe/images/bizUserManager.png', '', '0', null, '', '', 'bizMenu', '4', '', '#bizroot#BIZFRAME#bizMenu#bizUserManager#', '');

-- ----------------------------
-- Table structure for `tsys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_organization`;
CREATE TABLE `tsys_organization` (
  `org_id` varchar(40) NOT NULL,
  `dimension` varchar(8) default NULL,
  `org_code` varchar(32) default NULL,
  `org_name` varchar(32) default NULL,
  `parent_id` varchar(40) default NULL,
  `manage_id` varchar(40) default NULL,
  `position_code` varchar(64) default NULL,
  `org_cate` varchar(8) default NULL,
  `org_level` varchar(8) default NULL,
  `org_order` int(11) default NULL,
  `org_path` varchar(1024) default NULL,
  `sso_org_code` varchar(32) default NULL,
  `sso_parent_code` varchar(32) default NULL,
  `ext_id` varchar(128) default NULL,
  `remark` varchar(256) default NULL,
  PRIMARY KEY  (`org_id`),
  KEY `INDX_BIZ_ORG_CODE` (`org_code`),
  KEY `INDX_BIZ_ORG_DIMENSION` (`dimension`),
  KEY `INDX_BIZ_ORG_NAME` (`org_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_organization
-- ----------------------------
INSERT INTO `tsys_organization` VALUES ('0_000000', '0', '000000', '组织根', 'bizroot', 'bizroot', null, '1', '1', '0', '#bizroot#0_000000#', null, null, null, null);
INSERT INTO `tsys_organization` VALUES ('0_CEO', '0', 'CEO', 'CEO（公司总裁）', '0_company', '0_company', '0_CEOhead', '1', '0', '0', '#bizroot#0_000000#0_company#0_CEO#', '', '', '', '');
INSERT INTO `tsys_organization` VALUES ('0_company', '0', 'company', '某某公司', '0_000000', '0_000000', '0_companyhead', '1', '0', '0', '#bizroot#0_000000#0_company#', '', '', '', '');
INSERT INTO `tsys_organization` VALUES ('0_DEO', '0', 'DEO', '公司副总裁', '0_CEO', '0_company', '0_DEOhead', '1', '0', '0', '#bizroot#0_000000#0_company#0_CEO#0_DEO#', '', '', '', '');
INSERT INTO `tsys_organization` VALUES ('0_jingl_a', '0', 'jingl_a', '经理A', '0_CEO', '0_DEO', '0_jingl_ahead', '1', '0', '1', '#bizroot#0_000000#0_company#0_CEO#0_jingl_a#', '', '', '', '');

-- ----------------------------
-- Table structure for `tsys_org_user`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_org_user`;
CREATE TABLE `tsys_org_user` (
  `user_id` varchar(32) NOT NULL,
  `org_id` varchar(40) NOT NULL,
  PRIMARY KEY  (`user_id`,`org_id`),
  KEY `INDX_BIZ_ORGUSER_ORG_ID` (`org_id`),
  KEY `INDX_BIZ_USER_ID` (`user_id`),
  CONSTRAINT `FK_USERID_ORGUSER` FOREIGN KEY (`user_id`) REFERENCES `tsys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_org_user
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_parameter`;
CREATE TABLE `tsys_parameter` (
  `param_code` varchar(64) NOT NULL,
  `rel_org` varchar(40) NOT NULL,
  `kind_code` varchar(16) NOT NULL,
  `param_name` varchar(32) NOT NULL,
  `param_value` varchar(1024) NOT NULL,
  `ext_flag` varchar(8) default NULL,
  `lifecycle` varchar(8) default NULL,
  `platform` varchar(8) default NULL,
  `param_desc` varchar(256) default NULL,
  `param_regex` varchar(64) default NULL,
  PRIMARY KEY  (`param_code`,`rel_org`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_parameter
-- ----------------------------
INSERT INTO `tsys_parameter` VALUES ('cacheRefeshInterval', '0_000000', 'BIZ_PARAM', '缓存刷新间隔', '60', '', '', '1', '数值，代表缓存刷新的时间间隔(以秒为单位，默认60秒)', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('commonPositionCodeSuffix', '0_000000', 'BIZ_PARAM', '普通岗位编号后缀', 'common', '', '', '1', '字符串，代表新增组织的普通岗位编号后缀', null);
INSERT INTO `tsys_parameter` VALUES ('commonPositionNameSuffix', '0_000000', 'BIZ_PARAM', '普通岗位名称后缀', '普通岗', '', '', '1', '字符串，代表新增组织的普通岗位名称后缀', null);
INSERT INTO `tsys_parameter` VALUES ('defaultLogo', '0_000000', 'BIZ_PARAM', '默认主页LOGO', 'logo.png', '', '', '1', '默认主页LOGO', null);
INSERT INTO `tsys_parameter` VALUES ('defaultUserPassword', '0_000000', 'BIZ_PARAM', '默认密码', '111111', '', '', '1', '系统用户默认密码', null);
INSERT INTO `tsys_parameter` VALUES ('default_menu_depth', '0_000000', 'BIZ_PARAM', '默认主页菜单深度', '1', '', '', '1', '数值，代表默认主页菜单深度', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('desktopBg', '0_000000', 'BIZ_PARAM', '桌面背景', 'desktopBg.gif', '', '', '1', '桌面背景', null);
INSERT INTO `tsys_parameter` VALUES ('desktopLogo', '0_000000', 'BIZ_PARAM', '桌面LOGO', 'desktopLogo.gif', '', '', '1', '桌面LOGO', null);
INSERT INTO `tsys_parameter` VALUES ('desktop_navigation_menu_depth', '0_000000', 'BIZ_PARAM', '桌面顶置导航菜单深度', '1', '', '', '1', '数值，代表桌面顶置导航菜单深度', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('desktop_start_menu_depth', '0_000000', 'BIZ_PARAM', '桌面开始菜单深度', '1', '', '', '1', '数值，代表桌面开始菜单深度', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('isShowAdminPwd', '0_000000', 'BIZ_PARAM', '是否输出密码重置密码', '3', '1', '', '', '布尔，代表重置密码后是否输出密码。若值为true则表示重置密码后需显示密码，false表示不显示', '^true|false$');
INSERT INTO `tsys_parameter` VALUES ('lockScreenInterval', '0_000000', 'BIZ_PARAM', '锁屏时间隔', '10', '', '', '1', '数值，代表用户无活动锁屏时间隔(以分钟为单位，默认10分钟)', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('loginBg', '0_000000', 'BIZ_PARAM', '登陆页面背景', 'login_logo.png', '', '', '1', '登陆页面背景', null);
INSERT INTO `tsys_parameter` VALUES ('login_has_validateCode', '0_000000', 'BIZ_PARAM', '登陆需验证码', 'false', '', '', '1', '布尔值，代表登陆需验证码,true代表需要false代表不需要', '^true|false$');
INSERT INTO `tsys_parameter` VALUES ('logoHeight', '0_000000', 'BIZ_PARAM', 'logo高度', '70', '', '', '1', '数值，代表页面logo区域高度，默认值70', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('managerPositionCodeSuffix', '0_000000', 'BIZ_PARAM', '负责岗位编号后缀', 'head', '', '', '1', '字符串，代表新增组织的负责岗位编号后缀', null);
INSERT INTO `tsys_parameter` VALUES ('managerPositionNameSuffix', '0_000000', 'BIZ_PARAM', '负责岗位名称后缀', '主管岗', '', '', '1', '字符串，代表新增组织的负责岗位名称后缀', null);
INSERT INTO `tsys_parameter` VALUES ('maxWrongPass', '0_000000', 'BIZ_PARAM', '最大允许密码错误次数', '10', '', '', '1', '数值，表示大允许密码错误次数', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('menuLoadModel', '0_000000', 'BIZ_PARAM', '首页菜单加载模式', 'tree', '', '', '1', '字符串，代表首页菜单加载模式，目前只支持accordion和tree两种模式', '^accordion|tree$');
INSERT INTO `tsys_parameter` VALUES ('msgPollInterval', '0_000000', 'BIZ_PARAM', '消息轮询间隔', '3', '', '', '1', '数值，代表页面消息轮询间隔(以分钟为单位，默认3分钟)', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('openMaxTabNum', '0_000000', 'BIZ_PARAM', '最大菜单数目', '6', '', '', '1', '数值，代表主页打开的最大菜单数目', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('passRepeatCycle', '0_000000', 'BIZ_PARAM', '密码重复周期', '3', '1', '', '', '数值，代表用户密码重复周期(默认值0)。若值为4则表示修改密码时不能和之前的四次重复，0表示随意重复', '^[0-7]d*$');
INSERT INTO `tsys_parameter` VALUES ('passUnit', '0_000000', 'BIZ_PARAM', '密码有效时间单位', '2', '', '', '1', '0代表天，1代表周，2代表月，3代表年', '^[0-3]$');
INSERT INTO `tsys_parameter` VALUES ('passValidity', '0_000000', 'BIZ_PARAM', '密码有效周期', '2', '', '', '1', '数值，表示指定数量个密码有效单位的时间长度', '^[1-9]d*$');
INSERT INTO `tsys_parameter` VALUES ('shieldBcakSpace', '0_000000', 'BIZ_PARAM', '屏蔽鼠标右键', 'true', '', '', '1', '布尔值，页面是否需屏蔽鼠标右键,true代表屏蔽false代表不屏蔽', '^true|false$');
INSERT INTO `tsys_parameter` VALUES ('shieldF5', '0_000000', 'BIZ_PARAM', '屏蔽F5按钮', 'true', '1', '', '', '布尔值，页面是否需屏蔽F5按钮,true代表屏蔽false代表不屏蔽', '^true|false$');
INSERT INTO `tsys_parameter` VALUES ('singleTab', '0_000000', 'BIZ_PARAM', '单一打开菜单', 'true', '', '', '1', '布尔值，页面是否单一打开菜单,true代表单一打开 false代表多窗口打开', '^true|false$');
INSERT INTO `tsys_parameter` VALUES ('systemHelpUrl', '0_000000', 'BIZ_PARAM', '系统帮助手册地址', 'bizframe/jsp/help/bizframe-help.html', '', '', '1', '字符串，代表系统帮助手册地址', null);

-- ----------------------------
-- Table structure for `tsys_position`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_position`;
CREATE TABLE `tsys_position` (
  `position_code` varchar(64) NOT NULL,
  `position_name` varchar(64) NOT NULL,
  `parent_code` varchar(64) default NULL,
  `org_id` varchar(40) default NULL,
  `role_code` varchar(16) default NULL,
  `position_path` varchar(1024) default NULL,
  `remark` varchar(256) default NULL,
  `ext_field_1` varchar(256) default NULL,
  `ext_field_2` varchar(256) default NULL,
  `ext_field_3` varchar(256) default NULL,
  PRIMARY KEY  (`position_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_position
-- ----------------------------
INSERT INTO `tsys_position` VALUES ('0_000000common', '组织根普通岗', '0_000000head', '0_000000', '', '#bizroot#0_000000head#0_000000common#', '组织根普通岗', null, null, null);
INSERT INTO `tsys_position` VALUES ('0_000000head', '组织根主管', 'bizroot', '0_000000', '', '#bizroot#0_000000head#', '组织根主管', null, null, null);
INSERT INTO `tsys_position` VALUES ('0_CEOcommon', 'CEO（公司总裁）普通岗', '0_CEOhead', '0_CEO', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_CEOhead#0_CEOcommon#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_CEOhead', 'CEO（公司总裁）主管岗', '0_companyhead', '0_CEO', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_CEOhead#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_companycommon', '某某公司普通岗', '0_companyhead', '0_company', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_companycommon#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_companyhead', '某某公司主管岗', '0_000000common', '0_company', '', '#bizroot#0_000000head#0_000000common#0_companyhead#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_DEOcommon', '公司副总裁普通岗', '0_DEOhead', '0_DEO', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_DEOhead#0_DEOcommon#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_DEOhead', '公司副总裁主管岗', '0_companyhead', '0_DEO', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_DEOhead#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_jingl_acommon', '经理A普通岗', '0_jingl_ahead', '0_jingl_a', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_CEOhead#0_jingl_ahead#0_jingl_acommon#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('0_jingl_ahead', '经理A主管岗', '0_CEOhead', '0_jingl_a', '', '#bizroot#0_000000head#0_000000common#0_companyhead#0_CEOhead#0_jingl_ahead#', '', '', '', '');
INSERT INTO `tsys_position` VALUES ('5555', '客服', '', '0_jingl_a', '', '#bizroot#5555#', '', '', '', '');

-- ----------------------------
-- Table structure for `tsys_pos_user`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_pos_user`;
CREATE TABLE `tsys_pos_user` (
  `position_code` varchar(64) NOT NULL,
  `user_id` varchar(16) NOT NULL,
  PRIMARY KEY  (`user_id`,`position_code`),
  KEY `INDX_BIZ_POSUSER_POS_CODE` (`position_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_pos_user
-- ----------------------------
INSERT INTO `tsys_pos_user` VALUES ('0_jingl_acommon', '001');
INSERT INTO `tsys_pos_user` VALUES ('5555', '001');

-- ----------------------------
-- Table structure for `tsys_pro_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_pro_keyword`;
CREATE TABLE `tsys_pro_keyword` (
  `pro_keyword` varchar(16) NOT NULL,
  `std_type_code` varchar(16) default NULL,
  `pro_type` varchar(8) NOT NULL,
  `def_flag` varchar(8) default NULL,
  PRIMARY KEY  (`pro_keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_pro_keyword
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_role`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role`;
CREATE TABLE `tsys_role` (
  `role_code` varchar(16) NOT NULL,
  `role_name` varchar(64) default NULL,
  `creator` varchar(32) default NULL,
  `remark` varchar(256) default NULL,
  `parent_id` varchar(16) default NULL,
  `role_path` varchar(256) default NULL,
  PRIMARY KEY  (`role_code`),
  KEY `INDX_BIZ_ROLE_CREATOR` (`creator`),
  KEY `INDX_BIZ_ROLE_NAME` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_role
-- ----------------------------
INSERT INTO `tsys_role` VALUES ('003', '客服', 'admin', '客服所用', '', '');
INSERT INTO `tsys_role` VALUES ('admin', '系统管理员角色', 'admin', null, 'bizroot', '#bizroot#admin#');

-- ----------------------------
-- Table structure for `tsys_role_right`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role_right`;
CREATE TABLE `tsys_role_right` (
  `trans_code` varchar(32) NOT NULL,
  `sub_trans_code` varchar(32) NOT NULL,
  `role_code` varchar(16) NOT NULL,
  `create_by` varchar(32) default NULL,
  `create_date` bigint(20) default NULL,
  `begin_date` bigint(20) NOT NULL,
  `end_date` bigint(20) NOT NULL,
  `right_flag` varchar(8) NOT NULL,
  `right_enable` varchar(8) default NULL,
  PRIMARY KEY  (`trans_code`,`sub_trans_code`,`role_code`,`begin_date`,`end_date`,`right_flag`),
  KEY `FK_RIGHT_ROLE` (`role_code`),
  KEY `INDX_BIZ_ROLERIGHT_FLAG` (`right_flag`),
  KEY `INDX_BIZ_ROLERIGHT_TRANS_CODE` (`trans_code`,`sub_trans_code`),
  CONSTRAINT `FK_RIGHT_ROLE` FOREIGN KEY (`role_code`) REFERENCES `tsys_role` (`role_code`),
  CONSTRAINT `FK_RRIGHT_TRANS` FOREIGN KEY (`trans_code`, `sub_trans_code`) REFERENCES `tsys_subtrans` (`trans_code`, `sub_trans_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_role_right
-- ----------------------------
INSERT INTO `tsys_role_right` VALUES ('bizCustomer', 'bizCustomer', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizCustomer', 'bizCustomer', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizCustomer', 'bizCustomer', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizCustomerManager', 'bizCustomerManager', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizCustomerManager', 'bizCustomerManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizCustomerManager', 'bizCustomerManager', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInbox', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInbox', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInbox', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInboxFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInboxFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailInboxFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailPoll', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailPoll', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailPoll', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailView', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailView', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizEmailView', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgDel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgSend', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgSend', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailInbox', 'bizMsgSend', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutbox', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutbox', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutbox', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('BIZFRAME', 'BIZFRAME', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('BIZFRAME', 'BIZFRAME', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('BIZFRAME', 'BIZFRAME', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizLinkMan', 'bizLinkMan', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizLinkMan', 'bizLinkMan', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizLinkMan', 'bizLinkMan', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizMainCustomer', 'bizMainCustomer', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMainCustomer', 'bizMainCustomer', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMainCustomer', 'bizMainCustomer', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizMenu', 'bizMenu', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMenu', 'bizMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMenu', 'bizMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizMonitorMenu', 'bizMonitorMenu', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMonitorMenu', 'bizMonitorMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMonitorMenu', 'bizMonitorMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizMsgManager', 'bizMsgManager', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMsgManager', 'bizMsgManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizMsgManager', 'bizMsgManager', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAllot', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgCancel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgCancel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgCancel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDownload', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindAllot', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindFromCache', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindFromCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgFindFromCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSort', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSort', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizOrgSet', 'bizOrgSort', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'bizPageCacheSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'bizPageCacheSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'bizPageCacheSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'pageCacheSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'pageCacheSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPageCacheSet', 'pageCacheSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAllot', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosCancel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosCancel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosCancel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDownload', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindAllot', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindFromCache', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindFromCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosFindFromCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSelector', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSelector', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSelector', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizPosSet', 'bizPosSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallChange', 'bizSallChange', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallChange', 'bizSallChange', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallChange', 'bizSallChange', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallPlan', 'bizSallPlan', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallPlan', 'bizSallPlan', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSallPlan', 'bizSallPlan', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCacheFresh', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCacheFresh', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCacheFresh', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCachesFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCachesFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizCachesFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizSetCache', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizSetCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetCache', 'bizSetCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDownload', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictEntryFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizDictItemFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizSetDict', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizSetDict', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetDict', 'bizSetDict', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindDelete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetKind', 'bizSetKindFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLog', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLog', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLog', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStart', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStart', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStart', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStop', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStop', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogStop', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogSubTransFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogSubTransFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetLog', 'bizSetLogSubTransFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuDelete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuRolesFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuRolesFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuRolesFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSort', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSort', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSort', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSQL', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSQL', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSQL', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranDete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranDete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranDete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuSubtranFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuUsersFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuUsersFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizMenuUsersFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleHasRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleHasRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleHasRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleNoRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleNoRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizRoleNoRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizSetMenu', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizSetMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizSetMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserHasRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserHasRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserHasRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserNoRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserNoRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetMenu', 'bizUserNoRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParam', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParam', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParam', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamDlt', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamDlt', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamDlt', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetParam', 'bizSetParamFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleRightSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleRightSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleRightSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleSelector', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleSelector', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizRoleSelector', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRole', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleARight', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleARight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleARight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleDelete', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleEdit', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleORight', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleORight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'bizSetRoleORight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleMenuRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleMenuRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleMenuRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'rolePublicRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'rolePublicRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'rolePublicRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleRightView', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleRightView', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleRightView', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserDel', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetRole', 'roleUserFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizSetTrans', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizSetTrans', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizSetTrans', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransDownload', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransExport', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransExport', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransExport', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransModify', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransModify', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransModify', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransRemove', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransRemove', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetTrans', 'bizTransRemove', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAllotRole', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAllotRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAllotRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoBranch', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoOrg', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoPos', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizAssoPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindBranch', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindOrg', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindPos', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizBindPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizCancelUR', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizCancelUR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizCancelUR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizGiveUR', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizGiveUR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizGiveUR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizResetPass', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizResetPass', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizResetPass', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUser', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUser', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUser', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUserFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUserFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizSetUserFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizToAllotRole', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizToAllotRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizToAllotRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindBranch', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindOrg', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindPos', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUnBindPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserActivate', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserActivate', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserActivate', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserAdd', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserGetUI', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserGetUI', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserGetUI', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserLock', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserLock', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserLock', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserModfiy', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserModfiy', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserModfiy', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserOpAuth', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserOpAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserOpAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRemove', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRemove', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRemove', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightAuth', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightSet', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserRightSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserUnLock', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserUnLock', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'bizUserUnLock', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'onlineCount', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'onlineCount', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'onlineCount', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userMenuRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userMenuRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userMenuRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userPublicRightFind', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userPublicRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'userPublicRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserOrg', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserPos', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRight', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRole', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSetUser', 'viewUserRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysManager', 'bizSysManager', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysManager', 'bizSysManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysManager', 'bizSysManager', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCacheStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCacheStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCacheStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCEPStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCEPStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizCEPStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizDBStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizDBStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizDBStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizKernelStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizKernelStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizKernelStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysMain', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysMain', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysMain', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizSysStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizUIStatus', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizUIStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'bizUIStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'FindMonitorInfo', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'FindMonitorInfo', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'FindMonitorInfo', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'pauseBizService', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'pauseBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'pauseBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'queryBizService', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'queryBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'queryBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'restartBizService', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'restartBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'restartBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StartMonitorT', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StartMonitorT', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StartMonitorT', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StopMonitor', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StopMonitor', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizSysStatus', 'StopMonitor', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_role_right` VALUES ('bizUserManager', 'bizUserManager', '003', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizUserManager', 'bizUserManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_role_right` VALUES ('bizUserManager', 'bizUserManager', 'admin', 'admin', '0', '0', '0', '2', null);

-- ----------------------------
-- Table structure for `tsys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role_user`;
CREATE TABLE `tsys_role_user` (
  `user_code` varchar(32) NOT NULL,
  `role_code` varchar(16) NOT NULL,
  `right_flag` varchar(8) NOT NULL,
  PRIMARY KEY  (`user_code`,`role_code`,`right_flag`),
  KEY `FK_ROLEUSER_ROLE` (`role_code`),
  KEY `INDX_BIZ_ROLEUSER_FLAG` (`right_flag`),
  KEY `INDX_BIZ_ROLEUSER_RU` (`user_code`),
  CONSTRAINT `FK_ROLEUSER_ROLE` FOREIGN KEY (`role_code`) REFERENCES `tsys_role` (`role_code`),
  CONSTRAINT `FK_ROLEUSER_USER` FOREIGN KEY (`user_code`) REFERENCES `tsys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_role_user
-- ----------------------------
INSERT INTO `tsys_role_user` VALUES ('001', '003', '1');
INSERT INTO `tsys_role_user` VALUES ('admin', 'admin', '1');
INSERT INTO `tsys_role_user` VALUES ('admin', 'admin', '2');

-- ----------------------------
-- Table structure for `tsys_std_field`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_std_field`;
CREATE TABLE `tsys_std_field` (
  `field_code` varchar(16) NOT NULL,
  `rel_org` varchar(16) default NULL,
  `kind_code` varchar(16) NOT NULL,
  `biz_type_code` varchar(16) default NULL,
  `def_value_code` varchar(16) default NULL,
  `field_name` varchar(32) default NULL,
  `disp_ctrl` varchar(8) default NULL,
  `ctrl_attr` varchar(256) default NULL,
  `ctrl_event` varchar(256) default NULL,
  `null_flag` varchar(8) default NULL,
  `disp_flag` varchar(8) default NULL,
  `read_flag` varchar(8) default NULL,
  `remark` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_std_field
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_std_type`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_std_type`;
CREATE TABLE `tsys_std_type` (
  `std_type_code` varchar(16) NOT NULL,
  `std_type_name` varchar(32) default NULL,
  PRIMARY KEY  (`std_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_std_type
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_subtrans`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_subtrans`;
CREATE TABLE `tsys_subtrans` (
  `trans_code` varchar(32) NOT NULL,
  `sub_trans_code` varchar(32) NOT NULL,
  `sub_trans_name` varchar(256) default NULL,
  `rel_serv` varchar(256) default NULL,
  `rel_url` varchar(256) default NULL,
  `ctrl_flag` varchar(8) default NULL,
  `login_flag` varchar(8) default NULL,
  `remark` varchar(256) default NULL,
  `ext_field_1` varchar(256) default NULL,
  `ext_field_2` varchar(256) default NULL,
  `ext_field_3` varchar(256) default NULL,
  PRIMARY KEY  (`trans_code`,`sub_trans_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_subtrans
-- ----------------------------
INSERT INTO `tsys_subtrans` VALUES ('bizCustomer', 'bizCustomer', '客户信息', '', 'crm/customer/customerManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizCustomerManager', 'bizCustomerManager', '客户管理', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizEmailInbox', '消息收件箱', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizEmailInboxFind', '消息收件箱查询', 'bizframe.message.messageService', 'bizframe/message/emailInbox.mw', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizEmailPoll', '轮询消息', 'bizframe.message.messageService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizEmailView', '浏览消息', 'bizframe.message.messageService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizMsgDel', '删除消息', 'bizframe.message.messageService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailInbox', 'bizMsgSend', '发送消息', 'bizframe.message.messageService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailOutbox', 'bizEmailOutbox', '消息发件箱', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', '消息发件箱查询', 'bizframe.message.messageService', 'bizframe/message/emailOutbox.mw', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('BIZFRAME', 'BIZFRAME', '基础业务框架', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizLinkMan', 'bizLinkMan', '联系人信息', '', 'crm/customer/orgManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizMainCustomer', 'bizMainCustomer', '潜在客户', '', 'crm/customer/customerManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizMenu', 'bizMenu', '系统菜单设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizMonitorMenu', 'bizMonitorMenu', '统平台监控菜单', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizMsgManager', 'bizMsgManager', '消息管理', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', '统平台管理菜单', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgAdd', '组织机构添加', 'bizframe.authority.organization.addOrgService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgAllot', '分配用户组织机构', 'bizframe.authority.organization.allotOrgToUserService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgCancel', '取消用户组织机构', 'bizframe.authority.organization.cancelUserOrgService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgDel', '组织机构删除', 'bizframe.authority.organization.deleteOrgService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgDownload', '下载组织机构数据', 'bizframe.authority.organization.downloadOrgService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgEdit', '组织机构修改', 'bizframe.authority.organization.updateOrgService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgFind', '组织机构查询', 'bizframe.authority.organization.findOrgService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgFindAllot', '查询用户组织机构分配', 'bizframe.authority.organization.findAllotUsersService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgFindFromCache', '查询子组织机构', 'bizframe.authority.organization.finSubOrg', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgSet', '组织机构设置', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizOrgSet', 'bizOrgSort', '组织机构排序', 'bizframe.authority.organization.sortOrgService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPageCacheSet', 'bizPageCacheSet', '页面缓存设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPageCacheSet', 'pageCacheSet', '页面缓存设置', 'com.hundsun.jres.manage', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosAdd', '岗位添加', 'bizframe.authority.position.addPosService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosAllot', '分配用户岗位', 'bizframe.authority.position.allotPosToUserService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosCancel', '取消用户岗位', 'bizframe.authority.position.cancelUserPosService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosDel', '岗位删除', 'bizframe.authority.position.deletePosService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosDownload', '下载岗位数据', 'bizframe.authority.position.downloadPosService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosEdit', '岗位修改', 'bizframe.authority.position.updatePosService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosFind', '岗位查询', 'bizframe.authority.position.findPosService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosFindAllot', '查询用户岗位分配', 'bizframe.authority.position.findAllotUsersService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosFindFromCache', '查询子岗位机构', 'bizframe.authority.position.finSubPosService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosSelector', '查询用户可访问的岗位', 'bizframe.authority.organization.findUserAuthedPositions', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizPosSet', 'bizPosSet', '岗位设置', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSallChange', 'bizSallChange', '销售目标', '', 'crm/customer/customerManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSallPlan', 'bizSallPlan', '营销计划', '', 'crm/customer/customerManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetCache', 'bizCacheFresh', '缓存刷新', 'bizframe.common.cacheRefreshService', 'bizframe/common/cacheFresh.mw', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetCache', 'bizCachesFind', '获取所有缓存', 'bizframe.common.getCaches', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetCache', 'bizSetCache', '系统缓存设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetCommon', 'getSysDate', '获取服务器时间权限', 'bizframe.common.getSysDate', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetCommon', 'uniqueCode', '生成唯一业务编码服务', 'bizframe.common.generateUniqueCodeService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictEntryAdd', '字典条目添加', 'bizframe.dictionary.insertDictEntry', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictEntryDel', '字典条目删除', 'bizframe.dictionary.deleteDictEntry', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictEntryDownload', '字典条目下载', 'bizframe.dictionary.downloadDictEntry', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictEntryEdit', '字典条目修改', 'bizframe.dictionary.updateDictEntry', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictEntryFind', '字典条目查找', 'bizframe.dictionary.findDictEntry', 'bizframe/dictionary/dictManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictItemEdit', '字典项修改', 'bizframe.dictionary.insertDictItem', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizDictItemFind', '字典项查找', 'bizframe.common.findDictItems', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetDict', 'bizSetDict', '数据字典设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetKind', 'bizSetKind', '系统类别设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetKind', 'bizSetKindAdd', '类别添加', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetKind', 'bizSetKindDelete', '类别删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetKind', 'bizSetKindEdit', '类别修改', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetKind', 'bizSetKindFind', '类别查找  ', 'bizframe.kind.fetchKindList', 'bizframe/kind/kindManagement.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetLog', 'bizSetLog', '业务日志设置', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetLog', 'bizSetLogFind', '业务日志查询', 'bizframe.businessLog.logsFind', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetLog', 'bizSetLogStart', '业务日志开启', 'bizframe.businessLog.logsStart', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetLog', 'bizSetLogStop', '业务日志关闭', 'bizframe.businessLog.logsStop', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetLog', 'bizSetLogSubTransFind', '服务查询', 'bizframe.businessLog.bizServicesFind', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuAdd', '菜单新增', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuData', '菜单访问', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuDelete', '菜单删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuEdit', '菜单编辑', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuFind', '菜单查找', 'bizframe.authority.menu.menuQuery', 'bizframe/authority/menuManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuRolesFind', '菜单查找角色授权信息', 'bizframe.authority.menu.findRolesByMenu', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSort', '菜单排序', 'bizframe.authority.menu.menuSort', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSQL', '菜单导出SQL', 'bizframe.authority.menu.menuExportSQL', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', '菜单子功能查找', 'bizframe.authority.menu.menuSubtranAdd', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSubtranDete', '菜单子功能删除', 'bizframe.authority.menu.menuSubtranDelete', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', '菜单子功能修改', 'bizframe.authority.menu.menuSubtranEdit', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuSubtranFind', '菜单子功能查找', 'bizframe.authority.menu.menuSubtranFind', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizMenuUsersFind', '菜单查找用户授权信息', 'bizframe.authority.menu.findUsersByMenu', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizRoleHasRightFind', '查询角色未授权限', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', '角色菜单权限新增', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', '角色菜单权限删除', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizRoleNoRightFind', '查询角色已授权限', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizSetMenu', '系统菜单设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizUserHasRightFind', '查询用户已授权限', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', '用户菜单权限新增', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', '用户菜单权限删除', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetMenu', 'bizUserNoRightFind', '查询用户未授权限', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetParam', 'bizSetParam', '参数设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetParam', 'bizSetParamAdd', '参数增加', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetParam', 'bizSetParamDlt', '参数删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetParam', 'bizSetParamEdit', '参数修改', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetParam', 'bizSetParamFind', '参数查找', 'bizframe.parameter.paramQuerySvc', 'bizframe/parameter/sysParameter.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizCancelAuthR', '取消授权权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizCancelOpR', '取消操作权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizGiveAuthR', '授予分配权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizGiveOpR', '授予操作权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizOpAuthed', '已授权的操作权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRightAuthed', '已授权的授权权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleARightAdd', '角色授权权限新增', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleARightDlt', '角色授权权限删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleRightAdd', '角色操作权限新增', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleRightDlt', '角色操作权限删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleRightFind', '角色权限查找', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizRoleURhtFind', '角色权限查找', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizSetRightCheck', '权限校验', 'bizframe.authority.right.checkRight', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizToOpAuth', '待授权的操作权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRight', 'bizToRightAuth', '待授权的授权权限', 'bizframe.authority.right._rightService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizRoleRightSet', '角色权限处理', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizRoleSelector', '查询用户可访问的角色', 'bizframe.authority.organization.findUserAuthedRoles', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRole', '角色设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleAdd', '角色添加', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleARight', '角色授权权限', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleDelete', '角色删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleEdit', '角色修改', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleFind', '角色查找', 'bizframe.authority.role.roleQuery', 'bizframe/authority/roleManagement.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'bizSetRoleORight', '角色操作权限', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'roleMenuRightFind', '角色菜单权限查询', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'rolePublicRightFind', '角色公共权限查询', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'roleRightView', '查看角色权限', 'bizframe.authority.role.roleRightView', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'roleUserAdd', '分配角色用户', 'bizframe.authority.role.roleUserAdd', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'roleUserDel', '删除角色用户', 'bizframe.authority.role.roleUserDel', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetRole', 'roleUserFind', '查询角色用户', 'bizframe.authority.role.roleUserFind', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizAuthAuth', '同步授权', 'bizframe.authority.right.TransAuth', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizAuthneedAuth', '请求同步授权', 'bizframe.authority.right.TransAuth', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransAdd', '子交易添加', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransDel', '子交易删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransDownload', '子交易下载', 'bizframe.authority.subSysTrans.downloadSubSysTrans', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransEdit', '子交易修改', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransExport', '子交易导出', 'bizframe.authority.subSysTrans.exportSubSysTrans', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetSubTrans', 'bizSubTransFind', '子交易查找', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizSetTrans', '交易设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransAdd', '交易添加', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransDownload', '交易下载', 'bizframe.authority.sysTrans.downloadSysTrans', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransExport', '交易导出', 'bizframe.authority.sysTrans.exportSysTrans', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransFind', '交易查找', 'bizframe.authority.sysTrans.findSysTrans', 'bizframe/authority/sysTransManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransModify', '交易修改', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetTrans', 'bizTransRemove', '交易删除', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizAllotRole', '用户已分配角色查询', 'bizframe.authority.roleuser._roleUserService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizAssoBranch', '用户关联机构查找', 'bizframe.authority.user._userService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizAssoOrg', '用户关联组织机构查询', 'bizframe.authority.user.findAssoOrgsService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizAssoPos', '用户关联岗位查询', 'bizframe.authority.user.findAssoPosService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizBindBranch', '用户关联机构', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizBindOrg', '用户关联组织机构', 'bizframe.authority.user.bindUserOrgService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizBindPos', '用户关联岗位', 'bizframe.authority.user.bindUserPosService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizBindRole', '用户关联角色', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizCancelUR', '取消分配角色', 'bizframe.authority.roleuser._roleUserService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizGiveUR', '分配角色', 'bizframe.authority.roleuser._roleUserService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizResetPass', '用户清密', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizSetUser', '用户设置', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizSetUserFind', '用户查找', 'bizframe.authority.user._userService', 'bizframe/authority/userManage.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizToAllotRole', '用户待分配角色查询', 'bizframe.authority.roleuser._roleUserService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUnBindBranch', '用户关联机构解绑', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUnBindOrg', '取消用户关联组织机构', 'bizframe.authority.user.unBindUserOrgService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUnBindPos', '取消用户关联岗位', 'bizframe.authority.user.unBindUserPosService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserActivate', '激活用户', 'bizframe.authority.user._userService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserAdd', '用户新增', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserDownlaod', '用户下载', 'bizframe.authority.user._userService', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserGetUI', '获取用户设置界面配置', '', 'bizframe.authority.user._userService', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserLock', '用户锁定', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserModfiy', '用户修改', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserModiPass', '修改密码', 'bizframe.authority.user._userService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserOpAuth', '用户操作授权', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserRemove', '用户注销', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserRightAuth', '用户权限授权', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserRightSet', '用户权限处理', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'bizUserUnLock', '用户解锁', 'bizframe.authority.user._userService', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'onlineCount', '在线用户查询', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'userMenuRightFind', '用户菜单权限查询', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'userPublicRightFind', '用户公共权限查询', '', '', '0', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'viewUserOrg', '查看用户组织', 'bizframe.authority.user.viewUserOrgService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'viewUserPos', '查看用户岗位', 'bizframe.authority.user.viewUserPosService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'viewUserRight', '查看用户权限', 'bizframe.authority.user.viewUserRightService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSetUser', 'viewUserRole', '查看用户角色', 'bizframe.authority.user.viewUserRoleService', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSign', 'bizSignIn', '用户登录', 'bizframe.framework._signIn', 'bizframe/jsp/homepage/desktop/indexFrame.jsp', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSign', 'bizSignIn2', '默认登录', 'bizframe.framework._signIn', 'bizframe/jsp/homepage/default/indexFrame.jsp', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSign', 'bizSignOut', '签退', 'bizframe.framework._signOut', 'bizframe/jsp/login.jsp', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysManager', 'bizSysManager', '系统管理', '', '', '1', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizCacheStatus', '缓存状态信息', '', 'monitor/sysStatus/Cache.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizCEPStatus', 'CEP及其插件状态信息', '', 'monitor/sysStatus/CEPPlugin.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizDBStatus', '数据库状态信息', '', 'monitor/sysStatus/DataBase.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizframeworkStatus', '查询框架', '', 'monitor/sysStatus/framework.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizKernelStatus', '执行框架状态信息', '', 'monitor/sysStatus/BizKernel.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizSysMain', '系统状态主界面', '', 'monitor/sysStatus/sysMain.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizSysStatus', '系统监控', '', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'bizUIStatus', 'UIEngine状态信息', '', 'monitor/sysStatus/UIEngine.mw', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'FindMonitorInfo', '查询某类处在监控状态的服务的监控信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'getAllDataSourceInfo', '得到所有数据源的信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'getCacheInfo', '查询缓存信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'getDataSourceInfo', '得到指定的数据源的信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'modifyCounterFlag', '修改计数功能', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'pauseBizService', '停止执行框架中的服务', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryAllActiveConnect', '查询所有活动连接', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryAllEventService', '查询所有插件服务', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryAllPluginInfo', '查询所有的插件信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryBizkernelMonitor', '查询监控状态', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryBizService', '查询IAdapter信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryBizServiceInfo', '查询本地业务处理插件的线程队列等信息', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryConnectedNodes', '查询连接节点', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryDispatchPoolInfo', '查询cepcore的分发线程池', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryEventChains', '查询事件处理链', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryProcServices', '查询本地业务处理插件向cepcore注册的服务列表', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryRouteTable', '查询路由表', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryRuntimeParameters', '查询本地通道的一些运行时参数', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'queryWaitingThread', '查询调用本地通道的同步方法，在当前时刻等待的线程', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'registerCache', '缓存注册', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'RemoveMonitor', '移出监控服务', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'restartBizService', '重启服务', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'restartMonitorT', '重启服务', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'startBizkernelMonitor', '启动监控状态', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'StartMonitorT', '启动某类型监控', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'stopBizkernelMonitor', '停止监控状态', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'StopMonitor', '停止监控', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizSysStatus', 'unregisterCache', '缓存注销', 'com.hundsun.jres.manage', '', '', '1', '', '', '', '');
INSERT INTO `tsys_subtrans` VALUES ('bizUserManager', 'bizUserManager', '用户管理', '', '', '', '1', '', '', '', '');

-- ----------------------------
-- Table structure for `tsys_trans`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_trans`;
CREATE TABLE `tsys_trans` (
  `trans_code` varchar(32) NOT NULL,
  `trans_name` varchar(32) NOT NULL,
  `kind_code` varchar(16) default NULL,
  `model_code` varchar(32) default NULL,
  `remark` varchar(256) default NULL,
  `ext_field_1` varchar(256) default NULL,
  `ext_field_2` varchar(256) default NULL,
  `ext_field_3` varchar(256) default NULL,
  PRIMARY KEY  (`trans_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_trans
-- ----------------------------
INSERT INTO `tsys_trans` VALUES ('bizCustomer', '客户信息', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizCustomerManager', '客户管理', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizEmailInbox', '消息收件箱', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizEmailOutbox', '发件箱菜单', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('BIZFRAME', '基础业务框架', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizLinkMan', '联系人信息', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizMainCustomer', '潜在客户', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizMenu', '系统菜单', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizMonitorMenu', '系统平台管理菜单', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizMsgManager', '消息管理', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizOnlineMonitor', '系统平台管理菜单', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizOrgSet', '组织机构设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizPageCacheSet', '页面缓存设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizPosSet', '岗位设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSallChange', '销售目标', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizSallPlan', '营销计划', 'BIZFRAME', '', '', '', '', '');
INSERT INTO `tsys_trans` VALUES ('bizSetCache', '系统缓存设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetCommon', '公共服务管理', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetDict', '数据字典设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetKind', '系统类别设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetLog', '业务日志设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetMenu', '系统菜单设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetParam', '系统参数设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetRight', '权限设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetRole', '角色设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetSubTrans', '子交易设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetTrans', '交易设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSetUser', '用户设置', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSign', '签名服务', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSysManager', '系统管理', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizSysStatus', '系统状态信息', 'BIZFRAME', '', '', null, null, null);
INSERT INTO `tsys_trans` VALUES ('bizUserManager', '用户管理', 'BIZFRAME', '', '', null, null, null);

-- ----------------------------
-- Table structure for `tsys_user`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user`;
CREATE TABLE `tsys_user` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `user_pwd` varchar(32) NOT NULL,
  `org_id` varchar(40) NOT NULL,
  `user_type` varchar(8) NOT NULL,
  `user_status` varchar(8) NOT NULL,
  `lock_status` varchar(8) NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `modify_date` bigint(20) default NULL,
  `pass_modify_date` bigint(20) default NULL,
  `mobile` varchar(16) default NULL,
  `email` varchar(256) default NULL,
  `ext_flag` varchar(8) default NULL,
  `remark` varchar(256) default NULL,
  `ext_field_1` varchar(256) default NULL,
  `ext_field_2` varchar(256) default NULL,
  `ext_field_3` varchar(256) default NULL,
  `ext_field_4` varchar(256) default NULL,
  `user_order` int(11) default NULL,
  `ext_field_5` varchar(256) default NULL,
  PRIMARY KEY  (`user_id`),
  KEY `org_id` (`org_id`),
  KEY `INDX_BIZ_USER_NAME` USING BTREE (`user_name`),
  CONSTRAINT `FK_ORG_USER` FOREIGN KEY (`org_id`) REFERENCES `tsys_organization` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_user
-- ----------------------------
INSERT INTO `tsys_user` VALUES ('001', '张三', '4c7ffd6b6d7cc2e2bf0bad9fafe7f18a', '0_jingl_a', '1', '0', '0', '20130710', '0', '20130710', '', '', '', '', '', '', '8e5ea2d405d49952d5ad9c2c85740482', '', '2', '');
INSERT INTO `tsys_user` VALUES ('111111', '授权用户', 'd6a5b22c801a252a74522198a8b6e3a8', '0_000000', '0', '0', '0', '20101010', '20101010', '20101010', '', '', '', '', '', '', '', '', null, '');
INSERT INTO `tsys_user` VALUES ('admin', '系统管理员', 'fd2a708f34859ddc3096abf6878a7550', '0_000000', '0', '0', '0', '20101010', '20101010', '20130710', '', '', '', '', '', '', '4c46f2e1706c97ad404393fa22560b3b', '', '0', '');
INSERT INTO `tsys_user` VALUES ('system', '系统用户', '29f9c7ad788c0f6be5550590d0caaf45', '0_000000', '0', '0', '0', '20101010', '20101010', '20101010', '', '', '', '', '', '', '', '', null, '');

-- ----------------------------
-- Table structure for `tsys_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user_login`;
CREATE TABLE `tsys_user_login` (
  `user_id` varchar(32) NOT NULL,
  `last_login_date` bigint(20) default NULL,
  `last_login_time` bigint(20) default NULL,
  `last_login_ip` varchar(32) default NULL,
  `login_fail_times` bigint(20) default NULL,
  `last_fail_date` bigint(20) default NULL,
  `ext_field` varchar(16) default NULL,
  PRIMARY KEY  (`user_id`),
  CONSTRAINT `FK_USER_LOGIN` FOREIGN KEY (`user_id`) REFERENCES `tsys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_user_login
-- ----------------------------
INSERT INTO `tsys_user_login` VALUES ('001', '20130710', '135014', '127.0.0.1', '0', '0', '');
INSERT INTO `tsys_user_login` VALUES ('111111', '20130709', '211205', '127.0.0.1', '0', '0', '');
INSERT INTO `tsys_user_login` VALUES ('admin', '20130717', '223541', '127.0.0.1', '0', '20130716', '');
INSERT INTO `tsys_user_login` VALUES ('system', '20130709', '213644', '127.0.0.1', '0', '0', '');

-- ----------------------------
-- Table structure for `tsys_user_message`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user_message`;
CREATE TABLE `tsys_user_message` (
  `msg_id` varchar(32) NOT NULL,
  `msg_title` varchar(64) default NULL,
  `send_user_id` varchar(32) NOT NULL,
  `receive_user_id` varchar(32) NOT NULL,
  `send_date` bigint(20) default NULL,
  `msg_content` varchar(1024) default NULL,
  `msg_type` varchar(8) NOT NULL,
  `msg_isred` varchar(8) NOT NULL,
  `ext_field` varchar(16) default NULL,
  PRIMARY KEY  (`msg_id`),
  KEY `INDX_BIZ_USERMSG_RED` (`msg_isred`),
  KEY `INDX_BIZ_USERMSG_SEND` (`send_user_id`),
  KEY `INDX_BIZ_USERMSG_TEXT` (`msg_content`(255)),
  KEY `INDX_BIZ_USERMSG_TITLE` (`msg_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_user_message
-- ----------------------------

-- ----------------------------
-- Table structure for `tsys_user_right`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user_right`;
CREATE TABLE `tsys_user_right` (
  `trans_code` varchar(32) NOT NULL,
  `sub_trans_code` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `create_by` varchar(32) default NULL,
  `create_date` bigint(20) default NULL,
  `begin_date` bigint(20) NOT NULL,
  `end_date` bigint(20) NOT NULL,
  `right_flag` varchar(8) NOT NULL,
  `right_enable` varchar(8) default NULL,
  PRIMARY KEY  (`trans_code`,`sub_trans_code`,`user_id`,`begin_date`,`end_date`,`right_flag`),
  KEY `FK_RIGHT_USER` (`user_id`),
  KEY `INDX_BIZ_USERRIGHT_FL` USING BTREE (`right_flag`),
  KEY `INDX_BIZ_USERRIGHT_TRANS_CODE` USING BTREE (`trans_code`,`sub_trans_code`),
  CONSTRAINT `FK_RIGHT_USER` FOREIGN KEY (`user_id`) REFERENCES `tsys_user` (`user_id`),
  CONSTRAINT `FK_URIGHT_TRANS` FOREIGN KEY (`trans_code`, `sub_trans_code`) REFERENCES `tsys_subtrans` (`trans_code`, `sub_trans_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_user_right
-- ----------------------------
INSERT INTO `tsys_user_right` VALUES ('bizCustomer', 'bizCustomer', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizCustomerManager', 'bizCustomerManager', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailInbox', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailInbox', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailInboxFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailInboxFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailPoll', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailPoll', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailView', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizEmailView', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizMsgDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizMsgDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizMsgSend', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailInbox', 'bizMsgSend', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailOutbox', 'bizEmailOutbox', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailOutbox', 'bizEmailOutbox', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizEmailOutbox', 'bizEmailOutboxFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('BIZFRAME', 'BIZFRAME', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('BIZFRAME', 'BIZFRAME', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizLinkMan', 'bizLinkMan', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizMainCustomer', 'bizMainCustomer', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizMenu', 'bizMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizMenu', 'bizMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizMonitorMenu', 'bizMonitorMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizMonitorMenu', 'bizMonitorMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizMsgManager', 'bizMsgManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizMsgManager', 'bizMsgManager', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOnlineMonitor', 'bizOnlineMonitor', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgCancel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgCancel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFindAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFindAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFindFromCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgFindFromCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgSort', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizOrgSet', 'bizOrgSort', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPageCacheSet', 'bizPageCacheSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPageCacheSet', 'bizPageCacheSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPageCacheSet', 'pageCacheSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPageCacheSet', 'pageCacheSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosCancel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosCancel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFindAllot', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFindAllot', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFindFromCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosFindFromCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosSelector', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosSelector', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizPosSet', 'bizPosSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSallChange', 'bizSallChange', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizSallPlan', 'bizSallPlan', 'admin', 'admin', '0', '0', '0', '2', '1');
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizCacheFresh', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizCacheFresh', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizCachesFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizCachesFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizSetCache', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCache', 'bizSetCache', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCommon', 'getSysDate', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCommon', 'getSysDate', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCommon', 'uniqueCode', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetCommon', 'uniqueCode', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictEntryFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictItemEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictItemEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictItemFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizDictItemFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizSetDict', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetDict', 'bizSetDict', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetKind', 'bizSetKindFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLog', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLog', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogStart', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogStart', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogStop', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogStop', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogSubTransFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetLog', 'bizSetLogSubTransFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuRolesFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuRolesFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSort', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSort', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSQL', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSQL', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranDete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranDete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuSubtranFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuUsersFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizMenuUsersFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleHasRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleHasRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleMenuRightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleMenuRightDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleNoRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizRoleNoRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizSetMenu', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizSetMenu', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserHasRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserHasRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserMenuRightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserMenuRightDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserNoRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetMenu', 'bizUserNoRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParam', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParam', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamDlt', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamDlt', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetParam', 'bizSetParamFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizCancelAuthR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizCancelAuthR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizCancelOpR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizCancelOpR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizGiveAuthR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizGiveAuthR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizGiveOpR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizGiveOpR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizOpAuthed', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizOpAuthed', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRightAuthed', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRightAuthed', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleARightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleARightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleARightDlt', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleARightDlt', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightDlt', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightDlt', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleURhtFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizRoleURhtFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizToOpAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizToOpAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizToRightAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRight', 'bizToRightAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizRoleRightSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizRoleRightSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizRoleSelector', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizRoleSelector', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleARight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleARight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleDelete', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleDelete', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleORight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'bizSetRoleORight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleMenuRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleMenuRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'rolePublicRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'rolePublicRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleRightView', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleRightView', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetRole', 'roleUserFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransDel', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransDel', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransEdit', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransEdit', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransExport', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransExport', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetSubTrans', 'bizSubTransFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizSetTrans', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizSetTrans', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransDownload', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransDownload', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransExport', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransExport', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransModify', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransModify', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransRemove', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetTrans', 'bizTransRemove', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAllotRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAllotRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizAssoPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizBindPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizCancelUR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizCancelUR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizGiveUR', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizGiveUR', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizResetPass', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizResetPass', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizSetUser', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizSetUser', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizSetUserFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizSetUserFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizToAllotRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizToAllotRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindBranch', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindBranch', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUnBindPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserActivate', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserActivate', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserAdd', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserAdd', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserGetUI', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserGetUI', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserLock', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserLock', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserModfiy', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserModfiy', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserOpAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserOpAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRemove', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRemove', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRightAuth', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRightAuth', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRightSet', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserRightSet', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserUnLock', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'bizUserUnLock', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'onlineCount', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'onlineCount', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'userMenuRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'userMenuRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'userPublicRightFind', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'userPublicRightFind', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserOrg', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserOrg', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserPos', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserPos', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserRight', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserRight', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserRole', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSetUser', 'viewUserRole', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysManager', 'bizSysManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysManager', 'bizSysManager', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizCacheStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizCacheStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizCEPStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizCEPStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizDBStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizDBStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizKernelStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizKernelStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizSysMain', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizSysMain', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizSysStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizSysStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizUIStatus', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'bizUIStatus', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'FindMonitorInfo', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'FindMonitorInfo', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'pauseBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'pauseBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'queryBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'queryBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'restartBizService', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'restartBizService', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'StartMonitorT', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'StartMonitorT', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'StopMonitor', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizSysStatus', 'StopMonitor', 'admin', 'admin', '0', '0', '0', '2', null);
INSERT INTO `tsys_user_right` VALUES ('bizUserManager', 'bizUserManager', 'admin', 'admin', '0', '0', '0', '1', null);
INSERT INTO `tsys_user_right` VALUES ('bizUserManager', 'bizUserManager', 'admin', 'admin', '0', '0', '0', '2', null);
