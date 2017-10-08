package com.imooc.smartbutler.ui;

/*
 *  项目名：  SmartButler 
 *  包名：    com.imooc.smartbutler.ui
 *  文件名:   CourierActivity
 *  创建者:   HJF
 *  创建时间:  2016/11/9 22:19
 *  描述：    快递查询
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.adapter.CourierAdapter;
import com.imooc.smartbutler.entity.CourierData;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_corporation,ed_number;
    private Button btn_kd_sure;
    private ListView lv_courier;
    private List<CourierData> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();

    }

    private void initView() {
        ed_corporation= (EditText) findViewById(R.id.ed_corporation);
        ed_number= (EditText) findViewById(R.id.ed_number);
        btn_kd_sure= (Button) findViewById(R.id.btn_kd_sure);
        lv_courier= (ListView) findViewById(R.id.lv_courier);
        btn_kd_sure.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_kd_sure:
                /*
                1.获取输入框的内容
                2.判断是否为空
                3.构建URL
                4.使用RxVolley框架进行网络请求
                5.返回JSON数据
                 */
//                1.获取输入框内容
                String company=ed_corporation.getText().toString().trim();
                String number=ed_number.getText().toString().trim();
//                拼接URL
                String url="http://v.juhe.cn/exp/index?key="+StaticClass.COURER_ID+"&com="+company+"&no="+number;
//                判断是否为空
                if(!TextUtils.isEmpty(company)&&!TextUtils.isEmpty(number)){
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
//                            L.i(t);
                            //解析Json数据
                            parsingJson(t);

                        }
                    });
                }else {
                    Toast.makeText(CourierActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
 private void parsingJson(String t) {
    try{
        L.d("IN");
        JSONObject jsonObject=new JSONObject(t);
        JSONObject resultJson=jsonObject.getJSONObject("result");
        JSONArray jsonArray=resultJson.getJSONArray("list");
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject json= (JSONObject) jsonArray.get(i);
            CourierData data=new CourierData();
            data.setRemark(json.getString("remark"));
            data.setZone(json.getString("zone"));
            data.setDatetime(json.getString("datetime"));
            mList.add(data);
        }
        //倒序
        Collections.reverse(mList);
        //往listview中传入数据
        CourierAdapter courierAdapter=new CourierAdapter(this,mList);
        lv_courier.setAdapter(courierAdapter);
        L.d("是否执行了");

    }catch (JSONException e){
        e.printStackTrace();
    }

    }
    
}
