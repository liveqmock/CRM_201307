insert into gt_domains (ID, CREATETIME, LASTUPDATE, LASTUPDATEBY, ALIAS, CODE, NAME)
values (60000, '28-8月 -08 09.45.45.728000 上午', '19-9月 -11 03.40.19.044000 下午', '', '', '800BEST', '百世物流');


insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (100, '28-8月 -08 06.21.58.000000 上午', '', '', '', '', '', '', '', '', 100, '', '功能权限', '', '', '', '', null, '', '', '功能权限', '功能权限', '', '', '', null, '', null, null, null, 'NORMAL');

insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (101, '28-8月 -08 06.21.58.000000 上午', '', '', '', '', '', '', '', '', 100, '', '系统管理', '', '', '', '', 100, '', '', '系统管理', '系统管理', '', '', '', null, '', null, null, null, 'NORMAL');

insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (102, '28-8月 -08 06.21.58.000000 上午', '', 'RolePremissionCreateView', '', '', '', '', '', '', 100, 'RolePremissionCreateView', '角色新增', '', '', '', '', 101, '', '', '角色新增', '角色新增', '', '', '', null, '', null, null, null, 'NORMAL');

insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (104, '28-8月 -08 06.21.58.000000 上午', '', 'RolePremissionManageView', '', '', '', '', '', '', 100, 'RolePremissionManageView', '角色管理', '', '', '', '', 101, '', '', '角色管理', '角色管理', '', '', '', null, '', null, null, null, 'NORMAL');

insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (105, '28-8月 -08 06.21.58.000000 上午', '', 'AccountManageView', '', '', '', '', '', '', 100, 'AccountManageView', '用户管理', '', '', '', '', 101, '', '', '用户管理', '用户管理', '', '', '', null, '', null, null, null, 'NORMAL');

insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (106, '28-8月 -08 06.21.58.000000 上午', '', 'OrganizationManageView', '', '', '', '', '', '', 100, 'OrganizationManageView', '组织管理', '', '', '', '', 101, '', '', '组织管理', '组织管理', '', '', '', null, '', null, null, null, 'NORMAL');


insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (119, '14-1月 -12 03.36.43.000000 下午', '', 'PasswordEdit', '', '', '', '', '', '', 119, 'PasswordEdit', '修改密码', '', '', '', '', 101, '', '', '密码修改', '密码修改', '', '', '', null, '', null, null, null, 'NORMAL');


insert into gt_menu_item (ID, CREATEDTIME, UPDATETIME, ACTION, ALTIMAGE, DESCRIPTION, DESCRIPTIONEN, FORWARD, HEIGHT, IMAGE, SORTINDEX, LOCATION, NAME, ONCLICK, ONMOUSEOUT, ONMOUSEOVER, PAGE, PARENTID, ROLES, TARGET, TITLE, TITLEEN, TOOLTIP, WEBCONTEXT, WIDTH, DOMAIN_ID, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION, TYPE)
values (1000, '28-8月 -08 06.21.58.000000 上午', '', 'MessageTest', '', '', '', '', '', '', 100, 'MessageTest', '系统广播', '', '', '', '', 101, '', '', '系统广播', '系统广播', '', '', '', null, '', null, null, null, 'NORMAL');



insert into gt_roles (ID, LASTUPDATE, LASTUPDATEBY, CODE, DATALEVEL, DOMAIN_ID, NAME, ROLETYPE, DESCRIPTION, CREATEDTIME, UPDATEDTIME, CREATOR_ID, UPDATOR_ID, LOCKVERSION)
values (100, '', '', '', '', 60000, 'admin', '', '管理员', '', '09-2月 -12 10.45.45.892000 上午', null, 100, 0);


insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 102);

insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 104);

insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 105);

insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 119);

insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 106);

insert into gt_role_menuitem (ROLE, MENUITEM)
values (100, 101);

insert into gt_accounts (ID, CREATETIME, UPDATEDTIME, UPDATOR_ID, ACCOUNTTYPE, DISABLED, EMAIL, EXPIREDDATE, LOCALE, LOCKED, LOGINNAME, NAMECN, NAMEEN, PASSWORD, PASSWORDEXPIREDDATE, PHONECODE, DOMAIN_ID, LOGIN_TOKEN, LOCKVERSION, CREATEDTIME, CREATOR_ID)
values (100, '', '09-2月 -12 11.05.12.368000 上午', 100, '', 0, '', null, '', 0, 'ADMIN', 'ADMIN', 'ADMIN', '21232f297a57a5a743894a0e4a801fc3', null, '', 60000, '100', 2, '24-10月-11 02.00.06.488000 下午', -1);

insert into gt_accounts (ID, CREATETIME, UPDATEDTIME, UPDATOR_ID, ACCOUNTTYPE, DISABLED, EMAIL, EXPIREDDATE, LOCALE, LOCKED, LOGINNAME, NAMECN, NAMEEN, PASSWORD, PASSWORDEXPIREDDATE, PHONECODE, DOMAIN_ID, LOGIN_TOKEN, LOCKVERSION, CREATEDTIME, CREATOR_ID)
values (-1, '', '', null, '', 0, '', null, '', 0, 'SysAdmin', '系统', '', '', null, '', null, '', null, '', null);


insert into gt_organization (ID, CREATEDTIME, CREATOR_ID, DATA_LEVEL, DOMAIN_ID, EXTCODE, GECC_CODE, LOCKVERSION, UPDATEDTIME, UPDATOR_ID, TREEPATH, NAMEPATH, CODE, NAME, DESCRIPTION, STATUS, ADDRESS_ID, CONNACT_PERSON, CONNACT_PHONE, PARENT_ID)
values (60000, '', null, 'SYSTEM', 60000, '', '', 0, '', -1, '60000', '百世物流', '60000', '百世物流', '根节点，不可以删除', 'ACTIVE', null, '', '', 0);


insert into gt_account_org_assoc (ACCOUNT_ID, ORG_ID)
values (100, 60000);

insert into gt_account_role (ACCOUNT_ID, ROLE_ID)
values (100, 100);

