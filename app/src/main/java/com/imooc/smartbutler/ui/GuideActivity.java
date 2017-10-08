package com.imooc.smartbutler.ui;/*
* 项目名： SmartButler
* 包名： com.imooc.smartbutler.ui
* 文件名： GuideActivity
* 创建者： HJF
* 创建时间： 2017/5/922:56
* 描述： com.imooc.smartbutler.ui
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.imooc.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private List<View> mViews;
    private ImageView mImageView1,mImageView2,mImageView3,ignore;
    private Button come_btn;
    private View view1,view2,view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initData();
        initView();
    }

    private void initData() {
        mViews=new ArrayList<View>();
        view1=View.inflate(this,R.layout.guide_view1,null);
        view2=View.inflate(this,R.layout.guide_view2,null);
        view3=View.inflate(this,R.layout.guide_view3,null);
        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
    }

    private void initView() {
        come_btn=(Button) view3.findViewById(R.id.come_in_btn);
        come_btn.setOnClickListener(this);
        mImageView1= (ImageView) findViewById(R.id.guide_circle1);
        mImageView2= (ImageView) findViewById(R.id.guide_circle2);
        mImageView3= (ImageView) findViewById(R.id.guide_circle3);
        ignore= (ImageView) findViewById(R.id.ignore);
        viewPager= (ViewPager) findViewById(R.id.guide_viewPager);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(mViews.get(position));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setImg(true,false,false);
                        ignore.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setImg(false,true,false);
                        ignore.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setImg(false,false,true);
                        ignore.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setImg(Boolean img1,Boolean img2,Boolean img3){
        if(img1){
            mImageView1.setBackgroundResource(R.drawable.point_on);
        }else {
            mImageView1.setBackgroundResource(R.drawable.point_off);
        }
        if(img2){
            mImageView2.setBackgroundResource(R.drawable.point_on);
        }else {
            mImageView2.setBackgroundResource(R.drawable.point_off);
        }
        if(img3){
            mImageView3.setBackgroundResource(R.drawable.point_on);
        }else {
            mImageView3.setBackgroundResource(R.drawable.point_off);
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.come_in_btn:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }

    }
}

