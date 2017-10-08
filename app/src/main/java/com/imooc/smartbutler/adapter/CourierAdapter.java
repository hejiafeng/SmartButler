package com.imooc.smartbutler.adapter;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.adapter
* 文件名： CourierAdapter
* 创建者： HJF
* 创建时间： 2017/5/251:09
* 描述： com.imooc.smartbutler.adapter
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.CourierData;
import java.util.List;


public class CourierAdapter extends BaseAdapter{
    private  Context mContext;
    private List<CourierData> mList;
    private LayoutInflater inflater;
    private CourierData data;
    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext=mContext;
        this.mList=mList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark= (TextView) view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone= (TextView) view.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime= (TextView) view.findViewById(R.id.tv_datetime);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.tv_remark.setText(mList.get(i).getRemark());
        viewHolder.tv_zone.setText(mList.get(i).getZone());
        viewHolder.tv_datetime.setText(mList.get(i).getDatetime());
        return view;
    }
    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;

    }
}
