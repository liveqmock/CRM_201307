DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_BRANCH_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_BRANCH_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_BRANCH_NAME on tsys_branch(branch_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_BRANCH_PARENR_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_BRANCH_PARENR_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_BRANCH_PARENR_CODE on tsys_branch(parent_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_BU_BRANCHE_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_BU_BRANCHE_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_BU_BRANCHE_CODE on tsys_branch_user(branch_code); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_BU_USER_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_BU_USER_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_BU_USER_ID on tsys_branch_user(user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DEP_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DEP_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_DEP_NAME on tsys_dep(dep_name); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DEP_PCODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DEP_PCODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_DEP_PCODE on tsys_dep(parent_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DEP_BRANCH_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DEP_BRANCH_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_DEP_BRANCH_CODE on tsys_dep(branch_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DICT_ENTRY_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DICT_ENTRY_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_DICT_ENTRY_NAME on tsys_dict_entry(dict_entry_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DICTITEM_ENTRY_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DICTITEM_ENTRY_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_DICTITEM_ENTRY_NAME on tsys_dict_item(dict_item_name); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_DICTITEM_ENTRY_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_DICTITEM_ENTRY_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_DICTITEM_ENTRY_CODE on tsys_dict_item(dict_entry_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_KIND_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_KIND_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_KIND_NAME on tsys_kind(kind_name); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_KIND_PCODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_KIND_PCODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_KIND_PCODE on tsys_kind(parent_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_MENU_TRANS_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_MENU_TRANS_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_MENU_TRANS_CODE on tsys_menu(trans_code,sub_trans_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_MENU_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_MENU_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_MENU_NAME on tsys_menu(menu_name); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_MENU_PCODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_MENU_PCODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_MENU_PCODE on tsys_menu(parent_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_MENU_TREEIDX';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_MENU_TREEIDX';
 END IF;
END;
/
commit;
create index INDX_BIZ_MENU_TREEIDX on tsys_menu(tree_idx); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_MENU_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_MENU_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_MENU_CODE on tsys_menu(menu_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICE_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICE_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICE_NAME on tsys_office(office_name); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICE_BRANCH_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICE_BRANCH_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICE_BRANCH_CODE on tsys_office(branch_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICE_DEP_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICE_DEP_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICE_DEP_CODE on tsys_office(dep_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICE_PCODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICE_PCODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICE_PCODE on tsys_office(parent_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICEUSER_USERID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICEUSER_USERID';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICEUSER_USERID on tsys_office_user(user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_OFFICEUSER_OFFICECODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_OFFICEUSER_OFFICECODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_OFFICEUSER_OFFICECODE on tsys_office_user(office_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_PARAM_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_PARAM_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_PARAM_NAME on tsys_parameter(param_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_PARAM_VALUE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_PARAM_VALUE';
 END IF;
END;
/
commit;
create index INDX_BIZ_PARAM_VALUE on tsys_parameter(param_value); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_PARAM_ORG';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_PARAM_ORG';
 END IF;
END;
/
commit;
create index INDX_BIZ_PARAM_ORG on tsys_parameter(rel_org); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_PARAM_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_PARAM_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_PARAM_CODE on tsys_parameter(param_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLE_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLE_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLE_NAME on tsys_role(role_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLE_CREATOR';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLE_CREATOR';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLE_CREATOR on tsys_role(creator); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLERIGHT_TRANS_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLERIGHT_TRANS_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLERIGHT_TRANS_CODE on tsys_role_right(trans_code,sub_trans_code); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLERIGHT_ROLE_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLERIGHT_ROLE_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLERIGHT_ROLE_CODE on tsys_role_right(role_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLERIGHT_FLAG';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLERIGHT_FLAG';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLERIGHT_FLAG on tsys_role_right(right_flag);



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLEUSER_FLAG';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLEUSER_FLAG';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLEUSER_FLAG on tsys_role_user(right_flag); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ROLEUSER_RU';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ROLEUSER_RU';
 END IF;
END;
/
commit;
create index INDX_BIZ_ROLEUSER_RU on tsys_role_user(user_code,role_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_SBUTRANS_TRANS_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_SBUTRANS_TRANS_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_SBUTRANS_TRANS_CODE on tsys_subtrans(trans_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_SBUTRANS_SUB_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_SBUTRANS_SUB_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_SBUTRANS_SUB_CODE on tsys_subtrans(sub_trans_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_TRANS_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_TRANS_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_TRANS_NAME on tsys_trans(trans_name); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USER_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USER_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_USER_NAME on tsys_user(user_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USER_BRANCH_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USER_BRANCH_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_USER_BRANCH_CODE on tsys_user(branch_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USER_DEP_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USER_DEP_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_USER_DEP_CODE on tsys_user(dep_code); 



DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USER_REMARK';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USER_REMARK';
 END IF;
END;
/
commit;
create index INDX_BIZ_USER_REMARK on tsys_user(remark); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERRIGHT_TRANS_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERRIGHT_TRANS_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERRIGHT_TRANS_CODE on tsys_user_right(trans_code,sub_trans_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERRIGHT_USER_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERRIGHT_USER_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERRIGHT_USER_ID on tsys_user_right(user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERRIGHT_FLAG';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERRIGHT_FLAG';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERRIGHT_FLAG on tsys_user_right(right_flag); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERMSG_TITLE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERMSG_TITLE';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERMSG_TITLE on tsys_user_message(msg_title); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERMSG_SEND';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERMSG_SEND';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERMSG_SEND on tsys_user_message(send_user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERMSG_RED';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERMSG_RED';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERMSG_RED on tsys_user_message(msg_isred); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USERMSG_TEXT';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USERMSG_TEXT';
 END IF;
END;
/
commit;
create index INDX_BIZ_USERMSG_TEXT on tsys_user_message(msg_content); 
