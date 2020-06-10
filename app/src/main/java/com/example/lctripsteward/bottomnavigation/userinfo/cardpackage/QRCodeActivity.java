package com.example.lctripsteward.bottomnavigation.userinfo.cardpackage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class QRCodeActivity extends AppCompatActivity {

    private int couponId = -1;
    private ImageView imageQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ZXingLibrary.initDisplayOpinion(this);

        StatusBarUtils.setStatusBarColor(QRCodeActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(QRCodeActivity.this, true, true);  //状态栏字体颜色-黑


        imageQRCode = findViewById(R.id.imageQRCode);

        couponId = getIntent().getIntExtra("coupon_id", -1);
        if(couponId!=-1){
            Bitmap bitmap = CodeUtils.createImage(String.valueOf(couponId), 300, 300, null);
            imageQRCode.setImageBitmap(bitmap);
        }

        findViewById(R.id.buttonQRCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
