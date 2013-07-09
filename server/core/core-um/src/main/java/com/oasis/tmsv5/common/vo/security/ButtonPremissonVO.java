package com.oasis.tmsv5.common.vo.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按钮权限对象
 * @author bl00718
 *
 */
public class ButtonPremissonVO implements Serializable{

    private static final long serialVersionUID = -3976406270347957991L;
    
    /**
     * key为模块名称,list保存允许操作的按钮名字
     */
	Map<String,List<String>> premission =new HashMap<String, List<String>>();

    public Map<String, List<String>> getPremission() {
        return premission;
    }

    public void setPremission(Map<String, List<String>> premission) {
        this.premission = premission;
    }
	
}
