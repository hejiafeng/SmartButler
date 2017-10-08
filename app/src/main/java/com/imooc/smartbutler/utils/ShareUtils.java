package com.imooc.smartbutler.utils;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.utils
* 文件名： ShareUtils
* 创建者： HJF
* 创建时间： 2017/5/723:39
* 描述： SharedPreferences封装
*/

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    //存储数据名称
    public final static String NAME="config";
//    putint
    public static void putInt(Context mContext,String key,int values){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(key,values);
        editor.commit();
    }
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);

    }
//    String
    public static void putString(Context mContext,String key,String values){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,values);
        editor.commit();
    }
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,defValue);

    }
//    Boolean
    public static void putBoolean(Context mContext,String key,Boolean values){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,values);
        editor.commit();
    }
    public static boolean getBoolean(Context mContext,String key,Boolean defValue){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);

    }
    //删除单个
    public static void deleShare(Context mContext,String key){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();

    }
    //删除所有
    public static void deleAll(Context mContext,String key){
        SharedPreferences sp=mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }



}
