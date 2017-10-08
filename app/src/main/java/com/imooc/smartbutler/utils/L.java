package com.imooc.smartbutler.utils;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.utils
* 文件名： L
* 创建者： HJF
* 创建时间： 2017/5/720:54
* 描述： log封装类
*/

import android.util.Log;

public class L {
//    TAG
    private static final String TAG="SmartButler";
//    开关
    private static final Boolean DEBUG=true;
//    五个等级 DIWEF
    public static void d(String text){
        if (DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if (DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if (DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if (DEBUG){
            Log.e(TAG,text);
        }
    }
}
