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

    private TextView mTextTabOneRemind;
    private TextView mTextTabTwoRemind;
    private TextView mTextTabThreeRemind;
    private TextView mText_TabFourReming;

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
        toolBar();
        initNews();
    }

    private void toolBar() {
        mTextTitle = (TextView) findViewById(R.id.texttitle);
        mLinearAdd = (LinearLayout) findViewById(R.id.linearadd);
        mLinearAdd.setOnClickListener(this);
    }

    private void initNews() {
        mTextTabOneRemind = (TextView) findViewById(R.id.tabone_remind);
        mTextTabTwoRemind = (TextView) findViewById(R.id.tabtow_remind);
        mTextTabThreeRemind = (TextView) findViewById(R.id.tabthree_remind);
        mText_TabFourReming = (TextView) findViewById(R.id.tabfour_remind);
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
                    return createHomeFragment();
                }

                case 1: {
                    return createCommunFragment();
                }

                case 2: {
                    return createFindFragment();
                }

                case 3: {
                    return createMeFragment();
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

    private HomeFragment createHomeFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        return mHomeFragment;
    }

    private CommunFragment createCommunFragment() {
        if (mCommunFragment == null) {
            mCommunFragment = new CommunFragment();
        }
        return mCommunFragment;
    }

    private FindFragment createFindFragment() {
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
        }
        return mFindFragment;
    }

    private MeFragment createMeFragment() {
        if (mFourFragment == null) {
            mFourFragment = new MeFragment();
        }
        return mFourFragment;
    }

    private void setTitle(String title) {
        mTextTitle.setText(title);
    }

    private String remindNum(String msg) {
        return (Integer.parseInt(msg) > 99 ? "99" : msg);
    }

    public void setTabOneRemind(String msg, boolean isVisiable) {
        if (isVisiable) {
            mTextTabOneRemind.setText(remindNum(msg));
            mTextTabOneRemind.setVisibility(View.VISIBLE);
            return;
        }
        mTextTabOneRemind.setVisibility(View.GONE);
    }

    public void setTabTwoRemind(String msg, boolean isVisiable) {
        if (isVisiable) {
            mTextTabTwoRemind.setText(remindNum(msg));
            mTextTabTwoRemind.setVisibility(View.VISIBLE);
            return;
        }
        mTextTabTwoRemind.setVisibility(View.GONE);
    }

    public void setTabThreeRemind(String msg, boolean isVisiable) {
        if (isVisiable) {
            mTextTabThreeRemind.setText(remindNum(msg));
            mTextTabThreeRemind.setVisibility(View.VISIBLE);
            return;
        }
        mTextTabThreeRemind.setVisibility(View.GONE);
    }

    public void setTabFourRemind(String msg, boolean isVisiable) {
        if (isVisiable) {
            mText_TabFourReming.setText(remindNum(msg));
            mText_TabFourReming.setVisibility(View.VISIBLE);
            return;
        }
        mText_TabFourReming.setVisibility(View.GONE);
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

}
