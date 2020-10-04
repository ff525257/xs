package com.fastxs.simple.business.login;

import com.fast.fastxs.XsBaseActivity;

public class LoginActivity extends XsBaseActivity<LoginModelView> {

    @Override
    public LoginModelView initModelView() {
        return new LoginModelView(new LoginModel(), new LoginView(this));
    }
}
