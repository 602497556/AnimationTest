package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ZENGJIE on 2016/11/24.
 */
public class SimpleDraw extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    //子线程标志位
    private boolean isOnDrawing;

    private Path mPath;
    private Paint mPaint;


    public SimpleDraw(Context context) {
        super(context);
        init();
    }

    public SimpleDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        //这两个设置有什么用
        setFocusable(true);
        setFocusableInTouchMode(true);
        //屏幕常亮
        this.setKeepScreenOn(true);

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isOnDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isOnDrawing = false;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while ( isOnDrawing ){
            draw();
        }
        long end = System.currentTimeMillis();
        if(end - start < 100) {
            try {
                Thread.sleep(100 - (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 具体的绘制逻辑
     */
    private void draw() {
        try {
            //获得画布
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e){
        } finally {
            if( mCanvas != null )
            mHolder.unlockCanvasAndPost(mCanvas);//提交画布内容
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                //mPath.reset();
                break;
        }
        return true;
    }

}
