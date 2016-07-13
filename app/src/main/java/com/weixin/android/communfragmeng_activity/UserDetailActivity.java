package com.weixin.android.communfragmeng_activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weixin.android.R;
import com.weixin.android.activity.AppBaseActivity;

/**
 * Created by sujizhong on 16/7/13.
 */
public class UserDetailActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_userdetail);
        init();
    }

    private void init(){
        TextView title = (TextView) findViewById(R.id.back_title_layout_title);
        title.setText("详细资料");

        LinearLayout back = (LinearLayout) findViewById(R.id.linear_back);
        back.setOnClickListener(mOnClickListener);

        TextView add = (TextView) findViewById(R.id.text_config);
        add.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch(id){
                case R.id.linear_back:
                    UserDetailActivity.this.finish();
                    break;

                case R.id.text_config:
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
