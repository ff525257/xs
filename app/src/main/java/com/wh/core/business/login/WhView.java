package com.wh.core.business.login;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wh.R;
import com.wh.core.inject.ViewId;
import com.wh.core.mvvm.BaseViewRender;

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
