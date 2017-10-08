package com.imooc.smartbutler.ui;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： SettingActivity
* 创建者： HJF
* 创建时间： 2017/5/719:56
* 描述： 设置
*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.service.SmsService;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.ShareUtils;
import com.imooc.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Switch sw_speak;
    private Switch sw_sms;

    private LinearLayout ll_update;
    private TextView tv_version;

    private String versionName;
    private int versionCode;
    private String url;

    private LinearLayout ll_scan;
    private TextView tv_scan;

    private LinearLayout ll_buildscan;

    private LinearLayout ll_baiduMap;

    private LinearLayout ll_version;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        actiongbar设置返回箭头
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        //初始化语音开关
        sw_speak= (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean ischeck=ShareUtils.getBoolean(this,"ischeck",false);
        sw_speak.setChecked(ischeck);

        //初始化短信提醒开关
        sw_sms= (Switch) findViewById(R.id.sw_SMS);
        sw_sms.setOnClickListener(this);
        boolean smsIscheck=ShareUtils.getBoolean(this,"smsIsCheck",false);
        sw_sms.setChecked(smsIscheck);

        /*
        1.初始化控件
        2.设置点击事件
        2.1rxvolley获取json数据
        2.2解析json数据
        3.判断当前版本与最新版本
        3.1获取当前版本好
        3.2获取最新版本当前号
        3.3if比较版本

        4.如果当前版本不是最新版本，那么弹出dialog
        5.点击dialog的确定键，跳转到下载页面
        6.下载更新完后自动跳转到安装页面

         */
        //初始化版本更新
        ll_update= (LinearLayout) findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        //初始化TextView
        tv_version= (TextView) findViewById(R.id.tv_version);
        try {
            getVersionNameCode();
            tv_version.setText("检查版本："+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //初始化扫一扫
        ll_scan= (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);
        //初始化TextView
        tv_scan= (TextView) findViewById(R.id.tv_scan);

        //初始化生成二维码
        ll_buildscan= (LinearLayout) findViewById(R.id.ll_buildscan);
        ll_buildscan.setOnClickListener(this);
        //初始化百度地图
        ll_baiduMap= (LinearLayout) findViewById(R.id.ll_baiduMap);
        ll_baiduMap.setOnClickListener(this);

        //初始化版本信息
        ll_version=(LinearLayout) findViewById(R.id.ll_version);
        ll_version.setOnClickListener(this);


    }
    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sw_speak:
                L.d("SPEAK");
                //切换状态
                sw_speak.setSelected(!sw_speak.isSelected());
                //
                ShareUtils.putBoolean(this,"ischeck",sw_speak.isChecked());

                break;
            case R.id.sw_SMS:
                //切换状态
                sw_sms.setSelected(!sw_sms.isSelected());
                //
                ShareUtils.putBoolean(this,"smsIsCheck",sw_sms.isChecked());
                if(sw_sms.isChecked()){
                    L.d("startservice");
                    startService(new Intent(this,SmsService.class));
                }else{
                    L.d("stopservice");
                    stopService(new Intent(this,SmsService.class));
                }

                break;
            case R.id.ll_update:
                L.i("config");
                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("config"+t);
                        parsingJson(t);

                    }
                });

                break;
            case R.id.ll_scan:
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.ll_buildscan:
                Intent intent=new Intent(this,QrCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_baiduMap:
                Intent intentBaidu=new Intent(this,LocationActivity.class);
                startActivity(intentBaidu);
                break;
            case R.id.ll_version:
                Intent intentVersion=new Intent(this,AboutActivity.class);
                startActivity(intentVersion);
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            tv_scan.setText(scanResult);
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            int code=jsonObject.getInt("versionCode");
            url=jsonObject.getString("url");
            if(code>versionCode){
                L.d("showUpdateDialog");

                showUpdateDialog(jsonObject.getString("content"));
            }else {
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦！")
                .setMessage("修复多项Bug!")
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(SettingActivity.this, UpdatActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //我什么都不做，也会执行dismis方法
            }
        }).show();
    }
}
