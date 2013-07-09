package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.List;

public enum SettlementMethod {
    DEFAULT, // 缺省
    WEIGHT, // 按重量
    VOLUME, // 按体积
    AMOUNT, // 按数量
    STANDARD, // 标准
    BIG, // 取大
    SMALL, // 取小
    HEAVY_LIGHT, INHERIT,
    // for display
    DISPLAY_LIST, PROFILE_LIST;

    public static List<SettlementMethod> getStatus4List(SettlementMethod listType) {
        List<SettlementMethod> list = new ArrayList<SettlementMethod>();
        switch (listType) {
        case DISPLAY_LIST:
            list.add(SettlementMethod.DEFAULT);
            list.add(SettlementMethod.WEIGHT);
            list.add(SettlementMethod.VOLUME);
            list.add(SettlementMethod.AMOUNT);
            list.add(SettlementMethod.STANDARD);
            list.add(SettlementMethod.BIG);
            list.add(SettlementMethod.SMALL);
            list.add(SettlementMethod.HEAVY_LIGHT);
            break;
        }
        return list;
    }
    
    public static List<SettlementMethod> getProfileList(SettlementMethod listType) {
        List<SettlementMethod> list = new ArrayList<SettlementMethod>();
        switch (listType) {
        case PROFILE_LIST:
            list.add(SettlementMethod.WEIGHT);
            list.add(SettlementMethod.VOLUME);
            list.add(SettlementMethod.AMOUNT);
            list.add(SettlementMethod.STANDARD);
            break;
        }
        return list;
    }
}
