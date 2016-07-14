package com.weixin.android.coutomview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import com.weixin.android.R;
import com.weixin.android.utils.DialogUtil;

/**
 * Created by sujizhong on 16/7/13.
 */
public class AudioRecorderButton extends Button {

    private static final String TAG = AudioRecorderButton.class.getSimpleName();

    private final int MSG_DISMISS = 0;
    private final int MSG_SHOW_RECORDER = 1;

    private final int DISTANCE_Y_CANCLE = 50;

    private static final int STATE_NOR = 0;
    private static final int STATE_RECORDERING = 1;
    private static final int STATE_WAIT_CANCLE = 2;

    private int mCurrentState = STATE_NOR;

    private boolean mIsRecordering = false;
    private Context mCxt;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case MSG_DISMISS:
                    dismissRecorderingDialog();
                    break;

                case MSG_SHOW_RECORDER:
                    showRecorderingDialog();
                    break;
                default:
                    break;
            }
        }
    };

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCxt = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsRecordering = true;
                changeCurrentState(STATE_RECORDERING);
                //开始计时  录音
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

            case MotionEvent.ACTION_UP:
                getCurrentState();
                changeCurrentState(STATE_NOR);
                reset();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeCurrentState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (state) {
                case STATE_NOR:
                    setText(R.string.but_recorder_nor);
                    break;

                case STATE_RECORDERING:
                    setText(R.string.but_recoredering);
                    recodering();
                    break;

                case STATE_WAIT_CANCLE:
                    setText(R.string.but_recorder_cancle);
                    switchRecorderingState(false);

                    break;

                default:
                    break;
            }
        }
    }

    private boolean wantToCanle(int x, int y) {
        if (x < 0 || x > getWidth()) {
            return true;
        }
        if (y < -DISTANCE_Y_CANCLE || y > getHeight() + DISTANCE_Y_CANCLE) {
            return true;
        }
        return false;
    }

    private void recodering() {
        if (mIsRecordering) {
            //正在录音
            showRecorderingDialog();
            switchRecorderingState(true);
//            Toast.makeText(getContext(), "正在录音", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentState() {
        dismissRecorderingDialog();
        switch (mCurrentState) {
            case STATE_NOR:
                break;

            case STATE_RECORDERING:
                //计算时常大小
//                showRecorderTimeShort();
                Toast.makeText(getContext(), "发送语音", Toast.LENGTH_SHORT).show();

                //发送语音
                break;

            case STATE_WAIT_CANCLE:
                Toast.makeText(getContext(), "cancle", Toast.LENGTH_SHORT).show();
                //cancle
                break;

            default:
                break;
        }
    }

    private void reset() {
        mIsRecordering = false;
        mCurrentState = STATE_NOR;
    }

    private void showRecorderingDialog() {
        DialogUtil.getInstance().getDialog(mCxt, R.layout.dialog_util, 200, 200);
    }

    private void switchRecorderingState(boolean bool) {
        DialogUtil.getInstance().switchDialogState(bool);
    }

    private void dismissRecorderingDialog() {
        DialogUtil.getInstance().dismiss();
    }

    private void showRecorderTimeShort() {
        showRecorderingDialog();
        DialogUtil.getInstance().switchDialogTimeShort();
        mHandler.sendEmptyMessageDelayed(MSG_DISMISS, 500);
    }
}
