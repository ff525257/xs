package com.fast.fastxs.mvvm;

import com.fast.fastxs.config.BaseConfig;
import com.fast.fastxs.controlcenter.ControlCenter;
import com.fast.fastxs.http.HttpObserver;
import com.fast.fastxs.http.XoKCall;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class XsBaseModel {
    private ArrayList<XoKCall> mCallList = new ArrayList<XoKCall>(0);

    /**
     * 普通请求不带ID，采用默认的回调函数
     * @param url
     * @param method
     * @param aOb
     * @param header
     * @param body
     * @param isShowAlert
     */
    public void requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, HashMap<String, String> header, HashMap<String, String> body, boolean isShowAlert) {
        requestHttp(url, method, aOb, -1, BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, header, body, isShowAlert);
    }

    /**
     * 普通请求带ID，采用默认的回调函数
     * @param url
     * @param method
     * @param aOb
     * @param id 区分请求
     * @param header
     * @param body
     * @param isShowAlert
     */
    public void requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, int id, HashMap<String, String> header, HashMap<String, String> body, boolean isShowAlert) {
        requestHttp(url, method, aOb, id, BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, header, body, isShowAlert);
    }

    /**
     * 普通请求带ID，可设置成功和失败回调
     * @param url
     * @param method
     * @param aOb
     * @param id
     * @param successCallBackMethod
     * @param httpErrorCodeCallBack
     * @param header
     * @param body
     * @param isShowAlert
     */
    public void requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, int id, String successCallBackMethod, String httpErrorCodeCallBack, HashMap<String, String> header, HashMap<String, String> body, boolean isShowAlert) {
        requestHttpComm(url, method, aOb, successCallBackMethod, httpErrorCodeCallBack, isShowAlert, id, header, body);
    }

    /**
     * 普通请求带ID，可设置成功回调，失败采用默认
     * @param url
     * @param method
     * @param aOb
     * @param id
     * @param successCallBackMethod
     * @param header
     * @param body
     * @param isShowAlert
     */
    public void requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, int id, String successCallBackMethod, HashMap<String, String> header, HashMap<String, String> body, boolean isShowAlert) {
        requestHttpComm(url, method, aOb, successCallBackMethod, BaseConfig.Http.DEFHTTPERRMETHOD, isShowAlert, id, header, body);
    }


    /**
     * 所有普通网络请求最终函数
     *
     * @param url
     * @param method
     * @param aOb
     * @param isShowAlert
     * @param id
     * @param header
     * @param body
     */
    private void requestHttpComm(String url, BaseConfig.Http.Method method, HttpObserver aOb, String successCallBackMethod, String httpErrorCodeCallBack, boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        XoKCall call = ControlCenter.getInstanse().requestHttpWithCallBack(url, method, aOb, successCallBackMethod, httpErrorCodeCallBack, isShowAlert, id, header, body);
        mCallList.add(call);
    }

    public void onDestroy() {
        onDestroyCall();
    }

    private void onDestroyCall() {
        for (XoKCall xcall : mCallList) {
            if (xcall.getCall() != null) {
                xcall.getCall().cancel();
            }
        }
    }
}
