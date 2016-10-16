package com.example.animationtest.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by ZengJie on 2016/10/16.
 */
public class ThreeDRotateAnimation extends Animation {

    private int centerX,centerY;
    private Camera camera = new Camera();

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //中心点坐标
        centerX = width/2;
        centerY = height/2;
        setDuration(500);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        camera.save();
        //绕Y轴旋转
        camera.rotateY(360 * interpolatedTime);
        camera.getMatrix(matrix);
        //设置翻转中心点
        //animation里面的preTranslate和postTranslate方法，
        //preTranslate是指在rotateY前平移，postTranslate是指在rotateY后平移，
        //注意他们参数是平移的距离，而不是平移目的地的坐标！
        //由于旋转是以(0,0)为中心的,所以为了把硬币的中心与(0,0)对齐，
        //就要preTranslate(-centerX, -centerY),rotateY完成后,
        //调用postTranslate(centerX, centerY),再把图片移回来,这样看到的动画效果就是硬币从中心不停的旋转了。
        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
        camera.restore();
    }

}
