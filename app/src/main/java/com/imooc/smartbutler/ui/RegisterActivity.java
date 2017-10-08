package com.imooc.smartbutler.ui;/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： RegisterActivity
* 创建者： HJF
* 创建时间： 2017/5/1319:04
* 描述： com.imooc.smartbutler.ui
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText register_name,register_age,register_introduce,
            register_password,register_try_again,register_email;
    private RadioGroup radioGroup;
    private Button bt_register;
    Boolean isGender=true;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        register_name= (EditText) findViewById(R.id.register_name);
        register_age= (EditText) findViewById(R.id.register_age);
        register_introduce= (EditText) findViewById(R.id.register_introduce);
        register_password= (EditText) findViewById(R.id.register_password);
        register_try_again= (EditText) findViewById(R.id.register_try_again);
        register_email= (EditText) findViewById(R.id.register_email);
        radioGroup= (RadioGroup) findViewById(R.id.rg_radiogroup);
        bt_register= (Button) findViewById(R.id.register_in_register);
        bt_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_in_register:
                //获得输入框的值
                String name=register_name.getText().toString();
                String age=register_age.getText().toString();
                String introduce=register_introduce.getText().toString();
                String password=register_password.getText().toString();
                String try_again=register_try_again.getText().toString();
                String email=register_email.getText().toString();
                //判断是否为空
                if(!(TextUtils.isEmpty(name)&&TextUtils.isEmpty(age)&&
                    TextUtils.isEmpty(password)&&TextUtils.isEmpty(try_again)&&
                    TextUtils.isEmpty(email))){
                    if(!(password==try_again)){
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                if (i==R.id.rg_boy){
                                    isGender=true;
                                }else if(i==R.id.rg_girl){
                                    isGender=false;
                                }
                            };
                        });
                        if(TextUtils.isEmpty(introduce)){
                            introduce="这个人很懒，什么都没有留下";
                        }
                        //注册
                        MyUser user=new MyUser();
                        user.setUsername(name);
                        user.setAge(Integer.parseInt(age));
                        user.setDesc(introduce);
                        user.setGender(isGender);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"注册失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                };
                            }
                        });



                    }else{

                    };


            }else{
                    Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show();

            };
                break;

        }
    }
}
