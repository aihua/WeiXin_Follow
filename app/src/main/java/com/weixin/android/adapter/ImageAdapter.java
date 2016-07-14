package com.weixin.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.weixin.android.R;
import com.weixin.android.utils.FolderUtil;
import com.weixin.android.utils.ImageLoader;

import java.util.HashSet;
import java.util.List;

/**
 * Created by sujizhong on 16/3/20.
 */
public class ImageAdapter extends BaseAdapter {

    private List<String> mImageLists = null;
    private LayoutInflater mInflater;

    private int mPhotoMode = 0;
    private int mThreadCount = 2;

    private HashSet<Integer> mSet = new HashSet<>();

    private OnImageItemSelecter mOnImageItemSelecter;

    private class ImageHolder {
        ImageView mImage;
        ImageView mImage_Btn;
    }

    public ImageAdapter(Context cxt, int photoMode) {
        this.mPhotoMode = photoMode;
        mInflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return mImageLists == null ? 0 : mImageLists.size();
    }

    public void setDataList(List<String> imageList) {
        this.mImageLists = imageList;
    }

    @Override
    public Object getItem(int position) {
        return mImageLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageHolder imageHolder;
        if (convertView == null) {
            imageHolder = new ImageHolder();
            convertView = mInflater.inflate(R.layout.imageholder, parent, false);
            imageHolder.mImage = (ImageView) convertView.findViewById(R.id.image);
            imageHolder.mImage_Btn = (ImageView) convertView.findViewById(R.id.image_btn);
            convertView.setTag(imageHolder);
        } else {
            imageHolder = (ImageHolder) convertView.getTag();
        }
        imageHolder.mImage_Btn.setVisibility(mPhotoMode == FolderUtil.IMAGE_MUTIL_MODE ? View.GONE : View.VISIBLE);
        imageHolder.mImage_Btn.setImageResource(mSet.contains(position) ? R.drawable.pictures_selected : R.drawable.picture_unselected);
        imageHolder.mImage.setImageDrawable(null);
        String itemBean = mImageLists.get(position);
        ImageLoader.getInstance(mThreadCount, ImageLoader.Type.LIFO).loadImage(itemBean, imageHolder.mImage);
        imageHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelecter = mOnImageItemSelecter.onImageSelecterCallback(position);
                imageHolder.mImage_Btn.setImageResource(isSelecter ? R.drawable.pictures_selected : R.drawable.picture_unselected);
                imageMode(position);
            }
        });
        return convertView;
    }

    private void imageMode(int position) {
        if (mSet.contains(position)) {
            mSet.remove(position);
        } else {
            mSet.add(position);
        }
    }

    public void setonImageItemSelecter(OnImageItemSelecter onImageItemSelecter) {
        this.mOnImageItemSelecter = onImageItemSelecter;
    }

    public interface OnImageItemSelecter {
        boolean onImageSelecterCallback(int position);
    }

}
