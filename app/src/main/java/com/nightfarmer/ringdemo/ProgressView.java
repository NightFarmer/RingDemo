package com.nightfarmer.ringdemo;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangfan on 17-7-24.
 */

public class ProgressView extends View {
    private int height;
    private int width;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paintBg = new Paint();
    Paint paintBgEnds = new Paint();

    Paint paintProgress = new Paint();
    Paint getPaintProgressEnds = new Paint();

    RectF rect = new RectF();

    int strokeWidth = 30;

    private void init() {
        paintBg.setColor(Color.WHITE);
        paintBg.setStrokeWidth(strokeWidth);
        paintBg.setStyle(Paint.Style.STROKE);
        paintBg.setAntiAlias(true);

        paintBgEnds.setColor(Color.WHITE);
        paintBgEnds.setAntiAlias(true);

        paintProgress.setColor(Color.RED);
        paintProgress.setStrokeWidth(strokeWidth);
        paintProgress.setStyle(Paint.Style.STROKE);
        paintProgress.setAntiAlias(true);

        getPaintProgressEnds.setColor(Color.RED);
        getPaintProgressEnds.setAntiAlias(true);
    }

    private float progress = 0f;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    int[] colors = new int[]{
            Color.parseColor("#FFDDDD"),
            Color.parseColor("#FF0000"),
    };

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    ArgbEvaluator argbEvaluator = new ArgbEvaluator();//渐变色计算类

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = width / 2;
        int centerY = height / 2;

        float startX = (float) (circleSize * Math.sin(300 * Math.PI / 180));
        float startY = (float) (circleSize * Math.cos(300 * Math.PI / 180));
        paintBgEnds.setColor(Color.WHITE);//背板左圆端
        canvas.drawCircle(centerX + startX, centerY + startY, strokeWidth / 2f, paintBgEnds);

        float endX = (float) (circleSize * Math.sin(60 * Math.PI / 180));
        float endY = (float) (circleSize * Math.cos(60 * Math.PI / 180));
        paintBgEnds.setColor(Color.WHITE);//背板右圆端
        canvas.drawCircle(centerX + endX, centerY + endY, strokeWidth / 2f, paintBgEnds);

        canvas.drawArc(rect, 150, 240, false, paintBg);//背板

//        int currentLastColor = (int) (argbEvaluator.evaluate(60f / 360, colors[0], colors[1]));
        int currentLastColor = getCurrentColor(60f / 360, colors);
        paintBgEnds.setColor(currentLastColor);//进度左圆端
        canvas.drawCircle(centerX + startX, centerY + startY, strokeWidth / 2f, paintBgEnds);

        float currentX = (float) (circleSize * Math.sin((60 + 240 * (1 - progress)) * Math.PI / 180));
        float currentY = (float) (circleSize * Math.cos((60 + 240 * (1 - progress)) * Math.PI / 180));
//        currentLastColor = (int) (argbEvaluator.evaluate((300f - 240 * (1 - progress)) / 360, colors[0], colors[1]));
        currentLastColor = getCurrentColor((300f - 240 * (1 - progress)) / 360, colors);
        paintBgEnds.setColor(currentLastColor);//进度右圆端
        canvas.drawCircle(centerX + currentX, centerY + currentY, strokeWidth / 2f, paintBgEnds);


//        canvas.drawCircle(width, height, strokeWidth / 2f, getPaintProgressEnds);
        paintProgress.setShader(new SweepGradient(width / 2f, height / 2f, colors, null));
        canvas.rotate(90, width / 2f, height / 2);
        canvas.drawArc(rect, 60, 240 * progress, false, paintProgress);//进度

    }

    /**
     * 颜色渐变算法
     * 获取某个百分比下的渐变颜色值
     *
     * @param percent
     * @param colors
     * @return
     */
    public static int getCurrentColor(float percent, int[] colors) {
        float[][] f = new float[colors.length][3];
        for (int i = 0; i < colors.length; i++) {
            f[i][0] = (colors[i] & 0xff0000) >> 16;
            f[i][1] = (colors[i] & 0x00ff00) >> 8;
            f[i][2] = (colors[i] & 0x0000ff);
        }
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < f.length; j++) {
                if (f.length == 1 || percent == j / (f.length - 1f)) {
                    result = f[j];
                } else {
                    if (percent > j / (f.length - 1f) && percent < (j + 1f) / (f.length - 1)) {
                        result[i] = f[j][i] - (f[j][i] - f[j + 1][i]) * (percent - j / (f.length - 1f)) * (f.length - 1f);
                    }
                }
            }
        }
        return Color.rgb((int) result[0], (int) result[1], (int) result[2]);
    }

    float circleSize = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        int size = Math.min(width, height);
        rect.left = strokeWidth / 2f;
        rect.top = strokeWidth / 2f;
        rect.right = size - strokeWidth / 2f;
        rect.bottom = size - strokeWidth / 2f;

        circleSize = (width - strokeWidth) / 2f;

    }
}
