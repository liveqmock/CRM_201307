/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/7/20 22:49:19                           */
/*==============================================================*/


drop table if exists CST_ACTIVITY;

drop table if exists CST_BUSI_CHANCE;

drop table if exists CST_SERVICE;

drop table if exists cst_customer;

/*==============================================================*/
/* Table: CST_ACTIVITY                                          */
/*==============================================================*/
create table CST_ACTIVITY
(
   ID                   int(19) not null,
   TITLE                varchar(50),
   customerName         varchar(50),
   customerId           int(19),
   relatedType          varchar(20),
   relatedName          varchar(50),
   relatedId            int(19),
   linkmanName          varchar(20),
   linkmanId            int(19),
   beginTime            datetime,
   endTime              datetime,
   priority             varchar(10),
   activeType           varchar(10),
   cost                 decimal(10,2),
   stat                 varchar(10),
   description          varchar(500),
   createUserId         int(19),
   createUserName       varchar(20),
   createDate           datetime,
   updateUserId         int(19),
   updateUserName       varchar(20),
   updateDate           datetime,
   ext1                 varchar(20),
   ext2                 varchar(20),
   ext3                 varchar(20),
   ext4                 varchar(20),
   ext5                 varchar(20),
   ext6                 varchar(20),
   ext7                 varchar(20),
   ext8                 varchar(20),
   ext9                 varchar(20),
   ext10                varchar(20),
   primary key (ID)
);

/*==============================================================*/
/* Table: CST_BUSI_CHANCE                                       */
/*==============================================================*/
create table CST_BUSI_CHANCE
(
   id                   int(19),
   title                varchar(50),
   customerName         varchar(50),
   customerId           int(19),
   busiChanceCode       varchar(20),
   stat                 varchar(20),
   busiChanceSource     varchar(20),
   forcastDealTime      datetime,
   forcastDealMoney     decimal(10,2),
   forcastSaleQuantity  int(10),
   mainPoint            varchar(500),
   customerRequirement  varchar(500),
   ext8                 varchar(20),
   ext9                 varchar(20),
   ext10                varchar(20),
   createUserId         int(19),
   createUserName       varchar(20),
   createDate           datetime,
   updateUserId         int(19),
   updateUserName       varchar(20),
   updateDate           datetime,
   ext1                 varchar(20),
   ext2                 varchar(20),
   ext3                 varchar(20),
   ext4                 varchar(20),
   ext5                 varchar(20),
   ext6                 varchar(20),
   ext7                 varchar(20)
);

/*==============================================================*/
/* Table: CST_SERVICE                                           */
/*==============================================================*/
create table CST_SERVICE
(
   id                   int(19),
   title                varchar(50),
   customerName         varchar(50),
   customerId           int(19),
   orderCode            varchar(20),
   serviceCode          varchar(20),
   linkmanName          varchar(20),
   linkmanId            int(19),
   serviceSource        varchar(20),
   serviceType          varchar(20),
   serviceCause         varchar(20),
   priority             varchar(10),
   stat                 varchar(10),
   beginTime            datetime,
   innerReview          varchar(500),
   description          varbinary(500),
   ext8                 varchar(20),
   ext9                 varchar(20),
   ext10                varchar(20),
   createUserId         int(19),
   createUserName       varchar(20),
   createDate           datetime,
   updateUserId         int(19),
   updateUserName       varchar(20),
   updateDate           datetime,
   ext1                 varchar(20),
   ext2                 varchar(20),
   ext3                 varchar(20),
   ext4                 varchar(20),
   ext5                 varchar(20),
   ext6                 varchar(20),
   ext7                 varchar(20)
);

/*==============================================================*/
/* Table: cst_customer                                          */
/*==============================================================*/
create table cst_customer
(
   cust_id              int(11) not null auto_increment,
   cust_no              varchar(32) default NULL comment '客户编号',
   cust_name            varchar(250) default NULL comment '客户名称',
   full_pinyin_name     varchar(500) default NULL,
   simple_pinyin_name   varchar(250) default NULL,
   stock_code           varchar(20) default NULL comment '股票代码',
   invoice_address      varchar(250) default NULL comment '发票地址',
   cust_hot             int(11) default 0 comment '是否热点客户',
   cust_source          varchar(250) default NULL comment '客户来源',
   cust_type            varchar(32) default NULL comment '客户类型',
   employee_total       int(11) default NULL,
   cust_region          varchar(50) default NULL comment '领域',
   cust_level           int(11) default 1 comment '客户等级',
   cust_satisfy         varchar(21) default NULL comment '满意度',
   cust_credit          varchar(21) default NULL comment '信誉',
   country              varchar(32) default NULL comment '国家',
   province             varchar(32) default NULL comment '省份',
   city                 varchar(32) default NULL comment '城市',
   cust_addr            varchar(500) default NULL comment '地址',
   cust_zip_code        varchar(10) default NULL comment '邮编',
   cust_tel             varchar(50) default NULL comment '电话',
   cust_fax             varchar(50) default NULL comment '电话',
   cust_website         varchar(50) default NULL comment '网址',
   cust_licence_no      varchar(50) default NULL comment '工商执照号',
   cust_chieftain       varchar(50) default NULL comment '法人',
   cust_bankroll        bigint(20) default 0 comment '资金',
   cust_turnover        bigint(20) default 0 comment '年营业额',
   cust_bank            varchar(200) default NULL comment '签约银行',
   cust_bank_account    varchar(50) default NULL comment '签约银行账号',
   cust_local_tax_no    varchar(50) default NULL comment '地税编号',
   cust_national_tax_no varchar(50) default NULL comment '国税编号',
   create_userid        varchar(11) default NULL,
   create_username      varchar(32) default NULL,
   create_date          datetime default NULL,
   update_userid        varchar(11) default NULL,
   update_username      varchar(32) default NULL,
   update_date          datetime default NULL,
   cust_status          char(1) default '1' comment '客户状态',
   primary key (cust_id)
);

