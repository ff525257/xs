package com.fastxs.simple.adapter;

import android.support.annotation.NonNull;

import com.fast.fastxs.adapter.BaseItem;
import com.fast.fastxs.adapter.LayoutModelAdapter;
import com.fast.fastxs.adapter.dataobj.GeneralListObj;
import com.fastxs.simple.R;

import java.util.ArrayList;

public class DialogListAdapter extends LayoutModelAdapter<DialogListAdapter.DialogItem> {

    public DialogListAdapter(@NonNull ArrayList<DialogItem> list) {
        super(list);
    }

    @Override
    protected void bindViewHolder(XHolder holder, DialogItem baseBean, long position) {
        holder.setText(R.id.text, baseBean.getData().title);
    }

    public static class DialogItem extends BaseItem<GeneralListObj<String>> {

        public DialogItem(GeneralListObj data) {
            super(data);
            layoutId = R.layout.common_item_layout;
        }
    }


}
