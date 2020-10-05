package com.fastxs.simple.business.dialog.mvvm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.fast.fastxs.adapter.LayoutModelAdapter;
import com.fast.fastxs.adapter.SpaceItemDecoration;
import com.fast.fastxs.adapter.dataobj.GeneralListObj;
import com.fast.fastxs.drawable.RoundDrawable;
import com.fast.fastxs.inject.ViewId;
import com.fast.fastxs.mvvm.XsBaseViewRender;
import com.fast.fastxs.util.DeviceUtils;
import com.fast.fastxs.xdialog.XDialog;
import com.fastxs.simple.R;
import com.fastxs.simple.adapter.DialogListAdapter;

import java.util.ArrayList;

public class DialogView extends XsBaseViewRender {

    @ViewId(R.id.list)
    private RecyclerView list;

    private DialogListAdapter mAdapter;
    private ArrayList<DialogListAdapter.DialogItem> mList = new ArrayList<>();

    public DialogView(Context context) {
        super(context);
    }


    private XDialog xxx;

    public void showEditDialog() {
        xxx = new XDialog.DialogBuilder().setEdit("来吧", "输出密码", XDialog.FooterType.VERTICAL, new XDialog.OkClickLisenter() {
            @Override
            public void clickOK(ArrayList<Integer> selectsId, int position, String editText) {
                xxx.dismiss();
                Toast.makeText(mContext, editText, 1000).show();

                new XDialog.DialogBuilder().setLoading(editText).setGravity(Gravity.CENTER).create(mContext).show();
            }
        }).setGravity(Gravity.BOTTOM).setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).setCancelable(false).create(mContext);
        xxx.show();
    }


    @Override
    protected void initView() {
        super.initView();
        setTitle("Dialog");

        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("单个尾部", null)));
        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("水平", null)));
        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("垂直", null)));
        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("单选", null)));
        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("多选", null)));
        mList.add(new DialogListAdapter.DialogItem(new GeneralListObj("输入框", null)));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        list.addItemDecoration(new SpaceItemDecoration(0, DeviceUtils.dip2px(1f, mContext), mContext.getResources().getColor(R.color.gray)));

        mAdapter = new DialogListAdapter(mList);
        list.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LayoutModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                switch (position) {
                    case 0:
                        new XDialog.DialogBuilder().setDialog(mList.get(position).getData().title, new String[]{"底部只有一个取消按钮"}, XDialog.FooterType.SINGLE, null).
                                setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).create(mContext).show();
                        break;
                    case 1:
                        new XDialog.DialogBuilder().setDialog(mList.get(position).getData().title, new String[]{"底部水平排列"}, XDialog.FooterType.HORIZONTAL, null).
                                setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).create(mContext).show();
                        break;
                    case 2:
                        new XDialog.DialogBuilder().setDialog(mList.get(position).getData().title, new String[]{"底部垂直排列"}, XDialog.FooterType.VERTICAL, null).
                                setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).create(mContext).show();
                        break;
                    case 3:
                        new XDialog.DialogBuilder().setDialogRadio(mList.get(position).getData().title, new String[]{"1","2","3","4","5"}, XDialog.FooterType.HORIZONTAL, null).
                                setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).create(mContext).show();
                        break;
                    case 4:
                        new XDialog.DialogBuilder().setDialogCheckbox(mList.get(position).getData().title, new String[]{"1","2","3","4","5"}, XDialog.FooterType.HORIZONTAL, null).
                                setContentBackground(new RoundDrawable(mContext.getResources().getColor(R.color.green), 30)).create(mContext).show();
                        break;
                    case 5:
                        showEditDialog();
                        break;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialoglist_layout;
    }
}
