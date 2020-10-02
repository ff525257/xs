package com.fast.fastxs.xdialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import com.fast.fastxs.R;
import com.fast.fastxs.adapter.LayouModelAdapter;

import java.util.ArrayList;

public class SimpleDialogAdapter extends LayouModelAdapter<SimpleDialogAdapter.Item> {

    private XTextWatcher watcher;

    public SimpleDialogAdapter(ArrayList<Item> list) {
        super(list);
    }

    @Override
    protected void bindViewHolder(XHolder holder, Item item, long position) {
        //内容
        if (item instanceof ContentItem) {
            if (item instanceof EditContentItem) {
                EditText title = holder.getView(R.id.edit);
                setTextContent(title, item.getData());
                if (watcher == null) {
                    watcher = new XTextWatcher(item.getData());
                } else {
                    title.removeTextChangedListener(watcher);
                }
                title.addTextChangedListener(watcher);
            } else {
                if (item.getData().icon == 0) {
                    holder.getView(R.id.icon).setVisibility(View.GONE);
                }
                switch (item.getData().selectType) {
                    case NO:
                        holder.getView(R.id.checkBox).setVisibility(View.VISIBLE);
                        holder.getView(R.id.radio).setVisibility(View.VISIBLE);
                        break;
                    case RADIO:
                        holder.getView(R.id.checkBox).setVisibility(View.GONE);
                        RadioButton radioButton = holder.getView(R.id.radio);
                        radioButton.setVisibility(View.VISIBLE);
                        radioButton.setChecked(item.getData().isSelect);
                        break;
                    case CHECKBOX:
                        holder.getView(R.id.radio).setVisibility(View.GONE);
                        CheckBox checkBox = holder.getView(R.id.checkBox);
                        checkBox.setVisibility(View.VISIBLE);
                        checkBox.setChecked(item.getData().isSelect);
                        break;
                }
                setTextContent((TextView) holder.getView(R.id.content), item.getData());
            }
        } else if (item instanceof HeaderItem) {
            TextView title = (TextView) (holder.itemView);
            setTextContent(title, item.getData());
        } else if (item instanceof LoadingItem) {
            if (!TextUtils.isEmpty(item.getData().content)) {
                setTextContent((TextView) holder.getView(R.id.loading_title), item.getData());
            }
        }
    }

    private class XTextWatcher implements TextWatcher {
        private ItemObject itemObject;

        public XTextWatcher(ItemObject itemObject) {
            this.itemObject = itemObject;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            itemObject.content = s.toString();
        }
    }

    private void setTextContent(TextView textView, ItemObject itemObject) {
        if (!TextUtils.isEmpty(itemObject.content)) {
            textView.setText(itemObject.content);
        }
        if (!TextUtils.isEmpty(itemObject.description)) {
            textView.setHint(itemObject.description);
        }
        if (itemObject.textColor > 0) {
            textView.setTextColor(itemObject.textColor);
        }
        if (itemObject.textSize > 0) {
            textView.setTextSize(itemObject.textSize);
        }
        if (itemObject.gravity > 0) {
            textView.setGravity(itemObject.gravity);
        }
    }

    /**
     * 获取选择的下标位置
     * 先判断是否有头部跟尾部
     * 从头部开始获取选择下标
     * 选择的下标位移一个start
     *
     * @return
     */
    public ArrayList<Integer> getSecelePositions() {
        int count = getItemCount();
        if (count > 0) {
            Item frist = mList.get(0);
            Item last = mList.get(count - 1);
            int start = (frist instanceof HeaderItem) ? 1 : 0;
            int footer = (last instanceof FooterItem) ? 1 : 0;

            ArrayList<Integer> selectIds = new ArrayList<>(count - start - footer);
            for (int i = start; i < count - footer; i++) {
                if (mList.get(i).getData().isSelect) {
                    selectIds.add(i - start);
                }
            }
        }
        return null;
    }


    /**
     * 返回数据框内容
     *
     * @return
     */
    public String getEdittext() {
        int count = getItemCount();
        if (count > 0) {
            Item frist = mList.get(0);
            int start = (frist instanceof HeaderItem) ? 1 : 0;
            return mList.get(start).getData().content;
        }
        return null;
    }


    public static class Item extends LayouModelAdapter.BaseItem<ItemObject> {

        public Item(ItemObject data) {
            super(data);
        }
    }

    public static class HeaderItem extends Item {

        public HeaderItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_header;
        }
    }

    public static class ContentItem extends Item {

        public ContentItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_content;
        }
    }

    public static class EditContentItem extends ContentItem {

        public EditContentItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_content_edit;
        }
    }

    public static class FooterItem extends Item {

        public FooterItem(ItemObject data) {
            super(data);
        }
    }

    public static class VerticalFooterItem extends FooterItem {

        public VerticalFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_vertical;
        }
    }


    public static class CannleFooterItem extends FooterItem {

        public CannleFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_single_cannle;
        }
    }

    public static class HorizontalFooterItem extends FooterItem {

        public HorizontalFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_horizontal;
        }
    }


    public static class LoadingItem extends Item {

        public LoadingItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_loading;
        }
    }

    public enum SelectType {
        NO, CHECKBOX, RADIO
    }


    /**
     * item 描述
     */
    public static class ItemObject {
        /**
         * 内容
         */
        public String content;
        /**
         * 内容字体颜色
         */
        public int textColor;
        /**
         * 内容字体大小
         */
        public int textSize;
        /**
         * 描述
         */
        public String description;
        /**
         * 内容方位
         */
        public int gravity;
        /**
         * 图标
         */
        public int icon;
        /**
         * 选择形式
         */
        public SelectType selectType = SelectType.NO;
        /**
         * 是否选中
         */
        public boolean isSelect;
    }

}
