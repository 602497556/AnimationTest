package com.example.animationtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animationtest.R;
import com.example.animationtest.bean.Model;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Model> mDatas;
    private int curPage;
    private int pageSize;

    public GridViewAdapter(Context context,List<Model> mDatas,int curPage,int pageSize){
        this.mContext = context;
        this.mDatas = mDatas;
        this.curPage = curPage;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页
     * 的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curPage +1)*pageSize ? pageSize :
                mDatas.size()- curPage * pageSize ;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(pageSize * curPage + position);
    }

    @Override
    public long getItemId(int position) {
        return pageSize * curPage + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_view_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.grid_view_item_iv);
            holder.title = (TextView) convertView.findViewById(R.id.grid_view_item_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * 绑定数据时，正确的position = curPage * pageSize + position;
         */
        int pos = curPage * pageSize + position;
        holder.image.setImageResource(mDatas.get(pos).getIconRes());
        holder.title.setText(mDatas.get(pos).getName());
        return convertView;
    }

    public class ViewHolder {
        ImageView image;
        TextView title;
    }

}
