package com.weixin.android.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weixin.android.R;

/**
 * Created by sujizhong on 16/7/14.
 */
public class DialogUtil {

    private static DialogUtil mDialogUtil = null;

    private Dialog mDialog = null;
    private TextView mTextHint;
    private ImageView mImageCancle;
    private ImageView mImageAnim;
    private LinearLayout mLinear;

    private AnimationDrawable mDrawable;

    private Context mCxt;

    private DialogUtil() {
    }

    public static DialogUtil getInstance() {
        if (mDialogUtil == null) {
            mDialogUtil = new DialogUtil();
        }
        return mDialogUtil;
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public Dialog getDialog(Context cxt, int layoutId, int width, int height) {
        if (mDialog != null && mDialog.isShowing()) {
            return mDialog;
        }
        this.mCxt = cxt;
        mDialog = new Dialog(cxt);
        mDialog.show();
        int dialogwidth = ScreenUtil.dip2px(cxt, width);
        int dialogHeight = ScreenUtil.dip2px(cxt, height);
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View layout = inflater.inflate(layoutId, null, false);
        Window windlow = mDialog.getWindow();
        windlow.setGravity(Gravity.CENTER);
        windlow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(layout, new ViewGroup.LayoutParams(dialogwidth, dialogHeight));
        mTextHint = (TextView) layout.findViewById(R.id.text_hint);
        mImageCancle = (ImageView) layout.findViewById(R.id.image_cancle);
        mImageAnim = (ImageView) layout.findViewById(R.id.image_anim);
        mLinear = (LinearLayout) layout.findViewById(R.id.linear_recoder);
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    public void switchDialogState(boolean state) {
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }
        mImageCancle.setImageResource(R.drawable.cancel);
        if (state) {
            mTextHint.setText(mCxt.getString(R.string.sleekhead_cancle));
            mLinear.setVisibility(View.VISIBLE);
            startAnimation();
            mImageCancle.setVisibility(View.GONE);
        } else {
            mTextHint.setText(mCxt.getString(R.string.undohead_cancle));
            mImageCancle.setVisibility(View.VISIBLE);
            mLinear.setVisibility(View.GONE);
            stopAnimation();
        }
    }

    public void switchDialogTimeShort() {
        mTextHint.setText(mCxt.getString(R.string.recodering_time_shorter));
        mImageCancle.setVisibility(View.VISIBLE);
        mImageCancle.setImageResource(R.drawable.voice_to_short);
        mLinear.setVisibility(View.GONE);
    }

    public void startAnimation() {
        mDrawable = (AnimationDrawable) mImageAnim.getDrawable();
        mDrawable.start();
    }

    public void stopAnimation() {
        if (mDrawable != null) {
            mDrawable.stop();
        }
    }
}
