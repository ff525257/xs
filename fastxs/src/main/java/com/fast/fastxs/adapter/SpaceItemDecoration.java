package com.fast.fastxs.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int leftRight;
    private int top;
    private int lastBottom;
    private Paint mPaint;

    //leftRight为横向间的距离 topBottom为纵向间距离
    public SpaceItemDecoration(int leftRight, int top, int lastBottom, int color) {
        this.leftRight = leftRight;
        this.top = top;
        this.lastBottom = lastBottom;
        if (color != -1) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(color);
        }
    }

    //leftRight为横向间的距离 topBottom为纵向间距离
    public SpaceItemDecoration(int leftRight, int topBottom, int color) {
        this.leftRight = leftRight;
        this.top = topBottom;
        this.lastBottom = topBottom;
        if (color != -1) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(color);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mPaint != null) {
            int childCount = parent.getChildCount();
            int start = 1;
            for (int i = start; i < childCount; i++) {
                View view = parent.getChildAt(i);
                float dividerTop = view.getTop() - top;
                float dividerLeft = parent.getPaddingLeft();
                float dividerBottom = view.getTop();
                float dividerRight = parent.getWidth() - parent.getPaddingRight();
                c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            //竖直方向的
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //最后一项需要 bottom
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = lastBottom;
                }
                outRect.top = top;
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                outRect.top = top;
                outRect.left = leftRight;
                outRect.bottom = lastBottom;
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = 0;
                }
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = 0;
                }
            }
        }
    }


}

