package com.fastxs.simple.business.functionlist.mvvm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fast.fastxs.adapter.LayoutModelAdapter;
import com.fast.fastxs.adapter.SpaceItemDecoration;
import com.fast.fastxs.adapter.dataobj.GeneralListObj;
import com.fast.fastxs.inject.ViewId;
import com.fast.fastxs.mvvm.XsBaseViewRender;
import com.fast.fastxs.util.DeviceUtils;
import com.fastxs.simple.R;
import com.fastxs.simple.adapter.FunctionAdapter;
import com.fastxs.simple.business.dialog.DialogActivity;
import com.fastxs.simple.business.login.LoginActivity;

import java.util.ArrayList;

public class FunctionView extends XsBaseViewRender {

    @ViewId(R.id.list)
    private RecyclerView list;
    private FunctionAdapter mAdapter;
    private ArrayList<FunctionAdapter.FunctionItem> mList = new ArrayList<>();

    public FunctionView(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("来了老弟");

        mList.add(new FunctionAdapter.FunctionItem(new GeneralListObj("Http", new Intent((Activity) mContext, LoginActivity.class))));
        mList.add(new FunctionAdapter.FunctionItem(new GeneralListObj("Dialog", new Intent((Activity) mContext, DialogActivity.class))));
        mList.add(new FunctionAdapter.FunctionItem(new GeneralListObj("View", new Intent((Activity) mContext, DialogActivity.class))));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        list.addItemDecoration(new SpaceItemDecoration(0, DeviceUtils.dip2px(1f, mContext), mContext.getResources().getColor(R.color.gray)));

        mAdapter = new FunctionAdapter(mList);
        list.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LayoutModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                mContext.startActivity(mList.get(position).getData().data);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.function_layout;
    }
}
