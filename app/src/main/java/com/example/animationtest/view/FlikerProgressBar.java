package com.example.animationtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.animationtest.R;

/**
 * Created by ZengJie on 2016/10/4.
 */
public class FlikerProgressBar extends View implements Runnable {

    //进度条默认高度
    private int DEFAULT_HEIGHT_DP = 35;
    //最大进度
    private float MAX_PROGRESS = 100f;
    //文字画笔
    private Paint textPaint;
    //
    private Paint bgPaint;
    //下载进度
    private String progressText;
    //用于测量文字的边界
    private Rect textBounds;

    //左右来回移动的滑块
    private Bitmap flikerBitmap;
    //滑块的左边界
    private float flikerLeft;

    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    private int textSize;
    //下载中的颜色
    private int loadingColor;
    //暂停时的颜色
    private int stopColor;

    private int progressColor;
    //下载进度
    private float progress;

    //下载结束标志位
    private boolean isFinish = false;
    //暂停标志位
    private boolean isStop = false;

    private Thread thread;

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(
            PorterDuff.Mode.SRC_ATOP );


    public FlikerProgressBar(Context context) {
        this(context,null,0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlikerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    /**
     * 初始化自定义的属性
     */
    private void initAttrs(AttributeSet attrs) {
        if( attrs != null ){
            TypedArray ta = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.FlikerProgressBar );
            textSize = (int) ta.getDimension(
                    R.styleable.FlikerProgressBar_textSize,
                    dp2px(12));
            loadingColor = ta.getColor(
                    R.styleable.FlikerProgressBar_loadingColor,
                    Color.parseColor("#40c4ff"));
            stopColor = ta.getColor(
                    R.styleable.FlikerProgressBar_stopColor,
                    Color.parseColor("#ff9800"));
            ta.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int height;

        if( heightSpecMode == MeasureSpec.EXACTLY ){
            height = heightSpecSize;
        } else {
            height = (int) dp2px(DEFAULT_HEIGHT_DP);
        }
//        switch (heightSpecMode){
//            case MeasureSpec.AT_MOST:
//                height = (int) dp2px(DEFAULT_HEIGHT_DP);
//                break;
//            case MeasureSpec.EXACTLY:
//            case MeasureSpec.UNSPECIFIED:
//                height = heightSpecSize;
//                break;
//        }
        setMeasuredDimension(widthSpecSize,height);
        //onMeasure执行完之后，才能获得测量宽高
        init();
    }

    private void init(){
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);

        textBounds = new Rect();

        progressColor = loadingColor;

        flikerBitmap = BitmapFactory.decodeResource(
                getResources(),R.drawable.flicker);
        //指定滑块初始时的左边界
        flikerLeft = -flikerBitmap.getWidth();

        pgBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //装载画布，后续的所有操作都将作用在pgBitmap上
        pgCanvas = new Canvas(pgBitmap);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int width = flikerBitmap.getWidth();
        while( !isStop ){
            flikerLeft += dp2px(5);
            float progressWidth = (progress / MAX_PROGRESS)* getMeasuredWidth();
            if(flikerLeft >= progressWidth){
                flikerLeft = -width;
            }
            postInvalidate();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1,边框
        drawBorder(canvas);
        //2,进度
        drawProgress();

        canvas.drawBitmap(pgBitmap, 0, 0, null);

        //3,进度Text
        drawProgressText(canvas);
        //4,变色处理
        drawColorProgressText(canvas);
    }

    /**
     * 绘制边框
     *
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(progressColor);
        bgPaint.setStrokeWidth(dp2px(1));
        canvas.drawRect(0,0,getWidth(),getHeight(),bgPaint);
    }

    /**
     * 绘制进度
     */
    private void drawProgress() {
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(0);
        bgPaint.setColor(progressColor);
        float right = (progress / MAX_PROGRESS)*getMeasuredWidth();
//        pgBitmap = Bitmap.createBitmap((int) Math.max(right,1),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        pgCanvas = new Canvas(pgBitmap);
//        pgCanvas.drawColor(progressColor);
        pgCanvas.save(Canvas.CLIP_SAVE_FLAG);
        pgCanvas.clipRect(0,0,right,getMeasuredHeight());
        pgCanvas.drawColor(progressColor);
        pgCanvas.restore();

        if(!isStop){
            bgPaint.setXfermode(xfermode);
            pgCanvas.drawBitmap(flikerBitmap,flikerLeft,0,bgPaint);
            bgPaint.setXfermode(null);
        }
    }

    /**
     * 绘制中心文字
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        textPaint.setColor(progressColor);
        progressText = getProgressText();
        textPaint.getTextBounds(progressText,0,progressText.length(),textBounds);
        int tWidth = textBounds.width();
        int tHeight = textBounds.height();
        float xCoordinate = (getMeasuredWidth() - tWidth)/2;
        float yCoordinate = (getMeasuredHeight() + tHeight)/2;
        canvas.drawText(progressText,xCoordinate,yCoordinate,textPaint);
    }

    /**
     * 文字变色处理
     *
     * @param canvas
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textBounds.width();
        int tHeight = textBounds.height();
        float xCoordinate = (getMeasuredWidth() - tWidth)/2;
        float yCoordinate = (getMeasuredHeight() + tHeight)/2;
        float progressWidth = (progress/MAX_PROGRESS)*getMeasuredWidth();
        if(progressWidth > xCoordinate){
            canvas.save(Canvas.CLIP_SAVE_FLAG);
            float right = Math.min(progressWidth,xCoordinate + tWidth);
            canvas.clipRect(xCoordinate,0,right,getMeasuredHeight());
            canvas.drawText(progressText,xCoordinate,yCoordinate,textPaint);
            canvas.restore();
        }
    }

    //-------------- Setter start ------------//

    public void setProgress(float progress){
        if( !isStop ){
            this.progress = progress;
            invalidate();
        }
    }

    public void setStop(boolean stop){
        isStop = stop;
        if(isStop){
            progressColor = stopColor;
        } else {
            progressColor = loadingColor;
            thread = new Thread(this);
            thread.start();
        }
        invalidate();
    }

    public void finishLoad(){
        isFinish = true;
        setStop(true);
    }

    public boolean isFinish(){
        return isFinish;
    }

    public void toggle(){
        if(!isFinish){
            if(isStop){
                setStop(false);
            } else {
                setStop(true);
            }
        }
    }

    private String getProgressText() {
        String text;
        if( !isFinish ){
            if(!isStop){
                text = "下载中："+progress+"%";
            } else {
                text = "继续";
            }
        } else {
            text = "下载完成";
        }
        return text;
    }

    //-------------- Setter end -------------//


    /**
     * dp转px
     */
    private float dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp*density;
    }

}
