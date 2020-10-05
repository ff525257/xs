package com.fastxs.simple.business.login;

import com.fast.fastxs.XsBaseActivity;
import com.fastxs.simple.business.login.mvvm.LoginModel;
import com.fastxs.simple.business.login.mvvm.LoginModelView;
import com.fastxs.simple.business.login.mvvm.LoginView;

public class LoginActivity extends XsBaseActivity<LoginModelView> {

    @Override
    public LoginModelView initModelView() {
        return new LoginModelView(new LoginModel(), new LoginView(this));
    }
}
