package com.example.animationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.example.animationtest.view.PolygonsView;

public class PolygonsViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar sb1,sb2,sb3,sb4,sb5,sb6,sb7;
    private PolygonsView polygonsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_polygons_view);
        polygonsView = (PolygonsView) findViewById(R.id.polygons_view);
        sb1 = (SeekBar) findViewById(R.id.sb1);
        sb2 = (SeekBar) findViewById(R.id.sb2);
        sb3 = (SeekBar) findViewById(R.id.sb3);
        sb4 = (SeekBar) findViewById(R.id.sb4);
        sb5 = (SeekBar) findViewById(R.id.sb5);
        sb6 = (SeekBar) findViewById(R.id.sb6);
        sb7 = (SeekBar) findViewById(R.id.sb7);
        sb1.setOnSeekBarChangeListener(this);
        sb2.setOnSeekBarChangeListener(this);
        sb3.setOnSeekBarChangeListener(this);
        sb4.setOnSeekBarChangeListener(this);
        sb5.setOnSeekBarChangeListener(this);
        sb6.setOnSeekBarChangeListener(this);
        sb7.setOnSeekBarChangeListener(this);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float value = (float) (seekBar.getProgress()/10.0);
        switch (seekBar.getId()){
            case R.id.sb1:
                polygonsView.setValue1(value);
                break;
            case R.id.sb2:
                polygonsView.setValue2(value);
                break;
            case R.id.sb3:
                polygonsView.setValue3(value);
                break;
            case R.id.sb4:
                polygonsView.setValue4(value);
                break;
            case R.id.sb5:
                polygonsView.setValue5(value);
                break;
            case R.id.sb6:
                polygonsView.setValue6(value);
                break;
            case R.id.sb7:
                polygonsView.setValue7(value);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
