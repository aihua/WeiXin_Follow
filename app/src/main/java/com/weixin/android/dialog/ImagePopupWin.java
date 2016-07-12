package com.weixin.android.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.weixin.android.R;
import com.weixin.android.adapter.PopupAdapter;
import com.weixin.android.mode.FolderBean;
import com.weixin.android.utils.ScreenUtil;

import java.util.List;

/**
 * Created by sujizhong on 16/6/21.
 */
public class ImagePopupWin extends PopupWindow {

    private ListView mListView;
    private PopupAdapter mPopupAdapter;

    private OnImageCallBack mOnImageCallBack;

    public ImagePopupWin(Context cxt) {
        LayoutInflater layoutInflater = LayoutInflater.from(cxt);
        View rootView = layoutInflater.inflate(R.layout.windowphoto, null);
        mListView = (ListView) rootView.findViewById(R.id.photo_list);
        mPopupAdapter = new PopupAdapter(cxt) {
            @Override
            public void onItemClickListance(int position, FolderBean bean) {
                if (mOnImageCallBack != null) {
                    dismiss();
                    mOnImageCallBack.onImageCallResult(position, bean);
                }
            }
        };
        mListView.setAdapter(mPopupAdapter);
        setContentView(rootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ScreenUtil.getHeight(cxt) * 7 / 10);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.dialog_botin_botout);
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void setData(List<FolderBean> folderBeans, OnImageCallBack onImageCallBack, String fileName, int currentPosition) {
        this.mOnImageCallBack = onImageCallBack;
        mPopupAdapter.setData(folderBeans, fileName);
        mPopupAdapter.notifyDataSetChanged();
        mListView.setSelection(currentPosition);
    }

    public interface OnImageCallBack {
        void onImageCallResult(int position, FolderBean folderBean);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnImageCallBack != null) {
            mOnImageCallBack.onImageCallResult(-1, null);
        }
    }
}
