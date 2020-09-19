package com.wh.core;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;

import com.wh.core.common.util.InjectManager;
import com.wh.core.mv.ModelView;

public abstract class WHActivity<MV extends ModelView> extends Activity {

    protected MV mModelView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelView = initModelView();
        setContentView(mModelView.getRootView());

        InjectManager.inject(this);
    }

    public abstract MV initModelView();

    public ModelView getModelView() {
        return mModelView;
    }


    public <T extends View> T findViewById2(int resid) {
        return (T) findViewById(resid);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mModelView != null) {
            mModelView.onDestroy();
        }
    }
}
