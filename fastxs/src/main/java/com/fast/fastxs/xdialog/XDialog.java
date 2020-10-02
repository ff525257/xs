package com.fast.fastxs.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fast.fastxs.R;
import com.fast.fastxs.adapter.LayouModelAdapter;

import java.util.ArrayList;

/**
 * @author fmh
 * 为了统一请采用LayouModelAdapter做适配器
 * 尾部的footer布局,请使用R.id.cannle，默认为cannle加上了点击取消事件
 */
public class XDialog extends Dialog {

    protected Context mContext;

    private Window window;
    protected DialogBuilder mBuilder;
    private RecyclerView recyclerView;
    private ViewGroup rootView;

    public XDialog(Context context, DialogBuilder builder, int theme) {
        super(context, theme);
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


        rootView.setPadding(mBuilder.mUserPaddingLeft, mBuilder.mUserPaddingTop, mBuilder.mUserPaddingRight, mBuilder.mUserPaddingBottom);

        window.setAttributes(lp);

        initContentView(mBuilder.adapter);
        if (mBuilder.contentBackground != null) {
            recyclerView.setBackgroundDrawable(mBuilder.contentBackground);
        }
        setOnDismissListener(mBuilder.dismissListener);
        setOnShowListener(mBuilder.showListener);

        if (mBuilder.animation_resId != 0) {
            window.setWindowAnimations(mBuilder.animation_resId);
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

    public interface OkClickLisenter {
        //三种情况,选中,下标,输出框
        void clickOK(ArrayList<Integer> selectsId, int position, String editText);
    }

    public static class DialogBuilder {
        public int width;
        public int height;
        public int gravity = Gravity.BOTTOM;
        public boolean isCancelable = true;
        public Drawable contentBackground;
        public LayouModelAdapter adapter;
        public OnDismissListener dismissListener;
        public OnShowListener showListener;
        public int animation_resId;


        public int mUserPaddingBottom;
        public int mUserPaddingLeft;
        public int mUserPaddingTop;
        public int mUserPaddingRight;

        public DialogBuilder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public DialogBuilder setPadding(int left, int top, int right, int bottom) {
            this.mUserPaddingLeft = left;
            this.mUserPaddingTop = top;
            this.mUserPaddingRight = right;
            this.mUserPaddingBottom = bottom;
            return this;
        }

        public DialogBuilder setDismissListener(OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public DialogBuilder setShowListener(OnShowListener showListener) {
            this.showListener = showListener;
            return this;
        }

        public DialogBuilder setAdapter(LayouModelAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        /**
         * 带输入框的dialog
         *
         * @param title    标题
         * @param hint     提示
         * @param type     尾部类型
         * @param lisenter 确认回调
         * @return
         */
        public DialogBuilder setEdit(String title, String hint, FooterType type, final OkClickLisenter lisenter) {
            final ArrayList<SimpleDialogAdapter.Item> list = new ArrayList<>(0);
            initHeader(title, list);

            SimpleDialogAdapter.ItemObject itemObject = new SimpleDialogAdapter.ItemObject();
            itemObject.description = hint;
            list.add(new SimpleDialogAdapter.EditContentItem(itemObject));

            initFooter(type, list);
            final SimpleDialogAdapter adapter = new SimpleDialogAdapter(list);

            adapter.addChildItemClickListener(R.id.ok, new LayouModelAdapter.OnChildItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    if (lisenter != null) {
                        lisenter.clickOK(null, position, adapter.getEdittext());
                    }
                }
            });
            setAdapter(adapter);
            return this;
        }

        /**
         * 加载进度框
         *
         * @param title 进度框标题 默认加载中
         * @return
         */
        public DialogBuilder setLoading(String title) {
            final ArrayList<SimpleDialogAdapter.Item> list = new ArrayList<>(0);
            SimpleDialogAdapter.ItemObject itemObject = new SimpleDialogAdapter.ItemObject();
            itemObject.content = title;
            list.add(new SimpleDialogAdapter.LoadingItem(itemObject));
            final SimpleDialogAdapter adapter = new SimpleDialogAdapter(list);
            setAdapter(adapter);
            return this;
        }

        /**
         * 普通的dialog
         *
         * @param title           标题
         * @param contents        可选内容列表
         * @param type            底部UI
         * @param okClickListener 确认回调
         * @return
         */
        public DialogBuilder setDialog(String title, String[] contents, FooterType type, OkClickLisenter okClickListener) {
            return setDialog(title, contents, SimpleDialogAdapter.SelectType.NO, type, okClickListener);
        }


        /**
         * 带单选的dialog
         *
         * @param title           标题
         * @param contents        可选内容列表
         * @param type            底部UI
         * @param okClickListener 确认回调  返回选中数据{@link #getSimpleSecelePositions}
         * @return
         */
        public DialogBuilder setDialogRadio(String title, String[] contents, FooterType type, OkClickLisenter okClickListener) {
            return setDialog(title, contents, SimpleDialogAdapter.SelectType.RADIO, type, okClickListener);
        }

        /**
         * 带多选的dialog
         *
         * @param title           标题
         * @param contents        可选内容列表
         * @param type            底部UI
         * @param okClickListener 确认回调  返回选中数据{@link #getSimpleSecelePositions}
         * @return
         */
        public DialogBuilder setDialogCheckbox(String title, String[] contents, FooterType type, OkClickLisenter okClickListener) {
            return setDialog(title, contents, SimpleDialogAdapter.SelectType.CHECKBOX, type, okClickListener);
        }

        /**
         * @param title           标题
         * @param contents        内容
         * @param selectType      可选模式,支持单选,多选
         * @param type            脚步显示控件,支持下上,左右,单取消
         * @param okClickListener listitem确定监听
         * @return
         */
        public DialogBuilder setDialog(final String title, String[] contents, SimpleDialogAdapter.SelectType selectType, FooterType type, final OkClickLisenter okClickListener) {

            final ArrayList<SimpleDialogAdapter.Item> list = new ArrayList<>(0);
            initHeader(title, list);

            initContentList(contents, selectType, list);

            initFooter(type, list);

            //起始位置
            final int startIndex = !TextUtils.isEmpty(title) ? 1 : 0;

            final SimpleDialogAdapter adapter = new SimpleDialogAdapter(list);

            adapter.addChildItemClickListener(R.id.ok, new LayouModelAdapter.OnChildItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    if (okClickListener != null) {
                        okClickListener.clickOK(adapter.getSecelePositions(), position - startIndex, null);
                    }
                }
            });

            switch (selectType) {
                case CHECKBOX:
                    adapter.addChildItemClickListener(R.id.checkBox, new LayouModelAdapter.OnChildItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            list.get(position).getData().isSelect = !list.get(position).getData().isSelect;
                        }
                    });

                    adapter.setOnItemClickListener(new LayouModelAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            list.get(position).getData().isSelect = !list.get(position).getData().isSelect;
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

        /**
         * 初始化中间内容数据
         *
         * @param contents
         * @param selectType
         * @param list
         */
        private void initContentList(String[] contents, SimpleDialogAdapter.SelectType selectType, ArrayList<SimpleDialogAdapter.Item> list) {
            if (contents != null && contents.length != 0) {
                for (String obj : contents) {
                    SimpleDialogAdapter.ItemObject headerObj = new SimpleDialogAdapter.ItemObject();
                    headerObj.content = obj;
                    headerObj.selectType = selectType;
                    SimpleDialogAdapter.Item content = new SimpleDialogAdapter.ContentItem(headerObj);
                    list.add(content);
                }
            }
        }

        /**
         * 初始化尾部
         *
         * @param type
         * @param list
         */
        private void initFooter(FooterType type, ArrayList<SimpleDialogAdapter.Item> list) {
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

        /**
         * 初始化头部
         *
         * @param title
         * @param list
         */
        private void initHeader(String title, ArrayList<SimpleDialogAdapter.Item> list) {
            if (!TextUtils.isEmpty(title)) {
                SimpleDialogAdapter.ItemObject headerObj = new SimpleDialogAdapter.ItemObject();
                headerObj.content = title;
                SimpleDialogAdapter.Item header = new SimpleDialogAdapter.HeaderItem(headerObj);
                list.add(header);
            }
        }

        /**
         * 只支持SimpleDialogAdapter获取选中下标
         *
         * @return
         */
        private ArrayList<Integer> getSimpleSecelePositions() {
            if (adapter != null) {
                if (adapter instanceof SimpleDialogAdapter) {
                    return ((SimpleDialogAdapter) adapter).getSecelePositions();
                } else {
                    new IllegalAccessException("not support:" + adapter.toString());
                }
            }
            return null;
        }

        public DialogBuilder setWindowAnimations(int animation_resId) {
            this.animation_resId = animation_resId;
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

        public DialogBuilder setContentBackground(Drawable background) {
            this.contentBackground = background;
            return this;
        }

        /**
         * @param color argb
         * @return
         */
        public DialogBuilder setContentBackground(int color) {
            this.contentBackground = new ColorDrawable(color);
            return this;
        }


        public XDialog create(Context context) {
            XDialog dialog = new XDialog(context, this, R.style.DialogStyle);
            return dialog;
        }

        public XDialog create(Context context, int theme) {
            XDialog dialog = new XDialog(context, this, theme);
            return dialog;
        }

    }


}

