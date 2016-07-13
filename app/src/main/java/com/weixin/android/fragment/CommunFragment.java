package com.weixin.android.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grouping.android.adapter.GroupingAdapter;
import com.grouping.android.couview.SideBar;
import com.grouping.android.mode.GroupingModel;
import com.grouping.android.utils.CharacterParser;
import com.grouping.android.utils.ClientComparator;
import com.weixin.android.R;
import com.weixin.android.communfragmeng_activity.UserDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sujizhong on 16/7/11.
 */
public class CommunFragment extends AppBaseFragment {

    private static final String TAG = CommunFragment.class.getSimpleName();

    private ListView mListView;
    private SideBar mSideBar;

    private GroupingAdapter mGroupingAdapter;
    private ClientComparator mClientComparator;
    private CharacterParser mCharacterParser;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListView.setSelection(msg.what);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, null, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        View v = getView();
        mListView = (ListView) v.findViewById(R.id.fragment_second_listview);
        mListView.setOnItemClickListener(mOnItemClickListener);
        if (Build.VERSION.SDK_INT >= 9) {
            mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        mSideBar = (SideBar) v.findViewById(R.id.sidebar);
        mSideBar.setOnTouchingLetterChangedListener(mOnTouchingLetterChangedListener);

        mGroupingAdapter = new GroupingAdapter(getContext());
        mListView.setAdapter(mGroupingAdapter);

        mClientComparator = new ClientComparator();
        mCharacterParser = CharacterParser.getInstance();

        setDatas();
    }

    private ListView.OnItemClickListener mOnItemClickListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), UserDetailActivity.class);
            startActivity(intent);
        }
    };

    private SideBar.OnTouchingLetterChangedListener mOnTouchingLetterChangedListener = new SideBar.OnTouchingLetterChangedListener() {
        @Override
        public void onTouchingLetterChanged(String s) {
            int position = mGroupingAdapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                mHandler.sendEmptyMessage(position);
            }
        }
    };

    private void setDatas() {
        List<GroupingModel> groupingModels = new ArrayList<>();
        String[] name = getContext().getResources().getStringArray(R.array.date);
        int size = name.length;
        for (int i = 0; i < size; i++) {
            GroupingModel groupingModel = new GroupingModel();
            String fName = name[i];
            groupingModel.name = fName;
            String pinyin = mCharacterParser.getFristUpperChar(fName);
            groupingModel.textGrouptitle = pinyin;
            groupingModels.add(groupingModel);
        }
        Collections.sort(groupingModels, mClientComparator);
        mGroupingAdapter.setData(groupingModels);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
