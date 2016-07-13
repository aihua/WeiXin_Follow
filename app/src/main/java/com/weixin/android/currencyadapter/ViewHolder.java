package com.weixin.android.currencyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sujizhong on 16/7/3.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context cxt, ViewGroup parent, int layoutId, int postion) {
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(cxt).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
        this.mPosition = postion;
    }

    public static ViewHolder get(Context cxt, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(cxt, parent, layoutId, position);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
