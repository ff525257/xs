package com.fast.fastxs.mvvm;

import android.content.Context;
import android.view.View;

import com.fast.fastxs.util.InjectManager;
import com.fast.fastxs.util.LogUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class XsBaseViewRender {

    private static final String TAG = "XsBaseViewRender";
    protected Context mContext;
    protected View mRootView;

    public XsBaseViewRender(Context context) {
        mContext = context;
        mRootView = createView(context);
        InjectManager.inject(this);
    }


    public View getRootView() {
        return mRootView;
    }


    /**
     * 创建View,对应的是模块的跟View
     *
     * @param context
     * @return
     */
    private View createView(Context context) {
        return View.inflate(context, getLayoutId(), null);
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
            callbackMethod = callBack.getClass().getMethod(callBackMethod, HashMap.class);
            callbackMethod.invoke(callBack, result);
        } catch (Exception e) {
            LogUtils.w(TAG, "Exception ", e);
        }
    }


}
