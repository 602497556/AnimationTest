package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

import com.example.animationtest.R;

/**
 * Created by ZENGJIE on 2016/11/23.
 */
public class XfermodeView extends View {

    private Bitmap mBgBitmap, mFgBitmap;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;

    public XfermodeView(Context context) {
        super(context);
        init();
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的笔触和连接处更加圆润
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(50);
        //设置画笔的笔触和连接处更加圆润
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();

        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.top_image);
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(),
                mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //装载画布
        mCanvas = new Canvas(mFgBitmap);
        //作用在mFgBitmap上
        mCanvas.drawColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBgBitmap,0,0,null);
        canvas.drawBitmap(mFgBitmap,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE :
                mPath.lineTo(event.getX(), event.getY());
                break;
        }
        //drawPath的效果作用在mFgBitmap上
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

}
