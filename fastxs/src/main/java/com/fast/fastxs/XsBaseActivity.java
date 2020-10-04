package com.fast.fastxs;

import android.app.Activity;
import android.os.Bundle;

import com.fast.fastxs.mvvm.XsBaseModelView;
import com.fast.fastxs.util.InjectManager;

public abstract class XsBaseActivity<MV extends XsBaseModelView> extends Activity {

    protected MV mModelView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelView = initModelView();
        setContentView(mModelView.getRootView());

        //注解
        InjectManager.inject(this);
    }

    public abstract MV initModelView();


}
