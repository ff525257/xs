package com.fastxs.simple;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.fast.fastxs.mvvm.XsBaseViewRender;
import com.fast.fastxs.weight.CustomBarbar;
import com.fast.fastxs.xdialog.XDialog;

public abstract class BaseView extends XsBaseViewRender {
    protected Dialog mLoading;
    protected CustomBarbar mActionbar;

    public BaseView(Context context) {
        super(context);
    }

    protected void initView() {
        mActionbar = mRootView.findViewById(R.id.titleBar);
    }

    public View setTitle(String title) {
        return mActionbar.addMiddleViewExist(mActionbar.getTextView(title));
    }

    public void showLoad(String title) {
        if (mLoading == null) {
            mLoading = new XDialog.DialogBuilder().setLoading(title).setGravity(Gravity.CENTER).create(mContext);
        }
        mLoading.show();
    }

    public void showAlert(int id) {
        showLoad(null);
    }

    public void hideAlert(int id) {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }
}
