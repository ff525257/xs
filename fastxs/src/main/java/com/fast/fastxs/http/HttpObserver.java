package com.fast.fastxs.http;

import java.util.HashMap;

/**
 * id为每个请求的唯一标识
 */
public interface HttpObserver {
    /**
     * 对应ControlCenter.requestHttp正常返回数据
     *
     * @param ob 通信返回的数据
     */
    public void resposeContent(Object ob, int id, HashMap<String, String> body);

    /**
     * 请求失败错误情况处理
     *
     * @param code
     */
    public void resposeResposeCode(int code, int id, HashMap<String, String> body);


    /**
     * http请求callback之前的前拦截器(无http error code异常)</p>
     * <p>
     * 主要用于拦截服务端返回的自定义异常error_message</p>
     *
     * @param resultObj
     * @return 是否终止当前的回调进度    true:终止进度     false：继续进度
     */
    public boolean httpRequestCallBackPre(Object resultObj, int id);


    /**
     * http请求callback之前的后拦截器(无http error code异常)</p>
     *
     * @param resultObj
     * @return 是否终止当前的回调进度    true:终止进度     false：继续进度
     */
    public boolean httpRequestCallBackAfter(Object resultObj, int id, HashMap<String, String> body);


    /**
     * 显示通讯dialog
     */
    public void showAlert(boolean isShowAlert, int id);

    /**
     * 隐藏通讯dialog
     */
    public void hideAlert(int id);

    /**
     * 里面尽量不要做耗时操作,可能会导致Halder堵塞
     * @param current
     * @param total
     * @param id
     * @param body
     */
    public void onProgress(long current, long total, int id, HashMap<String, String> body);


}
