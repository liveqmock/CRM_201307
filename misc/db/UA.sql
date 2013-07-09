-- Create table
create table GT_ACCOUNTS
(
  ID                  NUMBER(19) not null,
  CREATETIME          TIMESTAMP(6),
  UPDATEDTIME         TIMESTAMP(6),
  UPDATOR_ID          NUMBER,
  ACCOUNTTYPE         VARCHAR2(255 CHAR),
  DISABLED            NUMBER(1) not null,
  EMAIL               VARCHAR2(255 CHAR),
  EXPIREDDATE         DATE,
  LOCALE              VARCHAR2(255 CHAR),
  LOCKED              NUMBER(1) not null,
  LOGINNAME           VARCHAR2(255 CHAR),
  NAMECN              VARCHAR2(255 CHAR),
  NAMEEN              VARCHAR2(255 CHAR),
  PASSWORD            VARCHAR2(255 CHAR),
  PASSWORDEXPIREDDATE DATE,
  PHONECODE           VARCHAR2(255 CHAR),
  DOMAIN_ID           NUMBER(19),
  LOGIN_TOKEN         VARCHAR2(256 CHAR),
  LOCKVERSION         NUMBER(6),
  CREATEDTIME         TIMESTAMP(6),
  CREATOR_ID          NUMBER(19),
  NET_AUTHORITY       VARCHAR2(10 CHAR) default 111
)


  
  -- Create table
create table GT_ACCOUNT_ORG_ASSOC
(
  ACCOUNT_ID NUMBER(19),
  ORG_ID     NUMBER(19)
)


  
  -- Create table
create table GT_ACCOUNT_ROLE
(
  ACCOUNT_ID NUMBER(19) not null,
  ROLE_ID    NUMBER(19) not null
)

  
  -- Create table
create table GT_DOMAINS
(
  ID           NUMBER(19) not null,
  CREATETIME   TIMESTAMP(6),
  LASTUPDATE   TIMESTAMP(6),
  LASTUPDATEBY VARCHAR2(255 CHAR),
  ALIAS        VARCHAR2(255 CHAR),
  CODE         VARCHAR2(255 CHAR),
  NAME         VARCHAR2(255 CHAR)
)

  
  -- Create table
create table GT_MENU_ITEM
(
  ID            NUMBER(19) not null,
  CREATEDTIME   TIMESTAMP(6),
  UPDATETIME    TIMESTAMP(6),
  ACTION        VARCHAR2(255 CHAR),
  ALTIMAGE      VARCHAR2(255 CHAR),
  DESCRIPTION   VARCHAR2(255 CHAR),
  DESCRIPTIONEN VARCHAR2(255 CHAR),
  FORWARD       VARCHAR2(255 CHAR),
  HEIGHT        VARCHAR2(255 CHAR),
  IMAGE         VARCHAR2(255 CHAR),
  SORTINDEX     NUMBER(19),
  LOCATION      VARCHAR2(255 CHAR),
  NAME          VARCHAR2(255 CHAR),
  ONCLICK       VARCHAR2(255 CHAR),
  ONMOUSEOUT    VARCHAR2(255 CHAR),
  ONMOUSEOVER   VARCHAR2(255 CHAR),
  PAGE          VARCHAR2(255 CHAR),
  PARENTID      NUMBER(19),
  ROLES         VARCHAR2(255 CHAR),
  TARGET        VARCHAR2(255 CHAR),
  TITLE         VARCHAR2(255 CHAR),
  TITLEEN       VARCHAR2(255 CHAR),
  TOOLTIP       VARCHAR2(255 CHAR),
  WEBCONTEXT    VARCHAR2(255 CHAR),
  WIDTH         VARCHAR2(255 CHAR),
  DOMAIN_ID     NUMBER(19),
  UPDATEDTIME   TIMESTAMP(6),
  CREATOR_ID    NUMBER(19),
  UPDATOR_ID    NUMBER(19),
  LOCKVERSION   NUMBER(19),
  TYPE          VARCHAR2(255),
  NET_AUTHORITY VARCHAR2(10 CHAR),
  COUNT         NUMBER(19)
)

  
  -- Create table
create table GT_NATURAL_ZONE
(
  ID          NUMBER(19) not null,
  CREATEDTIME TIMESTAMP(6),
  CREATOR_ID  NUMBER(19),
  DATA_LEVEL  VARCHAR2(255 CHAR),
  DOMAIN_ID   NUMBER(19),
  EXTCODE     VARCHAR2(255 CHAR),
  GECC_CODE   VARCHAR2(255 CHAR),
  LOCKVERSION NUMBER(19) not null,
  UPDATEDTIME TIMESTAMP(6),
  UPDATOR_ID  NUMBER(19),
  CODE        VARCHAR2(1023 CHAR),
  DESCRIPTION VARCHAR2(255 CHAR),
  NAME        VARCHAR2(255 CHAR),
  NAMEPATH    VARCHAR2(2047 CHAR),
  STATUS      VARCHAR2(255 CHAR),
  TREEPATH    VARCHAR2(1023 CHAR),
  POSTCODE    VARCHAR2(255 CHAR),
  PARENT      NUMBER(19)
)
-- Create/Recreate primary, unique and foreign key constraints 
alter table GT_NATURAL_ZONE
  add primary key (ID)
  using index 
  tablespace WOLF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 2M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes 
