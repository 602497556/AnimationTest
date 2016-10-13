package com.example.animationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.animationtest.adapter.CityAdapter;
import com.example.animationtest.bean.CityBean;
import com.example.animationtest.decoration.DividerItemDecoration;
import com.example.animationtest.decoration.TitleItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    private RecyclerView mRV;
    private List<CityBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initData();
        mRV = (RecyclerView) findViewById(R.id.recycler_view);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(new CityAdapter(this,mDatas));
        mRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRV.addItemDecoration(new TitleItemDecoration(this,mDatas));
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new CityBean("A", "安徽"));
        mDatas.add(new CityBean("B", "北京"));
        mDatas.add(new CityBean("F", "福建"));
        mDatas.add(new CityBean("G", "广东"));
        mDatas.add(new CityBean("G", "甘肃"));
        mDatas.add(new CityBean("G", "贵州"));
        mDatas.add(new CityBean("G", "广西"));
        mDatas.add(new CityBean("H", "河南"));
        mDatas.add(new CityBean("H", "湖北"));
        mDatas.add(new CityBean("H", "湖南"));
        mDatas.add(new CityBean("H", "河北"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));
    }


}
