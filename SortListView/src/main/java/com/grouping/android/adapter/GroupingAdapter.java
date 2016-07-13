package com.grouping.android.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.sortlistview.R;
import com.grouping.android.mode.GroupingModel;

import java.util.List;

public class GroupingAdapter extends BaseAdapter implements SectionIndexer {

    private List<GroupingModel> mList;
    private Context mContext;

    public GroupingAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<GroupingModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public Object getItem(int position) {
        return mList == null ? "" : mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final GroupingModel groupingModel = mList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.clientmanageractivity2_item, null);
            viewHolder.mText_LName = (TextView) view.findViewById(R.id.text_name);
            viewHolder.mText_Index = (TextView) view.findViewById(R.id.text_group_title);
            viewHolder.mText_Fname = (TextView) view.findViewById(R.id.text_fristname);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            viewHolder.mText_Index.setVisibility(View.VISIBLE);
            viewHolder.mText_Index.setText(groupingModel.textGrouptitle);
        } else {
            viewHolder.mText_Index.setVisibility(View.GONE);
        }
        String name = groupingModel.name;
        viewHolder.mText_LName.setText(TextUtils.isEmpty(name) ? "" : name);
        viewHolder.mText_Fname.setText(name.substring(0, 1));
        return view;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        int size = getCount();
        for (int i = 0; i < size; i++) {
            String sortStr = mList.get(i).textGrouptitle;
            char firstChar = sortStr.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).textGrouptitle.charAt(0);
    }

    final static class ViewHolder {
        TextView mText_Index;
        TextView mText_Fname;
        TextView mText_LName;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}