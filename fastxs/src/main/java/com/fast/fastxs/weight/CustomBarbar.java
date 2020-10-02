package com.fast.fastxs.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fast.fastxs.R;

import java.util.ArrayList;


/**
 * addLeftView 添加左边控件,按先后顺序排列
 * addRightView 添加右边控件,按先后顺序排列
 * addMiddleViewExist 添加中间控件,只允许同时存在一个
 *
 * @author fmh
 */
public class CustomBarbar extends RelativeLayout {
    private ArrayList<ViewItem> views = new ArrayList<>(0);
    /**
     * 间隙
     */
    private int gap = 0;

    public CustomBarbar(Context context) {
        super(context);
    }

    public CustomBarbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomBarbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomBarbar);
        gap = a.getDimensionPixelSize(R.styleable.CustomBarbar_gap, 10);
        a.recycle();
    }


    public View addLeftView(View view) {
        return addCustomView(view, Model.LEFT);
    }

    public View addRightView(View view) {
        return addCustomView(view, Model.RIGHT);
    }

    public View addMiddleViewExist(View view) {
        return addCustomView(view, Model.MIDDLE);
    }

    private View addCustomView(View view, Model model) {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        View lastViewByModel = null;
        int rule = ALIGN_PARENT_LEFT;
        switch (model) {
            case LEFT:
                rule = ALIGN_PARENT_LEFT;
                break;
            case RIGHT:
                rule = ALIGN_PARENT_RIGHT;
                break;
            case MIDDLE:
                rule = CENTER_IN_PARENT;
                break;
        }
        for (ViewItem item : views) {
            if (item.model == model) {
                lastViewByModel = item.view;
            }
        }
        //中间只允许放一个
        if (model == Model.MIDDLE && lastViewByModel != null) {
            removeView(lastViewByModel);
            lastViewByModel = null;
        }

        if (lastViewByModel == null) {
            lp.addRule(rule);
        } else {
            switch (model) {
                case LEFT:
                    lp.leftMargin = gap;
                    lp.addRule(RIGHT_OF, lastViewByModel.getId());
                    break;
                case RIGHT:
                    lp.rightMargin = gap;
                    lp.addRule(LEFT_OF, lastViewByModel.getId());
                    break;
                case MIDDLE:
                    break;
            }

        }

        lp.addRule(CENTER_VERTICAL);
        addView(view, lp);
        views.add(new ViewItem(view, model));
        view.setId(views.size());
        return view;
    }

    /**
     * 获取textview
     *
     * @param text
     * @return
     */
    public TextView getTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        return textView;
    }

    /**
     * 获取ImageView
     *
     * @param resId
     * @return
     */
    public ImageView getImageView(int resId) {
        ImageView imageView = new ImageView(getContext());
        if (resId != NO_ID) {
            imageView.setImageResource(resId);
        }
        return imageView;
    }

    private class ViewItem {
        public View view;
        public Model model = Model.LEFT;

        public ViewItem(View view, Model model) {
            this.view = view;
            this.model = model;
        }

    }

    public enum Model {
        LEFT, MIDDLE, RIGHT
    }

}
