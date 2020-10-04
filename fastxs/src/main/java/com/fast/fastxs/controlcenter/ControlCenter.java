package com.fast.fastxs.controlcenter;

import android.os.Handler;
import android.os.Message;


import com.fast.fastxs.config.BaseConfig;
import com.fast.fastxs.http.BaseHttpEngine;
import com.fast.fastxs.http.HttpEngine;
import com.fast.fastxs.http.HttpObserver;
import com.fast.fastxs.http.XhttpCallback;
import com.fast.fastxs.http.XoKCall;
import com.fast.fastxs.util.JSONUtil;
import com.fast.fastxs.util.LogUtils;

import org.json.JSONException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControlCenter {
    private static final String TAG = "ControlCenter";
    private static ControlCenter instanse = null;
    /**
     * 用于普通通信
     */
    private HttpEngine httpEngine = null;

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

    private H handler = new H();

    public XoKCall requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, true, BaseConfig.Http.REQ_DEF_ID, header, body);
    }


    public XoKCall requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, int id, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, true, id, header, body);
    }

    public XoKCall requestHttp(String url, BaseConfig.Http.Method method, HttpObserver aOb, boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBack(url, method, aOb,
                BaseConfig.Http.DEFHTTPSEUUCESSMETHOD, BaseConfig.Http.DEFHTTPERRMETHOD, isShowAlert, id, header, body);
    }

    public XoKCall requestHttpWithCallBack(String url, BaseConfig.Http.Method method, HttpObserver handObject, String successCallBackMethod, String httpErrorCodeCallBack, boolean isShowAlert, int id, HashMap<String, String> header, HashMap<String, String> body) {
        return requestHttpWithCallBackInner(url, method,
                handObject, successCallBackMethod, httpErrorCodeCallBack,
                httpEngine, isShowAlert, id, header, body);
    }

    private XoKCall requestHttpWithCallBackInner(final String url, final BaseConfig.Http.Method method, final HttpObserver handObject, final String successCallBackMethod,
                                                 final String httpErrorCodeCallBack, final BaseHttpEngine engine,
                                                 boolean isShowAlert, final int id, HashMap<String, String> header, HashMap<String, String> resBody) {
        XoKCall cancelable = null;
        if (handObject == null) {
            new IllegalAccessException("handObject can not is null.");
        }
        handObject.showAlert(isShowAlert, id);
        try {
            final Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put(BaseConfig.Http.HTTP_OBSERVER, handObject);
            resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_BOY, resBody);
            resultMap.put(BaseConfig.Http.HTTP_KEY_REQ_ID, id);
            //check header and body
            if (header == null) {
                header = new HashMap<>();
            }
            if (resBody == null) {
                resBody = new HashMap<>();
            }
            cancelable = engine.httpSend(url, method, header, resBody, new XhttpCallback() {

                @Override
                public void onResponse(String result, int code) {
                    if (code == BaseConfig.Http.HTTP_SUCCESS_CODE) {
                        LogUtils.i(TAG, "response:" + result);
                        Object resultData = null;
                        try {
                            resultData = JSONUtil.parseJsonResponse(result);
                            resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, resultData);
                            resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, successCallBackMethod);
                        } catch (JSONException e) {
                            LogUtils.w(TAG, "parseJsonResponse error", e);
                            resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, BaseConfig.Http.HTTP_PARSE_ERR);
                            resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, httpErrorCodeCallBack);
                        }

                    } else {
                        resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, code);// 结果数据
                        resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, httpErrorCodeCallBack);
                    }
                    Message msg = handler.obtainMessage(
                            BaseConfig.Http.HTTP_SECCESS, resultMap);
                    handler.sendMessage(msg);
                }

                @Override
                public void onProgress(long current, long total) {
                    LogUtils.d(TAG, "onProgress  total:" + total + " current:" + current);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_CURRENT, current);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_TOTAL, total);
                    Message msg = handler.obtainMessage(
                            BaseConfig.Http.HTTP_PROGRESS, resultMap);
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(Exception e, int code) {
                    LogUtils.d(TAG, "onFailure:" + e);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_RESULT_DATA, BaseConfig.Http.HTTP_IO_ERR);
                    resultMap.put(BaseConfig.Http.HTTP_KEY_CALL_BACK_METHOD, httpErrorCodeCallBack);

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

    private static class H extends Handler {
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
    }

}
