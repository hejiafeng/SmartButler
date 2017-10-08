package com.imooc.smartbutler.ui;/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： WebViewActivity
* 创建者： HJF
* 创建时间： 2017/6/123:35
* 描述： com.imooc.smartbutler.ui
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.imooc.smartbutler.R;

public class WebViewActivity extends BaseActivity {
    private ProgressBar mProgressbar;
    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }

    private void initView() {
        mProgressbar= (ProgressBar) findViewById(R.id.mProgressbar);
        mWebview= (WebView) findViewById(R.id.mWebview);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        final String url=intent.getStringExtra("url");

        //设置标题
        getSupportActionBar().setTitle(title);

        //进行加载网页的逻辑

        //支持Js
        mWebview.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebview.getSettings().setSupportZoom(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebview.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebview.loadUrl(url);

        //本地显示
        mWebview.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(url);
                return true;
            }
        });


    }
    public class WebViewClient extends WebChromeClient{
        //进度变化的监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                mProgressbar.setVisibility(View.GONE);
            }

            super.onProgressChanged(view, newProgress);
        }
    }
}
