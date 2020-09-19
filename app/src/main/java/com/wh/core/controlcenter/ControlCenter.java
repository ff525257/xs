package com.wh.core.controlcenter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.wh.core.BaseApplication;
import com.wh.core.common.util.DeviceUtils;
import com.wh.core.common.util.JSONUtil;
import com.wh.core.common.util.LogUtils;
import com.wh.core.config.BaseConfig;
import com.wh.core.http.BaseHttpEngine;
import com.wh.core.http.HttpEngine;
import com.wh.core.http.HttpObserver;
import com.wh.core.http.XhttpCallback;
import com.wh.core.http.XoKCall;

public class ControlCenter {
    private static final String TAG = "ControlCenter";
    private static ControlCenter instanse = null;
    /**
     * 用于普通通信
     */
    private HttpEngine httpEngine = null;// 发送通信的请求普通引擎

    /**
     * 获取控制中心的对象
     *
     * @return
     */
    public static ControlCenter getInstanse() {
        if (instanse == null) {
            instanse = new ControlCenter();
        }
        return instanse;
    }

    private ControlCenter() {
        httpEngine = new HttpEngine();
    }


    public XoKCall requestHttp(String url, String method, HttpObserver aOb, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, true, BaseConfig.Http.REQ_DEF_ID, header, body);
    }


    public XoKCall requestHttp(String url, String method, HttpObserver aOb, int id, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, true, id, header, body);
    }

    public XoKCall requestHttp(String url, String method, HttpObserver aOb, boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, isShowAlert, id, header, body);
    }

    public XoKCall requestHttpWithCallBack(String url, String method, HttpObserver handObject, String successCallBackMethod, String httpErrorCodeCallBack, boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {

        return requestHttpWithCallBackInner(url, method,
                handObject, successCallBackMethod, httpErrorCodeCallBack,
                httpEngine, isShowAlert, id, header, body);

    }

    private XoKCall requestHttpWithCallBackInner(final String url, final String method, final HttpObserver handObject, final String successCallBackMethod,
                                                 final String httpErrorCodeCallBack, final BaseHttpEngine engine,
                                                 boolean isShowAlert, final int id, final HashMap<String, String> header, final HashMap<String, String> resBody) {
        XoKCall cancelable = null;
        if (!DeviceUtils.isConnected(BaseApplication.getApplicationContext2()) || handObject == null) {
            return cancelable;
        }
        handObject.showAlert(isShowAlert, id);
        try {
            final Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put(BaseConfig.Http.HTTP_OBSERVER, handObject);
            cancelable = engine.httpSend(url, method, header, resBody, new XhttpCallback() {

                @Override
                public void onResponse(String body, int code) {
                    if (code == BaseConfig.Http.HTTP_SUCCESS_CODE) {
                        String str = body;
                        LogUtils.i(TAG, "response:" + str);
                        Object resultData = null;
                        try {
                            resultData = JSONUtil.parseJsonResponse(str);
                        } catch (JSONException e) {
                            LogUtils.w(TAG, "parseJsonResponse error", e);
                        }
                        resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, resultData);
                        resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, successCallBackMethod);
                    } else {
                        resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, code);// 结果数据
                        resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, httpErrorCodeCallBack);
                    }
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_ID, id);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_BOY, resBody);
                    Message msg = handler.obtainMessage(
                            BaseConfig.Http.HTTP_SECCESS, resultMap);
                    handler.sendMessage(msg);
                }

                @Override
                public void onProgress(long current, long total) {
                    LogUtils.d(TAG, "onProgress  total:" + total + " current:" + current);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_CURRENT, current);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_TOTAL, total);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_ID, id);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_BOY, resBody);
                    Message msg = handler.obtainMessage(
                            BaseConfig.Http.HTTP_PROGRESS, resultMap);
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(Exception e, int code) {
                    LogUtils.d(TAG, "onFailure:" + e);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, BaseConfig.Http.HTTP_IO_ERR);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, httpErrorCodeCallBack);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_ID, id);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_BOY, resBody);
                    Message msg = handler.obtainMessage(
                            BaseConfig.Http.HTTP_ERR, resultMap);
                    handler.sendMessage(msg);
                }
            });

        } catch (Exception e) {
            LogUtils.w(TAG, "httpEngine request error", e);
        }
        return cancelable;

    }


    /**
     * 处理httpengin 返回的数据
     */
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            // 消息对象
            HashMap<String, Object> msgObj = (HashMap<String, Object>) msg.obj;
            HttpObserver ob = (HttpObserver) msgObj
                    .get(BaseConfig.Http.HTTP_OBSERVER);
            if (null == ob)
                return;
            int id = Integer.parseInt(msgObj.get(BaseConfig.Http.HTTP_KEY_REQ_ID).toString());
            HashMap<String, String> body = (HashMap<String, String>) msgObj.get(BaseConfig.Http.HTTP_KEY_REQ_BOY);
            switch (msg.what) {

                // 正常http请求数据返回
                case BaseConfig.Http.HTTP_SECCESS:
                    Object resultObj = msgObj.get(BaseConfig.Http.HTTP_KEY_RESULT_DATA);
                    String callBackMethod = (String) msgObj.get(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD);

                    LogUtils.d(TAG, "callBackMethod:" + callBackMethod);
                    /**
                     * 执行ob回调前拦截器
                     */
                    if (ob.httpRequestCallBackPre(resultObj, id)) {
                        break;
                    }

                    Method callbackMethod = null;
                    try {
                        if (resultObj instanceof HashMap) {
                            HashMap<String, Object> result = (HashMap<String, Object>) resultObj;
                            Object tmp = result.get(BaseConfig.Http.BODY);
                            if (tmp != null) {
                                resultObj = tmp;
                            }
                        }
                        // 回调
                        callbackMethod = ob.getClass().getMethod(callBackMethod,
                                Object.class, int.class, HashMap.class);
                        callbackMethod.invoke(ob, resultObj, id, body);
                    } catch (Exception e) {
                        LogUtils.w(TAG, "Exception ", e);
                    }

                    /**
                     * 执行ob回调后拦截器
                     */
                    if (ob.httpRequestCallBackAfter(resultObj, id, body)) {
                        break;
                    }

                    break;

                // 请求失败错误情况处理
                case BaseConfig.Http.HTTP_ERR:
                    Integer httpResultCode = (Integer) msgObj
                            .get(BaseConfig.Http.HTTP_KEY_RESULT_DATA);
                    String httpCodeBackMethod = (String) msgObj
                            .get(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD);
                    Method httpCodecCallbackMethod = null;
                    try {
                        // 回调
                        httpCodecCallbackMethod = ob.getClass().getMethod(
                                httpCodeBackMethod, int.class, int.class, HashMap.class);
                        httpCodecCallbackMethod.invoke(ob, httpResultCode, id, body);
                    } catch (Exception e) {
                        LogUtils.w(TAG, "Exception ", e);
                    }
                    break;
                case BaseConfig.Http.HTTP_PROGRESS:
                    Long total = (Long) msgObj.get(BaseConfig.Http.HTTP_KEY_RESULT_TOTAL);
                    Long current = (Long) msgObj.get(BaseConfig.Http.HTTP_KEY_RESULT_CURRENT);
                    ob.onProgress(current, total, id, body);
                    break;
                default:
                    break;
            }
            ob.hideAlert(id);
        }
    };

}
