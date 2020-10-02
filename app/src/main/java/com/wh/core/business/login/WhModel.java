package com.wh.core.business.login;

import com.fast.fastxs.http.HttpObserver;
import com.fast.fastxs.mvvm.BaseModel;

public class WhModel extends BaseModel {
    public static final int LOGIN_ID = 0x01;

    public void login(String userName, String password, String method, HttpObserver aOb) {
        requestHttp("url", method, aOb, LOGIN_ID, null, null);
    }
}
