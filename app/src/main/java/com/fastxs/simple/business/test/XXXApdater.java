package com.fastxs.simple.business.test;

import android.support.annotation.NonNull;

import com.fast.fastxs.adapter.BaseItem;
import com.fast.fastxs.adapter.LayoutModelAdapter;
import com.fast.fastxs.adapter.dataobj.GeneralListObj;

import java.util.ArrayList;

public class XXXApdater extends LayoutModelAdapter<XXXApdater.XXXApdaterItem> {
    public XXXApdater(@NonNull ArrayList<XXXApdaterItem> list) {
        super(list);
    }

    @Override
    public void bindViewHolder(XHolder holder, XXXApdaterItem baseBean, long position) {
    }

    public class XXXApdaterItem extends BaseItem<GeneralListObj> {
        public XXXApdaterItem(GeneralListObj data) {
            super(data);
            layoutId = android.R.layout.activity_list_item;
        }
    }
}