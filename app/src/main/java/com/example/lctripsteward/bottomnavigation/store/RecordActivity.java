package com.example.lctripsteward.bottomnavigation.store;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.StatusBarUtils;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        StatusBarUtils.setStatusBarColor(RecordActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(RecordActivity.this, true, true);  //状态栏字体颜色-黑
    }

    public void recordBack(View view){
        finish();
    }
}
