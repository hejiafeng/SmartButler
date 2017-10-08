package com.imooc.smartbutler.utils;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.utils
* 文件名： UtilTools
* 创建者： HJF
* 创建时间： 2017/5/79:55
* 描述： 工具统一类
*/

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.smartbutler.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {
    public static void setFont(Context mContext, TextView textView){
        Typeface fontFypeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontFypeface);
    }
//读取图片
    public static void getImageToShare(Context mContext, ImageView imageView){
        //初始化头像
//        1.取得string
        String imageTitle=ShareUtils.getString(mContext,"image_title","");
        if(!imageTitle.equals("")){
//            2.利用Base64将string转换
            byte[] byteArray=Base64.decode(imageTitle,Base64.DEFAULT);
            ByteArrayInputStream byStream=new ByteArrayInputStream(byteArray);
//            3.生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }
//    存储图片
    public static void putImageToShare(Context mContext, ImageView imageView){
        //保存
        BitmapDrawable drawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        //第一步：将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,88,byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte[] byteArray=byStream.toByteArray();
        String imgString=new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
//        第三步：将String保存shareUtils
        ShareUtils.putString(mContext,"image_title",imgString);

    }

    public static String getVersion(Context context){
        PackageManager pm =context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return context.getString(R.string.text_unknown);
        }

    }


}
