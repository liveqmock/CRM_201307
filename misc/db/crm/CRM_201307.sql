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
   cust_no              varchar(32) default NULL comment '�ͻ����',
   cust_name            varchar(250) default NULL comment '�ͻ�����',
   full_pinyin_name     varchar(500) default NULL,
   simple_pinyin_name   varchar(250) default NULL,
   stock_code           varchar(20) default NULL comment '��Ʊ����',
   invoice_address      varchar(250) default NULL comment '��Ʊ��ַ',
   cust_hot             int(11) default 0 comment '�Ƿ��ȵ�ͻ�',
   cust_source          varchar(250) default NULL comment '�ͻ���Դ',
   cust_type            varchar(32) default NULL comment '�ͻ�����',
   employee_total       int(11) default NULL,
   cust_region          varchar(50) default NULL comment '����',
   cust_level           int(11) default 1 comment '�ͻ��ȼ�',
   cust_satisfy         varchar(21) default NULL comment '�����',
   cust_credit          varchar(21) default NULL comment '����',
   country              varchar(32) default NULL comment '����',
   province             varchar(32) default NULL comment 'ʡ��',
   city                 varchar(32) default NULL comment '����',
   cust_addr            varchar(500) default NULL comment '��ַ',
   cust_zip_code        varchar(10) default NULL comment '�ʱ�',
   cust_tel             varchar(50) default NULL comment '�绰',
   cust_fax             varchar(50) default NULL comment '�绰',
   cust_website         varchar(50) default NULL comment '��ַ',
   cust_licence_no      varchar(50) default NULL comment '����ִ�պ�',
   cust_chieftain       varchar(50) default NULL comment '����',
   cust_bankroll        bigint(20) default 0 comment '�ʽ�',
   cust_turnover        bigint(20) default 0 comment '��Ӫҵ��',
   cust_bank            varchar(200) default NULL comment 'ǩԼ����',
   cust_bank_account    varchar(50) default NULL comment 'ǩԼ�����˺�',
   cust_local_tax_no    varchar(50) default NULL comment '��˰���',
   cust_national_tax_no varchar(50) default NULL comment '��˰���',
   create_userid        varchar(11) default NULL,
   create_username      varchar(32) default NULL,
   create_date          datetime default NULL,
   update_userid        varchar(11) default NULL,
   update_username      varchar(32) default NULL,
   update_date          datetime default NULL,
   cust_status          char(1) default '1' comment '�ͻ�״̬',
   primary key (cust_id)
);

