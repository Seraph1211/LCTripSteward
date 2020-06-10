package com.example.lctripsteward.bottomnavigation.home.rank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.RankBean;
import com.example.lctripsteward.bottomnavigation.BottomNavigationActivity;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RankActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private final String TAG = "RankActivity";
    private Context context;
    private TabLayout mTab;
    private FrameLayout mFrame;
    private SwipeRefreshLayout srRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        StatusBarUtils.setStatusBarColor(RankActivity.this, R.color.colorOrange);  //设置状态栏颜色

        initView();

        setFragment(new TotalRankFragment());

        srRank.post(new Runnable() {
            @Override
            public void run() {
                srRank.setRefreshing(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            finishSwipeRefresh();
                            ToastUtils.showToast(context, "数据获取成功！");
                        }
                    }
                }).start();
            }
        });

    }

    private void initView() {
        context = RankActivity.this;

        // 获取控件对象
        mTab = findViewById(R.id.rank_tab);
        mFrame = findViewById(R.id.rank_frame);

        initSwipeRefresh();

        initTab();
        mTab.addOnTabSelectedListener(this);
    }

    private void initSwipeRefresh(){
        srRank = findViewById(R.id.sr_rank);

        srRank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            finishSwipeRefresh();
                            ToastUtils.showToast(context, "刷新成功！");
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
        if(srRank.isRefreshing()){
            srRank.post(new Runnable() {
                @Override
                public void run() {
                    srRank.setRefreshing(false);
                }
            });

        }
    }

    private void initTab(){
        mTab.addTab(mTab.newTab().setText("总碳积分排行"));
        mTab.addTab(mTab.newTab().setText("月度碳积分排行"));
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rank_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:{
                setFragment(new TotalRankFragment());
                break;
            }
            case 1:{
                setFragment(new MonthRankFragment());
                break;
            }
            default:break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_rank_back: {
                finish();
                startActivity(new Intent(RankActivity.this, BottomNavigationActivity.class));
            }

        }
    }

}
