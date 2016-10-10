package com.example.animationtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MyClockView extends SurfaceView implements SurfaceHolder.Callback,Runnable {


    private SurfaceHolder mHolder;
    //控制线程的标记位
    private boolean mFlag;

    public MyClockView(Context context) {
        super(context);
    }

    public MyClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    //Surface创建后调用，一般在这里做一些初始化工作
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mFlag = true;
        new Thread(this).start();
    }

    //Surface状态发生变化时调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //Surface销毁时调用，一般在这里结束绘制线程
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mFlag = false;
    }


    @Override
    public void run() {
        long start,end;
        while(mFlag){
            start = System.currentTimeMillis();
            draw();
            logic();
            end = System.currentTimeMillis();
            try {
                if( end - start < 1000) {
                    Thread.sleep(1000 - (end - start));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }

    /**
     * 逻辑操作
     */
    private void logic() {
    }

    /**
     * 绘制操作
     */
    private void draw() {
    }

}
