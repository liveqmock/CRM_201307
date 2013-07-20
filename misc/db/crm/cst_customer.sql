/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : crm_jres

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2013-07-20 08:19:33
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `cst_customer`
-- ----------------------------
DROP TABLE IF EXISTS `cst_customer`;
CREATE TABLE `cst_customer` (
  `cust_id` int(11) NOT NULL auto_increment,
  `cust_no` varchar(32) default NULL COMMENT '客户编号',
  `cust_name` varchar(250) default NULL COMMENT '客户名称',
  `full_pinyin_name` varchar(500) default NULL,
  `simple_pinyin_name` varchar(250) default NULL,
  `stock_code` varchar(20) default NULL COMMENT '股票代码',
  `invoice_address` varchar(250) default NULL COMMENT '发票地址',
  `cust_hot` int(11) default '0' COMMENT '是否热点客户',
  `cust_source` varchar(250) default NULL COMMENT '客户来源',
  `cust_type` varchar(32) default NULL COMMENT '客户类型',
  `employee_total` int(11) default NULL,
  `cust_region` varchar(50) default NULL COMMENT '领域',
  `cust_level` int(11) default '1' COMMENT '客户等级',
  `cust_satisfy` varchar(21) default NULL COMMENT '满意度',
  `cust_credit` varchar(21) default NULL COMMENT '信誉',
  `country` varchar(32) default NULL COMMENT '国家',
  `province` varchar(32) default NULL COMMENT '省份',
  `city` varchar(32) default NULL COMMENT '城市',
  `cust_addr` varchar(500) default NULL COMMENT '地址',
  `cust_zip_code` varchar(10) default NULL COMMENT '邮编',
  `cust_tel` varchar(50) default NULL COMMENT '电话',
  `cust_fax` varchar(50) default NULL COMMENT '电话',
  `cust_website` varchar(50) default NULL COMMENT '网址',
  `cust_licence_no` varchar(50) default NULL COMMENT '工商执照号',
  `cust_chieftain` varchar(50) default NULL COMMENT '法人',
  `cust_bankroll` bigint(20) default '0' COMMENT '资金',
  `cust_turnover` bigint(20) default '0' COMMENT '年营业额',
  `cust_bank` varchar(200) default NULL COMMENT '签约银行',
  `cust_bank_account` varchar(50) default NULL COMMENT '签约银行账号',
  `cust_local_tax_no` varchar(50) default NULL COMMENT '地税编号',
  `cust_national_tax_no` varchar(50) default NULL COMMENT '国税编号',
  `create_userid` varchar(11) default NULL,
  `create_username` varchar(32) default NULL,
  `create_date` datetime default NULL,
  `update_userid` varchar(11) default NULL,
  `update_username` varchar(32) default NULL,
  `update_date` datetime default NULL,
  `cust_status` char(1) default '1' COMMENT '客户状态',
  PRIMARY KEY  (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cst_customer
-- ----------------------------
INSERT INTO `cst_customer` VALUES ('1', '00001', '雷晓亮', null, null, null, null, '0', null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, null, null, null, null, null, null, '1');
