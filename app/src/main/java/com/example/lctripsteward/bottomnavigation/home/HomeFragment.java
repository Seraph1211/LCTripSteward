package com.example.lctripsteward.bottomnavigation.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.CarbonCreditsInfoBean;
import com.example.lctripsteward.beans.UserInfoBean;
import com.example.lctripsteward.bottomnavigation.BottomNavigationActivity;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.NetworkUtil;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private List<Integer> imageUrlList;
    private List<String> titleList;
    private Banner banner;

    private View view;
    private BottomNavigationActivity activity;

    private SwipeRefreshLayout srHome;

    private TextView textSubwayMileagesTotal;
    private TextView textSubwayMileagesYesterday;
    private TextView textBusMileagesTotal;
    private TextView textBusMileagesYesterday;
    private TextView textBikeMileagesTotal;
    private TextView textBikeMileagesYesterday;
    private TextView textWalkMileagesTotal;
    private TextView textWalkMileagesYesterday;

    private int subwayMileagesTotal = 0;
    private int subwayMileagesYesterday = 0;
    private int busMileagesTotal = 0;
    private int busMileagesYesterday = 0;
    private int bikeMileagesTotal = 0;
    private int bikeMileagesYesterday = 0;
    private int walkMileagesTotal = 0;  //总步行里程
    private int walkMileagesYesterday = 0;  //昨日步数

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        //initData();

        return view;
    }


    private void initView(){
        activity = (BottomNavigationActivity)getActivity();
        textSubwayMileagesTotal = view.findViewById(R.id.textSubwayMileagesTotal);
        textSubwayMileagesYesterday = view.findViewById(R.id.textSubwayMileagesYesterday);
        textBusMileagesTotal = view.findViewById(R.id.textBusMileagesTotal);
        textBusMileagesYesterday = view.findViewById(R.id.textBusMileagesYesterday);
        textBikeMileagesTotal = view.findViewById(R.id.textBikeMileagesTotal);
        textBikeMileagesYesterday = view.findViewById(R.id.textBikeMileagesYesterday);
        textWalkMileagesTotal = view.findViewById(R.id.textWalkMileagesTotal);
        textWalkMileagesYesterday = view.findViewById(R.id.textWalkMileagesYesterday);

        initBanner();
        initSwipeRefresh();
    }

    @SuppressLint("ResourceAsColor")
    private void initSwipeRefresh(){
        srHome = view.findViewById(R.id.sr_home);

        srHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //queryCarbonCreditsInfo();
                queryUserInfo();
            }
        });
    }

    private void initData(){
        srHome.post(new Runnable() {
            @Override
            public void run() {
                srHome.setRefreshing(true);
            }
        });
    }

    private void initBanner(){
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();

        imageUrlList.add(R.drawable.banner_1);
        imageUrlList.add(R.drawable.banner_2);
        imageUrlList.add(R.drawable.banner_3);
        imageUrlList.add(R.drawable.banner_4);

        titleList.add("低碳环保，新能源出行");
        titleList.add("健康生活，低碳出行");
        titleList.add("爱护地球，人人有责");
        titleList.add("3.30 地球一小时");

        banner = view.findViewById(R.id.bannerHome);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new MyLoader());
        banner.setImages(imageUrlList);
        banner.setBannerTitles(titleList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);  //切换频率
        banner.isAutoPlay(true);  //自动启动
        banner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        banner.start();
    }

    /**
     * 结束刷新
     */
    private void finishSwipeRefresh(){
        if(srHome.isRefreshing()){
            srHome.post(new Runnable() {
                @Override
                public void run() {
                    srHome.setRefreshing(false);
                }
            });

        }
    }

    /**
     * 从服务器获取用户信息并保存在本地
     */
    private void queryUserInfo(){
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(activity, "未连接网络");
            finishSwipeRefresh();
            return;
        }

        Log.d(TAG, "initData: [id="+UserInfo.id+", account="+UserInfo.account+", pwd="+UserInfo.password+"]");
        Log.d(TAG, "queryUserInfo: url="+HttpUtils.userInfoUrl+"?user_id="+ UserInfo.id);

        HttpUtils.getInfo(HttpUtils.userInfoUrl+"?user_id="+ UserInfo.id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "服务器故障", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "userInfo onFailure: 从服务器读取用户信息失败！");
                finishSwipeRefresh();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.i(TAG, "UserInfo: code="+code);

                String responseContent = response.body().string();
                Log.i(TAG, "UserInfo responseContent="+responseContent);

                if(code==200){  //服务器成功处理请求

                    UserInfoBean userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                    String msgCode = userInfoBean.getMsg_code();

                    if(msgCode.equals("0000")){
                        MySharedPreferencesUtils.saveUserInfo(getContext(), userInfoBean.getResultBean());
                        MySharedPreferencesUtils.putBoolean(getContext(), "has_user_info", true);
                    }else{
                        ToastUtils.showToast(activity, "获取数据失败");
                    }
                }else{
                    ToastUtils.showToast(activity, "服务器出错！");
                }

            }
        });
    }

    /**
     * 从服务器获取碳积分信息并保存在本地
     */
    private void queryCarbonCreditsInfo(){
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(activity, "未连接网络");
            finishSwipeRefresh();
            return;
        }

        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl+"&mileage_walk_today="+MySharedPreferencesUtils.getInt(getContext(), "step_count_today"),
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "服务器故障", Toast.LENGTH_SHORT).show();
                    }
                });
                finishSwipeRefresh();
                Log.d(TAG, "carbonCreditsInfo onFailure: 从服务器读取碳积分信息失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.d(TAG, "CarbonCredits code="+code);

                String responseContent = response.body().string();
                Log.d(TAG, "CarbonCreditsInfoBean:"+responseContent);

                if(code==200){  //服务器成功处理请求

                    CarbonCreditsInfoBean carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class); //即使没拿到数据bean也不会为Null，所以不必判空，但是当没拿到东西或者拿到一些奇怪的东西时的处理还没写好

                    String msgCode = carbonCreditsInfoBean.getMsg_code();
                    if(msgCode.equals("0000")){  //获取数据成功
                        MySharedPreferencesUtils.saveUserInfo(getContext(), carbonCreditsInfoBean.getResultBean());
                        MySharedPreferencesUtils.putBoolean(getContext(), "has_credits_info", true);
                        reloadMileageInfo();
                    }else {  //失败，则提示用户错误信息
                        ToastUtils.showToast(activity, carbonCreditsInfoBean.getMsg_message());
                    }
                }else{
                    ToastUtils.showToast(activity, "服务器出错！");
                }
            }
        });
    }

    public void reloadMileageInfo(){
        textSubwayMileagesTotal.setText(subwayMileagesTotal+"");
        textSubwayMileagesYesterday.setText(subwayMileagesYesterday+"");
        textBusMileagesTotal.setText(busMileagesTotal+"");
        textBusMileagesYesterday.setText(busMileagesYesterday+"");
        textBikeMileagesTotal.setText(bikeMileagesTotal+"");
        textBikeMileagesYesterday.setText(bikeMileagesYesterday+"");
        textWalkMileagesTotal.setText(walkMileagesTotal+"");
        textWalkMileagesYesterday.setText(walkMileagesYesterday+"");

        finishSwipeRefresh();  //结束SwipeRefresh的刷新
    }

    public void getMileageInfoFromSP(){
        subwayMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_subway_total");
        subwayMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_subway_yesterday");
        busMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_bus_total");
        busMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_bus_yesterday");
        bikeMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_bike_total");
        bikeMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_bike_yesterday");
        walkMileagesTotal = MySharedPreferencesUtils.getInt(getContext(), "mileage_walk_total");
        walkMileagesYesterday = MySharedPreferencesUtils.getInt(getContext(), "mileage_walk_yesterday");
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity())
                    .load(path).
                    into(imageView);
        }
    }

}