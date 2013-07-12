/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : ConvertSupport.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 功能说明: 类型转换服务调用工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class ConvertSupport {

	/**
	 * 根据对象列表转成对象Map集合
	 * @param objs 对象数组集合
	 * @return List<Map> Map中存放对象属性KEY/VALUE
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static List<Map> convertPOJOtoMap(List objs) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		List<Map> maps = new ArrayList<Map>();
		for(Object obj:objs){
			Map oMap = BeanUtils.describe(obj);
			maps.add(oMap);
		}
		return maps;
	}

}

