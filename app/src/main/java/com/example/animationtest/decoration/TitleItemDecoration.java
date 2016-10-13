package com.example.animationtest.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.animationtest.bean.CityBean;

import java.util.List;

/**
 * Created by Zengjie on 2016/10/13.
 */
public class TitleItemDecoration extends RecyclerView.ItemDecoration {

    private List<CityBean> mDatas;
    private Paint mPaint;
    //用于存放测量文字Rect
    private Rect mBounds;
    //Title的高
    private int mTitleHeight;
    //Title字体大小
    private int mTitleFontSize;
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONTS = Color.parseColor("#FF000000");

    public TitleItemDecoration(Context context,List<CityBean> dataSet){
        super();
        mDatas = dataSet;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,
                context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,
                context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if(position > -1){
            if(position == 0){
                //等于0肯定要有title
                outRect.set(0,mTitleHeight,0,0);
            } else {
                if(mDatas.get(position).getTag() != null && mDatas.get(position).getTag() != mDatas.get(position-1).getTag()){
                //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    outRect.set(0,mTitleHeight,0,0);
                } else {
                    outRect.set(0,0,0,0);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for(int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if(position > -1){
                if(position == 0){
                    //等于0肯定要有title
                    drawTitleArea(c,left,right,child,params,position);
                } else {
                    if(mDatas.get(position).getTag() != null && mDatas.get(position).getTag() != mDatas.get(position-1).getTag()){
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c,left,right,child,params,position);
                    } else {

                    }
                }
            }
        }
    }

    /**
     * 绘制title的背景以及字体
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child,
                               RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left,child.getTop()-params.topMargin-mTitleHeight,
                right,child.getTop()-params.topMargin,mPaint);
        mPaint.setColor(COLOR_TITLE_FONTS);
        mPaint.getTextBounds(mDatas.get(position).getTag(),
                0,mDatas.get(position).getTag().length(),mBounds);
        c.drawText(mDatas.get(position).getTag(),child.getPaddingLeft(),
                child.getTop()-params.topMargin-mTitleHeight/2+mBounds.height()/2,mPaint);

    }

    //最后调用，绘制在最上层
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int pos = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        String tag = mDatas.get(pos).getTag();
//        View child = parent.getChildAt(pos);
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(),parent.getPaddingTop(),
                parent.getWidth()-parent.getPaddingRight(),
                parent.getPaddingTop()+mTitleHeight,mPaint);
        mPaint.setColor(COLOR_TITLE_FONTS);
        mPaint.getTextBounds(tag,0,tag.length(),mBounds);
        c.drawText(tag,child.getPaddingLeft(),
                parent.getPaddingTop()+mTitleHeight/2+mBounds.height()/2,mPaint);
    }


}
