package com.example.lctripsteward.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(final Context context, final String message){
        Activity activity = (Activity)context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
