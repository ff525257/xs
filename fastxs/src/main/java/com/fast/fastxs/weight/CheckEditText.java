package com.fast.fastxs.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.fast.fastxs.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CheckEditText
 * @Description:
 * @Author: 范明华
 * @CreateDate: 2020/9/9 0009 14:12
 * @Version: 1.0
 */
public class CheckEditText extends android.support.v7.widget.AppCompatEditText {

    private String errorHint;
    private String pattern;
    private String emptyHint;

    public String getEmptyHint() {
        return emptyHint;
    }

    public void setEmptyHint(String emptyHint) {
        this.emptyHint = emptyHint;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getErrorHint() {
        return errorHint;
    }

    public void setErrorHint(String errorHint) {
        this.errorHint = errorHint;
    }


    public CheckEditText(Context context) {
        super(context);
    }

    public CheckEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CheckEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckEditText);
        errorHint = a.getString(R.styleable.CheckEditText_errorHint);
        pattern = a.getString(R.styleable.CheckEditText_pattern);
        emptyHint = a.getString(R.styleable.CheckEditText_emptyHint);
        a.recycle();
    }

    /**
     * @return null:表示通过,否则返回提示信息
     */
    public String isValidate() {
        //stp1:先判断是否为null
        if (getText().length() == 0 && !TextUtils.isEmpty(emptyHint)) {
            return emptyHint + getResources().getString(R.string.common_empty);
        }

        if (!TextUtils.isEmpty(errorHint)) {
            if (pattern != null && !TextUtils.isEmpty(pattern)) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(getText());
                //规则不为空就继续
                if (!m.matches()) {
                    return errorHint;
                }
            }
        }
        return null;
    }

    public static String invidateCheckViews(CheckEditText... views) {
        for (CheckEditText checkView : views) {
            String msg = checkView.isValidate();
            if (!TextUtils.isEmpty(msg)) {
                return msg;
            }
        }
        return null;
    }


}
