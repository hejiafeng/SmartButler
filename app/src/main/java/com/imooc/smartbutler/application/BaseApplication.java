package com.imooc.smartbutler.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.imooc.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/*
 * 项目名： SmartButler
 * 包名： com.imooc.smartbutler.application
 * 文件名： BaseApplication
 * 创建者： HJF
 * 创建时间： 2017/5/79:37
 * 描述： com.imooc.smartbutler.application
 */

public class BaseApplication extends Application {
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bungly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BOMB_APP_ID);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"="+StaticClass.SMART_VOICE);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
