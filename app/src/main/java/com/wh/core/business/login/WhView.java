package com.wh.core.business.login;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fast.fastxs.inject.ViewId;
import com.fast.fastxs.mvvm.BaseViewRender;
import com.fast.fastxs.weight.CustomBarbar;
import com.fast.fastxs.xdialog.XDialog;
import com.wh.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WhView extends BaseViewRender {

    @ViewId(R.id.editText)
    private TextView text1;
    @ViewId(R.id.editText2)
    private TextView text2;
    @ViewId(R.id.titleBar)
    private CustomBarbar barbar;

    public WhView(Context context) {
        super(context);
    }

    /**
     * 绑定事件,回调
     *
     * @param callBack
     * @param method
     * @param keys
     */
    protected void bindLogin(final Object callBack, final String method, final String[] keys) {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> result = new HashMap<>(keys.length);
                result.put(keys[0], text1.getText().toString());
                result.put(keys[1], text2.getText().toString());
                callBackMethod(callBack, method, result);
            }
        });
    }

    private XDialog xxx;

    public void showDialog() {
           /*new XDialog.DialogBuilder().setSize(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT).setContentBackgroundResource(R.color.colorPrimary).
                setGravity(Gravity.BOTTOM).setPadding(11,11,11,11).
                setDialogCheckbox("111", new String[]{"111", "22222"}, XDialog.FooterType.HORIZONTAL, new LayouModelAdapter.OnChildItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {

                    }
                }).create(mRender.getRootView().getContext());*/

        xxx = new XDialog.DialogBuilder().setEdit("来吧", "输出密码", XDialog.FooterType.VERTICAL, new XDialog.OkClickLisenter() {
            @Override
            public void clickOK(ArrayList<Integer> selectsId, int position, String editText) {
                xxx.dismiss();
                Toast.makeText(mContext, editText, 1000).show();

                new XDialog.DialogBuilder().setLoading(editText).setGravity(Gravity.CENTER).create(mContext).show();
            }
//        }).setGravity(Gravity.BOTTOM).setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green),30)).setCancelable(false).create(mContext);
        }).setGravity(Gravity.BOTTOM).setContentBackground(mContext.getResources().getColor(R.color.green)).setCancelable(false).create(mContext);
        xxx.show();
    }


    protected void refreshUi() {
    }

    @Override
    protected void initView(Context context) {
        text1.setText("1111111111");
        barbar.addLeftView(barbar.getImageView(android.R.drawable.star_on)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
            }
        });
        barbar.addLeftView(barbar.getTextView("左2"));
        barbar.addRightView(barbar.getTextView("右1"));
        barbar.addRightView(barbar.getTextView("右2"));
        barbar.addMiddleViewExist(barbar.getTextView("标题"));
        barbar.addMiddleViewExist(barbar.getTextView("标题1"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_layout;
    }
}
