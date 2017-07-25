package com.nightfarmer.ringdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zhangfan on 17-7-24.
 */

public class RingView extends View {
    private int height;
    private int width;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paintRing = new Paint();
    Paint paintEnds = new Paint();

    private void init() {
        paintRing.setColor(Color.RED);
        paintRing.setStrokeWidth(ringWidth);
        paintRing.setStyle(Paint.Style.STROKE);
        paintRing.setColor(Color.WHITE);
        paintRing.setAntiAlias(true);
        paintEnds.setAntiAlias(true);
        paintEnds.setStyle(Paint.Style.FILL);
        paintEnds.setColor(Color.RED);
    }

    int ringWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getContext().getResources().getDisplayMetrics());
    int[] ringColors = new int[]{
            Color.RED,
            Color.parseColor("#00FF0000")
    };
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int offsetX = Math.abs(width - height) / 2;
        int offsetY = Math.abs(height - width) / 2;
        int left = (width > height ? offsetX : 0) + ringWidth / 2;
        int top = (height > width ? offsetY : 0) + ringWidth / 2;
        int right = width - (width > height ? offsetX : 0) - ringWidth / 2;
        int bottom = height - (height > width ? offsetY : 0) - ringWidth / 2;
        RectF rectF = new RectF(left, top, right, bottom);

//        canvas.rotate(-90, width / 2, height / 2);

        if (ringColors.length > 1) {
            paintRing.setShader(new SweepGradient(width / 2, height / 2, ringColors, null));
        } else {
            paintRing.setColor(ringColors[0]);
        }
        canvas.drawArc(rectF, 0, 360, false, paintRing);

        canvas.drawCircle(width - ringWidth / 2, 1f * height / 2, ringWidth / 2, paintEnds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }
}
