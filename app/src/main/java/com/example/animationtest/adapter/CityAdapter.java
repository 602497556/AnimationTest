package com.example.animationtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.animationtest.R;
import com.example.animationtest.bean.CityBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{

    private List<CityBean> mDataList;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public CityAdapter(Context context,List<CityBean> mDatas){
        inflater = LayoutInflater.from(context);
        this.mDataList = mDatas;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_city,parent,false));
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder holder, int position) {

        holder.tv.setText(mDataList.get(position).getCity());
        //如果设置了回调，则设置点击事件
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(v,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tvCity);
        }
    }

    /**
     * 点击事件回调接口
     */
    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }


}
