package com.weixin.android.mefragment_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weixin.android.R;
import com.weixin.android.activity.AppBaseActivity;
import com.weixin.android.adapter.ImageAdapter;
import com.weixin.android.dialog.ImagePopupWin;
import com.weixin.android.mode.FolderBean;
import com.weixin.android.thread.RequestMediaIm;
import com.weixin.android.utils.FolderUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PotoActivity extends AppBaseActivity implements View.OnClickListener {

    private static boolean mIsOpen = false;
    private static int mPhotoMode = FolderUtil.IMAGE_MUTIL_MODE;

    private static final int DATA_LOADED = 0x11000000;
    private static final int SELECT_TOP = 0x22000000;
    private static final int IMAGE_FRIST = -1;

    private final int mSwitch_ = -1;
    private final int mSwitch_0 = 0;

    private String mCurrentFileName;
    private int mCurrentPostion;

    public static void openPhotoActivity(Activity act, int imageSelectMode) {
        if (mIsOpen || act == null) return;
        if (imageSelectMode != FolderUtil.IMAGE_MUTIL_MODE && imageSelectMode != FolderUtil.IMAGE_SINGLE_MODE)
            return;
        mIsOpen = true;
        Intent intent = new Intent(act, PotoActivity.class);
        mPhotoMode = imageSelectMode;
        act.startActivityForResult(intent, FolderUtil.IMAGE_ACTIVITY_RESULT);
    }

    private String mCurrentDirPath = "";
    private List<String> mImgs = new ArrayList<>();
    private List<FolderBean> mFolderBeans = null;

    private ImageAdapter mImageAdapter = null;
    private GridView mGridView = null;

    private RelativeLayout mRela_Bottom;
    private TextView mText_PackName;
    private TextView mText_Count;
    private TextView mText_Config;
    private LinearLayout mLinear_Back;

    private HashSet<Integer> mSet = new HashSet<>();

    private ImagePopupWin mImagePopupWin;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int id = msg.what;
            switch (id) {
                case DATA_LOADED:
                    RequestMediaIm.MediaData data = (RequestMediaIm.MediaData) msg.obj;
                    mFolderBeans = data.mFolderBeans;
                    mCurrentDirPath = data.mDir;
                    setAllImageData();
                    break;

                case SELECT_TOP:
                    mGridView.setSelection(0);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
        init();
        checkPermission();
        requestData();
        initEvent();
    }

    private void initEvent() {
        mRela_Bottom.setOnClickListener(this);
        mLinear_Back.setOnClickListener(this);
        mText_Config.setOnClickListener(this);
    }

    private void init() {
        mRela_Bottom = (RelativeLayout) findViewById(R.id.rela_bottom);
        mText_PackName = (TextView) findViewById(R.id.text_packagename);
        mText_Count = (TextView) findViewById(R.id.text_count);
        mGridView = (GridView) findViewById(R.id.gridview_photowall);
        mLinear_Back = (LinearLayout) findViewById(R.id.linear_back);
        mText_Config = (TextView) findViewById(R.id.text_config);
        mText_Config.setVisibility(View.GONE);
        photoMode();
        setAdapter();
    }

    private void setAdapter() {
        mImageAdapter = new ImageAdapter(this, mPhotoMode);
        mImageAdapter.setonImageItemSelecter(mOnImageItemSelecter);
        mGridView.setAdapter(mImageAdapter);
    }

    private ImageAdapter.OnImageItemSelecter mOnImageItemSelecter = new ImageAdapter.OnImageItemSelecter() {
        @Override
        public boolean onImageSelecterCallback(int position) {
            if (position == IMAGE_FRIST) {
                return false;
            }
            switch (mPhotoMode) {
                case FolderUtil.IMAGE_MUTIL_MODE:
                    getModeMutilImage(position);
                    finish();
                    break;

                case FolderUtil.IMAGE_SINGLE_MODE:
                    return getModeSingleImage(position);

                default:
                    break;
            }
            return false;
        }
    };

    private boolean getModeSingleImage(int position) {
        if (mSet.contains(position)) {
            mSet.remove(position);
            syncConfigBtn_();
            return false;
        } else {
            if (mSet.size() >= 9) {
                showToast(getString(R.string.max_select_images));
                return false;
            }
            mSet.add(position);
            syncConfigBtn_();
            return true;
        }
    }

    private void requestData() {
        if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            showToast(getString(R.string.sdcard_no_use));
            return;
        }
        new RequestMediaIm(this, mOnMediaLoadReault).start();
    }

    private RequestMediaIm.OnMediaLoadReault mOnMediaLoadReault = new RequestMediaIm.OnMediaLoadReault() {

        @Override
        public void onMediaCallback(RequestMediaIm.MediaData mediaData) {
            if (mediaData == null) {
                showToast(getString(R.string.no_getimages));
                return;
            }
            Message msg = new Message();
            msg.what = DATA_LOADED;
            msg.obj = mediaData;
            mHandler.sendMessage(msg);
        }
    };

    private void setAllImageData() {
        if (mCurrentDirPath != null && mCurrentDirPath.equals(getString(R.string.all_images)))
            return;
        mCurrentDirPath = getString(R.string.all_images);
        int totalCount = 0;
        mImgs.clear();
        for (FolderBean folderBean : mFolderBeans) {
            File file = new File(folderBean.getDir());
            String path = file.getAbsolutePath();
            List<String> folders = imageFilter(file);
            for (String itemImage : folders) {
                mImgs.add(path + "/" + itemImage);
                totalCount++;
            }
        }
        notifyState(getString(R.string.all_images), String.valueOf(totalCount));
    }

    private List<String> imageFilter(final File file) {
        return Arrays.asList(file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".png") || filename.endsWith(".jpeg") || filename.endsWith(".jpg")) {
                    return true;
                }
                return false;
            }
        }));
    }

    private void switchDir(int position) {
        FolderBean folderBean = mFolderBeans.get(position);
        String dir = folderBean.getDir();
        if (dir.equals(mCurrentDirPath)) {
            return;
        }
        mHandler.sendEmptyMessage(SELECT_TOP);
        mImgs.clear();
        mCurrentDirPath = dir;
        File file = new File(dir);
        String path = file.getAbsolutePath();
        List<String> images = imageFilter(file);
        for (String itemImage : images) {
            mImgs.add(path + "/" + itemImage);
        }
        String foldername = folderBean.getName();
        notifyState(foldername.substring(1, foldername.length()), String.valueOf(folderBean.getCount()));
    }

    private void notifyState(String packName, String imageCount) {
        this.mCurrentFileName = packName;
        mImageAdapter.setDataList(mImgs);
        mText_PackName.setText(packName);
        mText_Count.setText(imageCount + getString(R.string.za));
        mImageAdapter.notifyDataSetChanged();
    }

    private void showPopuWindow() {
        mImagePopupWin = new ImagePopupWin(this);
        mImagePopupWin.setData(mFolderBeans, new ImagePopupWin.OnImageCallBack() {

            @Override
            public void onImageCallResult(int position, FolderBean folderBean) {
                setAlpha(1.0f);
                if (position != mSwitch_) {
                    PotoActivity.this.mCurrentPostion = position;
                }
                switch (position) {
                    case mSwitch_:
                        break;

                    case mSwitch_0:
                        setAllImageData();
                        break;

                    default:
                        switchDir(position - 1);
                        break;
                }
            }
        }, mCurrentFileName, mCurrentPostion);
        int[] location = new int[2];
        mRela_Bottom.getLocationInWindow(location);
        int popupWidth = mImagePopupWin.getWidth();
        int popupHeight = mImagePopupWin.getHeight();
        mImagePopupWin.showAtLocation(mRela_Bottom, Gravity.NO_GRAVITY, (location[0] + mRela_Bottom.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        setAlpha(0.4f);
    }

    private void setAlpha(float alpha) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void startCameraCallResult() {
        File android = new File(FolderUtil.IMAGE_SAVE_PATH);
        if (!android.exists()) {
            android.mkdir();
        } else {
            FolderUtil.clearFolder(new File(FolderUtil.IMAGE_SAVE_PATH));
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FolderUtil.IMAGE_SAVE_PATH, FolderUtil.get().setImageName())));
        startActivityForResult(intent, FolderUtil.IMAGE_ACTIVITY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FolderUtil.IMAGE_ACTIVITY_RESULT) {
            setResult(FolderUtil.IMAGE_ACTIVITY_RESULT, getIntent_().putExtra(FolderUtil.IMAGE_SUCESS_RESULT, FolderUtil.get().getImageName()));
            finish();
        }
    }

    public Intent getIntent_() {
        return new Intent();
    }

    public ArrayList<CharSequence> getList() {
        return new ArrayList<>(9);
    }

    public void photoMode() {
        FolderUtil.get().setImageSelectMode(mPhotoMode);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.CAMERA}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void syncConfigBtn_() {
        mText_Config.setVisibility(mSet.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getModeMutilImage(int position) {
        setResult(FolderUtil.IMAGE_SINGLE_MODE, getIntent_().putExtra(FolderUtil.IMAGE_SUCESS_RESULT, mImgs.get(position)));
    }

    private void imageList() {
        int length = mImgs.size();
        ArrayList<CharSequence> list = getList();
        for (int i = 0; i < length; i++) {
            if (mSet.contains(i)) {
                list.add(mImgs.get(i));
            }
        }
        list.trimToSize();
        if (list.size() <= 0) {
            showToast(getString(R.string.no_selecte_images));
            return;
        }
        setResult(FolderUtil.IMAGE_MUTIL_MODE, getIntent_().putCharSequenceArrayListExtra(FolderUtil.IMAGE_SUCESS_RESULT, list));
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rela_bottom:
                showPopuWindow();
                break;

            case R.id.linear_back:
                finish();
                break;

            case R.id.text_config:
                imageList();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mImagePopupWin != null && mImagePopupWin.isShowing()) {
            mImagePopupWin.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        try {
            mIsOpen = false;
            mPhotoMode = 0;
            mSet.clear();
            mSet = null;
            if (mFolderBeans != null) {
                mFolderBeans.clear();
                mFolderBeans = null;
            }
            if (mImgs != null) {
                mImgs = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
