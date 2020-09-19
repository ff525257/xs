package com.wh.core.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wh.R;
import com.wh.core.layoutmodel.LayouModelAdapter;

import java.util.ArrayList;


public class XDialog extends Dialog {

    protected Context mContext;

    private Window window;
    protected DialogBuilder mBuilder;
    private RecyclerView recyclerView;
    private ViewGroup rootView;

    public XDialog(Context context, DialogBuilder builder) {
        super(context, R.style.DialogStyle);
        mContext = context;
        mBuilder = builder;
    }

    public XDialog(Context context) {
        super(context, R.style.DialogStyle);
        mContext = context;
    }

    public DialogBuilder getDialogBuilder() {
        return mBuilder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basedialog);
        rootView = (ViewGroup) findViewById(R.id.baselayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (mBuilder.width == 0) {
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            lp.width = mBuilder.width;
        }
        if (mBuilder.height == 0) {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            lp.height = mBuilder.height;
        }
        window.setGravity(mBuilder.gravity);
        setCanceledOnTouchOutside(mBuilder.isCancelable);

        window.setAttributes(lp);

        initContentView(mBuilder.adapter);
        if (mBuilder.contentBackgroundResource != 0) {
            rootView.setBackgroundResource(mBuilder.contentBackgroundResource);
        }
        setOnDismissListener(mBuilder.dismissListener);
        setOnShowListener(mBuilder.showListener);

        if (mBuilder.animat_resId != 0) {
            window.setWindowAnimations(mBuilder.animat_resId);
        }
    }

    private void initContentView(LayouModelAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.addChildItemClickListener(R.id.cannle, new LayouModelAdapter.OnChildItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                dismiss();
            }
        });
    }

    public enum FooterType {
        NO, HORIZONTAL, VERTICAL, SINGLE
    }

    public static class DialogBuilder {
        public int width;
        public int height;
        public int gravity = Gravity.BOTTOM;
        public boolean isCancelable = true;
        public int contentBackgroundResource;
        public LayouModelAdapter adapter;
        public DialogInterface.OnDismissListener dismissListener;
        public DialogInterface.OnShowListener showListener;
        public int animat_resId;
        //回调ID
        public int callbackId;

        public DialogBuilder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public DialogBuilder setDismissListener(DialogInterface.OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public void setCallbackId(int callbackId) {
            this.callbackId = callbackId;
        }

        public DialogBuilder setShowListener(DialogInterface.OnShowListener showListener) {
            this.showListener = showListener;
            return this;
        }

        public DialogBuilder setAdapter(LayouModelAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public DialogBuilder setDialog(String title, String[] contents, FooterType type, LayouModelAdapter.OnChildItemClickListener okClickListener) {
            return setDialog(title, contents, SimpleDialogAdapter.SelectType.NO, type, okClickListener);
        }

        public DialogBuilder setDialog(final String title, String[] contents, SimpleDialogAdapter.SelectType selectType, FooterType type, LayouModelAdapter.OnChildItemClickListener okClickListener) {

            final ArrayList<SimpleDialogAdapter.Item> list = new ArrayList<>(0);
            //STEP1  头部
            {
                if (!TextUtils.isEmpty(title)) {
                    SimpleDialogAdapter.ItemObject headerObj = new SimpleDialogAdapter.ItemObject();
                    headerObj.text = title;
                    SimpleDialogAdapter.Item header = new SimpleDialogAdapter.HeaderItem(headerObj);
                    list.add(header);
                }
            }

            //STEP2 内容
            {
                if (contents != null && contents.length != 0) {
                    for (String obj : contents) {
                        SimpleDialogAdapter.ItemObject headerObj = new SimpleDialogAdapter.ItemObject();
                        headerObj.text = obj;
                        headerObj.selectType = selectType;
                        SimpleDialogAdapter.Item content = new SimpleDialogAdapter.ContentItem(headerObj);
                        list.add(content);
                    }
                }
            }

            //STEP2 脚部
            {
                SimpleDialogAdapter.ItemObject footerObj = new SimpleDialogAdapter.ItemObject();
                SimpleDialogAdapter.Item footer = null;
                switch (type) {
                    case SINGLE:
                        footer = new SimpleDialogAdapter.CannleFooterItem(footerObj);
                        break;
                    case HORIZONTAL:
                        footer = new SimpleDialogAdapter.HorizontalFooterItem(footerObj);
                        break;
                    case VERTICAL:
                        footer = new SimpleDialogAdapter.VerticalFooterItem(footerObj);
                        break;
                }

                list.add(footer);
            }
            final int startIndex = !TextUtils.isEmpty(title) ? 1 : 0;

            final SimpleDialogAdapter adapter = new SimpleDialogAdapter(list);

            adapter.addChildItemClickListener(R.id.ok, okClickListener);

            switch (selectType) {
                case CHECKBOX:
                    adapter.addChildItemClickListener(R.id.checkBox, new LayouModelAdapter.OnChildItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            list.get(position).getData().isSelect = true;
                        }
                    });

                    adapter.setOnItemClickListener(new LayouModelAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            list.get(position).getData().isSelect = true;
                            adapter.notifyItemChanged(position);
                        }
                    });

                    break;
                case RADIO:
                    final LayouModelAdapter.OnItemClickListener itemClickListener = new LayouModelAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            if (position < startIndex) {
                                return;
                            }
                            for (int i = startIndex; i < list.size() - 1; i++) {
                                if (i == position) {
                                    list.get(i).getData().isSelect = true;
                                } else {
                                    list.get(i).getData().isSelect = false;
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    };
                    adapter.addChildItemClickListener(R.id.radio, new LayouModelAdapter.OnChildItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            itemClickListener.onItemClick(position, view);
                        }
                    });

                    adapter.setOnItemClickListener(itemClickListener);

                    break;

            }

            this.adapter = adapter;
            return this;
        }


        public DialogBuilder setWindowAnimations(int animat_resId) {
            this.animat_resId = animat_resId;
            return this;
        }

        public DialogBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public DialogBuilder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public DialogBuilder setContentBackgroundResource(int resourceId) {
            this.contentBackgroundResource = resourceId;
            return this;
        }


        public XDialog create(Context context) {
            XDialog dialog = new XDialog(context, this);
            return dialog;
        }

    }


}

