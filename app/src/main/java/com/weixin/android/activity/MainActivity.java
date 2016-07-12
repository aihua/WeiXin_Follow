package com.weixin.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.weixin.android.R;
import com.weixin.android.coutomview.WechatRadioGroup;
import com.weixin.android.fragment.MeFragment;
import com.weixin.android.fragment.OneFragment;
import com.weixin.android.fragment.SecondFragment;
import com.weixin.android.fragment.ThreeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppBaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private OneFragment mOneFragment;
    private SecondFragment mSecondFragment;
    private ThreeFragment mThreeFragment;
    private MeFragment mFourFragment;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    private WechatRadioGroup mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        addPager();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mGroup = (WechatRadioGroup) findViewById(R.id.radiogroup);
        mGroup.setViewPager(mViewPager);
    }

    private void addPager() {
        mOneFragment = new OneFragment();
        mFragments.add(mOneFragment);
        mSecondFragment = new SecondFragment();
        mFragments.add(mSecondFragment);
        mThreeFragment = new ThreeFragment();
        mFragments.add(mThreeFragment);
        mFourFragment = new MeFragment();
        mFragments.add(mFourFragment);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
