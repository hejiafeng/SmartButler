package com.imooc.smartbutler.ui;
/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： LoginActivity
* 创建者： HJF
* 创建时间： 2017/5/1318:21
* 描述： com.imooc.smartbutler.ui
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.smartbutler.MainActivity;
import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.MyUser;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.ShareUtils;
import com.imooc.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register_btn,login_btn;
    private EditText editText_name,editText_password;
    private CheckBox checkBox;
    //忘记密码
    private TextView tvRemPassword;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        //初始化
        tvRemPassword= (TextView) findViewById(R.id.tvRemPassword);
        tvRemPassword.setOnClickListener(this);
        register_btn= (Button) findViewById(R.id.register);
        register_btn.setOnClickListener(this);
        login_btn= (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        editText_name= (EditText) findViewById(R.id.login_name);
        editText_password= (EditText) findViewById(R.id.login_password);
        //初始化customDialog
        customDialog=new CustomDialog(this,0,0,R.layout.dialog_loding,
                R.style.Theme_Dialog, Gravity.CENTER);
        //点击窗口外无效
        customDialog.setCancelable(true);
        checkBox= (CheckBox) findViewById(R.id.login_checkBox);
        //初次打开界面，默认是false
        Boolean isCheck=ShareUtils.getBoolean(this,"isPassword",false);
        checkBox.setChecked(isCheck);
        L.d(isCheck+"");
        //判断，true就把名字和密码填上去，否则不填上去
        if (isCheck){
//            ShareUtils.putString(this,"name",editText_name.getText().toString().trim());
//            ShareUtils.putString(this,"password",editText_password.getText().toString().trim());

            L.d("赋值");
            String name=ShareUtils.getString(this,"name",null);
            editText_name.setText(name);
            String password=ShareUtils.getString(this,"password",null);
            editText_password.setText(password);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                customDialog.show();
                String lonin_name=editText_name.getText().toString().trim();
                String lonin_password=editText_password.getText().toString().trim();
                if(!TextUtils.isEmpty(lonin_name)&&!TextUtils.isEmpty(lonin_password)){
                    MyUser user=new MyUser();
                    user.setUsername(lonin_name);
                    user.setPassword(lonin_password);

                    user.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            customDialog.dismiss();
                            if(e==null){
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(this,"输入为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvRemPassword:
                //启动修改密码activity
                startActivity(new Intent(this,ChangePassword.class));
                break;
                }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //判断checkbox是否选中,根据它的状态决定ischeck
        ShareUtils.putBoolean(this,"isPassword",checkBox.isChecked());
        if(checkBox.isChecked()){
            ShareUtils.putString(this,"name",editText_name.getText().toString().trim());
            ShareUtils.putString(this,"password",editText_password.getText().toString().trim());

        }else {
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"password");
            L.d("isCheck=false");
        }

    }


}
