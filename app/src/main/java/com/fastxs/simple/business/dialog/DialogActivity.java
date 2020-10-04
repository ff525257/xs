package com.fastxs.simple.business.dialog;

import com.fast.fastxs.XsBaseActivity;

public class DialogActivity extends XsBaseActivity<DialogModelView> {

    @Override
    public DialogModelView initModelView() {
        return new DialogModelView(new DialogModel(), new DialogView(this));
    }
}
