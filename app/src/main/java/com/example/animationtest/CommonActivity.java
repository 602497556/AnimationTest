package com.example.animationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.animationtest.view.SimpleDraw;
import com.example.animationtest.view.XfermodeView;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SimpleDraw(this));

    }


}
