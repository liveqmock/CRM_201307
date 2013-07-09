package com.oasis.tmsv5.util.tools;

import com.oasis.tmsv5.util.exception.EnumTypeNotFoundException;


public class EnumTaker {

    public static <T extends Enum<T>> T getEnumByName(Class<T> enumType, String name) throws EnumTypeNotFoundException {

        T result = null;
        try {
            result = Enum.valueOf(enumType, name.toUpperCase());
        } catch (Exception e) {
            throw new EnumTypeNotFoundException(name + " can not be cast to " + enumType.getName());
        }
        return result;
    }

    public static <T extends Enum<T>> T[] getEnums(Class<T> enumType) {
        return enumType.getEnumConstants();
    }
}
