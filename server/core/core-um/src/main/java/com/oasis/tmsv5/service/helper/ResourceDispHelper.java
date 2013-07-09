package com.oasis.tmsv5.service.helper;

import java.text.MessageFormat;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ResourceDispHelper {
	private static final String COFIG_FILE = "resourceDisp.properties";

    private static Configuration config;

    private static ResourceDispHelper instance = new ResourceDispHelper();

    private ResourceDispHelper() {
        try {
            config = new PropertiesConfiguration(COFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResourceDispHelper getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return config.getString(key);
    }
    
    public static void main(String[] args) {
    	String val = ResourceDispHelper.getInstance().getValue("TRUCKRSJOB_MESSAGE");
    	Object[] obj = {"a","b","c","d","e"};
    	val = MessageFormat.format(val, obj);
    	System.out.println(val);
    }
}
