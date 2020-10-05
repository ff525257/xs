package com.fastxs.simple.business.login.mvvm;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fast.fastxs.inject.ViewId;
import com.fast.fastxs.mvvm.XsBaseViewRender;
import com.fastxs.simple.R;

import java.util.HashMap;

public class LoginView extends XsBaseViewRender {

    @ViewId(R.id.editText)
    private TextView text1;
    @ViewId(R.id.editText2)
    private TextView text2;

    public LoginView(Context context) {
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


    protected void refreshUi(Object ob) {
        text1.setText(ob.toString());
    }

    @Override
    protected void initView() {
        super.initView();
        mActionbar.addMiddleViewExist(mActionbar.getTextView("Dialog"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_layout;
    }
}
