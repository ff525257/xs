package com.wh.core.common.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XStringUtils {

    /**
     * 为http请求URL添加参数
     *
     * @param url    http请求url
     * @param params 参数键值对
     * @return url (带参数)
     */
    public static String addParamsToHttpUrl(String url, HashMap<String, String> params) {
        return addParamsToHttpUrl(url, params, true);
    }

    /**
     * 为http请求URL添加参数
     *
     * @param url    http请求url
     * @param params 参数键值对
     * @return url (带参数)
     */
    public static String addParamsToHttpUrl(String url, HashMap<String, String> params, boolean hasEnds) {
        if (params ==null || params.isEmpty()) {
            return url;
        }
        // 判断URL是否以问号结尾
        if (!url.endsWith("?") && hasEnds) {
            url += "?";
        }
        // 生成参数字符串
        StringBuffer paramString = new StringBuffer();

        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            paramString.append("&");
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            paramString.append(key + "=" + val);
        }

        url += paramString.toString();
        return url;
    }

    /**
     * 首字母大写
     */
    public static String fristToUpString(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }
}
