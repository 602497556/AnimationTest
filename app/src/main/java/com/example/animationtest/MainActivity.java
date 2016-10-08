package com.example.animationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.btn_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.btn_2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.btn_3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.btn_4);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                Intent intent1 = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn_2:
                Intent intent2 = new Intent(MainActivity.this,TimerActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_3:
                Intent intent3 = new Intent(MainActivity.this,DropActivity.class);
                startActivity(intent3);
                break;

            case R.id.btn_4:
                Intent intent4 = new Intent(MainActivity.this,FlikerProgressBarActivity.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
    }

    public void btnPanel(View view){
        startActivity(new Intent(MainActivity.this,PanelActivity.class));
    }

}
