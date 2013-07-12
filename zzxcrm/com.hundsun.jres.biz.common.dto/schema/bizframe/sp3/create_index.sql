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
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_USER_ORG_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_USER_ORG_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_USER_ORG_ID on tsys_user(org_id); 



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


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_ORG_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_ORG_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_ORG_ID on tsys_log(org_id); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_ORG_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_ORG_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_ORG_NAME on tsys_log(org_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_USER_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_USER_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_USER_ID on tsys_log(user_id); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_USER_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_USER_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_USER_NAME on tsys_log(user_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_TRANS';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_TRANS';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_TRANS on tsys_log(sub_trans_code,trans_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_LOG_CONTENT';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_LOG_CONTENT';
 END IF;
END;
/
commit;
create index INDX_BIZ_LOG_CONTENT on tsys_log(oper_contents); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORG_DIMENSION';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORG_DIMENSION';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORG_DIMENSION on tsys_organization(dimension); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORG_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORG_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORG_CODE on tsys_organization(org_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORG_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORG_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORG_NAME on tsys_organization(org_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORG_PATH';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORG_PATH';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORG_PATH on tsys_organization(org_path); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORGUSER_USER_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORGUSER_USER_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORGUSER_USER_ID on tsys_org_user(user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_ORGUSER_ORG_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_ORGUSER_ORG_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_ORGUSER_ORG_ID on tsys_org_user(org_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POS_NAME';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POS_NAME';
 END IF;
END;
/
commit;
create index INDX_BIZ_POS_NAME on tsys_position(position_name); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POS_PCODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POS_PCODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_POS_PCODE on tsys_position(parent_code); 

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POS_ORG_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POS_ORG_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_POS_ORG_ID on tsys_position(org_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POS_ROLE_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POS_ROLE_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_POS_ROLE_CODE on tsys_position(role_code); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POS_PATH';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POS_PATH';
 END IF;
END;
/
commit;
create index INDX_BIZ_POS_PATH on tsys_position(position_path); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POSUSER_USER_ID';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POSUSER_USER_ID';
 END IF;
END;
/
commit;
create index INDX_BIZ_POSUSER_USER_ID on tsys_pos_user(user_id); 


DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_INDEXES indx WHERE indx.index_name = 'INDX_BIZ_POSUSER_POS_CODE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop  index INDX_BIZ_POSUSER_POS_CODE';
 END IF;
END;
/
commit;
create index INDX_BIZ_POSUSER_POS_CODE on tsys_pos_user(position_code); 