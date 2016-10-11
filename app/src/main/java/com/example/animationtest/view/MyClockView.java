package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MyClockView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    //默认半径
    private static final int DEFAULT_RADIUS = 200;

    private SurfaceHolder mHolder;
    //控制线程的标记位
    private boolean mFlag;
    //圆以及刻度的画笔
    private Paint mPaint;
    //指针画笔
    private Paint mPointerPaint;
    private Canvas mCanvas;
    //画布的宽高
    private int mCanvasWidth,mCanvasHeight;
    //时钟半径
    private int radius = DEFAULT_RADIUS;
    //时刻度长度,秒刻度长度
    private int mHourDegreeLength,mSecondDegreeLength;
    //时针，分针和秒针的长度
    private int mHourPointerLength,mMinutePointerLength,mSecondPointerLength;
    //时分秒
    private int mHour,mMinute,mSecond;


    public MyClockView(Context context) {
        this(context,null);
    }

    public MyClockView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        //获得时分秒
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mSecond = Calendar.getInstance().get(Calendar.SECOND);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint.setColor(Color.BLACK);
        mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointerPaint.setTextSize(22);
        mPointerPaint.setTextAlign(Paint.Align.CENTER);

        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int desiredWidth,desiredHeight;
        if(widthMode == MeasureSpec.EXACTLY){
            desiredWidth = widthSize;
        } else {
            desiredWidth = radius*2 + getPaddingLeft() + getPaddingRight();
            if(widthMode == MeasureSpec.AT_MOST){
                desiredWidth = Math.min(widthSize,desiredWidth);
            }
        }

        if(heightMode == MeasureSpec.EXACTLY){
            desiredHeight = heightSize;
        } else {
            desiredHeight = radius*2 + getPaddingTop() + getPaddingBottom();
            if(heightMode == MeasureSpec.AT_MOST){
                desiredHeight = Math.min(heightSize,desiredHeight);
            }
        }
        //加4是为了设置默认的2px的内边距，因为绘制时钟的圆的画笔设置的宽度为2px
        setMeasuredDimension(mCanvasWidth = desiredWidth+4,mCanvasHeight = desiredHeight+4);
        radius = (int) (Math.min(mCanvasWidth-getPaddingLeft()-getPaddingRight(),
                           mCanvasHeight-getPaddingTop()-getPaddingBottom())*1.0f/2);
        calculateLengths();
    }

    /**
     * 计算指针和刻度长度
     */
    private void calculateLengths() {
        //定义时刻度长度为半径的1/7
        mHourDegreeLength = (int) (radius*1.0f/7);
        //定义秒刻度长度为时刻度长度的一半
        mSecondDegreeLength = (int) (mHourDegreeLength*1.0f/2);
        //时针长度为半径的一半
        //指针长度比：hour:minute:second = 1:1.25:1.5
        mHourPointerLength = (int) (radius*1.0f/2);
        mMinutePointerLength = (int) (mHourPointerLength*1.25f);
        mSecondPointerLength = (int) (mHourPointerLength*1.5f);
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
        mSecond++;
        if(mSecond == 60){
            mSecond = 0;
            mMinute++;
            if(mMinute == 60){
                mMinute = 0;
                mHour++;
                if(mHour == 24){
                    mHour = 0;
                }
            }
        }

    }

    /**
     * 绘制操作,这部分代码基本固定
     */
    private void draw() {
        try{
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null){
                drawDetail();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(mCanvas != null){
                //提交画布，否则什么都看不见
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    /**
     * 详细的绘制
     */
    private void drawDetail() {
        //刷屏
        mCanvas.drawColor(Color.WHITE);
        //1,将坐标系原点移至去除内边距后的画布中心
        mCanvas.translate(mCanvasWidth*1.0f/2+getPaddingLeft()-getPaddingRight(),
                        mCanvasHeight*1.0f/2+getPaddingTop()-getPaddingBottom());
        //2,绘制圆盘
        mPaint.setStrokeWidth(2f);
        mCanvas.drawCircle(0,0,radius,mPaint);
        //3，绘制时刻度
        for(int i=0;i<12;i++){
            mCanvas.drawLine(0,radius,0,radius-mHourDegreeLength,mPaint);
            mCanvas.rotate(30);
        }
        //4,绘制秒刻度
        mPaint.setStrokeWidth(1.5f);
        for(int i=0;i<60;i++){
            if(i%5 != 0){
                mCanvas.drawLine(0,radius,0,radius-mSecondDegreeLength,mPaint);
            }
            mCanvas.rotate(6);
        }
        //5,绘制数字
        mPointerPaint.setColor(Color.BLACK);
        for(int i=0;i<12;i++){
            String text = (6+i) < 12 ? String.valueOf(6+i):(6+i) > 12 ?
                    String.valueOf(6+i-12): "12";
            mCanvas.drawText(text,0,radius*5.5f/7,mPointerPaint);
            mCanvas.rotate(30);
        }
        //6,绘制上下午
        mCanvas.drawText(mHour<12 ? "AM" : "PM",0,radius*1.5f/4,mPointerPaint);
        //7,绘制时针
        Path path = new Path();
        path.moveTo(0,0);
        int[] hourPointerCoordinates = getPointerCoordinates(mHourPointerLength);
        path.lineTo(hourPointerCoordinates[0],hourPointerCoordinates[1]);
        path.lineTo(hourPointerCoordinates[2],hourPointerCoordinates[3]);
        path.lineTo(hourPointerCoordinates[4],hourPointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mHour % 12 * 30 + mMinute *1.0f /60 *30);
        mCanvas.drawPath(path,mPointerPaint);
        mCanvas.restore();
        //8，绘制分针
        path.reset();
        path.moveTo(0,0);
        int [] minutePointerCoordinates = getPointerCoordinates(mMinutePointerLength);
        path.lineTo(minutePointerCoordinates[0],minutePointerCoordinates[1]);
        path.lineTo(minutePointerCoordinates[2],minutePointerCoordinates[3]);
        path.lineTo(minutePointerCoordinates[4],minutePointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mMinute*6);
        mCanvas.drawPath(path,mPointerPaint);
        mCanvas.restore();
        //9,绘制秒针
        mPointerPaint.setColor(Color.RED);
        path.reset();
        path.moveTo(0,0);
        int [] secondPointerCoordinates = getPointerCoordinates(mSecondPointerLength);
        path.lineTo(secondPointerCoordinates[0],secondPointerCoordinates[1]);
        path.lineTo(secondPointerCoordinates[2],secondPointerCoordinates[3]);
        path.lineTo(secondPointerCoordinates[4],secondPointerCoordinates[5]);
        path.close();
        mCanvas.save();
        mCanvas.rotate(180 + mSecond * 6);
        mCanvas.drawPath(path,mPointerPaint);
        mCanvas.restore();
    }

    /**
     * 计算指针形状的三个坐标
     *
     * @param pointerLength 指针长度
     * @return int[]{x1,y1,x2,y2,x3,y3}
     */
    private int[] getPointerCoordinates(int pointerLength) {
        int y = (int) (pointerLength*3.0f/4);
        int x = (int) (y * Math.tan(Math.PI/180 * 5));
        return new int[]{-x,y,0,pointerLength,x,y};
    }


}
