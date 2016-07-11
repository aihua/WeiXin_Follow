package com.weixin.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.weixin.android.R;

/**
 * Created by sujizhong on 16/7/11.
 */
public class FourFragment extends AppBaseFragment implements View.OnClickListener {

    private RelativeLayout mRelaPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_four, null, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        View v = getView();
        mRelaPhoto = (RelativeLayout) v.findViewById(R.id.rela_photo);
        mRelaPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rela_photo:
                break;

            default:
                break;

        }

    }
}
