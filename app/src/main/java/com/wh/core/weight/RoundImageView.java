package com.wh.core.weight;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.wh.R;


/**
 * 可设置4个圆角,圆形,覆盖阴影，中间显示文字
 * darkDegree阴影效果
 */
public class RoundImageView extends AppCompatImageView {
    public static final int TYPE_NONE = 0;//(默认)android.widget.ImageView
    public static final int TYPE_CIRCLE = 1;//圆形
    public static final int TYPE_ROUNDED_RECT = 2;//圆角矩形

    private static final int DEFAULT_TYPE = TYPE_NONE;
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_RECT_ROUND_RADIUS = 0;

    private int mType = DEFAULT_TYPE;
    private int mBorderColor;
    private int mBorderWidth;
    private int mRectRoundRadius;

    private int cornerTopLeftRadius; // 左上角圆角半径
    private int cornerTopRightRadius; // 右上角圆角半径
    private int cornerBottomLeftRadius; // 左下角圆角半径
    private int cornerBottomRightRadius; // 右下角圆角半径
    //RGB偏移量，变暗为负数
    private int darkDegree;
    //上面绘制文字
    private String mText;
    private int textColor;
    private float textSize;

    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF mRectBorder = new RectF();
    private RectF mRectBitmap = new RectF();

    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RoundImageView(Context context) {
        super(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        boolean isCircle = ta.getBoolean(R.styleable.RoundImageView_is_circle, false);

        mBorderWidth = ta.getDimensionPixelSize(R.styleable.RoundImageView_border_width, DEFAULT_BORDER_WIDTH);
        mBorderColor = ta.getColor(R.styleable.RoundImageView_border_color, DEFAULT_BORDER_COLOR);
        mRectRoundRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_corner_radius, DEFAULT_RECT_ROUND_RADIUS);
        if (mRectRoundRadius > 0) {
            mType = TYPE_ROUNDED_RECT;
        }
        cornerTopLeftRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_corner_top_right_radius, cornerTopLeftRadius);
        cornerTopRightRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_corner_top_left_radius, cornerTopRightRadius);
        cornerBottomLeftRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_corner_bottom_right_radius, cornerBottomLeftRadius);
        cornerBottomRightRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_corner_bottom_right_radius, cornerBottomRightRadius);
        darkDegree = ta.getInt(R.styleable.RoundImageView_dark_degree, darkDegree);

        mText = ta.getString(R.styleable.RoundImageView_text);
        textColor = ta.getColor(R.styleable.RoundImageView_textcolor, Color.BLACK);
        textSize = ta.getDimension(R.styleable.RoundImageView_textSize, new Configuration().fontScale);

        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        if (isCircle) {
            mType = TYPE_CIRCLE;
        }

        if (darkDegree != 0) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.set(new float[]{1, 0, 0, 0, darkDegree, 0, 1, 0, 0, darkDegree, 0, 0, 1, 0, darkDegree, 0, 0, 0, 1, 0});
            ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
            mPaintBitmap.setColorFilter(cmcf);
        }

        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mType != TYPE_NONE) {
            Bitmap rawBitmap = getBitmap(getDrawable());
            if (rawBitmap != null) {
                int viewWidth = getWidth();
                int viewHeight = getHeight();
                int viewMinSize = Math.min(viewWidth, viewHeight);
                float dstWidth = mType == TYPE_CIRCLE ? viewMinSize : viewWidth;
                float dstHeight = mType == TYPE_CIRCLE ? viewMinSize : viewHeight;
                float halfBorderWidth = mBorderWidth / 2.0f;
                float doubleBorderWidth = mBorderWidth * 2;

                if (mShader == null || rawBitmap != mRawBitmap) {
                    mRawBitmap = rawBitmap;
                    mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    mMatrix.setScale((dstWidth - doubleBorderWidth) / rawBitmap.getWidth(), (dstHeight - doubleBorderWidth) / rawBitmap.getHeight());
                    mShader.setLocalMatrix(mMatrix);
                    mPaintBitmap.setShader(mShader);
                }

                mPaintBorder.setStyle(Paint.Style.STROKE);
                mPaintBorder.setStrokeWidth(mBorderWidth);
                mPaintBorder.setColor(mBorderWidth > 0 ? mBorderColor : Color.TRANSPARENT);

                if (mType == TYPE_CIRCLE) {
                    float radius = viewMinSize / 2.0f;
                    canvas.drawCircle(radius, radius, radius - halfBorderWidth, mPaintBorder);
                    canvas.translate(mBorderWidth, mBorderWidth);
                    canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius - mBorderWidth, mPaintBitmap);
                } else if (mType == TYPE_ROUNDED_RECT) {
                    mRectBorder.set(halfBorderWidth, halfBorderWidth, dstWidth - halfBorderWidth, dstHeight - halfBorderWidth);
                    mRectBitmap.set(0.0f, 0.0f, dstWidth - doubleBorderWidth, dstHeight - doubleBorderWidth);
                    float borderRadius = mRectRoundRadius - halfBorderWidth > 0.0f ? mRectRoundRadius - halfBorderWidth : 0.0f;
                    float bitmapRadius = mRectRoundRadius - mBorderWidth > 0.0f ? mRectRoundRadius - mBorderWidth : 0.0f;
                    if (borderRadius > bitmapRadius) {
                        canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder);
                        canvas.translate(mBorderWidth, mBorderWidth);
                    }
                    canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBitmap);


                    if ((cornerTopLeftRadius + cornerTopRightRadius + cornerBottomLeftRadius + cornerBottomRightRadius) > 0) {
                        if (cornerTopLeftRadius == 0) {
                            clipTopLeft(canvas, mPaintBitmap, (int) bitmapRadius, (int) mRectBitmap.width(), (int) mRectBitmap.height());
                        }
                        if (cornerTopRightRadius == 0) {
                            clipTopRight(canvas, mPaintBitmap, (int) bitmapRadius, (int) mRectBitmap.width(), (int) mRectBitmap.height());
                        }
                        if (cornerBottomLeftRadius == 0) {
                            clipBottomLeft(canvas, mPaintBitmap, (int) bitmapRadius, (int) mRectBitmap.width(), (int) mRectBitmap.height());
                        }
                        if (cornerBottomRightRadius == 0) {
                            clipBottomRight(canvas, mPaintBitmap, (int) bitmapRadius, (int) mRectBitmap.width(), (int) mRectBitmap.height());
                        }
                    }
                }
                if (!TextUtils.isEmpty(mText)) {
                    Rect rect = new Rect();
                    textPaint.getTextBounds(mText, 0, mText.length(), rect);
                    canvas.drawText(mText, (viewWidth - rect.width()) / 2, (viewHeight - rect.height()) / 2 + rect.height(), textPaint);
                }
            }
        } else {
            super.onDraw(canvas);
        }
    }

    public void setText(String text) {
        mText = text;
    }

    private void clipTopLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, 0, offset, offset);
        canvas.drawRect(block, paint);
    }

    private void clipTopRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, 0, width, offset);
        canvas.drawRect(block, paint);
    }

    private void clipBottomLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, height - offset, offset, height);
        canvas.drawRect(block, paint);
    }

    private void clipBottomRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, height - offset, width, height);
        canvas.drawRect(block, paint);
    }


    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
        invalidate();
    }

    public void setCornerRadius(int radius) {
        if (radius > 0) {
            mType = TYPE_ROUNDED_RECT;
        }
        mRectRoundRadius = radius;
        invalidate();
    }

    public void setCornerRadiusNotUpdate(int radius) {
        if (radius > 0) {
            mType = TYPE_ROUNDED_RECT;
        }
        mRectRoundRadius = radius;
    }


}