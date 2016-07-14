package com.weixin.android.utils;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by sujizhong on 16/7/14.
 */
public class AudioManager {

    private boolean mIsPrepared = false;

    private static AudioManager mAudioManager = null;

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private AudioListener mAudioListener = null;


    private AudioManager() {

    }

    public static AudioManager getInstance() {
        if (mAudioManager == null) {
            mAudioManager = new AudioManager();
        }
        return mAudioManager;
    }

    public void prepareAudio() throws IllegalStateException {
        mIsPrepared = false;
        File file = new File(mDir);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = generateFileName();
        File filePath = new File(file, fileName);
        mCurrentFilePath = filePath.getAbsolutePath();
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setOutputFile(filePath.getAbsolutePath()); //设置输出文件
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //设置音频源为麦克风
        //十以上使用该参数 使用早期版本也没有问题
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        //设置音频编码
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mIsPrepared = true;
            if (mAudioListener != null) {
                mAudioListener.prearedFinish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * */
    private String generateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    public int voliceLevel(int levelMax) {
        if (mIsPrepared) {
            try {
                //1-32767
                return levelMax * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return 1;
            }
        }
        return 1;
    }

    public void release() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mIsPrepared = false;
        }
    }

    public void cancle() {
        if (mMediaRecorder != null) {
            release();
            if (mCurrentFilePath != null) {
                File file = new File(mCurrentFilePath);
                file.delete();
                mCurrentFilePath = null;
            }
        }
    }

    public interface AudioListener {
        void prearedFinish();
    }

    public void setAudioListener(AudioListener audioListener) {
        this.mAudioListener = audioListener;
    }

}
