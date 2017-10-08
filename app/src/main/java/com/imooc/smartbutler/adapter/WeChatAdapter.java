package com.imooc.smartbutler.adapter;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.adapter
* 文件名： WeChatAdapter
* 创建者： HJF
* 创建时间： 2017/6/121:22
* 描述： com.imooc.smartbutler.adapter
*/

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.WeChatData;
import com.imooc.smartbutler.utils.PicassoUtils;

import java.util.List;

public class WeChatAdapter extends BaseAdapter {
    private List<WeChatData> mlists;
    private Context mContext;
    private LayoutInflater inflater;
    private int width,heigth;
    private WindowManager mWindowManger;
    private WeChatData data;

    public WeChatAdapter(Context mContext, List<WeChatData> mlists){
        this.mContext=mContext;
        this.mlists=mlists;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWindowManger= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width=mWindowManger.getDefaultDisplay().getWidth();
        heigth=mWindowManger.getDefaultDisplay().getHeight();


    }
    @Override
    public int getCount() {
        return mlists.size();
    }

    @Override
    public Object getItem(int i) {
        return mlists.get(i);
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
            //先加载view,如果是activity则不需要加载view
            view=inflater.inflate(R.layout.wechat_item,null);
            viewHolder.img_wechat= (ImageView) view.findViewById(R.id.img_wechat);
            viewHolder.tv_wechat_title= (TextView) view.findViewById(R.id.tv_wechat_title);
            viewHolder.tv_wechat_source= (TextView) view.findViewById(R.id.tv_wechat_source);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
//        Picasso.with(mContext)
//                .load(mlists.get(i).getImgUrl())
//                .resize(50, 50)
//                .centerCrop()
//                .into(viewHolder.img_wechat);
//        if (image.isEmpty()) {
//            iview.setImageResource(R.drawable.placeholder);
//        } else{
//            Picasso.with(_c).load(image).into(iview);
//        }
//            PicassoUtils.loadImageViewHolder(mContext,
//                    mlists.get(i).getImgUrl(),
//                    R.mipmap.ic_launcher,
//                    R.mipmap.ic_launcher,
//                    viewHolder.img_wechat);

//        PicassoUtils.loadImageViewSize(mContext,mlists.get(i).getImgUrl(),width/3,80,viewHolder.img_wechat);
        data=mlists.get(i);
        viewHolder.tv_wechat_title.setText(data.getTitle());
        viewHolder.tv_wechat_source.setText(data.getSource());
        if(!TextUtils.isEmpty(data.getImgUrl())){
            PicassoUtils.loadImageViewSize(mContext,mlists.get(i).getImgUrl(),width/3,250,viewHolder.img_wechat);

        }
        return view;
    }
    class ViewHolder{
        ImageView img_wechat;
        TextView tv_wechat_title;
        TextView tv_wechat_source;

    }
}
