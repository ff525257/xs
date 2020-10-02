package com.fast.fastxs.http;


import com.fast.fastxs.config.BaseConfig;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 描述:BaseHttpEngine
 * <p/>
 * <p>
 * 基于http的基础Engine
 * <p/>
 */
public abstract class BaseHttpEngine {

    protected boolean alertFlag = true; // 是否弹出通讯进度条

    /**
     * 方法功能说明：发送http请求，根据传回来的参数method的不同，判断调用httpGet或者httpPost方法
     */
    public abstract XoKCall httpSend(String url, String method, final HashMap<String, String> header, final HashMap<String, String> body, XhttpCallback callback)
            throws Exception;


    /**
     * 方法功能说明：把流转换成字符串
     *
     * @param
     * @return String
     * @see
     */
    public static String inputStream2String(InputStream inStream)
            throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return new String(outSteam.toByteArray(),
                    BaseConfig.DEFAULT_ENCODE);
        } finally {
            outSteam.close();// 读取完毕关闭输出流
        }

    }


}
