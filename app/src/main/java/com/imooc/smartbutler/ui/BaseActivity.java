package com.imooc.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： BaseActivity
* 创建者： HJF
* 创建时间： 2017/5/79:40
* 描述： Activity基类
*/

/**
 * 主要做的事情：
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */
public class BaseActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        actiongbar设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//    菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
