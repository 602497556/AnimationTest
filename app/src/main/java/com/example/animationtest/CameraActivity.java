package com.example.animationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.animationtest.view.CameraSurfaceView;
import com.example.animationtest.view.RectOnCamera;

public class CameraActivity extends AppCompatActivity implements
                                View.OnClickListener,RectOnCamera.IAutoFocus {

    private CameraSurfaceView mCameraSurfaceView;
    private RectOnCamera mRectOnCamera;
    private Button mTakePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.camera_surface_view);
        mRectOnCamera = (RectOnCamera) findViewById(R.id.rect_on_camera);
        mRectOnCamera.setAutoFocus(this);
        mTakePic = (Button) findViewById(R.id.btn_take_pic);
        mTakePic.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_take_pic:
                mCameraSurfaceView.takePicture();
                break;
            default:
                break;
        }
    }

    @Override
    public void autoFocus() {
        mCameraSurfaceView.setAutoFocus();
    }
}
