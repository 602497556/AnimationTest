package com.example.animationtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.animationtest.view.FlikerProgressBar;

public class FlikerProgressBarActivity extends AppCompatActivity {

    private FlikerProgressBar flikerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fliker_progress_bar);
        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);
        flikerProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flikerProgressBar.isFinish()){
                    flikerProgressBar.toggle();
                }
            }
        });
        downLoad();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            if( msg.arg1 == 100 ){
                flikerProgressBar.finishLoad();
            }
        }
    };

    private void downLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    try {
                        Thread.sleep(200);
                        Message message = handler.obtainMessage();
                        message.arg1 = i+1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
