package com.wh.core.business.login;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wh.R;
import com.wh.core.inject.ViewId;
import com.wh.core.mvvm.BaseViewRender;
import com.wh.core.xdialog.XDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class WhView extends BaseViewRender {

    @ViewId(R.id.editText)
    private TextView text1;
    @ViewId(R.id.editText2)
    private TextView text2;

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

        xxx = new XDialog.DialogBuilder().setEdit("来吧", "输出密码", XDialog.FooterType.HORIZONTAL, new XDialog.OkClickLisenter() {
            @Override
            public void clickOK(ArrayList<Integer> selectsId, int position, String editText) {
                xxx.dismiss();
                Toast.makeText(mContext, editText, 1000).show();
            }
        }).setGravity(Gravity.CENTER).create(mContext);
        xxx.show();
    }


    protected void refreshUi() {
    }

    @Override
    protected void initView(Context context) {
        text1.setText("1111111111");
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_layout;
    }
}
