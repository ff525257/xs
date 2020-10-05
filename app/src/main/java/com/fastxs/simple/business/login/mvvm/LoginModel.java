package com.fastxs.simple.business.login.mvvm;

import com.fast.fastxs.config.BaseConfig;
import com.fast.fastxs.http.HttpObserver;
import com.fast.fastxs.mvvm.XsBaseModel;

public class LoginModel extends XsBaseModel {
    public static final int LOGIN_ID = 0x01;

    public void login(String userName, String password, String callbackMethod, HttpObserver aOb) {
        requestHttp("http://wthrcdn.etouch.cn/weather_mini?citykey=101070101", BaseConfig.Http.Method.GET, aOb, LOGIN_ID, callbackMethod, null, null, true);
    }
}
