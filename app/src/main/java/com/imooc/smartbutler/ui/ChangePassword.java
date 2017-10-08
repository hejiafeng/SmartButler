package com.imooc.smartbutler.ui;/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： ChangePassword
* 创建者： HJF
* 创建时间： 2017/5/1622:36
* 描述： 修改密码
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.MyUser;
import com.imooc.smartbutler.utils.L;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private EditText etCpInput,etOldPassword,etNewPassword,etAgainPassword;
    private Button etCpSure,btChangePassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        initView();
    }

    private void initView() {
        etCpInput = (EditText) findViewById(R.id.etCpInput);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etAgainPassword = (EditText) findViewById(R.id.etAgainPassword);
        etCpSure = (Button) findViewById(R.id.bt_Sure);
        btChangePassword= (Button) findViewById(R.id.btChangePassword);
        btChangePassword.setOnClickListener(this);
        etCpSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_Sure:
                //获取邮箱
                final String email = etCpInput.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    //重置密码
                    BmobUser.resetPasswordByEmail(email, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ChangePassword.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePassword.this, "重置密码错误" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btChangePassword:
                String oldpassword=etOldPassword.getText().toString().trim();
                String newpassword=etNewPassword.getText().toString().trim();
                String againpassword=etAgainPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(oldpassword)&&!TextUtils.isEmpty(newpassword)&&!TextUtils.isEmpty(againpassword)){
                   if (newpassword.equals(againpassword)){
                       MyUser.updateCurrentUserPassword(oldpassword, newpassword, new UpdateListener() {

                           @Override
                           public void done(BmobException e) {
                               if(e==null){
//                                   toast("密码修改成功，可以用新密码进行登录啦");
                                   Toast.makeText(ChangePassword.this,"密码修改成功，可以用新密码进行登录啦",Toast.LENGTH_SHORT).show();
                                   finish();
                               }else{
//                                   toast("失败:" + e.getMessage());
                                   L.d("登录失败");
                                   Toast.makeText(ChangePassword.this,"失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();

                               }
                           }

                       });
                   }else {
                       Toast.makeText(this,"输入密码不一致",Toast.LENGTH_SHORT).show();
                   }

                }else {
                    Toast.makeText(this,"输入框为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
    }
}