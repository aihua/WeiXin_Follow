package com.weixin.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weixin.android.R;
import com.weixin.android.mode.FolderBean;
import com.weixin.android.utils.ImageLoader;

import java.util.List;


/**
 * Created by sujizhong on 16/3/29.
 */
public abstract class PopupAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FolderBean> mFolderBeans;

    private final int mAdapterIndex = 0;
    private final int mAdapterIndex_ = 1;

    private String mCurrentFileName;
    private Context mCxt;

    public PopupAdapter(Context cxt) {
        this.mCxt = cxt;
        mInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<FolderBean> folderBeans, String fileName) {
        mFolderBeans = folderBeans;
        this.mCurrentFileName = fileName;
    }

    private class ImageHolder {
        ImageView mImage_Frist;
        TextView mText_Title;
        TextView mText_Count;
        ImageView mImage_Current;
    }

    @Override
    public int getCount() {
        if (mFolderBeans == null) return 0;
        return mFolderBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mFolderBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageHolder imageHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.window_photo_item, parent, false);
            imageHolder = new ImageHolder();
            imageHolder.mImage_Frist = (ImageView) convertView.findViewById(R.id.currimage);
            imageHolder.mText_Title = (TextView) convertView.findViewById(R.id.text_photo_title);
            imageHolder.mText_Count = (TextView) convertView.findViewById(R.id.text_photo_count);
            imageHolder.mImage_Current = (ImageView) convertView.findViewById(R.id.image_current);
            convertView.setTag(imageHolder);
        } else {
            imageHolder = (ImageHolder) convertView.getTag();
        }
        imageHolder.mImage_Frist.setImageDrawable(null);
        if (position == mAdapterIndex) {
            final FolderBean itemBean = mFolderBeans.get(position);
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(itemBean.getFirstImagePath(), imageHolder.mImage_Frist);
            imageHolder.mText_Title.setText(mCxt.getString(R.string.all_images));
            imageHolder.mText_Count.setText("");
            imageHolder.mImage_Current.setImageResource(mCurrentFileName.equals(mCxt.getString(R.string.all_images)) ? R.drawable.pictures_selected : R.drawable.picture_unselected);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListance(position, itemBean);
                }
            });
        } else {
            final FolderBean itemBean = mFolderBeans.get(position - mAdapterIndex_);
            ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(itemBean.getFirstImagePath(), imageHolder.mImage_Frist);
            String fileName = itemBean.getName();
            if (fileName != null) {
                String subFileName = fileName.substring(1, fileName.length());
                imageHolder.mText_Title.setText(fileName == null ? "" : subFileName);
                imageHolder.mImage_Current.setImageResource(mCurrentFileName.equals(subFileName) ? R.drawable.pictures_selected : R.drawable.picture_unselected);
            }
            int count = itemBean.getCount();
            imageHolder.mText_Count.setText(count == 0 ? "0张" : count + "张");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListance(position, itemBean);
                }
            });
        }
        return convertView;
    }

    public abstract void onItemClickListance(int position, FolderBean bean);

}
