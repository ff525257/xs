package com.wh.core.xdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;


import com.wh.core.layoutmodel.BaseBean;
import com.wh.core.layoutmodel.LayouModelAdapter;

import java.util.ArrayList;

public class DialogBuilder {
    public int width;
    public int height;
    public int gravity = Gravity.BOTTOM;
    public boolean isCancelable = true;
    public int contentBackgroundResource;
    public DialogRecyclerAdapter adapter;
    public DialogInterface.OnDismissListener dismissListener;
    public DialogInterface.OnShowListener showListener;
    public int animat_resId;
    //回调ID
    public int callbackId;
    public String title;
    public boolean hasHeader;
    public boolean hasFooter;
    public int footerOrientation;

    public DialogBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DialogBuilder setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public void setCallbackId(int callbackId) {
        this.callbackId = callbackId;
    }

    public DialogBuilder setShowListener(DialogInterface.OnShowListener showListener) {
        this.showListener = showListener;
        return this;
    }

    public DialogBuilder setAdapter(DialogRecyclerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public DialogBuilder setAdapter(DialogRecyclerAdapter adapter, boolean hasHeader, boolean hasFooter) {
        this.adapter = adapter;
        this.hasHeader = hasFooter;
        this.hasFooter = hasFooter;
        return this;
    }

    public DialogBuilder setWindowAnimations(int animat_resId) {
        this.animat_resId = animat_resId;
        return this;
    }

    public DialogBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public DialogBuilder setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public DialogBuilder setContentBackgroundResource(int resourceId) {
        this.contentBackgroundResource = resourceId;
        return this;
    }


    public DialogBuilder setNormal(String title , ArrayList<BaseBean> contentList) {
        ArrayList<BaseBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(title)) {
            this.title = title;
            hasHeader = true;
        }

        if (contentList != null) {
            list.addAll(contentList);
        }

        if (footerOrientation > 0) {
            this.footerOrientation = footerOrientation;
            hasFooter = true;
        }

        adapter = new DialogRecyclerAdapter(list);
        adapter.setOnItemClickListener(new LayouModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
            }
        });
        return this;
    }

    public XDialog create(Context context) {
        XDialog mXDialog = new XDialog(context, this);
        return mXDialog;
    }

}
