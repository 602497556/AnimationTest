package com.example.animationtest.transformation;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2016/10/20.
 */
public class MyTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float MAX_ROTATE = 30f;

    @Override
    public void transformPage(View page, float position) {
        float centerX = page.getWidth()/2;
        float centerY = page.getWidth()/2;
        float scaleFactor = Math.max(MIN_SCALE, 1-Math.abs(position));
        float rotate = 20 * Math.abs(position);
        /**
         * [-Infinity,-1)已经看不到了;(1,+Infinity] 已经看不到了
         *[-1,1]重点看[-1,1]这个区间,其他两个的View都已经看不到了~~
         *假设现在ViewPager在A页现在滑出B页,则:A页的position变化就是(0,-1]
         *B页的position变化就是[1,0]
         */
        if(position < -1){

        } else if(position < 0){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(rotate);
        } else if(position >=0 && position <1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        } else if(position >=1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }

    }


}
