package com.weixin.android.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by sujizhong on 16/7/8.
 */
public class ImageHelper {

    public static Bitmap handlerImageEffect(Bitmap bitmap, float hue, float stauration, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix humMatrix = new ColorMatrix();
        humMatrix.setRotate(0, 127);
        humMatrix.setRotate(1, 127);
        humMatrix.setRotate(2, 127);

        ColorMatrix staurationMatrix = new ColorMatrix();
        staurationMatrix.setSaturation(stauration);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(127, 127, 127, 1);

        //融合到一起
        ColorMatrix imageColorMatrix = new ColorMatrix();
        imageColorMatrix.postConcat(humMatrix);
        imageColorMatrix.postConcat(staurationMatrix);
        imageColorMatrix.postConcat(lumMatrix);

        //通过ColorMatrixColorFilter
        paint.setColorFilter(new ColorMatrixColorFilter(imageColorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }
}
