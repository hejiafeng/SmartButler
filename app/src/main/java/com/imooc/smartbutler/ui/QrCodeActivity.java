package com.imooc.smartbutler.ui;/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： QrCodeActivity
* 创建者： HJF
* 创建时间： 2017/7/2217:10
* 描述： com.imooc.smartbutler.ui
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.imooc.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class QrCodeActivity extends BaseActivity{
    private ImageView iv_qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
    }

    private void initView() {
        iv_qrcode= (ImageView) findViewById(R.id.iv_qrcode);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qrcode.setImageBitmap(qrCodeBitmap);
    }
}
