package com.example.lctripsteward.bottomnavigation.userinfo.cardpackage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CardPackageBean;
import com.example.lctripsteward.bottomnavigation.home.report.MonthReportActivity;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 在init()中完成recyclerView数据的加载???
 */

public class CardPackageActivity extends AppCompatActivity {
    private static final String TAG = "CardPackageActivity";
    private RecyclerView cardPackageRecyclerView;
    private List<CardPackageBean.ResultBean.CouponBagBean> couponBagBeans = new ArrayList<>();
    private ImageButton buttonBack;
    private SwipeRefreshLayout srCardPackage;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_package);

        StatusBarUtils.setStatusBarColor(CardPackageActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(CardPackageActivity.this, true, true);  //状态栏字体颜色-黑

        init();

        queryCouponInfo();
    }

    public void init(){
        //模拟卡包中的数据
        for(int i=0; i<8; i++){
            CardPackageBean.ResultBean.CouponBagBean couponBagBean = new CardPackageBean.ResultBean.CouponBagBean();
            couponBagBean.setValue(10);
            couponBagBean.setCoupon_name("吕小布的蛋糕店"+(i+1)+"号");
            couponBagBean.setCoupon_id(i);
            couponBagBeans.add(couponBagBean);
        }

        //初始化RecyclerView
        cardPackageRecyclerView = findViewById(R.id.recyclerViewCardPackage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cardPackageRecyclerView.setLayoutManager(layoutManager);
        cardPackageRecyclerView.setAdapter(new CardPackageRecyclerViewAdapter(this, couponBagBeans));

        buttonBack = findViewById(R.id.buttonCardPackageBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initSwipeRefresh();

    }


    private void initSwipeRefresh(){
        srCardPackage = findViewById(R.id.sr_card_package);

        srCardPackage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1200);
                            CardPackageActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finishSwipeRefresh();
                                }
                            });
                            ToastUtils.showToast(CardPackageActivity.this, "刷新完成！");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 结束刷新
     */
    private void finishSwipeRefresh(){
        if(srCardPackage.isRefreshing()){
            srCardPackage.post(new Runnable() {
                @Override
                public void run() {
                    srCardPackage.setRefreshing(false);
                }
            });

        }
    }

    public void queryCouponInfo(){
        HttpUtils.getInfo(HttpUtils.cardPackageUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);
                CardPackageBean cardPackageBean = new Gson().fromJson(responseContent, CardPackageBean.class);
                List<CardPackageBean.ResultBean.CouponBagBean> couponBag = cardPackageBean.getResult().getCoupon_bag();  //拿到coupon list
            }
        });
    }
}
