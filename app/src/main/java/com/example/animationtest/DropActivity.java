package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

public class DropActivity extends AppCompatActivity {

    private LinearLayout mHiddenView;
    private float mDensity;
    private int mHiddenViewMeasuredHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        mHiddenView = (LinearLayout) findViewById(R.id.hidden_view);
        //获取像素密度
        mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        mHiddenViewMeasuredHeight = (int)(mDensity*40+0.5);
    }


    public void llClick(View view){
        if(mHiddenView.getVisibility() == View.GONE){
            //打开动画
            animateOpen(mHiddenView);
        } else {
            //关闭动画
            animateClose(mHiddenView);
        }
    }

    private void animateOpen(final View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view,0,mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animateClose(final View view){
        int originalHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view,originalHeight,0);
        //动画结束后，设置View.Gone,否则Close之后就不能Open了
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start, int end){
        ValueAnimator animator = ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = value;
                view.setLayoutParams(params);
            }
        });
        return animator;
    }

    public void btnTest(View view){
        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            Log.d("------------->","内存卡存在");
        }else{
            Log.d("------------->","内存卡不存在");
            String path = Environment.getExternalStorageDirectory()+"/zengjie";
            File file = new File(path);
            if(!file.exists()){
                file.mkdir();
            }
            Log.d("------------->",Environment.getExternalStorageDirectory().toString());
            Log.d("------------->",Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        Log.d("------------->",getFilesDir().toString());
    }


}
