package com.imooc.smartbutler.ui;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： UpdatActivity
* 创建者： HJF
* 创建时间： 2017/7/1721:15
* 描述：UpdatActivity
*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.imooc.smartbutler.R;
import com.imooc.smartbutler.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

public class UpdatActivity extends BaseActivity{
    private String url;
    private String path;
    private TextView tv_size;
    private NumberProgressBar number_progress_bar;

    //正在下载
    public static final int HANDLER_LODING = 10001;
    //下载完成
    public static final int HANDLER_OK = 10002;
    //下载失败
    public static final int HANDLER_ON = 10003;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LODING:
                    //实时更新进度
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes + " / " + totalSize);
                    //
                    number_progress_bar.setProgress((int)(((float)transferredBytes/(float)totalSize)*100));
                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动这个应用安装
                    startInstallApk();
                    break;
                case HANDLER_ON:
                    tv_size.setText("下载失败");
                    break;

            }

        }
    };
//自动安装
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();

    }

    private void initView() {
//      初始化NumberProgressBar
        number_progress_bar= (NumberProgressBar) findViewById(R.id.number_progress_bar);
//      初始化textView
        tv_size= (TextView) findViewById(R.id.tv_size);

        url=getIntent().getStringExtra("url");
        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";
        RxVolley.download(path, url, new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                Message msg=new Message();
                msg.what=HANDLER_LODING;
                Bundle bundle = new Bundle();
                bundle.putLong("transferredBytes", transferredBytes);
                bundle.putLong("totalSize", totalSize);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }
        }, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                handler.sendEmptyMessage(HANDLER_OK);
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
                handler.sendEmptyMessage(HANDLER_ON);
            }
        });
    }
}
