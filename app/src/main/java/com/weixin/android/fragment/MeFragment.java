package com.weixin.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weixin.android.R;
import com.weixin.android.activity.MainActivity;
import com.weixin.android.mefragment_activity.PotoActivity;
import com.weixin.android.utils.FolderUtil;
import com.weixin.android.utils.ImageLoader;

/**
 * Created by sujizhong on 16/7/11.
 */
public class MeFragment extends AppBaseFragment implements View.OnClickListener {

    private static final String TAG = MeFragment.class.getSimpleName();

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
        RelativeLayout realPhoto = (RelativeLayout) v.findViewById(R.id.rela_photo);
        realPhoto.setOnClickListener(this);

        LinearLayout linearPictures = (LinearLayout) v.findViewById(R.id.rela_pictures);
        linearPictures.setOnClickListener(this);

        LinearLayout linearFover = (LinearLayout) v.findViewById(R.id.rela_favor);
        linearFover.setOnClickListener(this);

        LinearLayout linearMoneyPackage = (LinearLayout) v.findViewById(R.id.rela_moneypackage);
        linearMoneyPackage.setOnClickListener(this);

        LinearLayout linearCardPackage = (LinearLayout) v.findViewById(R.id.rela_cardpackage);
        linearCardPackage.setOnClickListener(this);

        LinearLayout linearBrow = (LinearLayout) v.findViewById(R.id.rela_brow);
        linearBrow.setOnClickListener(this);

        LinearLayout linearSetting = (LinearLayout) v.findViewById(R.id.rela_setting);
        linearSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rela_photo:
                PotoActivity.openPhotoActivity((MainActivity)getContext(), FolderUtil.IMAGE_MUTIL_MODE);
                break;

            case R.id.rela_pictures:
                break;

            case R.id.rela_favor:
                break;

            case R.id.rela_moneypackage:
                break;

            case R.id.rela_cardpackage:
                break;

            case R.id.rela_brow:
                break;

            case R.id.rela_setting:
                break;

            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FolderUtil.IMAGE_ACTIVITY_RESULT && data != null) {
            String imageUri = data.getStringExtra(FolderUtil.IMAGE_SUCESS_RESULT);
            ImageLoader.getInstance(1, ImageLoader.Type.FIFO).loadImage(imageUri, (ImageView) getView().findViewById(R.id.image));
        }
    }
}
