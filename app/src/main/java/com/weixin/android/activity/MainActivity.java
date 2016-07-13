package com.weixin.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weixin.android.R;
import com.weixin.android.coutomview.WechatRadioGroup;
import com.weixin.android.fragment.CommunFragment;
import com.weixin.android.fragment.FindFragment;
import com.weixin.android.fragment.HomeFragment;
import com.weixin.android.fragment.MeFragment;

public class MainActivity extends AppBaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_SAVE_CURRENT_POSTION = "KEY_SAVE_CURRENTPOSTION";
    private int mPosition;

    private final int mFragmentSize = 4;

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private WechatRadioGroup mWechartRadioGroup;

    private TextView mTextTitle;
    private LinearLayout mLinearAdd;

    private HomeFragment mHomeFragment;
    private CommunFragment mCommunFragment;
    private FindFragment mFindFragment;
    private MeFragment mFourFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mWechartRadioGroup = (WechatRadioGroup) findViewById(R.id.radiogroup);
        mWechartRadioGroup.setViewPager(mViewPager);

        mTextTitle = (TextView) findViewById(R.id.texttitle);
        mLinearAdd = (LinearLayout) findViewById(R.id.linearadd);
        mLinearAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.linearadd:
                break;

            default:
                break;
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MainActivity.this.mPosition = position;
            switch (position) {
                case 0: {
                    if (mHomeFragment == null) {
                        mHomeFragment = new HomeFragment();
                    }
                    return mHomeFragment;
                }
                case 1: {
                    if (mCommunFragment == null) {
                        mCommunFragment = new CommunFragment();
                    }
                    return mCommunFragment;
                }
                case 2: {
                    if (mFindFragment == null) {
                        mFindFragment = new FindFragment();
                    }
                    return mFindFragment;
                }
                case 3: {
                    if (mFourFragment == null) {
                        mFourFragment = new MeFragment();
                    }
                    return mFourFragment;
                }
                default:
                    break;
            }
            return mHomeFragment;
        }

        @Override
        public int getCount() {
            return mFragmentSize;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SAVE_CURRENT_POSTION, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(KEY_SAVE_CURRENT_POSTION);
            mViewPager.setCurrentItem(position);
            mWechartRadioGroup.setClickedViewChecked(position);
        }
    }

    private void setTitle(String title) {
        mTextTitle.setText(title);
    }
}
