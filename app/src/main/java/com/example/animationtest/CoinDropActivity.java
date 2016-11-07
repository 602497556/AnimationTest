package com.example.animationtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.animationtest.view.ThreeDRotateAnimation;

public class CoinDropActivity extends AppCompatActivity {

    private Button button;
    private ImageView ivCoin,ivWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_drop);
        initView();
    }

    private void initView() {
        ivCoin = (ImageView) findViewById(R.id.iv_Coin);
        ivWallet = (ImageView) findViewById(R.id.iv_wallet);
        button = (Button) findViewById(R.id.btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation();
            }
        });
    }

    private void setAnimation() {
        //硬币的动画
        startCoin();
        //硬币动画开始后，设置此方法判断硬币快到Wallet
        setWallet();
    }

    /**
     * 硬币平移+旋转动画
     */
    private void startCoin() {
        Animation translateAnim = AnimationUtils.loadAnimation(this,R.anim.coin_drop_in);
        ThreeDRotateAnimation rotateAnim = new ThreeDRotateAnimation();
        //此处是AnimationSet ,注意与下面用的是AnimatorSet
        AnimationSet animSet = new AnimationSet(true);
        animSet.setDuration(800);
        animSet.addAnimation(translateAnim);
        animSet.addAnimation(rotateAnim);
        ivCoin.startAnimation(animSet);
    }

    /**
     * 大概硬币掉落到钱包上边缘时，开始Wallet的动画
     */
    private void setWallet() {
        ValueAnimator animator = ValueAnimator.ofInt(0,1);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if(fraction >= 0.75){
                    animation.cancel();
                    startWallet();
                }
            }
        });
        animator.start();
    }

    /**
     * 钱包的动画包括 X 和 Y两个方向上的缩放
     */
    private void startWallet() {
        ObjectAnimator objectAnim1 = ObjectAnimator.ofFloat(ivWallet,"scaleX",1,1.1f,0.9f,1);
        objectAnim1.setDuration(600);
        ObjectAnimator objectAnim2 = ObjectAnimator.ofFloat(ivWallet,"scaleY",1,0.75f,1.25f,1);
        objectAnim2.setDuration(600);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(objectAnim1,objectAnim2);
        animatorSet.start();
    }

}
