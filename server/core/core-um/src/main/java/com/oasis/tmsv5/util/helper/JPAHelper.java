package com.oasis.tmsv5.util.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oasis.tmsv5.util.exception.NotyetSupportedException;

public class JPAHelper {

    private static final Log logger = LogFactory.getLog(JPAHelper.class);

    private static final String PRIMARY_KEY = "id";

    private static final String VERSION_KEY = "lockVersion";

    public static <T> String getPrimaryKey(Class<T> type) {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (field.getType() != Long.class) {
                    logger.error("Only Long type primary key supported.");
                    throw new NotyetSupportedException("can't get primary key.");
                }
                return field.getName();
            }
        }
        if (type.getSuperclass() != null) {
            return getPrimaryKey(type.getSuperclass());
        }
        return PRIMARY_KEY;
    }

    public static <T> String getTableName(Class<T> type) {
        if (!type.isAnnotationPresent(Table.class)) {
            throw new NotyetSupportedException("can't get table name.");
        }
        return type.getAnnotation(Table.class).name();
    }

    public static <T> String getSequenceName(Class<T> type) {
        if (!type.isAnnotationPresent(SequenceGenerator.class)) {
            throw new NotyetSupportedException("can't get sequence name.");
        }
        return type.getAnnotation(SequenceGenerator.class).name();
    }

    public static <T> String getVersionName(Class<T> type) {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Version.class)) {
                if (field.getType() != int.class) {
                    logger.error("Only Long type primary key supported.");
                    throw new NotyetSupportedException("can't get primary key.");
                }
                return field.getName();
            }
        }
        if (type.getSuperclass() != null) {
            return getVersionName(type.getSuperclass());
        }
        return VERSION_KEY;
    }

    public static ModelColumn getColumnByField(Field field, Object obj) {
        ModelColumn column = new ModelColumn();
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            column.setName(name);
            Object value = PropertyHelper.getValue(obj, field.getName());
            column.setValue(value);
        } else {
            column.setName(field.getName());
            Object value = PropertyHelper.getValue(obj, field.getName());
            column.setValue(value);
        }
        column.setField(field.getName());
        return column;
    }

    public static List<ModelColumn> getColumnsByObj(Object obj) {
        List<ModelColumn> ret = new ArrayList<ModelColumn>();
        List<Field> fieldList =getAllFields(obj.getClass());
        for (Field field : fieldList) {
            ret.add(getColumnByField(field, obj));
        }
        return ret;
    }
    
    public static List<Field> getAllFields(Class<?> type){
        List<Field> fieldList = new ArrayList<Field>();       
        while (type.getSuperclass() != null) {
            for (Field field : type.getDeclaredFields()) {
                if("serialVersionUID".equalsIgnoreCase(field.getName())){
                    continue;
                }
                if(field.isAnnotationPresent(Transient.class)){
                    continue;
                }
                
                fieldList.add(field);
            }
            type = type.getSuperclass();
        }        
       
        return fieldList;
    }

    public static <T> boolean hasField(Class<T> type, String fieldName) {
        for (Field field : type.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return true;
            }
        }
        if (type.getSuperclass() != null) {
            return hasField(type.getSuperclass(), fieldName);
        }
        return false;
    }

    

}
