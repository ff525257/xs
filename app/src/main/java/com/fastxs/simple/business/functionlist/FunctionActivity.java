package com.fastxs.simple.business.functionlist;

import com.fast.fastxs.XsBaseActivity;
import com.fastxs.simple.business.functionlist.mvvm.FunctionModel;
import com.fastxs.simple.business.functionlist.mvvm.FunctionModelView;
import com.fastxs.simple.business.functionlist.mvvm.FunctionView;

public class FunctionActivity extends XsBaseActivity<FunctionModelView> {

    @Override
    public FunctionModelView initModelView() {
        return new FunctionModelView(new FunctionModel(), new FunctionView(this));
    }
}
