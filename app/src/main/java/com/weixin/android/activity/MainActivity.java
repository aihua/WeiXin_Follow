package com.weixin.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.weixin.android.R;
import com.weixin.android.fragment.FourFragment;
import com.weixin.android.fragment.OneFragment;
import com.weixin.android.fragment.SecondFragment;
import com.weixin.android.fragment.ThreeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppBaseActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private OneFragment mOneFragment;
    private SecondFragment mSecondFragment;
    private ThreeFragment mThreeFragment;
    private FourFragment mFourFragment;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

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
        mViewPager.addOnPageChangeListener(this);

    }

    private void addPager() {
        mOneFragment = new OneFragment();
        mFragments.add(mOneFragment);
        mSecondFragment = new SecondFragment();
        mFragments.add(mSecondFragment);
        mThreeFragment = new ThreeFragment();
        mFragments.add(mThreeFragment);
        mFourFragment = new FourFragment();
        mFragments.add(mFourFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
