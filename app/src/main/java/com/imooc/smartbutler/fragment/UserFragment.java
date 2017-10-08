package com.imooc.smartbutler.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.smartbutler.R;
import com.imooc.smartbutler.entity.MyUser;
import com.imooc.smartbutler.ui.CourierActivity;
import com.imooc.smartbutler.ui.LoginActivity;
import com.imooc.smartbutler.ui.PhoneActivity;
import com.imooc.smartbutler.utils.L;
import com.imooc.smartbutler.utils.UtilTools;
import com.imooc.smartbutler.view.CustomDialog;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.fragment
* 文件名： BulterFragment
* 创建者： HJF
* 创建时间： 2017/5/710:50
* 描述： com.imooc.smartbutler.fragment
*/

public class UserFragment extends Fragment implements View.OnClickListener {
    private EditText ed_username,ed_usergender,ed_userage,ed_userintro;
    private Button btn_upstate,btn_ensure_change,btn_exit;
    private Button btn_camera,btn_picture,btn_cancel;
    private TextView tv_courier,ed_queryadress;

    private CustomDialog customDialog;
    //圆形头像
    private CircleImageView profile_image;

    public final static int CHOOSE_PHOTO=101;

    public final static int TAKE_PHOTO=102;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ed_queryadress= (TextView) view.findViewById(R.id.ed_queryadress);
        ed_queryadress.setOnClickListener(this);
        tv_courier= (TextView) view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);
        //初始化btn与edittext
        btn_upstate= (Button) view.findViewById(R.id.btn_upstate);
        btn_ensure_change= (Button) view.findViewById(R.id.btn_ensure_change);
        btn_exit= (Button) view.findViewById(R.id.btn_exit);

        ed_username= (EditText) view.findViewById(R.id.ed_username);
        ed_usergender= (EditText) view.findViewById(R.id.ed_usergender);
        ed_userage= (EditText) view.findViewById(R.id.ed_userage);
        ed_userintro= (EditText) view.findViewById(R.id.ed_userintro);
        //头像
        profile_image= (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
//      初始化头像
        UtilTools.getImageToShare(getActivity(),profile_image);


        //初始dialog
            customDialog=new CustomDialog(getActivity(),0,0,R.layout.dialog_photo,
                    R.style.Theme_Dialog, Gravity.BOTTOM,0);
        customDialog.setCancelable(false);

        //dialog里的控件
        btn_camera= (Button) customDialog.findViewById(R.id.btn_camera);
        btn_picture= (Button) customDialog.findViewById(R.id.btn_picture);
        btn_cancel= (Button) customDialog.findViewById(R.id.btn_cancel);

        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //设置用户数据
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        String username = userInfo.getUsername();
        Integer age = userInfo.getAge();
        Boolean gender =userInfo.isGender();
        String desc=userInfo.getDesc();
        ed_username.setText(username);
        ed_usergender.setText(gender?"男":"女");
        ed_userage.setText(age+"");
        ed_userintro.setText(desc);
        //默认不能修改
        setEnabled(false);

        btn_ensure_change.setVisibility(View.GONE);
        btn_upstate.setOnClickListener(this);
        btn_ensure_change.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

    }

    private void setEnabled(boolean b) {
        ed_username.setEnabled(b);
        ed_usergender.setEnabled(b);
        ed_userage.setEnabled(b);
        ed_userintro.setEnabled(b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_upstate:
                btn_ensure_change.setVisibility(View.VISIBLE);
                setEnabled(true);

                break;
            case R.id.btn_ensure_change:
                MyUser newUser = new MyUser();
                String name=ed_username.getText().toString().trim();
                String age=ed_userage.getText().toString().trim();
                String gender=ed_usergender.getText().toString().trim();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(age)&&!TextUtils.isEmpty(gender)){
                    newUser.setUsername(name);
                    newUser.setAge(Integer.parseInt(age));
                    if (gender.equals("男")){
                        newUser.setGender(true);
                    }else{
                        newUser.setGender(false);
                    }
                    String desc=ed_userintro.getText().toString().trim();
                    if(!TextUtils.isEmpty(desc)){
                        newUser.setDesc(desc);
                    }else {
                        newUser.setDesc("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getContext(),"更新用户信息成功",Toast.LENGTH_SHORT).show();
                                btn_ensure_change.setVisibility(View.GONE);
                                setEnabled(false);

                            }else{
//                            toast();
                                Toast.makeText(getContext(),"更新用户信息失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(getContext(),"输入框为空",Toast.LENGTH_SHORT).show();

                }


                break;
            case R.id.btn_exit:
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(),LoginActivity.class));
                L.d("UserFragment");
                getActivity().finish();

                break;
            case R.id.profile_image:
                L.d("customdialog.show");
                customDialog.show();
                break;
            case R.id.btn_cancel:
                customDialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();

                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.ed_queryadress:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }

    }

    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //选择图片格式
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
        customDialog.dismiss();
    }

    public final static String PHOTO_IMAGE_FILE_NAME="fileImg.jpg";
    private Uri imageUri;
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        创建File对象，用于存储拍照后的图片
        File outputImage = new File(Environment.
                getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri=Uri.fromFile(outputImage);
        //存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
        customDialog.dismiss();
    }
    private File tempFile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=getActivity().RESULT_CANCELED){
            switch (requestCode){
                case TAKE_PHOTO:
                    tempFile=new File(Environment.
                            getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case CHOOSE_PHOTO:

                    startPhotoZoom(data.getData());
                    break;
                case CROP_PHOTO:
                    if(data!=null){
                        setImageToView(data);
                    }
                    if(tempFile!=null){
                        tempFile.delete();
                    }
                    break;
            }
        }
    }
    //设置头像
    private void setImageToView(Intent data) {
        Bundle bundle=data.getExtras();
        if(bundle!=null){
            Bitmap bitmap=bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }
    public final static int CROP_PHOTO=103;
    private void startPhotoZoom(Uri uri){
        if(uri==null){
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri,"image/*");
        //设置存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        //设置裁剪
        intent.putExtra("crop",true);
        //设置裁剪宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
//        设置裁剪图片质量
        intent.putExtra("outputX",320);
        intent.putExtra("outputY",320);
        //发送数据
        intent.putExtra("return-data",true);
        // 启动裁剪程序
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(getActivity(),profile_image);

    }
}
