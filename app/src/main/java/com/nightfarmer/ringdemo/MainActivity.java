package com.nightfarmer.ringdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View ring_by_canvas = findViewById(R.id.ring_by_canvas);
        View ring_by_shape = findViewById(R.id.ring_by_shape);
        ProgressView progressView1 = (ProgressView) findViewById(R.id.progressView1);
        ProgressView progressView2 = (ProgressView) findViewById(R.id.progressView2);

        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(ring_by_canvas, "rotation", -360);
        rotation1.setInterpolator(new LinearInterpolator());
        rotation1.setRepeatCount(ValueAnimator.INFINITE);
        rotation1.setDuration(1500);
        rotation1.start();
        ObjectAnimator rotation2 = ObjectAnimator.ofFloat(ring_by_shape, "rotation", -360);
        rotation2.setInterpolator(new LinearInterpolator());
        rotation2.setRepeatCount(ValueAnimator.INFINITE);
        rotation2.setDuration(1500);
        rotation2.start();

        ObjectAnimator progress1 = ObjectAnimator.ofFloat(progressView1, "progress", 1);
        progress1.setInterpolator(new LinearInterpolator());
        progress1.setRepeatCount(ValueAnimator.INFINITE);
        progress1.setRepeatMode(ObjectAnimator.REVERSE);
        progress1.setDuration(3000);
        progress1.start();

        progressView2.setColors(new int[]{
                Color.parseColor("#FF0000"),
                Color.parseColor("#FF7F00"),
                Color.parseColor("#FFFF00"),
                Color.parseColor("#00FF00"),
                Color.parseColor("#00FFFF"),
                Color.parseColor("#0000FF"),
                Color.parseColor("#8B00FF"),
        });
        ObjectAnimator progress2 = ObjectAnimator.ofFloat(progressView2, "progress", 1);
        progress2.setInterpolator(new LinearInterpolator());
        progress2.setRepeatCount(ValueAnimator.INFINITE);
        progress2.setRepeatMode(ObjectAnimator.REVERSE);
        progress2.setDuration(3000);
        progress2.start();
    }
}
