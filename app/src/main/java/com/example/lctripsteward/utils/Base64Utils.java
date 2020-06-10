package com.example.lctripsteward.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Base64Utils {

    private static final String TAG = "Base64Utils";

    /**
     * 解密
     * @param base64String
     * @param imageView
     */
    public static void loadBase64Image(String base64String, ImageView imageView){
        Log.d(TAG, "loadBase64Image: ");
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String.split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //设置ImageView图片
        imageView.setImageBitmap(decodedByte);
    }

    /**
     * 解密
     * @param base64String
     * @param imageView
     */
    public static void loadBase64Image(String base64String, CircleImageView imageView){
        Log.d(TAG, "loadBase64Image: ");
        //将Base64编码字符串解码成Bitmap
        //byte[] decodedString = Base64.decode(base64String.split(",")[1], Base64.DEFAULT);
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //设置ImageView图片
        imageView.setImageBitmap(decodedByte);
    }

    /**
     * 加密
     * @param bitmap
     * @return
     */
    public static String encodeImageToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //读取图片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream); //参数如果为100那么就不压缩
        byte[] bytes = outputStream.toByteArray();
        String strImg = Base64.encodeToString(bytes, Base64.DEFAULT);
        return strImg;
    }

}
