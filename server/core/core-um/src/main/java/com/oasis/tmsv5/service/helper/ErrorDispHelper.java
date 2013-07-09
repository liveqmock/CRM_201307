package com.oasis.tmsv5.service.helper;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ErrorDispHelper {
	private static final String COFIG_FILE = "errorDisp.properties";

    private static Configuration config;

    private static ErrorDispHelper instance = new ErrorDispHelper();

    private ErrorDispHelper() {
        try {
            config = new PropertiesConfiguration(COFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ErrorDispHelper getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return config.getString(key);
    }
}