create index GTR_IDX_064 on GT_NATURAL_ZONE (PARENT)
  tablespace WOLF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 2M
    minextents 1
    maxextents unlimited
  );
create index TMS_INDEX_04 on GT_NATURAL_ZONE (TREEPATH)
  tablespace WOLF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 3M
    minextents 1
    maxextents unlimited
  );

  
  -- Create table
create table GT_ORGANIZATION
(
  ID             NUMBER(19) not null,
  CREATEDTIME    TIMESTAMP(6),
  CREATOR_ID     NUMBER(19),
  DATA_LEVEL     VARCHAR2(255 CHAR),
  DOMAIN_ID      NUMBER(19),
  EXTCODE        VARCHAR2(255 CHAR),
  GECC_CODE      VARCHAR2(255 CHAR),
  LOCKVERSION    NUMBER(19) not null,
  UPDATEDTIME    TIMESTAMP(6),
  UPDATOR_ID     NUMBER(19),
  TREEPATH       VARCHAR2(1023 CHAR),
  NAMEPATH       VARCHAR2(2047 CHAR),
  CODE           VARCHAR2(1023 CHAR),
  NAME           VARCHAR2(255 CHAR),
  DESCRIPTION    VARCHAR2(255 CHAR),
  STATUS         VARCHAR2(255 CHAR),
  ADDRESS_ID     NUMBER(19),
  CONNACT_PERSON VARCHAR2(255 CHAR),
  CONNACT_PHONE  VARCHAR2(255 CHAR),
  PARENT_ID      NUMBER(19)
)
-- Create/Recreate primary, unique and foreign key constraints 
alter table GT_ORGANIZATION
  add primary key (ID)
  using index 
  tablespace WOLF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

  
  -- Create table
create table GT_ORG_NZONE_ASSOC
(
  ORGANIZATION_ID NUMBER(19),
  NZONE_ID        NUMBER(19)
)

  
  -- Create table
create table GT_PREDEFINEDCODE
(
  ID          NUMBER(19) not null,
  CODE        VARCHAR2(255),
  VALUE       VARCHAR2(255),
  DESCRIPTION VARCHAR2(1024),
  TYPE        VARCHAR2(255),
  DOMAIN_ID   NUMBER(19),
  CREATEDTIME TIMESTAMP(6),
  UPDATEDTIME TIMESTAMP(6),
  CREATOR_ID  NUMBER(19),
  UPDATOR_ID  NUMBER(19),
  LOCKVERSION NUMBER(19)
)
-- Create/Recreate primary, unique and foreign key constraints 
alter table GT_PREDEFINEDCODE
  add primary key (ID)
  using index 
  tablespace WOLF
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

  
  -- Create table
create table GT_ROLES
(
  ID            NUMBER(19) not null,
  LASTUPDATE    TIMESTAMP(6),
  LASTUPDATEBY  VARCHAR2(255 CHAR),
  CODE          VARCHAR2(255 CHAR),
  DATALEVEL     VARCHAR2(255 CHAR),
  DOMAIN_ID     NUMBER(19),
  NAME          VARCHAR2(255 CHAR),
  ROLETYPE      VARCHAR2(255 CHAR),
  DESCRIPTION   VARCHAR2(255 CHAR),
  CREATEDTIME   TIMESTAMP(6),
  UPDATEDTIME   TIMESTAMP(6),
  CREATOR_ID    NUMBER(19),
  UPDATOR_ID    NUMBER(19),
  LOCKVERSION   NUMBER(19),
  NET_AUTHORITY VARCHAR2(10 CHAR) default 111
)

  
  -- Create table
create table GT_ROLE_MENUITEM
(
  ROLE     NUMBER(19),
  MENUITEM NUMBER(19)
)

  
  
  -- Create table
create table GT_SEQUENCE
(
  ID          NUMBER(19) not null,
  KEY         VARCHAR2(255 CHAR),
  LASTUPDATED TIMESTAMP(6),
  NEXTID      NUMBER(19),
  PATTERN     VARCHAR2(255 CHAR),
  RECIRCLE    VARCHAR2(255 CHAR) not null,
  TYPE        VARCHAR2(255 CHAR),
  LOCKVERSION NUMBER(6),
  DOMAIN_ID   NUMBER(19),
  CREATOR_ID  NUMBER(19),
  UPDATOR_ID  NUMBER(19),
  CREATEDTIME TIMESTAMP(6),
  UPDATEDTIME TIMESTAMP(6)
)
