package com.wh.core.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wh.R;

public class DividerTextView extends TextView {

    public static final short TOP = 1;
    public static final short BOTTOM = 2;
    public static final short ALL = 3;

    /**
     * 后面请跟绘制onDraw的情况
     *
     * @param showLine
     */
    public void setShowLine_notInvalidate(boolean showLine) {
        if (showLine != isShowLine) {
            isShowLine = showLine;
        }
    }

    private boolean isShowLine;

    private Paint paint;
    /**
     * 默认线宽为1px
     */
    private float lineWidth = 1;
    /**
     * 默认线的颜色为浅灰 #D0D0D0
     */
    private int lineColor = 0xffD0D0D0;
    /**
     * 默认上下都显示
     */
    private int showStyle = -1;

    public DividerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerTextView);
        lineColor = a.getColor(R.styleable.DividerTextView_lineColor, 0xffD0D0D0);
        lineWidth = a.getDimensionPixelSize(R.styleable.DividerTextView_lineWidth, 1);
        showStyle = a.getInt(R.styleable.DividerTextView_showStyle, showStyle);
        a.recycle();

        if (showStyle != -1) {
            paint = new Paint();
            paint.setColor(lineColor);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(lineWidth);
            isShowLine = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowLine) {
            if (showStyle == TOP) {
                canvas.drawLine(0, lineWidth / 2, getWidth(), lineWidth / 2, paint);
            } else if (showStyle == BOTTOM) {
                canvas.drawLine(0, getHeight() - lineWidth / 2, getWidth(), getHeight() - lineWidth / 2, paint);
            } else if (showStyle == ALL) {
                canvas.drawLine(0, lineWidth / 2, getWidth(), lineWidth / 2, paint);
                canvas.drawLine(0, getHeight() - lineWidth / 2, getWidth(), getHeight() - lineWidth / 2, paint);
            }
        }
    }


}
