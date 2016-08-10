package com.example.animationtest;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ValueAnimator animator = ValueAnimator.ofInt(0,100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ((TextView)view).setText("$ "+ valueAnimator.getAnimatedValue());
                    }
                });
                animator.setDuration(3000);
                animator.start();
            }
        });
    }
}
