package com.imooc.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.adapter.WeChatAdapter;
import com.imooc.smartbutler.entity.WeChatData;
import com.imooc.smartbutler.ui.WebViewActivity;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.fragment
* 文件名： BulterFragment
* 创建者： HJF
* 创建时间： 2017/5/710:50
* 描述： com.imooc.smartbutler.fragment
*/

public class WechatFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<WeChatData> mlists=new ArrayList<>();
    private ListView mListView;
    private List<String> mListTitle=new ArrayList<>();
    private List<String> mListUrl=new ArrayList<>();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_wechat,null);
        initView();
        return view;
    }

    private void initView() {

        String url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_ID+"&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                 L.i("微信精选"+t);
                L.i("解析数据");
                //解析Json数据
                parsingJson(t);

            }
        });


    }
    private void parsingJson(String t) {
        try {
            JSONObject jsonObect = new JSONObject(t);
            JSONObject jsonResult=jsonObect.getJSONObject("result");
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json=jsonArray.getJSONObject(i);
                String title = json.getString("title");
                String source = json.getString("source");
                String url = json.getString("url");
                String imgUrl=json.getString("firstImg");
                WeChatData weChatData = new WeChatData();
                weChatData.setTitle(title);
                weChatData.setSource(source);
                weChatData.setImgUrl(imgUrl);
                mListTitle.add(title);
                mListUrl.add(url);
                mlists.add(weChatData);
            }
            mListView= (ListView) view.findViewById(R.id.lv_wechat);
            WeChatAdapter weChatAdapter=new WeChatAdapter(getActivity(),mlists);
            mListView.setAdapter(weChatAdapter);
            mListView.setOnItemClickListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("title", mListTitle.get(i));
        intent.putExtra("url", mListUrl.get(i));
        startActivity(intent);

    }
}
