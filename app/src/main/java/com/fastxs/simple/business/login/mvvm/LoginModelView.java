package com.fastxs.simple.business.login.mvvm;

import com.fast.fastxs.mvvm.XsBaseModelView;

import java.util.HashMap;


public class LoginModelView extends XsBaseModelView<LoginModel, LoginView> {

    public LoginModelView(LoginModel baseModel, LoginView baseView) {
        super(baseModel, baseView);
    }

    @Override
    public void init() {//绑定View事件,告诉View层model层需要哪些数据,对应的对调即为 {@link login(HashMap<String, String> result)}
        mBaseView.bindLogin(this, "login", new String[]{"usernmae", "password"});
    }


    public void login(HashMap<String, String> result) {
        mBaseModel.login(result.get("usernmae"), result.get("password"), "refreshUi", this);
    }

    //Object ob, int id, HashMap<String, String> body
    public void refreshUi(Object ob, int id, HashMap<String, String> body) {
        mBaseView.refreshUi(ob);
    }

}
