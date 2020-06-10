package com.example.lctripsteward.bottomnavigation.userinfo.competition;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.StatusBarUtils;


public class CompetitionActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CompetitionActivity";
    private ImageButton btnBack;  //返回按钮
    private TextView tvA;  //tabA
    private TextView tvB;  //tabB
    private TextView tvC;  //tabC
    private TextView tvRequest;  //获奖要求
    private TextView tvBonus;  //现有奖金
    private TextView tvNum;  //现已报名人数
    private TextView tvJoin;  //本期是否报名
    private Button btnCompetition;  //btn
    private ScrollView sv;

    private int availableCredits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        initView();

        availableCredits = MySharedPreferencesUtils.getInt(CompetitionActivity.this, "carbon_credits_available");
    }

    public void initView(){
        btnBack = findViewById(R.id.buttonCompetitionBack);
        tvA = findViewById(R.id.tv_a);
        tvB = findViewById(R.id.tv_b);
        tvC = findViewById(R.id.tv_c);
        //tvRequest = findViewById(R.id.tvRequest);
        tvBonus = findViewById(R.id.tv_bonus);
        tvNum = findViewById(R.id.tv_num);
        tvJoin = findViewById(R.id.tv_join_in_or_not);
        btnCompetition = findViewById(R.id.btn_competition);
        sv = findViewById(R.id.sv_competition);

        btnBack.setOnClickListener(this);
        tvA.setOnClickListener(this);
        tvB.setOnClickListener(this);
        tvC.setOnClickListener(this);
        btnCompetition.setOnClickListener(this);

        StatusBarUtils.setStatusBarColor(CompetitionActivity.this, R.color.colorB);  //设置状态栏颜色
        //StatusBarUtils.setLightStatusBar(CompetitionActivity.this, true, true);  //状态栏字体颜色-黑
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCompetitionBack:{
                finish();
                break;
            }
            case R.id.tv_a:{
                Log.d(TAG, "onClick: a");
                sv.setBackgroundResource(R.drawable.bg_competition_1);
                tvA.setBackgroundResource(R.drawable.bg_tab_1);
                tvB.setBackground(null);
                tvC.setBackground(null);

                StatusBarUtils.setStatusBarColor(CompetitionActivity.this, R.color.colorA);  //设置状态栏颜色

                initA();

                break;
            }
            case R.id.tv_b:{
                Log.d(TAG, "onClick: b");
                sv.setBackgroundResource(R.drawable.bg_competition_2);
                tvA.setBackground(null);
                tvB.setBackgroundResource(R.drawable.bg_tab_1);
                tvC.setBackground(null);

                StatusBarUtils.setStatusBarColor(CompetitionActivity.this, R.color.colorB);  //设置状态栏颜色

                initB();
                break;
            }
            case R.id.tv_c:{
                Log.d(TAG, "onClick: c");
                sv.setBackgroundResource(R.drawable.bg_competition_3);
                tvA.setBackground(null);
                tvB.setBackground(null);
                tvC.setBackgroundResource(R.drawable.bg_tab_1);

                StatusBarUtils.setStatusBarColor(CompetitionActivity.this, R.color.colorC);  //设置状态栏颜色

                initC();
                break;
            }
            case R.id.btn_competition:{
                AlertDialog dialog = new AlertDialog.Builder(CompetitionActivity.this)
                        .setTitle("是否消耗90积分报名本期比赛？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(availableCredits > 90){
                                    Toast.makeText(CompetitionActivity.this, "报名成功！", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    MySharedPreferencesUtils.putInt(CompetitionActivity.this, "carbon_credits_available", availableCredits-90);

                                    tvJoin.setText("本期已报名！");
                                }else {
                                    Toast.makeText(CompetitionActivity.this, "积分不足！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();

                break;
            }
        }
    }

    private void initA(){

    }

    private void initB(){

    }

    private void initC(){

    }
}
