package com.oasis.tmsv5.util.helper;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyHelper {
    private static final Log logger = LogFactory.getLog(PropertyHelper.class);

    public static Object getValue(Object obj, String key) {

        try {
            return PropertyUtils.getNestedProperty(obj, key);
        } catch (Exception e) {
            logger.error(key + " can not get value ");
            //logger.error(LogMessageUtil.printStack(e));
            return null;
        }
    }

    public static void setValue(Object obj, String key, Object value) {

        try {
            PropertyUtils.setProperty(obj, key, value);
        } catch (Exception e) {
            logger.error(key + " can not set value ");
            //logger.error(LogMessageUtil.printStack(e));
        }

    }

    public static void setNestedPropertyValue(Object obj, String key, Object value) {

        try {
            PropertyUtils.setNestedProperty(obj, key, value);
        } catch (Exception e) {
            logger.error(key + " can not set Nested value ");
            //logger.error(LogMessageUtil.printStack(e));
        }

    }
}
