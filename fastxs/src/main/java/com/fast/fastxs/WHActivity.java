package com.fast.fastxs;

import android.app.Activity;
import android.os.Bundle;

import com.fast.fastxs.mvvm.ModelView;
import com.fast.fastxs.util.InjectManager;


public abstract class WHActivity<MV extends ModelView> extends Activity {

    protected MV mModelView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelView = initModelView();
        setContentView(mModelView.getRootView());

        //注解
        InjectManager.inject(this);
    }

    public abstract MV initModelView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mModelView != null) {
            mModelView.onDestroy();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mModelView != null) {
            mModelView.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mModelView != null) {
            mModelView.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mModelView != null) {
            mModelView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mModelView != null) {
            mModelView.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mModelView != null) {
            mModelView.onStop();
        }
    }
}
