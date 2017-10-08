package com.imooc.smartbutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.imooc.smartbutler.fragment.BulterFragment;
import com.imooc.smartbutler.fragment.GirlFragment;
import com.imooc.smartbutler.fragment.UserFragment;
import com.imooc.smartbutler.fragment.WechatFragment;
import com.imooc.smartbutler.ui.SettingActivity;
import com.imooc.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    TabLayout
    private TabLayout mTabLayout;
//    ViewPager
    private ViewPager mViewPager;
//    Title
    private List<String> mTitle;
//    Fragment
    private List<Fragment> mFragment;
//    FloatingActionButton
    private FloatingActionButton fab_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        除去阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
        L.d("test");
        L.i("test");
        L.w("test");
        L.e("test");
//        CrashReport.testJavaCrash();


    }
    //初始化数据
    private void initData(){
        mTitle=new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");
        mFragment=new ArrayList<>();
        mFragment.add(new BulterFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());

    }
    //初始化View
    private void initView(){
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        fab_setting.setVisibility(View.GONE);
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
//        预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
//        设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                Intent intent=new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

}
