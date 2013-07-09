package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaron Chen
 */
public enum CustomerPaymentMethod {

    WEEK, MONTH, BYBILL, PREPAY, POSTPAY;

    private final String PREFIX = "PAYMENT_METHOD_";

    public String getKey() {
        return PREFIX + this.name();
    }

    public static List<String> getKeyList() {
        List<String> list = new ArrayList<String>();
        CustomerPaymentMethod[] methods = CustomerPaymentMethod.values();
        for (CustomerPaymentMethod method : methods) {
            list.add(method.getKey());
        }
        return list;
    }

}
