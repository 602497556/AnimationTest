package com.example.animationtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.animationtest.R;
import com.example.animationtest.util.SizeUtils;

/**
 * Created by Administrator on 2016/11/7.
 */
public class PolygonsView extends View {
    //默认的View大小
    private int defaultSize;
    //中心点
    private int center;
    //最外层多边形半径
    private int one_radius;
    //能力值画笔
    private Paint rank_Paint;
    //中心线画笔
    private Paint center_Paint;
    //最外层多边形画笔
    private Paint one_Paint;
    //第二层多边形画笔
    private Paint two_Paint;
    //第三层多边形画笔
    private Paint three_Paint;
    //最四层多边形画笔
    private Paint four_Paint;
    //字体画笔
    private Paint str_Paint;
    private String[] str = {"击杀", "生存", "助攻",
                            "物理", "魔法", "防御", "金钱"};
    //字体矩形
    private Rect str_rect;

    private float f1,f2,f3,f4,f5,f6,f7;

    public PolygonsView(Context context) {
        this(context,null);
    }

    public PolygonsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolygonsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultSize = SizeUtils.dp2px(context,300);
        //初始化能力值画笔
        rank_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rank_Paint.setColor(Color.RED);
        rank_Paint.setStrokeWidth(6);
        rank_Paint.setStyle(Paint.Style.STROKE);
        //初始化字体画笔
        str_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        str_Paint.setColor(Color.BLACK);
        str_Paint.setTextSize(SizeUtils.dp2px(context,16));
        str_rect = new Rect();
        str_Paint.getTextBounds(str[0],0,str[0].length(),str_rect);
        //初始化最外层多边形画笔
        one_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        one_Paint.setColor(getResources().getColor(R.color.one));
        one_Paint.setStyle(Paint.Style.FILL);
        //初始化第二层多边形画笔
        two_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        two_Paint.setColor(getResources().getColor(R.color.two));
        two_Paint.setStyle(Paint.Style.FILL);
        //初始化第三层多边形画笔
        three_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        three_Paint.setColor(getResources().getColor(R.color.three));
        three_Paint.setStyle(Paint.Style.FILL);
        //初始化最内层多边形画笔
        four_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        four_Paint.setColor(getResources().getColor(R.color.four));
        four_Paint.setStyle(Paint.Style.FILL);
        //初始化中心线画笔
        center_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        center_Paint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int w, h;
        if( widthMode == MeasureSpec.EXACTLY ){
            w = widthSize;
        } else {
            w = Math.min(widthSize,defaultSize);
        }
        if( heightMode == MeasureSpec.EXACTLY){
            h = heightSize;
        } else {
            h = Math.min(heightSize,defaultSize);
        }
        //中心点
        center = w / 2;
        one_radius = center - getPaddingTop() - 2 * str_rect.height();
        f1 = one_radius-one_radius / 4 * 1;
        f2 = one_radius-one_radius / 4 * 1;
        f3 = one_radius-one_radius / 4 * 1;
        f4 = one_radius-one_radius / 4 * 1;
        f5 = one_radius-one_radius / 4 * 1;
        f6 = one_radius-one_radius / 4 * 1;
        f7 = one_radius-one_radius / 4 * 1;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制最外层的多边形
        drawPolygon(canvas,one_Paint,0);
        //绘制第二层的多边形
        drawPolygon(canvas,two_Paint,one_radius/4);
        //绘制第三层的多边形
        drawPolygon(canvas,three_Paint,one_radius/2);
        //绘制第四层的多边形
        drawPolygon(canvas,four_Paint,one_radius / 2 + one_radius / 4);
        //绘制中心线
        drawCenterLine(canvas);
        //绘制文字
        drawText(canvas);
        //绘制能力值
        drawValue(canvas);
    }

    /**
     * 绘制能力值
     *
     * @param canvas
     */
    private void drawValue(Canvas canvas) {
        Path path = new Path();
        path.moveTo(center,getPaddingTop()+2*str_rect.height()+f1);

        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 7))*(one_radius-f2)),
                (float) (getPaddingTop() + 2 * str_rect.height() + one_radius -
                        Math.abs(Math.cos(Math.toRadians(360 / 7)) * (one_radius-f2))));

        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 7 + 360 / 7 / 2))*(one_radius-f3)),
                (float) (Math.cos(Math.toRadians(360 / 7 + 360 / 7 / 2))*(one_radius-f3))+center);

        path.lineTo((float) (center + Math.sin(Math.toRadians(360 / 7 / 2)) *(one_radius-f4)),
                (float) (Math.cos(Math.toRadians(360 / 7 / 2)) *(one_radius-f4)) + center);

        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 7 / 2)) *(one_radius-f5)),
                (float) (Math.cos(Math.toRadians(360 / 7 / 2)) *(one_radius-f5)) + center);

        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 7 + 360 / 7 / 2))*(one_radius-f6)),
                (float) (Math.cos(Math.toRadians(360 / 7 + 360 / 7 / 2)) *(one_radius-f6)) + center);

        path.lineTo((float) (center - Math.sin(Math.toRadians(360 / 7)) *(one_radius-f7)),
                (float) (getPaddingTop() + 2 * str_rect.height() + one_radius - Math.abs(
                        Math.cos(Math.toRadians(360 / 7)) *(one_radius-f7))));
        path.close();
        canvas.drawPath(path,rank_Paint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.drawText(str[0],center - str_rect.width() / 2,
                (float)(getPaddingTop()+ 1.5 * str_rect.height()), str_Paint);

        canvas.drawText(str[1],(float)(center + Math.sin(Math.toRadians(360/7)) * one_radius+str_rect.height()/2),
                (float)(getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)),str_Paint);

        canvas.drawText(str[2],(float)(center + Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius+
                str_rect.height()/2),(float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*
                one_radius+str_rect.height()/2),str_Paint);

        canvas.drawText(str[3],(float)(center + Math.sin(Math.toRadians(360/7/2))*one_radius-
                str_rect.height()/2 +str_rect.width()/2),
                (float) (center + Math.cos(Math.toRadians(360/7/2))*one_radius+str_rect.height()),str_Paint);

        canvas.drawText(str[4],(float)(center - Math.sin(Math.toRadians(360/7/2))*one_radius+
                str_rect.height() / 2 - str_rect.width() * 1.5),(float) (center + Math.cos(Math.toRadians(360/7/2))
                *one_radius+str_rect.height()),str_Paint);

        canvas.drawText(str[5],(float)(center - Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius-
                str_rect.height()/2-str_rect.width()),(float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))
                *one_radius+str_rect.height()/2),str_Paint);

        canvas.drawText(str[6],(float) (center - Math.sin(Math.toRadians(360/7)) * one_radius-str_rect.height()/2-
                str_rect.width()),(float) (getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)),str_Paint);
    }

    private void drawText2(Canvas canvas){
        canvas.save();
        canvas.translate(center,center);
        float degree = (float) (360/7 + 0.5);
        canvas.save();
        for(int i=0; i<str.length;i++){
            canvas.translate(-str_rect.width()/2,-(one_radius + str_rect.height()/2));
            canvas.rotate(-i*degree);
            canvas.drawText(str[i],0, 0,str_Paint);
            canvas.restore();
            canvas.rotate(degree);
        }
        canvas.restore();
    }

    /**
     * 绘制多边形
     *
     * @param canvas canvas
     * @param paint paint
     * @param d 多边形之间的间距
     */
    private void drawPolygon(Canvas canvas,Paint paint,int d){
        int distance = d;
        Path path = new Path();
        path.moveTo(center, getPaddingTop()+ 2 * str_rect.height()+ distance);
        //Math.sin(x)里的x是弧度而不是角度
        path.lineTo((float)(center + Math.sin(Math.toRadians(360/7))*(one_radius-distance)),
                (float)(getPaddingTop()+ 2 * str_rect.height()+one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7))*(one_radius-distance))));

        path.lineTo((float)(center + Math.sin(Math.toRadians(360/7 +360/7/2))*(one_radius-distance)),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*(one_radius-distance)) );

        path.lineTo((float)(center + Math.sin(Math.toRadians(360/7/2))*(one_radius-distance)),
                (float)(center + Math.cos(Math.toRadians(360/7/2))*(one_radius-distance)));

        path.lineTo((float)(center - Math.sin(Math.toRadians(360/7/2))*(one_radius-distance)),
                (float)(center + Math.cos(Math.toRadians(360/7/2))*(one_radius-distance)));

        path.lineTo((float)(center - Math.sin(Math.toRadians(360/7 +360/7/2))*(one_radius-distance)),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*(one_radius-distance)) );

        path.lineTo((float)(center - Math.sin(Math.toRadians(360/7)) * (one_radius-distance)),
                (float)(getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * (one_radius-distance))) );
        path.close();
        canvas.drawPath(path,paint);
    }

    /**
     * 绘制中心线
     *
     * @param canvas
     */
    private void drawCenterLine(Canvas canvas) {
        //保存当前状态
        canvas.save();
        canvas.rotate(0,center,center);
        float startY = getPaddingTop() + 2 * str_rect.height();
        float endY = center;
        //此处为什么要多加0.5 --> 多边形的角与中心线没有完全重合
        float degree = (float) (360 / 7 + 0.5);
        for(int i=0; i<7; i++){
            canvas.drawLine(center,startY,center,endY,center_Paint);
            canvas.rotate(degree,center,center);
        }
        canvas.restore();
    }

    public void setValue1(float value) {
        f1 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue2(float value) {
        f2 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue3(float value) {
        f3 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue4(float value) {
        f4 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue5(float value) {
        f5 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue6(float value) {
        f6 = one_radius-one_radius / 4 * value;
        invalidate();
    }

    public void setValue7(float value) {
        f7 = one_radius-one_radius / 4 * value;
        invalidate();
    }


}
