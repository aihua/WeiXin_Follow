package com.weixin.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weixin.android.R;
import com.weixin.android.activity.MainActivity;
import com.weixin.android.communfragmeng_activity.ChatActivity;
import com.weixin.android.currencyadapter.Bean;
import com.weixin.android.currencyadapter.CommonBaseAdapter;
import com.weixin.android.currencyadapter.ViewHolder;
import com.weixin.android.mode.HomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujizhong on 16/7/11.
 */
public class HomeFragment extends AppBaseFragment {

    private ListView mListView;
    private ChatAdapter mChatAdapter;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, null, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setDatas();
    }

    private void init() {
        View v = getView();
        mListView = (ListView) v.findViewById(R.id.listview);
        mListView.setOnItemClickListener(mOnItemClickListener);
        if (Build.VERSION.SDK_INT >= 9) {
            mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        ((MainActivity)getActivity()).setTabOneRemind("10", true);
    }

    private ListView.OnItemClickListener mOnItemClickListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            startActivity(intent);
        }
    };

    private void setDatas() {
        List<Bean> datas = new ArrayList<>();
        String[] name = getContext().getResources().getStringArray(R.array.date);
        for (int i = 0; i < 10; i++) {
            HomeBean homeBean = new HomeBean();
            homeBean.title = name[i];
            homeBean.desc = "相关信息信息";
            datas.add(homeBean);
        }
        mChatAdapter = new ChatAdapter(getContext(), R.layout.homefragment_item, datas);
        mListView.setAdapter(mChatAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private class ChatAdapter extends CommonBaseAdapter {

        public ChatAdapter(Context cxt, int itemLayoutId, List<Bean> datas) {
            super(cxt, itemLayoutId, datas);
        }

        @Override
        public void viewCallback(ViewHolder viewHolder, Bean itemBean, int position) {

        }
    }
}
