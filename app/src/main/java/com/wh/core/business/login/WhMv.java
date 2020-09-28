package com.wh.core.business.login;

import com.wh.core.mvvm.ModelView;
import com.wh.core.xdialog.XDialog;

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

    XDialog d = null;

    public void renderLogin(HashMap<String, String> result) {
        //((WhModel) mModel).login(result.get("usernmae"), result.get("password"), "refreshUi", this);

        mRender.showDialog();
    }

    public void modelRefreshUi(Object ob, int id, HashMap<String, String> body) {
        mRender.refreshUi();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }


}
