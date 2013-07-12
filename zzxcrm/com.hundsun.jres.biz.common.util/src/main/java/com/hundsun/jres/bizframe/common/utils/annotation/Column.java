/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : Column.java
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
package com.hundsun.jres.bizframe.common.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * 可选,列名(默认值是属性名)
	 */
	String name() default "";
	/**
	 * 可选,是否在该列上设置唯一约束(默认值false)
	 */
    boolean unique() default false;
    /**
     * 可选,是否设置该列的值可以为空(默认值false)
     */
    boolean nullable() default true;
    /**
     * 可选,该列是否作为生成的insert语句中的一个列(默认值true)
     */
    boolean insertable() default true;
    /**
     * 可选,该列是否作为生成的update语句中的一个列(默认值true)
     */
    boolean updatable() default true;
    /**
     * 可选,为这个特定列覆盖SQL DDL片段 (这可能导致无法在不同数据库间移植)
     */
    String columnDefinition() default "";
    /**
     * 可选,定义对应的表(默认为主表)
     */
    String table() default "";
    /**
     * 可选,列长度(默认值255)
     */
    int length() default 255;
    /**
     * 可选,列十进制精度(decimal precision)(默认值0)
     */
    int precision() default 0;
    /**
     * 可选,如果列十进制数值范围(decimal scale)可用,在此设置(默认值0)
     */
    int scale() default 0;
}
