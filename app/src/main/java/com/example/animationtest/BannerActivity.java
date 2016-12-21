package com.example.animationtest;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener {

    private static final String TAG = "BannerActivity";

    private List<ImageView> imgs;

    private ViewPager viewPager;

    private TextView adNameText;

    private LinearLayout dotsLayout; //动态添加圆点

    private String[] imgsDesc;

    private int prePosition = 0;

    private boolean needSwitch = true; //是否需要轮播的标志位

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: currentItem =" + viewPager.getCurrentItem());
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        viewPager = (ViewPager) findViewById(R.id.vp_banner);
        adNameText = (TextView) findViewById(R.id.tv_img_desc);
        dotsLayout = (LinearLayout) findViewById(R.id.ll_dot_group);
        initViewPagerData();
        viewPager.setAdapter(new MyPagerAdapter());

        // 设置当前ViewPager要显示第几个条目，实现从第一次滑到最后一个
        int item = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2 % imgs.size());
        viewPager.setCurrentItem(item);

        dotsLayout.getChildAt(prePosition).setEnabled(true);
        adNameText.setText(imgsDesc[prePosition]);
        viewPager.addOnPageChangeListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( needSwitch ){
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void initViewPagerData() {
        imgsDesc = new String[]{"标题1","标题2","标题3","标题4","标题5"};
        imgs = new ArrayList<>();
        int imgIds[] = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
        ImageView iv;
        View dotView;
        for( int i=0; i<imgIds.length; i++){
            iv = new ImageView(this);
            iv.setBackgroundResource(imgIds[i]);
            imgs.add(iv);
            dotView = new View(this);
            dotView.setBackgroundResource(R.drawable.dot_selector);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15,15);
            if( i != 0 ){
                lp.leftMargin = 20;
            }
            dotView.setLayoutParams(lp);
            dotView.setEnabled(false);
            dotsLayout.addView(dotView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % imgs.size();
        Log.d(TAG, "onPageSelected: imgs.size =" + imgs.size());
        Log.d(TAG, "onPageSelected: new position =" + newPosition);
        dotsLayout.getChildAt(prePosition).setEnabled(false);
        dotsLayout.getChildAt(newPosition).setEnabled(true);
        adNameText.setText( imgsDesc[newPosition] );
        prePosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        needSwitch = false;
        super.onDestroy();
    }

    /**
     * ViewPager的数据适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPos = position % imgs.size();
            ImageView iv = imgs.get(newPos);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
