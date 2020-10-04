package com.fastxs.simple.business.functionlist;

import com.fast.fastxs.XsBaseActivity;

public class FunctionActivity extends XsBaseActivity<FunctionModelView> {

    @Override
    public FunctionModelView initModelView() {
        return new FunctionModelView(new FunctionModel(), new FunctionView(this));
    }
}
