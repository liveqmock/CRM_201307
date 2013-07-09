package com.oasis.tmsv5.util.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class BarCodeHelper {
    public final static String PRE_PINCODE = "P";

    public final static String PRE_BARCODE = "S";

    public static String getBarcodeByPinCode(String pinCode) {
        if (!pinCodeValidate(pinCode)) {
            return pinCode;
        }
        // P1000003
        String basis = StringUtils.substring(pinCode, 1, pinCode.length() - 1);
        /**
         *bar code producing rule
         */
        int sum = 0;
        for (int i = 0; i < basis.length(); i++) {
            if (i % 2 == 0) {
                sum = sum + (Integer.parseInt(String.valueOf(basis.charAt(i))) * 3);
            } else {
                sum = sum + (Integer.parseInt(String.valueOf(basis.charAt(i))));
            }
        }
        int mod = (10 - (sum % 10)) % 10;

        String barCode = PRE_BARCODE + String.valueOf(mod) + basis;

        return barCode;
    }

    public static String getBarcode(String basisCode) {
        return getBarcodeByPinCode(getPinCode(basisCode));
    }

    public static String getPinCode(String basisCode) {
        if (basisCode == null || basisCode.length() > 6) {
            return basisCode;
        }
        Random random = new Random();
        int rm = random.nextInt(10);
        return PRE_PINCODE + StringUtils.leftPad(basisCode, 6, "0") + String.valueOf(rm);
    }
    
    public static String getPinCodeByBarcode(String barCode) {
        if (StringUtils.isEmpty(barCode) || barCode.length() != 8) {
            return barCode;
        }

        Random random = new Random();
        int rm = random.nextInt(10);
        return PRE_PINCODE + StringUtils.right(barCode, 6) + String.valueOf(rm);
    }

    private static boolean pinCodeValidate(String pinCode) {
        if (StringUtils.isEmpty(pinCode) || pinCode.length() != 8) {
            return false;
        }

        return true;
    }

    public static void main(String... s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("please input something like 123536:");
        String basis = br.readLine();
       
        System.out.println(getPinCode(basis));
        System.out.println(getBarcode(basis));
        
        System.out.println(getPinCodeByBarcode(getBarcode(basis)));
        

    }
}
