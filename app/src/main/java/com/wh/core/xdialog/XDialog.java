package com.wh.core.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wh.R;


public class XDialog extends Dialog {

    protected Context mContext;

    private Window window;
    protected DialogBuilder mBuilder;
    private RecyclerView recyclerView;
    private ViewGroup rootView;
    private TextView title;
    private ViewGroup footerParent;
    private LayoutInflater inflater;

    public XDialog(Context context, DialogBuilder builder) {
        super(context, R.style.DialogStyle);
        mContext = context;
        mBuilder = builder;
        inflater = LayoutInflater.from(context);
    }

    public XDialog(Context context) {
        super(context, R.style.DialogStyle);
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public DialogBuilder getDialogBuilder() {
        return mBuilder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basedialog);
        rootView = (ViewGroup) findViewById(R.id.baselayout);
        title = (TextView) findViewById(R.id.title);
        footerParent = (ViewGroup) findViewById(R.id.footerParent);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (mBuilder.width == 0) {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            lp.width = mBuilder.width;
        }
        if (mBuilder.height == 0) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            lp.height = mBuilder.height;
        }
        window.setGravity(mBuilder.gravity);
        setCanceledOnTouchOutside(mBuilder.isCancelable);

        window.setAttributes(lp);

        if (mBuilder.hasHeader) {
            title.setText(mBuilder.title);
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
        if (mBuilder.hasFooter) {
            footerParent.setVisibility(View.VISIBLE);
            /*switch (mBuilder.footerOrientation) {
                case FooterType.HORIZONTAL:
                    View child = inflater.inflate(R.layout.foot_hb, footerParent, false);
                    footerParent.addView(child);
                    break;
                case FooterType.VERTICAL:
                    child = inflater.inflate(R.layout.foot_vb, footerParent, false);
                    footerParent.addView(child);
                    break;
                case FooterType.ONLYCANCEL:
                    child = inflater.inflate(R.layout.foot_vb, footerParent, false);
                    footerParent.addView(child);
                    break;
            }
            TextView positiveButton = (TextView) footerParent.findViewById(R.id.positiveButton);
            TextView negativeButton = (TextView) footerParent.findViewById(R.id.negativeButton);
            if (mBuilder.footerOrientation == FooterType.ONLYCANCEL) {
                positiveButton.setVisibility(View.GONE);
            }
            positiveButton.setOnClickListener(this);
            negativeButton.setOnClickListener(this);*/
        } else {
            footerParent.setVisibility(View.GONE);
        }
        initContentView(mBuilder.adapter);
        if (mBuilder.contentBackgroundResource != 0) {
            rootView.setBackgroundResource(mBuilder.contentBackgroundResource);
        }
        setOnDismissListener(mBuilder.dismissListener);
        setOnShowListener(mBuilder.showListener);

        if (mBuilder.animat_resId != 0) {
            window.setWindowAnimations(mBuilder.animat_resId);
        } else {
           /* if ((mBuilder.gravity & Gravity.TOP) == Gravity.TOP) {
                window.setWindowAnimations(R.style.Dialog_Slide_Top);
            } else if ((mBuilder.gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                window.setWindowAnimations(R.style.Dialog_Slide_Bottom);
            } else if ((mBuilder.gravity & Gravity.CENTER) == Gravity.CENTER) {
                window.setWindowAnimations(R.style.Dialog_Slide_Center);
            }*/
        }

    }

    private void initContentView(DialogRecyclerAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}

