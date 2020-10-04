package com.fast.fastxs.config;

public class BaseConfig {
    public static final String DEFAULT_ENCODE = "uft-8";

    public static class Http {

        public enum Method{
            POST,GET
        }

        public static final String KEY_FILENAME = "fileName";
        public static final String KEY_FILEPATH = "filePath";
        public static final String JSONTYPE = "jsonType";
        public static final String FILETYPE = "fileType";
        public static final String DOWNLOADTYPE = "fileDownloadType";
        public static final String DOWNLOADDIR = "fileDownloadDir";
        /**
         * 默认http请求成功回调方法名称
         */
        public static final String DEFHTTPSEUUCESSMETHOD = "resposeContent";
        /**
         * 默认http请求失败回调方法名称
         */
        public static final String DEFHTTPERRMETHOD = "resposeResposeCode";

        public static final int HTTP_SECCESS = 0X01;
        public static final int HTTP_ERR = 0X02;
        public static final int HTTP_PROGRESS = 0X03;

        public static final String HTTP_KEY_RESULT_TOTAL = "total";
        public static final String HTTP_KEY_RESULT_CURRENT = "current";


        public static final String BODY = "data";
        public static final String ERR_MSG = "msg";
        public static final String CODE = "code";

        /**
         * 请求ID，主要是做区别连接
         */
        public static final String HTTP_KEY_REQ_ID = "req_id";
        /**
         * 请求body参数
         */
        public static final String HTTP_KEY_REQ_BOY = "req_boy";

        public static final String HTTP_OBSERVER = "observer";
        public static final String HTTP_KEY_RESULT_DATA = "resultData";
        public static final String HTTP_KEY_CALL_BACK_METHOD = "callBackMethod";

        /**
         * 默认id
         */
        public static final int REQ_DEF_ID = 0x01;
        /**
         * http成功值
         */
        public static final int HTTP_SUCCESS_CODE = 200;
        public static final int HTTP_IO_ERR = 1000;
        public static final int HTTP_PARSE_ERR = 1001;
        public static final int CONNECTTIMEOUT  = 20*1000;
    }
}
