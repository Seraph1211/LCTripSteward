package com.example.lctripsteward.bottomnavigation.store;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CommodityBean;
import com.example.lctripsteward.utils.StatusBarUtils;

public class CommodityInfoActivity extends AppCompatActivity {
    private static final String TAG = "CommodityInfoActivity";

    private CommodityBean commodityBean;
    private ImageButton btnBack;
    private Button btnExchange;
    private ImageView ivCommodity;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvCreditsCost;
    private TextView tvIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_info);

        StatusBarUtils.setStatusBarColor(CommodityInfoActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(CommodityInfoActivity.this, true, true);  //状态栏字体颜色-黑

        initView();

    }

    public void initView(){
        commodityBean = (CommodityBean)getIntent().getSerializableExtra("commodityBean");
        Log.d(TAG, "onCreate: "+commodityBean.getCommodityResultBean().getCommodityName());

        ivCommodity = findViewById(R.id.imageCommodityInCommodityInfo);
        tvName = findViewById(R.id.tvCommodityName);
        tvPrice = findViewById(R.id.tvCommodityPrice);
        tvCreditsCost = findViewById(R.id.tvCreditsCost);
        tvIntroduction = findViewById(R.id.tvCommodityIntroduction);

        Glide.with(CommodityInfoActivity.this)
                .load(commodityBean.getCommodityResultBean().getCommodityPicture())
                .into(ivCommodity);
        tvName.setText(commodityBean.getCommodityResultBean().getCommodityName());
        tvCreditsCost.setText(commodityBean.getCommodityResultBean().getCarbonCreditsNeeds()+"");
        tvPrice.setText(commodityBean.getCommodityResultBean().getCommodityPrice()+"");
        tvIntroduction.setText(commodityBean.getCommodityResultBean().getCommodityIntroduce());

        btnBack = findViewById(R.id.button_commodity_info_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnExchange = findViewById(R.id.buttonExchange);
        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                buyCommodity();
            }
        });
    }

    public void buyCommodity(){
        AlertDialog dialog = new AlertDialog.Builder(CommodityInfoActivity.this)
                .setTitle("是否兑换？")
                .setPositiveButton("兑换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CommodityInfoActivity.this, "积分不足！", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}
