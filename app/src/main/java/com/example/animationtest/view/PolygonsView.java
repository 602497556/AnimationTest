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
    //各多边形之间的间距
    private int distance;
    //等级画笔
    private Paint rank_Paint;
    //中心线画笔
    private Paint center_Paint;
    //最外层多边形画笔
    private Paint one_Paint;
    //第二层多边形画笔
    private Paint two_Paint;
    //最三层多边形画笔
    private Paint three_Paint;
    //最四层多边形画笔
    private Paint four_Paint;
    //字体画笔
    private Paint str_Paint;
    private String[] str = {"击杀", "生存", "助攻",
                            "物理", "魔法", "防御", "金钱"};
    //字体矩形
    private Rect str_rect;

    public PolygonsView(Context context) {
        this(context,null);
    }

    public PolygonsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolygonsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultSize = SizeUtils.dp2px(context,300);
        //初始化等级画笔
        rank_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rank_Paint.setColor(Color.RED);
        rank_Paint.setStrokeWidth(5);
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
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制最外层的多边形
        drawFirstPolygon(canvas);
        //绘制第二层的多边形
        drawSecondPolygon(canvas);
        //绘制第三层的多边形
        drawThirdPolygon(canvas);
        //绘制第四层的多边形
        drawFourthPolygon(canvas);
        //绘制中心线
        drawCenterLine(canvas);
        //绘制文字
        drawText(canvas);


    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.drawText(str[0],center - str_rect.width()/2,(float)(getPaddingTop()+ 1.5*str_rect.height()),str_Paint);
        canvas.drawText(str[1],(float)(center + Math.sin(Math.toRadians(360/7)) * one_radius+str_rect.height()/2),
                (float)(getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)),str_Paint);
        canvas.drawText(str[2],(float)(center + Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius+str_rect.height()/2),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*one_radius+str_rect.height()/2),str_Paint);
        canvas.drawText(str[3],(float)(center + Math.sin(Math.toRadians(360/7/2))*one_radius-
                str_rect.height()/2 +str_rect.width()/2),
                (float) (center + Math.cos(Math.toRadians(360/7/2))*one_radius+str_rect.height()),str_Paint);
        canvas.drawText(str[4],(float)(center - Math.sin(Math.toRadians(360/7/2))*one_radius+
                str_rect.height() / 2 - str_rect.width() * 1.5),(float) (center + Math.cos(Math.toRadians(360/7/2))*one_radius+
                str_rect.height()),str_Paint);
        canvas.drawText(str[5],(float)(center - Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius-
                str_rect.height()/2-str_rect.width()),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*one_radius+str_rect.height()/2),str_Paint);
        canvas.drawText(str[6],(float) (center - Math.sin(Math.toRadians(360/7)) * one_radius-str_rect.height()/2-
                str_rect.width()),(float) (getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)),str_Paint);
    }

    /**
     * 绘制第四层的多边形
     *
     * @param canvas
     */
    private void drawFourthPolygon(Canvas canvas) {
        distance = one_radius / 2 + one_radius / 4;
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
        canvas.drawPath(path,four_Paint);
    }

    /**
     * 绘制第三层的多边形
     *
     * @param canvas
     */
    private void drawThirdPolygon(Canvas canvas) {
        distance = one_radius / 2;
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
        canvas.drawPath(path,three_Paint);
    }

    /**
     * 绘制第二层的多边形
     *
     * @param canvas
     */
    private void drawSecondPolygon(Canvas canvas) {
        distance = one_radius / 4;
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
        canvas.drawPath(path,two_Paint);

    }

    /**
     * 绘制最外层的多边形
     *
     * @param canvas
     */
    private void drawFirstPolygon(Canvas canvas) {
        Path path = new Path();
        path.moveTo(center, getPaddingTop()+ 2 * str_rect.height());
        //Math.sin(x)里的x是弧度而不是角度
        path.lineTo((float) (center + Math.sin(Math.toRadians(360/7)) * one_radius),
                (float) (getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)) );
        path.lineTo((float)(center + Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*one_radius) );
        path.lineTo((float)(center + Math.sin(Math.toRadians(360/7/2))*one_radius),
                (float) (center + Math.cos(Math.toRadians(360/7/2))*one_radius));
        path.lineTo((float)(center - Math.sin(Math.toRadians(360/7/2))*one_radius),
                (float) (center + Math.cos(Math.toRadians(360/7/2))*one_radius));
        path.lineTo((float)(center - Math.sin(Math.toRadians(360/7 +360/7/2))*one_radius),
                (float)(center + Math.cos(Math.toRadians(360/7 +360/7/2))*one_radius) );
        path.lineTo((float) (center - Math.sin(Math.toRadians(360/7)) * one_radius),
                (float) (getPaddingTop()+ 2 * str_rect.height()+ one_radius -
                        Math.abs(Math.cos(Math.toRadians(360/7)) * one_radius)) );
        path.close();
        canvas.drawPath(path,one_Paint);
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
        //此处为什么要多加0.5？
        float degree = (float) (360 / 7 +0.5);
        for(int i=0; i<7; i++){
            canvas.drawLine(center,startY,center,endY,center_Paint);
            canvas.rotate(degree,center,center);
        }
        canvas.restore();
    }


}
