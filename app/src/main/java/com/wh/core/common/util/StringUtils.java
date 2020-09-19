package com.wh.core.common.util;

public class StringUtils {
    /**
     * 首字母大写
     */
    public static String fristToUpString(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }
}
