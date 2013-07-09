package com.oasis.tmsv5.util.query;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Cache {
    public static final String FIND = "find";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String DEL_LIST = "del_list";
    public static final String UPDATE_LIST = "update_list";
    
    public String action() default "";

}
