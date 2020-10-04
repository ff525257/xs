package com.fastxs.simple;

import android.view.View;

import com.fast.fastxs.http.HttpObserver;
import com.fast.fastxs.mvvm.XsBaseModelView;

import java.util.HashMap;

public abstract class BaseModelView<M extends BaseModel, V extends BaseView> extends XsBaseModelView implements HttpObserver {
    protected M mBaseModel;
    protected V mBaseView;

    public abstract void init();

    public BaseModelView(M baseModel, V baseView) {
        this.mBaseModel = baseModel;
        this.mBaseView = baseView;
        //通知View层初始化
        mBaseView.initView();
        //初始化
        init();
    }

    @Override
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
