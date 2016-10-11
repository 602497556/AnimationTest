package com.example.animationtest;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.animationtest.view.MyClockView;

import static com.example.animationtest.R.id.tv_time;

public class PanelActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private MyClockView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        toolbar = (Toolbar) findViewById(R.id.panel_tb);
        setSupportActionBar(toolbar);

        clockView = (MyClockView) findViewById(R.id.clockview);
        final TextView tvTime = (TextView) findViewById(tv_time);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

        clockView.setOnTimeChangeListener(new MyClockView.OnTimeChangeListener() {
            @Override
            public void onTimeChange(View v, int hour, int minute, int second) {
                tvTime.setText(String.format("%s-%s-%s",hour,minute,second));
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                clockView.setTime(11,59,55);
                break;
            case R.id.btn2:
                clockView.setTime(20,30,0);
                break;
            case R.id.btn3:
                clockView.setTime(23,59,55);
                break;
        }

    }


}
