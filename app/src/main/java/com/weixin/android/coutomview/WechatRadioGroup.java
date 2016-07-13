package com.weixin.android.coutomview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by sjz on 2016/5/10.
 */
public class WechatRadioGroup extends RadioGroup implements ViewPager.OnPageChangeListener {


//    private static final String KEY_SAVE_STATE = "KEY_SAVE_STATE";
//    private static final String KEY_SYSTEM_STATE = "KEY_SYSTEM_STATE";

    private ViewPager mViewPager;
//    private int mCurrentPosition;

    public WechatRadioGroup(Context context) {
        super(context);
    }

    public WechatRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int position = i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClickedViewChecked(position);
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(position, false);
                    }
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateGradient(position, positionOffset);
    }

    private void updateGradient(int position, float offset) {
        if (offset > 0) {
            ((WechatRadioButton) getChildAt(position)).updateAlpha(255 * (1 - offset));
            ((WechatRadioButton) getChildAt(position + 1)).updateAlpha(255 * offset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setSelectedViewChecked(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setSelectedViewChecked(int position) {
        int childCound = getChildCount();
        for (int i = 0; i < childCound; i++) {
            ((WechatRadioButton) getChildAt(i)).setChecked(false);
        }
        ((WechatRadioButton) getChildAt(position)).setChecked(true);
//        mCurrentPosition = position;
    }

    public void setClickedViewChecked(int position) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((WechatRadioButton) getChildAt(i)).setRadioButtonChecked(false);
        }
        ((WechatRadioButton) getChildAt(position)).setRadioButtonChecked(true);
//        mCurrentPosition = position;
    }

//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(KEY_SYSTEM_STATE, super.onSaveInstanceState());
//        bundle.putInt(KEY_SAVE_STATE, mCurrentPosition);
//        return bundle;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        if (state instanceof Bundle) {
//            Bundle bundle = (Bundle) state;
//            mCurrentPosition = bundle.getInt(KEY_SAVE_STATE);
//            super.onRestoreInstanceState(bundle.getParcelable(KEY_SYSTEM_STATE));
//        }
//        setClickedViewChecked(mCurrentPosition);
//    }
}
