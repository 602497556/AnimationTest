package com.example.animationtest;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.animationtest.view.SimpleDraw;
import com.example.animationtest.view.XfermodeView;

public class CommonActivity extends AppCompatActivity {
    
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_svg);
        iv = (ImageView) findViewById(R.id.iv_svg);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }

    private void animate() {
        Drawable drawable = iv.getDrawable();
        if( drawable instanceof Animatable ){
            ((Animatable) drawable).start();
        }
    }


}
