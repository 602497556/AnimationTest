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
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<CityBean> mDataList;
    private LayoutInflater inflater;

    public CityAdapter(Context context,List<CityBean> mDatas){
        inflater = LayoutInflater.from(context);
        this.mDataList = mDatas;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityAdapter.ViewHolder(inflater.inflate(R.layout.item_city,parent,false));
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        holder.tv.setText(mDataList.get(position).getCity());
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


}
