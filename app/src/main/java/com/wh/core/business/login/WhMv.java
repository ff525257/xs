package com.wh.core.business.login;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.wh.R;
import com.wh.core.layoutmodel.LayouModelAdapter;
import com.wh.core.mv.ModelView;
import com.wh.core.xdialog.SimpleDialogAdapter;
import com.wh.core.xdialog.XDialog;

import java.util.HashMap;

public class WhMv extends ModelView<WhModel, WhView> {

    public WhMv(WhModel model, WhView render) {
        super(model, render);
    }

    @Override
    public void init() {
        //绑定View事件,设置数据
        mRender.bindLogin(this, "login", new String[]{"usernmae", "password"});
    }

    XDialog d = null;

    public void renderLogin(HashMap<String, String> result) {
        //((WhModel) mModel).login(result.get("usernmae"), result.get("password"), "refreshUi", this);
        d = new XDialog.DialogBuilder().setSize(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT).setContentBackgroundResource(R.color.colorPrimary).
                setGravity(Gravity.BOTTOM).setDialog("111", new String[]{"111", "22222"}, SimpleDialogAdapter.SelectType.RADIO, XDialog.FooterType.HORIZONTAL, new LayouModelAdapter.OnChildItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                d.dismiss();
            }
        }).create(mRender.getRootView().getContext());
        d.show();
    }

    public void modelRefreshUi(Object ob, int id, HashMap<String, String> body) {
        mRender.refreshUi();
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
    }


}
