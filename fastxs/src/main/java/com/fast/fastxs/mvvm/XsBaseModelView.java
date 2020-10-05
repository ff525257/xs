package com.fast.fastxs.mvvm;

import android.view.View;

import com.fast.fastxs.http.HttpObserver;

import java.util.HashMap;

public abstract class XsBaseModelView<M extends XsBaseModel, V extends XsBaseViewRender> implements HttpObserver {

    protected M mBaseModel;
    protected V mBaseView;

    public abstract void init();

    public XsBaseModelView(M baseModel, V baseView) {
        this.mBaseModel = baseModel;
        this.mBaseView = baseView;
        //通知View层初始化
        mBaseView.initView();
        //初始化
        init();
    }

    public View getRootView() {
        return mBaseView.getRootView();
    }

    @Override
    public void resposeContent(Object ob, int id, HashMap<String, String> body) {

    }

    @Override
    public void resposeResposeCode(int code, int id, HashMap<String, String> body) {

    }

    @Override
    public boolean httpRequestCallBackPre(Object resultObj, int id) {
        return false;
    }

    @Override
    public boolean httpRequestCallBackAfter(Object resultObj, int id, HashMap<String, String> body) {
        return false;
    }

    @Override
    public void showAlert(boolean isShowAlert, int id) {
        if (isShowAlert) {
            mBaseView.showAlert(id);
        }
    }

    @Override
    public void hideAlert(int id) {
        mBaseView.hideAlert(id);
    }

    @Override
    public void onProgress(long current, long total, int id, HashMap<String, String> body) {

    }

}
