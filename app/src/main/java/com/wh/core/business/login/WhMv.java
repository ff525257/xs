package com.wh.core.business.login;


import com.fast.fastxs.mvvm.ModelView;

import java.util.HashMap;


public class WhMv extends ModelView<WhModel, WhView> {

    public WhMv(WhModel model, WhView render) {
        super(model, render);
    }

    @Override
    public void init() {//绑定View事件,告诉View层model层需要哪些数据,对应的对调即为 {@link renderLogin(HashMap<String, String> result)}
        mRender.bindLogin(this, "login", new String[]{"usernmae", "password"});
    }


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
