package com.fastxs.simple.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.fast.fastxs.adapter.LayoutModelAdapter;
import com.fastxs.simple.R;
import com.fastxs.simple.adapter.dataobj.GeneralListObj;

import java.util.ArrayList;

public class FunctionAdapter extends LayoutModelAdapter<FunctionAdapter.FunctionItem> {

    public FunctionAdapter(@NonNull ArrayList<FunctionItem> list) {
        super(list);
    }

    @Override
    protected void bindViewHolder(XHolder holder, FunctionItem baseBean, long position) {
        holder.setText(R.id.text, baseBean.getData().title);
    }

    public static class FunctionItem extends LayoutModelAdapter.BaseItem<GeneralListObj<Intent>> {

        public FunctionItem(GeneralListObj data) {
            super(data);
            layoutId = R.layout.common_item_layout;
        }
    }


}
