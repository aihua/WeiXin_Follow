package com.weixin.android.coutomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.weixin.android.R;

/**
 * Created by sujizhong on 16/7/13.
 */
public class AudioRecorderButton extends Button {

    private static final int STATE_NOR = 0;
    private static final int STATE_RECORDERING = 1;
    private static final int STATE_WAIT_CANCLE = 2;

    private int mCurrentState = STATE_NOR;

    private boolean mIsRecordering = false;

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                changeCurrentState(STATE_RECORDERING);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mIsRecordering) {
                    if (wantToCanle(x, y)) {
                        changeCurrentState(STATE_WAIT_CANCLE);
                    } else {
                        changeCurrentState(STATE_RECORDERING);
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                getCurrentState();
                changeCurrentState(STATE_NOR);
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeCurrentState(int state) {
        if(mCurrentState != state){
            mCurrentState = state;
            switch (state){
                case STATE_NOR:
                    setText(R.string.but_recorder_nor);
                    break;

                case STATE_RECORDERING:
                    setText(R.string.but_recoredering);
                    if(mIsRecordering){

                    }
                    break;

                case STATE_WAIT_CANCLE:
                    setText(R.string.but_recorder_nor);
                    break;

                default:
                    break;
            }
        }
    }

    private boolean wantToCanle(int x, int y) {
        return false;
    }

    private void getCurrentState() {
        switch (mCurrentState) {
            case STATE_NOR:
                break;

            case STATE_RECORDERING:
                break;

            case STATE_WAIT_CANCLE:
                break;

            default:
                break;
        }
    }

    /**
     * 用于恢复一些状态
     * */
    private void reset(){
        mIsRecordering = false;
        mCurrentState = STATE_NOR;

    }
}
