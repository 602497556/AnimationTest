package com.example.animationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.animationtest.view.RoundIndicatorView;

public class CommonActivity extends AppCompatActivity {
    
    private ImageView iv;

    private EditText etValue;
    private Button start;
    private RoundIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_indicator_view);

        indicatorView = (RoundIndicatorView) findViewById(R.id.r_i_v);
        etValue = (EditText) findViewById(R.id.edit);
        start = (Button) findViewById(R.id.btn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.valueOf(etValue.getText().toString());
                indicatorView.setCurrentNumAnim(a);
                etValue.setText("");
            }
        });

//        iv = (ImageView) findViewById(R.id.iv_svg);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animate();
//            }
//        });

    }


//    private void animate() {
//        Drawable drawable = iv.getDrawable();
//        if( drawable instanceof Animatable ){
//            ((Animatable) drawable).start();
//        }
//    }


}
