package com.oasis.tmsv5.util.tools;


import org.apache.commons.beanutils.PropertyUtils;

public class EasyUtil {

    public static <T> boolean isNull(T obj, String fieldPath) {

        try {
            return PropertyUtils.getNestedProperty(obj, fieldPath) == null ? true : false;
        } catch (Exception e) {
            return true;
        }

    }

    public static <T> boolean isEmpty(T obj) {

        try {
            if (obj.equals(""))
                return true;
        } catch (NullPointerException e) {
            return true;
        }

        return false;
    }

}
