package com.example.animationtest.firework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

import com.example.animationtest.bean.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/18.
 */
public class Firework {

    private final String TAG = this.getClass().getSimpleName();

    private static final int DEFAULT_ELEMENT_COUNT = 12;
    private static final float DEFAULT_ELEMENT_SIZE = 8;
    private static final int DEFAULT_DURATION = 400;
    private static final float DEFAULT_LAUNCH_SPEED = 18;
    private static final float DEFAULT_WIND_SPEED = 6;
    private static final float DEFAULT_GRAVITY = 6;

    private Paint mPaint;
    private int count;
    private int duration;
    private int[] colors;
    private int color;
    private float launchSpeed;
    private float windSpeed;
    private float gravity;
    private float elementSize;
    private int windDirection; //1 or -1
    private Location location;

    private ValueAnimator animator;
    private float animatorValue;

    private List<Element> elements = new ArrayList<>();

    private AnimationEndListener listener;

    public Firework(Location location, int windDirection){
        this.location = location;
        this.windDirection = windDirection;

        colors = baseColors;
        duration = DEFAULT_DURATION;
        gravity = DEFAULT_GRAVITY;
        windSpeed = DEFAULT_WIND_SPEED;
        elementSize = DEFAULT_ELEMENT_SIZE;
        launchSpeed = DEFAULT_LAUNCH_SPEED;
        count = DEFAULT_ELEMENT_COUNT;
        init();
    }

    private void init() {
        Random random = new Random();
        color = colors[random.nextInt(colors.length)];
        //给每个火花设定一个随机的方向
        for(int j=0;j<count;j++){
            elements.add(new Element(color,Math.toRadians(random.nextInt(180)),
                    random.nextFloat()*launchSpeed));
        }
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    public void fire(){
        animator = ValueAnimator.ofFloat(1,0);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                //计算每个火花的位置
                for(Element element : elements){
                    element.x = (float) (element.x + Math.cos(element.direction)
                            *element.speed*animatorValue + windSpeed*windDirection);
                    element.y = (float) (element.y - Math.sin(element.direction)*
                            element.speed*animatorValue+ gravity*(1-animatorValue));

                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd();
            }
        });
        animator.start();
    }

    public void draw(Canvas canvas){
        mPaint.setAlpha((int) (225*animatorValue));
        for(Element element : elements){
            canvas.drawCircle(location.x + element.x, location.y +element.y, elementSize,mPaint);
        }
    }

    public void setColors(int[] colors){
        this.colors = colors;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void addAnimationEndListener(AnimationEndListener listener){
        this.listener = listener;
    }

    /**
     * 烟花的所有颜色
     */
    private static final int[] baseColors = {0xFFFF43,0x00E500,0x44CEF6,0xFF0040,
                                    0xFF00FFB7,0x008CFF,0xFF5286,0x562CFF,
                                    0x2C9DFF,0x00FFFF,0x00FF77,0x11FF00,
                                    0xFFB536,0xFF4618,0xFF334B,0x9CFA18};

    /**
     * 动画结束之后回调的接口
     */
    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    public static class Location{
        public float x;
        public float y;

        public Location(float x,float y){
            this.x = x;
            this.y = y;
        }
    }

}
