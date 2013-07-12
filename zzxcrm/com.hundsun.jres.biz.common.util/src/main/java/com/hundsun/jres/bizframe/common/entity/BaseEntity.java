/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : BaseEntity.java
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
package com.hundsun.jres.bizframe.common.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.hundsun.jres.bizframe.common.utils.annotation.AnnotationUtil;

/**
 * 功能说明: 实体对象基类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分页开始记录号
	 */
	private Integer start;
	
	/**
	 * 每页限制条数
	 */
	private Integer limit;

	/**
	 * 获取实体标识
	 * 
	 * @return 实体标识
	 */
	public Serializable getId() {
		try {
			String id = AnnotationUtil.getIdName(this.getClass());
			Field field = this.getClass().getDeclaredField(id);
			field.setAccessible(true);
			return (Serializable) field.get(this);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 设置实体标识
	 * @param id	实体标识
	 */
	public void setId(Serializable id) {
		try {
			String idName = AnnotationUtil.getIdName(this.getClass());
			Field field = this.getClass().getDeclaredField(idName);
			field.setAccessible(true);
			field.set(this,id);
		} catch (Exception e) {			
		}
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * 输出缺省值
	 */
	public String toString() {
	    StringBuilder result = new StringBuilder();
	    String newLine = System.getProperty("line.separator");
	    result.append(newLine);
	    result.append( this.getClass().getName() );
	    result.append( " Object {" );
	    result.append(newLine);

	    //determine fields declared in this class only (no fields of superclass)
	    Field[] fields = this.getClass().getDeclaredFields();
	    //print field names paired with their values
	    for ( Field field : fields  ) {
    	  field.setAccessible(true);
	      result.append("  ");
	      try {
	        result.append( field.getName() );
	        result.append(": ");
	        //requires access to private field:
	        result.append( field.get(this) );
	      }
	      catch(Exception e){
	    	e.printStackTrace();
	      }
	      result.append(newLine);
	    }
	    result.append("}");

	    return result.toString();
	  }

	
	
}
