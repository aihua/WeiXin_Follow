package com.weixin.android.currencyadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sujizhong on 16/7/3.
 */
public abstract class CommonBaseAdapter extends BaseAdapter {

    private Context mCxt;
    private List<Bean> mDatas;
    private int mItemLayoutId;

    public CommonBaseAdapter(Context cxt, int itemLayoutId, List<Bean> datas) {
        this.mCxt = cxt;
        this.mDatas = datas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mCxt, convertView, parent, mItemLayoutId, position);
        viewCallback(viewHolder, mDatas == null ? null : mDatas.get(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void viewCallback(ViewHolder viewHolder, Bean itemBean, int position);

}
