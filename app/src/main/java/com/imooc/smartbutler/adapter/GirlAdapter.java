package com.imooc.smartbutler.adapter;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.adapter
* 文件名： GirlAdapter
* 创建者： HJF
* 创建时间： 2017/6/1014:38
* 描述： com.imooc.smartbutler.adapter
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.GirlData;
import com.imooc.smartbutler.utils.PicassoUtils;

import java.util.List;

public class GirlAdapter extends BaseAdapter {
    private Context mContext;
    private List<GirlData> mLists;
    private LayoutInflater inflater;
    private GirlData data;
    private WindowManager wm;
    //屏幕宽
    private int width;
    public GirlAdapter(Context mContext, List<GirlData> mLists) {
        this.mContext=mContext;
        this.mLists=mLists;
        //作用是加载view，只要知道R.layout即可加载
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.girl_item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageview);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        data = mLists.get(i);
        //解析图片
        String url = data.getImgUrl();

        PicassoUtils.loadImageViewSize(mContext,
                url,
                width/2,
                500,
                viewHolder.imageView);
        return view;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
