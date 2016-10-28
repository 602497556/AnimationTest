package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/10/28.
 */
public class RectOnCamera extends View {

    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;
    private RectF mRectF;
    //圆
    private Point centerPoint;
    private int radio;

    public RectOnCamera(Context context) {
        this(context,null);
    }

    public RectOnCamera(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectOnCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenMetrics(context);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);//防抖动
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);//空心
        int marginLeft = (int) (mScreenWidth * 0.15);
        int marginTop = (int) (mScreenHeight * 0.25);
        mRectF = new RectF(marginLeft,marginTop,
                mScreenWidth-marginLeft, mScreenHeight-marginTop);
        centerPoint = new Point(mScreenWidth/2,mScreenHeight/2);
        radio = (int) (mScreenWidth*0.1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRectF,mPaint);
        mPaint.setColor(Color.WHITE);
        //外圆
        canvas.drawCircle(centerPoint.x,centerPoint.y,radio,mPaint);
        //内圆
        canvas.drawCircle(centerPoint.x,centerPoint.y,radio-20,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("-------------------->","motionevent:"+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                centerPoint = new Point(x , y);
                invalidate();
                if(mIAutoFocus != null){
                    mIAutoFocus.autoFocus();
                }
                return true;
        }
        return true;
    }

    private IAutoFocus mIAutoFocus;

    /**
     * 聚焦的回调接口
     */
    public interface IAutoFocus{
        void autoFocus();
    }

    public void setAutoFocus(IAutoFocus mAutoFocus){
        this.mIAutoFocus = mAutoFocus;
    }

    /**
     * 获取屏幕参数
     *
     * @param context context
     */
    private void getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }


}
