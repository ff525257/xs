package com.wh.core.business.login;

import com.wh.core.WHActivity;

public class LoginActivity extends WHActivity<WhMv> {

    @Override
    public WhMv initModelView() {
        return new WhMv(new WhModel(), new WhView(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
