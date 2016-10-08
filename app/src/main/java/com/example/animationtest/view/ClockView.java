package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/8.
 */
public class ClockView extends View {

    private int mWidth,mHeight,radius;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取宽高参数
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        radius = mWidth/2;
        //1,画圆
        drawCircle(canvas);
        //2,画刻度线以及文字
        drawDegree(canvas);
        //3,画两根指针
        drawIndicator(canvas);
    }

    /**
     * 画出外层的圆
     */
    private void drawCircle(Canvas canvas) {
        Paint cirClePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cirClePaint.setStrokeWidth(5);
        cirClePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth/2,mHeight/2,radius,cirClePaint);
    }

    /**
     * 画刻度以及刻度下方的文字
     */
    private void drawDegree(Canvas canvas) {
        Paint paintDegree = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDegree.setStrokeWidth(6);
        for(int i=0;i<24;i++){
            String text = String.valueOf(i);
            if(i==0||i==6||i==12||i==18){
                //粗刻度
                paintDegree.setStrokeWidth(10);
                paintDegree.setTextSize(30);
                canvas.drawLine(mWidth/2,mHeight/2-radius,
                        mWidth/2,mHeight/2-radius+60,paintDegree);
                canvas.drawText(text,mWidth/2-paintDegree.measureText(text)/2,
                        mHeight/2-radius+90,paintDegree);
            } else {
                //细刻度
                paintDegree.setStrokeWidth(6);
                paintDegree.setTextSize(20);
                canvas.drawLine(mWidth/2,mHeight/2-radius,mWidth/2,mHeight/2-radius+30,paintDegree);
                canvas.drawText(text,mWidth/2-paintDegree.measureText(text)/2,
                        mHeight/2-radius+60,paintDegree);
            }
            //通过旋转画布简化坐标运算
            canvas.rotate(15,mWidth/2,mHeight/2);
        }
    }

    /**
     * 画一粗一细的两根指针
     */
    private void drawIndicator(Canvas canvas) {
        //画圆心
        Paint paintPointer = new Paint();
        paintPointer.setStrokeWidth(30);
        canvas.drawPoint(mWidth/2,mHeight/2,paintPointer);
        //画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawLine(0,0,100,100,paintHour);
        canvas.drawLine(0,0,100,200,paintMinute);
        canvas.restore();
    }
}
