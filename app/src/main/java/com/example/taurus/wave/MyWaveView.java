package com.example.taurus.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Taurus on 2018/3/30.
 */


public class MyWaveView extends View {

    Paint mPaint;
    int colorPaint = Color.parseColor("#4099AE");
    Path mPath;
    int mWidth;
    int mHeight;
    int mline = 80;
    int temp = -1000;
    Point conPoint1;
    Point conPoint2;

    public MyWaveView(Context context) {
        super(context);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyWaveView);
        colorPaint = a.getColor(R.styleable.MyWaveView_waveColor, Color.parseColor("#4099AE"));
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(colorPaint);
        mPaint.setStyle(Paint.Style.FILL);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        ValueAnimator animator = ValueAnimator.ofInt(-1200, 900,-1200,1000,-500,600,-1000,1300,-1200);
        animator.setDuration(20000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mline =  80+20*temp/1000;
                temp = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }


    private Path getPath() {
        mPath = new Path();
        mWidth = getWidth();
        mHeight = getHeight();
        conPoint1 = new Point(mWidth / 4, mline + 100*temp/1000);
        conPoint2 = new Point(mWidth / 4 * 3, mline - 100*temp/1000);
        mPath.moveTo(0, mline);
        mPath.cubicTo(conPoint1.getX(), conPoint1.getY(), conPoint2.getX(), conPoint2.getY(), mWidth, mline);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.lineTo(0, 0);
        mPath.close();
        return mPath;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getPath(), mPaint);
    }

    class Point {
        private int X;
        private int Y;

        private Point(int X, int Y) {
            setX(X);
            setY(Y);
        }

        private int getX() {
            return X;
        }

        private int getY() {
            return Y;
        }

        private void setX(int X) {
            this.X = X;
        }

        private void setY(int Y) {
            this.Y = Y;
        }
    }
}


