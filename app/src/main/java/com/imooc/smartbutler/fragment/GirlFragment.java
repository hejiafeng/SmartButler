package com.imooc.smartbutler.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.adapter.GirlAdapter;
import com.imooc.smartbutler.entity.GirlData;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.PicassoUtils;
import com.imooc.smartbutler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.fragment
* 文件名： BulterFragment
* 创建者： HJF
* 创建时间： 2017/5/710:50
* 描述： com.imooc.smartbutler.fragment
*/

public class GirlFragment extends Fragment {
    private View view;
    private GridView gridView;
    private GirlAdapter mAdapter;
    private List<GirlData> mLists=new ArrayList<>();
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;

    //图片地址的数据
    private List<String> mListUrl=new ArrayList<>();

    //PhotoView
    private PhotoViewAttacher mAttacher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_girl,null);
        findView();
        return view;
    }

    private void findView() {
        L.i("girlView");
        gridView= (GridView) view.findViewById(R.id.mGridView);
        //初始化dialog
        dialog=new CustomDialog(getActivity(),
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_girl,
                R.style.Theme_Dialog, Gravity.CENTER,0);

        //初始化
        iv_img= (ImageView) dialog.findViewById(R.id.iv_img);

//        gridView.setAdapter(setAdapter);
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("Girl Json:" + t);
                L.i("解析成功了么，美女呢？");
                parsingJson(t);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //解析图片
                PicassoUtils.loadImaheView(getActivity(),mListUrl.get(i),iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();

            }
        });



    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonobject=new JSONObject(t);
            JSONArray jsonArray= (JSONArray) jsonobject.get("results");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject json= (JSONObject) jsonArray.get(i);
                String imgUrl=json.getString("url");
                GirlData data=new GirlData();
                data.setImgUrl(imgUrl);
                mLists.add(data);
                mListUrl.add(imgUrl);
                L.e(i+"");
                L.e("美女呢妈蛋");

            }
            //接下来就是将mlist传入adapter 以及gridview中实现。
            mAdapter = new GirlAdapter(getActivity(), mLists);
            //设置适配器
            gridView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
