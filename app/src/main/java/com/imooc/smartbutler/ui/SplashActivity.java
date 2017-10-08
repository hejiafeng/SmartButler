package com.imooc.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.utils.ShareUtils;
import com.imooc.smartbutler.utils.StaticClass;
import com.imooc.smartbutler.utils.UtilTools;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： SplashActivity
* 创建者： HJF
* 创建时间： 2017/5/922:17
* 描述： com.imooc.smartbutler.ui
*/



public class SplashActivity extends AppCompatActivity {
    /**
     *1.延时2000ms
     *2.判断程序是否第一次运行
     *3.自定义字体
     *4.Activity全屏主题
     */
    private TextView tv;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        Intent intent=new Intent(SplashActivity.this,GuideActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
            }
        }
    };

//    判断程序是否第一次执行
    private boolean isFirst() {
        Boolean isFirst=ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else{
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv= (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFont(this,tv);
    }


//    禁止返回键
    @Override
    public void onBackPressed() {
    }
}
