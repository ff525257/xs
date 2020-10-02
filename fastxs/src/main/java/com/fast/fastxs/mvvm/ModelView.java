package com.fast.fastxs.mvvm;

import android.view.View;

import com.fast.fastxs.http.HttpObserver;

import java.util.HashMap;

/**
 * 均采用反射回调,
 * model的回调以model开头,
 * render的回调以render开头
 * render不自己定义事件，均有MV控制
 */
public abstract class ModelView<M extends BaseModel, V extends BaseViewRender> implements HttpObserver {
    protected M mModel;
    protected V mRender;

    public ModelView(M model, V render) {
        this.mModel = model;
        this.mRender = render;
        init();
    }

    public View getRootView() {
        return mRender.getRootView();
    }


    public abstract void init();

    public void onDestroy() {
        mModel.onDestroy();
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
        if (mRender != null && isShowAlert) {
            mRender.showAlert(id);
        }
    }

    @Override
    public void hideAlert(int id) {
        if (mRender != null) {
            mRender.hideAlert(id);
        }
    }

    @Override
    public void onProgress(long current, long total, int id, HashMap<String, String> body) {

    }

    public void onStart() {
    }

    public void onRestart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }
}
