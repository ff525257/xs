package com.wh.core.xdialog;

import android.view.View;
import android.widget.TextView;

import com.wh.core.layoutmodel.BaseBean;
import com.wh.core.layoutmodel.LayouModelAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogRecyclerAdapter extends LayouModelAdapter {

    public DialogRecyclerAdapter(ArrayList<BaseBean> list) {
        super(list);
    }

    @Override
    protected void beforeClick(int position, View view) {
        super.beforeClick(position, view);
    }

    @Override
    protected void onBindViewHolder(LayouModelAdapter.XHolder holder, BaseBean baseBean, long position) {
        int ps = (int) position;
        final HashMap<String, Object> map = mList.get(ps).getData();
        if (baseBean instanceof BaseBean.Center_TitleBean) {
            TextView title = (TextView) holder.itemView;
            title.setText("22423423");
        }
    }

}
