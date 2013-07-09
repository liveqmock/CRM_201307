package com.oasis.tmsv5.util.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oasis.tmsv5.util.tools.LogMessageUtil;

public class URLCoderHelper {

    private static final Log log = LogFactory.getLog(URLCoderHelper.class);

    public static String decode(String arg) {
        String ret = null;
        try {
            ret = URLDecoder.decode(arg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(LogMessageUtil.printStack(e));
        }
        return ret;
    }

    public static String encode(String arg) {
        String ret = null;
        try {
            ret = URLEncoder.encode(arg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(LogMessageUtil.printStack(e));
        }
        return ret;
    }
}
