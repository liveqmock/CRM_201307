package com.oasis.tmsv5.service.helper;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class EnumDispHelper {
    private static final String COFIG_FILE = "enumDisp.properties";

    private static Configuration config;

    private static EnumDispHelper instance = new EnumDispHelper();

    private EnumDispHelper() {
        try {
            config = new PropertiesConfiguration(COFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EnumDispHelper getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return config.getString(key);
    }
}
