update tsys_parameter param set param.param_regex='^[0-3]$' 
where param.param_code='passUnit';

update tsys_parameter param set param.param_regex='^[1-9]d*$' 
where param.param_code in('passValidity',
                           'maxWrongPass',
                           'default_menu_depth',
                           'desktop_start_menu_depth',
                           'desktop_navigation_menu_depth',
                           'openMaxTabNum','logoHeight','lockScreenInterval','msgPollInterval','cacheRefeshInterval');
                           
update tsys_parameter param set param.param_regex='^true|false$' 
where param.param_code in('shieldF5','shieldBcakSpace','login_has_validateCode','singleTab');


update tsys_parameter param set param.param_regex='^accordion|tree$' 
where param.param_code in('menuLoadModel');


update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.24';
commit;