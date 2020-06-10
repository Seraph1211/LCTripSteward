package com.example.lctripsteward.utils;


import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialogUtil {
    public static void showLoadingDialog(final Context context, String title, String msg, String toast){
        final ProgressDialog dialog = ProgressDialog.show(context, "正在领取...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(context, "领取成功！");

            }
        }).start();
    }
}
