package com.example.animationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.animationtest.view.RoundImageView;

public class CommonActivity extends AppCompatActivity {

    private RoundImageView mQiqiu;
    private RoundImageView mMeinv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mMeinv = (RoundImageView) findViewById(R.id.riv_aa);
        mQiqiu = (RoundImageView) findViewById(R.id.riv_qiqiu);

        mMeinv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeinv.setType( 0 );
            }
        });

        mQiqiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQiqiu.setBorderRadius(70);
            }
        });

    }


}
