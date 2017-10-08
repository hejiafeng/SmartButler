package com.imooc.smartbutler.adapter;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.adapter
* 文件名： ChatListAdaper
* 创建者： HJF
* 创建时间： 2017/6/420:50
* 描述： com.imooc.smartbutler.adapter
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.ChatListData;
import com.imooc.smartbutler.utils.L;

import java.util.List;

public class ChatListAdaper extends BaseAdapter {
    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;
    private List<ChatListData> mLists;
    private Context mContext;
    private LayoutInflater inflater;

    public ChatListAdaper(Context mContext, List<ChatListData> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //获取当前要显示的type 根据这个type来区分数据的加载
        int type = getItemViewType(i);
        L.e(i+""+"ni");
        L.d(type+"哈哈");
        ViewHolderLeftText viewHolderLeftText=null;
        ViewHolderRightText viewHolderRightText=null;
        if (view == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(mLists.get(i).getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(mLists.get(i).getText());
                break;

        }
        return view;
    }
    //根据数据源的positiion来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mLists.get(position);
        int type = data.getType();
        return type;
    }
    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3; //mlisy.size + 1
    }

    //左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
