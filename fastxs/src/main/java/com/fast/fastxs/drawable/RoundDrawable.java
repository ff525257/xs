package com.fast.fastxs.drawable;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * 圆角图片
 */
public class RoundDrawable extends StateListDrawable {


    public RoundDrawable(int backgroundColor, int radius) {
        this(backgroundColor, backgroundColor, radius, radius, radius, radius);
    }

    public RoundDrawable(int backgroundColor, int backgroundColorPress, int radius) {
        this(backgroundColor, backgroundColorPress, radius, radius, radius, radius);
    }


    public RoundDrawable(int backgroundColor, int backgroundColorPress, int leftTopRadius,
                         int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {

        //默认
        ShapeDrawable defaultDrawable = new ShapeDrawable(
                getRoundRectShape(leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius)
        );
        defaultDrawable.getPaint().setColor(backgroundColor);
        addState(new int[]{-android.R.attr.state_pressed}, defaultDrawable);

        if (backgroundColor == backgroundColorPress) {
            return;
        }

        ShapeDrawable drawablePress = new ShapeDrawable(
                getRoundRectShape(leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius)
        );
        drawablePress.getPaint().setColor(backgroundColorPress);
        addState(new int[]{android.R.attr.state_pressed}, drawablePress);

    }

    /**
     * {l, l, t, t, r, r, b, b};
     *
     * @param leftTop
     * @param rightTop
     * @param rightBottom
     * @param leftBottom
     * @return
     */
    private RoundRectShape getRoundRectShape(int leftTop, int rightTop, int rightBottom, int leftBottom) {
        float outerRadii[] = new float[8];
        if (leftTop > 0) {
            outerRadii[0] = outerRadii[1] = leftTop;
        }
        if (rightTop > 0) {
            outerRadii[2] = outerRadii[3] = rightTop;
        }
        if (rightBottom > 0) {
            outerRadii[4] = outerRadii[5] = rightBottom;
        }
        if (leftBottom > 0) {
            outerRadii[6] = outerRadii[7] = leftBottom;
        }
        return new RoundRectShape(outerRadii, null, null);
    }

}
