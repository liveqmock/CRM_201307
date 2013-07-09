package com.oasis.tmsv5.util.tools;

import org.apache.commons.lang.ArrayUtils;

public class LogMessageUtil {

    public static final String DEFALUT_JOIN = "=";

    public static final String DEFALUT_SPLIT = ",";

    public static String LINE_SEPARATOR = "" + (char) 13 + (char) 10;

    private static <T> String format(String key, T value, String join) {
        if (join == null) {
            join = LogMessageUtil.DEFALUT_JOIN;
        }
        return key + join + value;
    }

    public static <T> String format(String key, T value) {
        return format(key.toUpperCase(), value, null);
    }

    public static <T> String format(String prefixMsg, String key, T value) {
        return prefixMsg + ": " + format(key.toUpperCase(), value, null);
    }

    public static String format(String prefixMsg, String[][] keyVals) {

        StringBuilder keyValsStr = null;
        if (keyVals != null && keyVals.length > 0) {
            keyValsStr = new StringBuilder();
            for (int i = 0; i < keyVals.length; i++) {
                keyValsStr.append(keyVals[i][0] + DEFALUT_JOIN + keyVals[i][1]);
                if (i != keyVals.length - 1)
                    keyValsStr.append(DEFALUT_SPLIT);
            }
        }

        return prefixMsg + ": " + ((keyValsStr != null) ? keyValsStr.toString() : "");
    }

    public static String printStack(Throwable e) {
        String detailMsg = e.getMessage();
        StringBuilder sb = new StringBuilder();
        sb.append(detailMsg);
        sb.append(LogMessageUtil.LINE_SEPARATOR);
        
        if (e.getCause() == null) {
            sb.append(ArrayUtils.toString(e.getStackTrace()).replaceAll(",", LogMessageUtil.LINE_SEPARATOR));
            return sb.toString();
        }
        StringBuilder sbCauseBy = new StringBuilder();
        while (e.getCause() != null) {             
            sbCauseBy.insert(0,getStack(e.getCause()));
            sbCauseBy.insert(0,"Caused by:");            
            e = e.getCause();
        }
        return sb.append(sbCauseBy).toString();

    }

    private static String getStack(Throwable e) {
        return ArrayUtils.toString(e.getStackTrace()).replaceAll(",", LogMessageUtil.LINE_SEPARATOR);
    }

    /**
     * 
     * Test code
     */
    public static void main(String... s) {
        String[][] keyVals = new String[][] { { "key1", "val1" }, { "key2", "val2" } };

        System.out.print(LogMessageUtil.format("TEST", keyVals));

    }

}
