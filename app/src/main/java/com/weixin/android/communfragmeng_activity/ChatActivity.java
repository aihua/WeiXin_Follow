package com.weixin.android.communfragmeng_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.weixin.android.R;
import com.weixin.android.activity.AppBaseActivity;
import com.weixin.android.coutomview.AudioRecorderButton;

/**
 * Created by sujizhong on 16/7/13.
 */
public class ChatActivity extends AppBaseActivity {

    private ListView mListView;

    private AudioRecorderButton mAudioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        TextView title = (TextView) findViewById(R.id.back_title_layout_title);
        title.setText("聊天");

        LinearLayout back = (LinearLayout) findViewById(R.id.linear_back);
        back.setOnClickListener(mOnClickListener);

        TextView add = (TextView) findViewById(R.id.text_config);
        add.setText("+");
        add.setOnClickListener(mOnClickListener);

        mListView = (ListView) findViewById(R.id.chatactivity_listview);
        mListView.setOnItemClickListener(mOnItemClickListener);

        mAudioButton = (AudioRecorderButton) findViewById(R.id.buttonautiorecorder);
        mAudioButton.setOnClickListener(mOnClickListener);

    }

    private ListView.OnItemClickListener mOnItemClickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.linear_back:
                    ChatActivity.this.finish();
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
