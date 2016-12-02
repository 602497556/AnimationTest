package com.example.animationtest.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.animationtest.R;

import static android.graphics.Paint.Style.STROKE;

/**
 * Created by ZENG on 2016/12/1.
 *
 * 仿支付宝芝麻信用
 *
 * blog: http://blog.csdn.net/ccy0122/article/details/53241648
 */
public class RoundIndicatorView extends View {

    //定义半径为宽度的1/4
    private int radius;
    //内圆弧的宽度
    private int arcInWidth;
    //内圆弧的宽度
    private int arcOutWidth;
    //圆弧画笔
    private Paint arcPaint;
    //细刻度画笔
    private Paint degreePaint1;
    //粗刻度画笔
    private Paint degreePaint2;
    //绘制数字和文本的画笔
    private Paint textPaint;

    private int maxNum;

    //圆盘起始角度( x正半轴开始，顺时针顺转 )
    private int startAngle;
    //圆盘扫过的角度
    private int sweepAngle;

    private int mWidth, mHeight;

    private int currentNum = 0;

    private String[] text = {"较差","中等","良好","优秀","极好"};

    private int[] indicatorColor = {0xffffffff,0x00ffffff,0x99ffffff,0xffffffff};


    public RoundIndicatorView(Context context) {
        this(context, null);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    /**
     * 初始化自定义的属性和画笔
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if( attrs != null ){
            TypedArray ta = context.obtainStyledAttributes(
                    attrs, R.styleable.RoundIndicatorView);
            maxNum = ta.getInt(R.styleable.RoundIndicatorView_maxNum, 500);
            startAngle = ta.getInt(R.styleable.RoundIndicatorView_startAngle, 160);
            sweepAngle = ta.getInt(R.styleable.RoundIndicatorView_sweepAngle, 220);
            ta.recycle();
        }

        arcInWidth = dp2px(8);
        arcOutWidth = dp2px(3);

        //初始化画圆弧的画笔
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(STROKE);
        arcPaint.setColor(0xffffffff);
        arcPaint.setAlpha(0x40); //透明度范围为00~ff

        //细刻度的画笔
        degreePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        degreePaint1.setColor(0xffffffff);
        degreePaint1.setStrokeWidth(dp2px(1));
        degreePaint1.setAlpha(0x50);

        //粗刻度的画笔
        degreePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        degreePaint2.setColor(0xffffffff);
        degreePaint2.setStrokeWidth(dp2px(2));
        degreePaint2.setAlpha(0x70);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xffffffff);
        textPaint.setTextSize(sp2px(8));

        //设置背景
        setBackgroundColor(0xFFFF6347);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if( widthMode == MeasureSpec.EXACTLY ){
            mWidth = widthSize;
        } else {
            mWidth = dp2px(300);
        }
        if( heightMode == MeasureSpec.EXACTLY ){
            mHeight = heightSize;
        } else {
            mHeight = dp2px(400);
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = getMeasuredWidth() / 4; //不要在构造方法里初始化，那时还没测量宽高
        canvas.save();
        canvas.translate( mWidth/2 , mWidth/2 );
        //画内外圆弧
        drawArc(canvas);
        //画刻度
        drawDegree(canvas);
        //画外圆弧上的进度
        drawIndicator(canvas);
        //绘制中心文字
        drawCenterText(canvas);
        canvas.restore();
    }

    /**
     * 绘制中心部分的文字
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(radius/2);
        paint.setColor(0xffffffff);
        //paint.setAlpha(0x90);
        canvas.drawText(currentNum+"",
                -paint.measureText(currentNum+"")/2, 0,paint);
        paint.setTextSize(radius/4);
        String str = "信用";
        if( currentNum <= maxNum*1/5 ){
            str += text[0];
        } else if( currentNum > maxNum*1/5 && currentNum <= maxNum*2/5 ){
            str += text[1];
        } else if( currentNum > maxNum*2/5 && currentNum <= maxNum*3/5 ){
            str += text[2];
        } else if( currentNum > maxNum*3/5 && currentNum <= maxNum*4/5){
            str += text[3];
        } else if( currentNum > maxNum*4/5){
            str += text[4];
        }
        Rect bounds = new Rect();
        paint.getTextBounds(str,0,str.length(),bounds);
        canvas.drawText(str, -bounds.width()/2, bounds.height()+20,paint);
        canvas.restore();
    }

    /**
     * 绘制外圆弧上的进度
     *
     * @param canvas
     */
    private void drawIndicator(Canvas canvas) {
        canvas.save();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(STROKE);
        int angle;
        if( currentNum <= maxNum ){
            angle = (int) ((float) currentNum/(float)maxNum * sweepAngle);
        } else {
            angle = sweepAngle;
        }
        paint.setStrokeWidth(arcOutWidth);
        Shader shader = new SweepGradient(0, 0, indicatorColor, null);
        paint.setShader(shader);
        int w = dp2px(10);
        RectF rectf = new RectF(-radius-w,-radius-w,radius+w,radius+w);
        canvas.drawArc(rectf, startAngle, angle, false, paint);
        float x = (float) ((radius+w)*Math.cos(Math.toRadians(startAngle+angle)));
        float y = (float) ((radius+w)*Math.sin(Math.toRadians(startAngle+angle)));
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(0xffffffff);
        //需关闭硬件加速
        paint2.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(x, y, dp2px(3), paint2);
        canvas.restore();
    }

    /**
     * 画刻度，包括粗细刻度，数字，文字
     *
     * @param canvas
     */
    private void drawDegree(Canvas canvas) {
        canvas.save();
        float angle = (float) sweepAngle / 30; //每次旋转的角度
        //将起始刻度点旋转到正上方
        canvas.rotate( -(270-startAngle) ); //负数为顺时针旋转，正数为逆时针旋转
        for( int i=0; i<=30; i++ ){
            if( i % 6 == 0 ){
                //画粗刻度
                canvas.drawLine(0, -radius - arcInWidth/2,
                        0, -radius + arcInWidth/2, degreePaint2);
                //绘制数字
                textPaint.setStrokeWidth(dp2px(2));
                textPaint.setAlpha(0x70);
                drawText(canvas, i* maxNum/30+"", textPaint);
            } else {
                //画细刻度
                canvas.drawLine(0, -radius - arcInWidth/2,
                        0, -radius + arcInWidth/2, degreePaint1);
            }
            if( i==3 || i==9 || i==15 || i==21 || i==27 ){
                //画刻度区间的文字
                textPaint.setStrokeWidth(dp2px(2));
                textPaint.setAlpha(0x50);
                drawText(canvas, text[(i-3)/6],textPaint);
            }
            canvas.rotate(angle); //逆时针旋转
        }
        canvas.restore();
    }

    /**
     * 绘制text
     *
     * @param canvas 画布
     * @param text 要绘制的文本
     * @param paint 画笔
     */
    private void drawText(Canvas canvas, String text, Paint paint) {
        //相比getTextBounds来说，这个方法获得的是float类型，更精确
        //Rect rect = new Rect();
        //paint.getTextBounds(text, 0, text.length(), rect);
        float width = paint.measureText(text);
        canvas.drawText(text, -width/2, -radius+dp2px(15), paint);
    }

    /**
     * 画内外圆弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        //内圆
        arcPaint.setStrokeWidth(arcInWidth);
        RectF rect = new RectF(-radius,-radius,radius,radius);
        canvas.drawArc(rect,startAngle,sweepAngle,false,arcPaint);
        //外圆
        arcPaint.setStrokeWidth(arcOutWidth);
        int w = dp2px(10);
        RectF rect2 = new RectF(-radius-w,-radius-w,radius+w,radius+w);
        canvas.drawArc(rect2,startAngle,sweepAngle,false,arcPaint);
        canvas.restore();
    }

    //对currentNum进行属性动画，就要提供对应的get,set方法
    public int getCurrentNum(){
        return currentNum;
    }

    public void setCurrentNum(int cur){
        this.currentNum = cur;
        invalidate();
    }

    public void setCurrentNumAnim(int num){
        if( num > maxNum ){
            num = maxNum;
        }
        //根据进度差计算动画时间
        float duration = (float) Math.abs(num - currentNum)/maxNum * 1500 +500;
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "currentNum", num);
        anim.setDuration((long) Math.min(duration,2000));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int color = calculateColor(value);
                setBackgroundColor(color);
            }
        });
        anim.start();
    }

    /**
     * 用ArgbEvaluator估值器实现颜色渐变，调用它的evaluate方法，传入一个0~1的比例，
     * 传入开始和结束的颜色，就可以根据当前比例得到介于这两个颜色之间的颜色值
     *
     * @param value
     * @return color
     */
    private int calculateColor(int value) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        float fraction;
        int color;
        if( value < maxNum/2 ){
            fraction = (float) value / (maxNum/2);
            //由红到橙
            color = (int) evaluator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00);
        } else {
            fraction = (float) (value-maxNum/2)/(maxNum/2);
            //由橙到蓝
            color = (int) evaluator.evaluate(fraction, 0xFFFF8C00,0xFF00CED1);
        }
        return color;
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return pxValue
     */
    private int dp2px(int dpValue){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return pxValue
     */
    private int sp2px(int spValue){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spValue,
                getResources().getDisplayMetrics());
    }


}
