package com.imooc.smartbutler.ui;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： Test01
* 创建者： HJF
* 创建时间： 2017/9/1614:16
* 描述： com.imooc.smartbutler.ui
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class Test01 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRetrofit();
        initVolley();
    }

    private void initVolley() {
        //Volley第一步
        RequestQueue mRequestQueue= Volley.newRequestQueue(this);
        //Volley第二步
        StringRequest stringRequest=new StringRequest("http://www.baidu.com",
                new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },new com.android.volley.Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Volley第三步
        mRequestQueue.add(stringRequest);

    }

    private void initRetrofit() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.195:8080")
                .build();
//        retrofit.create();

    }

    //使用okhttpClient Http GET
    private void initView() {
        //创建okhttpclient对象
        OkHttpClient client=new OkHttpClient();
        //创建一个Request
        final Request request=new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        //new call
        Call call=client.newCall(request);
//        call.execute();
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String htmlStr=response.body().string();

            }
        });




    }
}
