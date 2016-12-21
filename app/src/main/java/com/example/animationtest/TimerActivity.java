package com.example.animationtest;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animationtest.adapter.GridViewAdapter;
import com.example.animationtest.adapter.ViewPagerAdapter;
import com.example.animationtest.bean.Model;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private TextView tvTimer;

    private ViewPager mViewPager;
    private List<View> viewLists;
    private List<Model> mDatas;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;

    /**
     * 总的页数
     */
    private int pageCount;

    /**
     * 当前页
     */
    private int curPage = 0;

    private LayoutInflater inflater;

    private LinearLayout llDot;

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖",
                                "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
                                "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务",
                                "美发", "丽人", "景点", "足疗按摩", "运动健身",
                                "健身", "超市", "买菜", "今日新单", "小吃快餐",
                                "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影",
                                "学习培训", "家装", "结婚", "全部分配"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initView();
        initData();
        pageCount = (int) Math.ceil( titles.length * 1.0f / pageSize ); //向上取整
        viewLists = new ArrayList<>();
        inflater = LayoutInflater.from(this);
        for(int i=0; i < pageCount; i++) {
            GridView gridView = (GridView) inflater.inflate(
                    R.layout.grid_view, mViewPager, false);
            gridView.setAdapter(new GridViewAdapter(this, mDatas, i, pageSize));
            viewLists.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    int pos = curPage * pageSize + position;
                    Toast.makeText(TimerActivity.this,mDatas.get(pos).getName(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(viewLists));
        //设置圆点
        setOvalLayout();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i=0; i<titles.length; i++){
            // 动态获取资源ID，第一个参数是资源名，第二个参数是资源类型
            // 例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap",
                    getPackageName());
            mDatas.add(new Model(titles[i],imageId));
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        llDot = (LinearLayout) findViewById(R.id.ll_dot);

        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ValueAnimator animator = ValueAnimator.ofInt(0,100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ((TextView)view).setText("$ "+ valueAnimator.getAnimatedValue());
                    }
                });
                animator.setDuration(3000);
                animator.start();
            }
        });
    }

    /**
     * 设置圆点
     */
    private void setOvalLayout() {
        for( int i=0; i < pageCount; i++ ){
            llDot.addView(inflater.inflate(R.layout.dot, null));
        }
        //默认显示第一页
        llDot.getChildAt(0).findViewById(R.id.view_dot).
                setBackgroundResource(R.drawable.dot_selected);
        //给ViewPager设置监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //取消圆点选中
                llDot.getChildAt(curPage).findViewById(R.id.view_dot).
                        setBackgroundResource(R.drawable.dot_normal);
                //设置圆点选中
                llDot.getChildAt(position).findViewById(R.id.view_dot).
                        setBackgroundResource(R.drawable.dot_selected);
                curPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
