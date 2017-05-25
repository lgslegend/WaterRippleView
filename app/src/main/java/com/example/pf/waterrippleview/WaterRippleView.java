package com.example.pf.waterrippleview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 水波纹效果
 * Created by Administrator on 2017/5/25.
 */

public class WaterRippleView extends View {
    private Path mPath;
    private Paint mPaint;
    /**
     * 一个波长
     */
    private int WAVE_LENGTH = 800;
    /**
     * 当前水波纹的高度
     */
    private int mCurHeight = 1200;
    /**
     * 水波纹偏移量
     */
    private int mDelayX;

    public WaterRippleView(Context context) {
        this(context, null);
    }

    public WaterRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();

        startAnim();
    }

    private void startAnim() {
        ValueAnimator anim = ValueAnimator.ofInt(0, WAVE_LENGTH);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDelayX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();


        ValueAnimator anim2 = ValueAnimator.ofInt(mCurHeight, 0);
        anim2.setInterpolator(new LinearInterpolator());
//        anim2.setRepeatCount(ValueAnimator.INFINITE);
        anim2.setDuration(5000);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim2.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int halfWaveLength = WAVE_LENGTH / 4;
        mPath.reset();
        mPath.moveTo(-WAVE_LENGTH + mDelayX, mCurHeight);

        for (int i = -WAVE_LENGTH; i < getWidth() + WAVE_LENGTH * 2; i += WAVE_LENGTH) {
            mPath.rQuadTo(halfWaveLength / 2, 60, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength / 2, -60, halfWaveLength, 0);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }
}