package com.grouping.android.couview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.sortlistview.R;

import java.util.Random;

public class SideBar extends View {

    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;

    public static String[] mChar = {"A", "B", "C", "D", "E", "F",

            "G", "H", "I", "J", "K", "L",

            "M", "N", "O", "P", "Q", "R",

            "S", "T", "U", "V", "W", "X",

            "Y", "Z", "#"};

    private static int[] mColor = {R.color.black, R.color.blue, R.color.gray, R.color.green, R.color.ltgray, R.color.red, R.color.white,};

    private int mChoose = -1;
    private Paint mPpaint = new Paint();
    private Random mRandom = new Random();
    private TextView mTextDialog;
    private Animation mAnimation;
    private Context mContext;

    public void setTextView(TextView mTextDialog) {

        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.layoutanim);
        this.mTextDialog = mTextDialog;
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SideBar(Context context) {
        super(context);
        this.mContext = context;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / mChar.length;

        for (int i = 0; i < mChar.length; i++) {
//			mPpaint.setColor(Color.rgb(33, 65, 98));
            mPpaint.setColor(Color.parseColor("#999999"));
            // paint.setColor(Color.WHITE);
            mPpaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPpaint.setAntiAlias(true);
            mPpaint.setTextSize(25);
            if (i == mChoose) {
                mPpaint.setColor(getResources().getColor(R.color.sklyblue));
                mPpaint.setFakeBoldText(true);
            }
            float xPos = width / 2 - mPpaint.measureText(mChar[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mChar[i], xPos, yPos, mPpaint);
            mPpaint.reset();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = mChoose;
        final OnTouchingLetterChangedListener listener = mOnTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * mChar.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                mChoose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < mChar.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(mChar[c]);
                        }

                        if (mTextDialog != null) {
                            mTextDialog.setText(mChar[c]);
                            mTextDialog.setTextColor(mContext.getResources().getColor(mColor[mRandom.nextInt(6)]));
                            mTextDialog.setVisibility(View.VISIBLE);
                            mTextDialog.startAnimation(mAnimation);
                        }
                        mChoose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        mOnTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}