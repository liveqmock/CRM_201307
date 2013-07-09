package com.oasis.tmsv5.util.properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class GBKDispHelper {
    private static final String COFIG_FILE = "gbkDisp.properties";

    private static Configuration config;

    private static GBKDispHelper instance = new GBKDispHelper();

    private GBKDispHelper() {
        try {
            config = new PropertiesConfiguration(COFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GBKDispHelper getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return config.getString(key);
    }
    
    public String[] getValueArray(String key){
        return  config.getStringArray(key);
    }
}