package com.wh.core.xdialog;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wh.R;
import com.wh.core.layoutmodel.LayouModelAdapter;

import java.util.ArrayList;

public class SimpleDialogAdapter extends LayouModelAdapter<SimpleDialogAdapter.Item> {


    public SimpleDialogAdapter(ArrayList<Item> list) {
        super(list);
    }

    @Override
    protected void bindViewHolder(XHolder holder, Item baseBean, long position) {
        if (baseBean instanceof HeaderItem) {
            TextView title = (TextView) (holder.itemView);
            title.setText(baseBean.getData().text);
            if (baseBean.getData().textColor != 0) {
                title.setTextColor(baseBean.getData().textColor);
            }
            if (baseBean.getData().gravity != 0) {
                title.setGravity(baseBean.getData().gravity);
            }
        } else if (baseBean instanceof ContentItem) {
            if (baseBean.getData().icon == 0) {
                holder.findViewById(R.id.icon).setVisibility(View.GONE);
            }
            switch (baseBean.getData().selectType) {
                case NO:
                    holder.findViewById(R.id.checkBox).setVisibility(View.VISIBLE);
                    holder.findViewById(R.id.radio).setVisibility(View.VISIBLE);
                    break;
                case RADIO:
                    holder.findViewById(R.id.checkBox).setVisibility(View.GONE);
                    RadioButton radioButton = holder.findViewById(R.id.radio);
                    radioButton.setVisibility(View.VISIBLE);
                    radioButton.setChecked(baseBean.getData().isSelect);
                    break;
                case CHECKBOX:
                    holder.findViewById(R.id.radio).setVisibility(View.GONE);
                    CheckBox checkBox = holder.findViewById(R.id.checkBox);
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setChecked(baseBean.getData().isSelect);
                    break;
            }

            holder.setText(R.id.content, baseBean.getData().text);
        }
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

    public static class VerticalFooterItem extends Item {

        public VerticalFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_vertical;
        }
    }


    public static class CannleFooterItem extends Item {

        public CannleFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_single_cannle;
        }
    }

    public static class HorizontalFooterItem extends Item {

        public HorizontalFooterItem(ItemObject data) {
            super(data);
            layoutId = R.layout.simple_footer_horizontal;
        }
    }

    public enum SelectType {
        NO, CHECKBOX, RADIO
    }


    /**
     * item 描述
     */
    public static class ItemObject {
        public String text;
        public int textColor;
        public int gravity;
        public int icon;
        public SelectType selectType = SelectType.NO;
        //是否选中
        public boolean isSelect;
    }

}
