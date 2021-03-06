package com.example.animationtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.animationtest.view.FireworkView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] mRes = {R.id.imageView_a,R.id.imageView_b,R.id.imageView_c,R.id.imageView_d,R.id.imageView_e};
    private List<ImageView> mImageViews = new ArrayList<>();

    private boolean mFlag = true;

    private EditText mEditText;
    private FireworkView fireworkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mEditText = (EditText) findViewById(R.id.et_firework);
        fireworkView = (FireworkView) findViewById(R.id.fire_work);
        fireworkView.bindEditText(mEditText);

        for(int i=0;i<mRes.length;i++){
            ImageView iv = (ImageView) findViewById(mRes[i]);
            mImageViews.add(iv);
            iv.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_a:
                if(mFlag){
                    startAnim();
                } else {
                    closeAnim();
                }
                break;

            default:
                Toast.makeText(MenuActivity.this,""+view.getId(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImageViews.get(0),"alpha",1F,0.5F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageViews.get(1),"translationY",200F);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageViews.get(2),"translationX",200F);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImageViews.get(3),"translationY",-200F);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImageViews.get(4),"translationX",-200F);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(animator0,animator1,animator2,animator3,animator4);
        set.start();
        mFlag = false;
    }

    private void closeAnim(){
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImageViews.get(0),"alpha",0.5F,1F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageViews.get(1),"translationY",200F,0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageViews.get(2),"translationX",200F,0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImageViews.get(3),"translationY",-200F,0);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImageViews.get(4),"translationX",-200F,0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(animator0,animator1,animator2,animator3,animator4);
        set.start();
        mFlag = true;
    }
}
