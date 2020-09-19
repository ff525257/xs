package com.wh.core.business.login;

import android.view.Gravity;
import android.view.WindowManager;

import com.wh.R;
import com.wh.core.layoutmodel.BaseBean;
import com.wh.core.mv.ModelView;
import com.wh.core.xdialog.DialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class WhMv extends ModelView<WhModel, WhView> {

    public WhMv(WhModel model, WhView render) {
        super(model, render);
    }

    @Override
    public void init() {
        //绑定View事件,设置数据
        mRender.bindLogin(this, "login", new String[]{"usernmae", "password"});
    }

    public void renderLogin(HashMap<String, String> result) {
        //((WhModel) mModel).login(result.get("usernmae"), result.get("password"), "refreshUi", this);
        ArrayList<BaseBean> list = new ArrayList<BaseBean>();
        BaseBean bean = new BaseBean.Center_TitleBean();
        list.add(bean);
        new DialogBuilder().setSize(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT).setContentBackgroundResource(R.color.colorPrimary).
                setGravity(Gravity.BOTTOM).setNormal("1112", list).create(mRender.getRootView().getContext()).show();
    }

    public void modelRefreshUi(Object ob, int id, HashMap<String, String> body) {
        mRender.refreshUi();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }


}
