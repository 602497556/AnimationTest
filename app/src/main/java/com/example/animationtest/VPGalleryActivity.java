package com.example.animationtest;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.animationtest.transformation.MyTransformer;
import com.example.animationtest.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class VPGalleryActivity extends AppCompatActivity {

    private ViewPager viewPagerGallery;
    //图片数组
    private int[] mImgResId = {R.mipmap.first,R.mipmap.second,R.mipmap.third,
                                R.mipmap.fourth,R.mipmap.fifth};

    private List<ImageView> ivLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_gallery);
        initData();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPagerGallery = (ViewPager) findViewById(R.id.view_pager_gallery);
        viewPagerGallery.setOffscreenPageLimit(3);
        int pageWidth = (int) (getResources().getDisplayMetrics().widthPixels *3.0f /5.0f);
        ViewGroup.LayoutParams lp = viewPagerGallery.getLayoutParams();
        if(lp == null){
            lp = new ViewGroup.LayoutParams(pageWidth,ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pageWidth;
        }
        viewPagerGallery.setLayoutParams(lp);
        viewPagerGallery.setPageMargin(-50);
        //为解决触摸滑动ViewPager左右两边的页面无反应的问题：
        //需要为ViewPager的父容器设置OnTouchListener，将触摸事件传递给ViewPager
        findViewById(R.id.rl).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPagerGallery.dispatchTouchEvent(event);
            }
        });
        viewPagerGallery.setAdapter(new MyAdapter());
        viewPagerGallery.setPageTransformer(true, new MyTransformer());
    }

    private void initData() {
        for(int i=0; i<mImgResId.length; i++){
            ImageView iv = new ImageView(this);
            iv.setImageResource(mImgResId[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ivLists.add(iv);
        }
//        ImageView first=new ImageView(this);
//        first.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.first,this));
//        ImageView second=new ImageView(this);
//        second.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.second,this));
//        ImageView third=new ImageView(this);
//        third.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.third,this));
//        ImageView fourth=new ImageView(this);
//        fourth.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.fourth,this));
//        ImageView fifth=new ImageView(this);
//        fifth.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.fifth,this));
//        ivLists.add(first);
//        ivLists.add(second);
//        ivLists.add(third);
//        ivLists.add(fourth);
//        ivLists.add(fifth);
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = ivLists.get(position);
            container.addView(iv, position);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(ivLists.get(position));
        }

        @Override
        public int getCount() {
            return ivLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
