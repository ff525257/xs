package com.wh.core.mvvm;

import com.wh.core.common.util.LogUtils;
import com.wh.core.common.util.StringUtils;
import com.wh.core.controlcenter.ControlCenter;
import com.wh.core.http.HttpObserver;
import com.wh.core.http.XoKCall;
import com.wh.core.mvvm.ModelView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseModel{
    private ArrayList<XoKCall> mCallList = new ArrayList<XoKCall>(0);
    private static String TAG = "BaseModel";

    /**
     * 在ModelView里面回调
     *
     * @param callBackMethod
     * @param resultData
     * @param id
     */
    private void callBackMethod(ModelView mv,String callBackMethod, Object resultData, int id) {
        if (mv == null) {
            LogUtils.w(TAG, "mv is null");
            return;
        }
        Method callbackMethod = null;
        try {
            // 回调
            callbackMethod = mv.getClass().getMethod("model" + StringUtils.fristToUpString(callBackMethod), Object.class, Integer.class);
            callbackMethod.invoke(mv, resultData, id);
        } catch (Exception e) {
            LogUtils.w(TAG, "Exception ", e);
        }
    }

    public void requestHttp(String url, String method, HttpObserver aOb, HashMap<String, String> header, HashMap<String, String> body) {
        requestHttp(url, method, aOb, true, -1, header, body);
    }

    public void requestHttp(String url, String method, HttpObserver aOb, int id, HashMap<String, String> header, HashMap<String, String> body) {
        requestHttp(url, method, aOb, true, id, header, body);
    }

    public void requestHttp(String url, String method, HttpObserver aOb,  boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        requestHttpComm(url, method, aOb, isShowAlert, id, header, body);
    }

    /**
     * 所有网络请求最终函数
     * @param url
     * @param method
     * @param aOb
     * @param isShowAlert
     * @param id
     * @param header
     * @param body
     */
    private void requestHttpComm(String url, String method, HttpObserver aOb,boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        XoKCall call = ControlCenter.getInstanse().requestHttp(url, method, aOb, isShowAlert, id, header, body);
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
