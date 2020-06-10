package com.example.lctripsteward.bottomnavigation.home.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.ToastUtils;


/**
 * 用户选择地铁等公共交通出行方式的数据来源
 * 用户只能选择一个公共交通平台作为数据来源
 * 用户可以在不同的公共交通出行平台之间切换
 * 默认数据来源为长沙地铁
 */
public class SetDataSourceActivity extends AppCompatActivity {
    private Context context;

    private SwitchCompat switchChangSha;
    private SwitchCompat switchWuhan;
    private SwitchCompat switchXuZhou;
    private SwitchCompat switchChongQing;
    private SwitchCompat switchBeiJing;
    private SwitchCompat switchShenZhen;

    private SwitchCompat switchChosen;  //已被选中的switch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data_source);

        initView();

    }

    private void initView(){
        context = SetDataSourceActivity.this;

        switchChangSha = findViewById(R.id.switch_changsha);
        switchChosen = switchChangSha;
        switchChangSha.setChecked(true);
        switchChangSha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchChangSha;
                    showLoadingDialog();

                }
            }
        });

        switchWuhan = findViewById(R.id.switch_wuhan);
        switchWuhan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchWuhan;
                    showLoadingDialog();

                }
            }
        });


        switchXuZhou = findViewById(R.id.switch_xuzhou);
        switchXuZhou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchXuZhou;
                    showLoadingDialog();
                }
            }
        });

        switchChongQing = findViewById(R.id.switch_chongqing);
        switchChongQing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchChongQing;
                    showLoadingDialog();
                }
            }
        });

        switchBeiJing = findViewById(R.id.switch_beijing);
        switchBeiJing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchBeiJing;
                    showLoadingDialog();
                }
            }
        });


        switchShenZhen = findViewById(R.id.switch_shenzhen);
        switchShenZhen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchChosen.setChecked(false);
                    switchChosen = switchShenZhen;
                    showLoadingDialog();
                }
            }
        });
    }

    private void showLoadingDialog(){
        final ProgressDialog dialog = ProgressDialog.show(context, "提示", "数据同步中,请稍后..."
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(context, "数据同步成功！");

            }
        }).start();
    }

    public void back(View view){
        finish();
    }
}
