package com.oasis.tmsv5.common.enums.type;

import java.io.InputStream;
import java.util.Properties;

public enum UnitType {
    POINT, WEIGHT_UNIT, VOLUME_UNIT, PIECE_UNIT;

    private static Properties property;

    static {
        property = new Properties();
        try {
            InputStream inputStream = UnitType.class.getResourceAsStream("/genitrans-presentation.properties");
            property.load(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getNameCN() {
        return property.getProperty(this.name());
    }
}
