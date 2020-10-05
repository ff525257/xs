package com.fastxs.simple.business.test;

import com.fast.fastxs.XsBaseActivity;
import com.fastxs.simple.business.test.mvvm.*;

public class XXXActivity extends XsBaseActivity<XXXModelView> {
    @Override
    public XXXModelView initModelView() {
        return new XXXModelView(new XXXModel(), new XXXView(this));
    }
}