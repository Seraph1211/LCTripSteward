package com.example.lctripsteward.bottomnavigation.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.bottomnavigation.BottomNavigationActivity;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Store3Fragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private View view;
    private BottomNavigationActivity activity;
    private static final int COUPON_INFO_READY = 231;
    private static final int COMMODITY_INFO_READY = 232;
    private static final int SECOND_HAND_READY = 234;
    private static final String TAG = "Store3Activity";
    private ImageView ivBanner;
    private TabLayout tabLayout;
    private TextView tvAvailableCredits;
    private Button btnShoppingRecord;
    private SwipeRefreshLayout srStore;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store3, container, false);

        queryGoodsInfo();

        initView();

        return view;
    }

    private void initSwipeRefresh(){
        srStore = view.findViewById(R.id.sr_store);

        srStore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finishSwipeRefresh();
                                }
                            });
                            ToastUtils.showToast(activity, "刷新完成！");
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
        if(srStore.isRefreshing()){
            srStore.post(new Runnable() {
                @Override
                public void run() {
                    srStore.setRefreshing(false);
                }
            });

        }
    }

    public void initView(){
        activity = (BottomNavigationActivity) getActivity();

        ivBanner = view.findViewById(R.id.imageBannerStore3_2);
        tabLayout = view.findViewById(R.id.tabLayoutStore3_2);
        tvAvailableCredits = view.findViewById(R.id.textCreditsStore3_2);
        btnShoppingRecord = view.findViewById(R.id.buttonToShoppingCredits2);

        int availableCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_available");
        if(availableCredits < 0){
            availableCredits = 0;
        }
        tvAvailableCredits.setText(availableCredits+"");

        initTab();

        Glide.with(getContext())
                .load(R.drawable.banner_store)
                .into(ivBanner);

        btnShoppingRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getContext(), "Shopping Record");
                startActivity(new Intent(getContext(), RecordActivity.class));
            }
        });

        initSwipeRefresh();
    }

    public void initTab(){
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.addTab(tabLayout.newTab().setText("优惠券"));
        tabLayout.addTab(tabLayout.newTab().setText("商品"));
        tabLayout.addTab(tabLayout.newTab().setText("二手交易"));
    }

    public void queryCouponList(){
        setFragment(new CouponFragment());
    }

    public void queryCommodityList(){
        setFragment(new CommodityFragment());
    }

    public void querySecondHandList(){
        setFragment(new SecondHandCommodityFragment());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:{
                queryCouponList();
                break;
            }
            case 1:{
                queryCommodityList();
                break;
            }
            case 2:{
                querySecondHandList();
                break;
            }

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameStore3_2, fragment);
        fragmentTransaction.commit();
    }

    public void queryGoodsInfo(){
        HttpUtils.getInfo(HttpUtils.commodityInfoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "Store code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "Store responseContent="+responseContent);
                }else {
                    ToastUtils.showToast(getContext(), "服务器错误");
                }
            }
        });
    }
}
