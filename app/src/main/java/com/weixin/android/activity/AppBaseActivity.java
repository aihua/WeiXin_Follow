package com.weixin.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sujizhong on 16/7/11.
 * 所有Activity必须继承自该Activity
 */
public class AppBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
