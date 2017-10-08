package com.imooc.smartbutler.view;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.view
* 文件名： CustomDialog
* 创建者： HJF
* 创建时间： 2017/5/180:52
* 描述： 自定义Dialog
*/

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.imooc.smartbutler.R;


public class CustomDialog extends Dialog{
//定义模板
    public CustomDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout,style, Gravity.CENTER);
    }
    //定义属性
    public CustomDialog(Context context,int width,int heigth,
                        int layout,int style,int gravity,int anim){
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window=getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity=gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }
    //与上面区别是，少了动画，也就是上面的动画是可以使用默认的。如果动画使用下面就可以调用下面的方法
    public CustomDialog(Context context,int width,
                        int heigth,int layout,int style,int gravity){
        this(context,width,heigth,layout,style,gravity, R.style.pop_anim_style);
    }
}
