package com.fastxs.simple.business.dialog;

import com.fast.fastxs.XsBaseActivity;
import com.fastxs.simple.business.dialog.mvvm.DialogModel;
import com.fastxs.simple.business.dialog.mvvm.DialogModelView;
import com.fastxs.simple.business.dialog.mvvm.DialogView;

public class DialogActivity extends XsBaseActivity<DialogModelView> {

    @Override
    public DialogModelView initModelView() {
        return new DialogModelView(new DialogModel(), new DialogView(this));
    }
}
