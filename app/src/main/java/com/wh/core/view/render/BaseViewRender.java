package com.wh.core.view.render;

import android.content.Context;
import android.view.View;

import com.wh.core.common.util.InjectManager;
import com.wh.core.common.util.LogUtils;
import com.wh.core.common.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class BaseViewRender {

    private static final String TAG = "BaseViewRender";
    protected Context mContext;
    protected View mRootView;

    public BaseViewRender(Context context) {
        mContext = context;
        mRootView = createView(context);
        InjectManager.inject(this);
        initView(context);
    }


    public View getRootView() {
        return mRootView;
    }

    /**
     * 仅做初始化视图的操作
     *
     * @param context
     * @return
     */
    protected abstract void initView(final Context context);

    /**
     * 创建View,对应的是模块的跟View
     *
     * @param context
     * @return
     */
    private android.view.View createView(Context context) {
        return android.view.View.inflate(context, getLayoutId(), null);
    }

    public <T extends View> T findViewById(int resid) {
        return (T) mRootView.findViewById(resid);
    }


    public abstract int getLayoutId();

    /**
     * 在callBack里面回调
     *
     * @param callBackMethod
     * @param result
     */
    public void callBackMethod(Object callBack, String callBackMethod, HashMap<String, Object> result) {
        if (callBack == null) {
            LogUtils.w(TAG, "callBack is null");
            return;
        }
        Method callbackMethod = null;
        try {
            // 回调
            callbackMethod = callBack.getClass().getMethod("render" + StringUtils.fristToUpString(callBackMethod), HashMap.class);
            callbackMethod.invoke(callBack, result);
        } catch (Exception e) {
            LogUtils.w(TAG, "Exception ", e);
        }
    }

    public void showAlert(int id) {
    }

    public void hideAlert(int id) {
    }
}
