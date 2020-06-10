package com.example.lctripsteward.bottomnavigation.userinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.CarbonCreditsInfoBean;
import com.example.lctripsteward.beans.UserInfoBean;
import com.example.lctripsteward.bottomnavigation.BottomNavigationActivity;
import com.example.lctripsteward.bottomnavigation.userinfo.cardpackage.CardPackageActivity;
import com.example.lctripsteward.bottomnavigation.userinfo.competition.CompetitionActivity;
import com.example.lctripsteward.bottomnavigation.userinfo.merchant.MerchantActivity;
import com.example.lctripsteward.bottomnavigation.userinfo.merchant.MerchantUploadCertificateActivity;
import com.example.lctripsteward.bottomnavigation.userinfo.team.TeamActivity;
import com.example.lctripsteward.utils.Base64Utils;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.NetworkUtil;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class UserInfoFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "UserInfoFragment";

    private SwipeRefreshLayout srUserInfo;

    private TextView textUserName;  //用户昵称
    private CircleImageView imageHeadPortrait;  //用户头像
    private TextView textUserTotalCarbonCredits;  //总碳积分
    private TextView textUserAvailableCredits;    //可用碳积分
    private TextView textUserReadyToGetCredits;   //待领取的碳积分
    private Button buttonGetCredits;  //按钮，领取碳积分
    private AppCompatImageButton btnModify;

    private View view;
    private BottomNavigationActivity activity;
    private Context context;

    private ConstraintLayout clCardPackage;  //卡包入口
    private ConstraintLayout clMerchant;  //商家入口
    private ConstraintLayout clTeam;  //队伍入口
    private ConstraintLayout clCallUs;  //联系方式入口
    private ConstraintLayout clExplain;  //帮助信息入口
    private ConstraintLayout clCompetition;

    private int availableCarbonCredits = 0;
    private int totalCarbonCredits = 0;
    private int readyToGetCarbonCredits = 0;
    private String userNickname = "";
    private String userHeadPortrait;

    private UserInfoBean userInfoBean = new UserInfoBean();  //用户信息
    private CarbonCreditsInfoBean carbonCreditsInfoBean = new CarbonCreditsInfoBean();  //用户碳积分信息


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, container, false);

        initView();
        initData();

        return view;
    }

    private void initSwipeRefresh(){
        srUserInfo = view.findViewById(R.id.sr_user_info);

        srUserInfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //queryCarbonCreditsInfo();
                queryUserInfo();

                updateUI();

            }
        });
    }

    /**
     * 结束刷新
     */
    private void finishSwipeRefresh(){
        if(srUserInfo.isRefreshing()){
            srUserInfo.post(new Runnable() {
                @Override
                public void run() {
                    srUserInfo.setRefreshing(false);
                }
            });
        }
    }

    /**
     * 获取用户基本信息
     */
    private void queryUserInfo(){
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(activity, "未连接网络");
            finishSwipeRefresh();
            return;
        }

      HttpUtils.getInfo(HttpUtils.userInfoUrl+"?user_id="+ UserInfo.id, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               ToastUtils.showToast(activity, "服务器错误");
               Log.d(TAG, "onFailure: 从服务器读取用户信息失败！");
               finishSwipeRefresh();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               finishSwipeRefresh();

               int code = response.code();
               Log.d(TAG, "onResponse: code="+code);

               String responseContent = response.body().string();
               Log.i(TAG, "onResponse: userInfo's responseContent: "+responseContent);

               if(code==200){  //code==200表示服务器成功处理了请求

                   userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);
                   String msgCode = userInfoBean.getMsg_code();
                   Log.d(TAG, "onResponse: msgCode="+msgCode);

                   if(msgCode.equals("0000")){ //信息获取成功
                       userNickname = MySharedPreferencesUtils.getString(getContext(), "nickname");
                       userHeadPortrait = MySharedPreferencesUtils.getString(getContext(), "img");

                       activity.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               textUserName.setText(userInfoBean.getResultBean().getNickname());  //设置用户昵称

                               if(!userHeadPortrait.equals("empty")){
                                   Base64Utils.loadBase64Image(userHeadPortrait, imageHeadPortrait);
                               }
                           }
                       });
                       finishSwipeRefresh();
                   }

               }else {
                   ToastUtils.showToast(activity, "服务器出错！");
               }
           }
       });
    }

    /**
     * 获取用户碳积分信息
     */
    private void queryCarbonCreditsInfo(){
        if(!NetworkUtil.isNetworkAvailable(getContext())){
            ToastUtils.showToast(activity, "未连接网络");
            finishSwipeRefresh();
            return;
        }

        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl+"&mileage_walk_today="+ MySharedPreferencesUtils.getInt(getContext(), "step_count_today"),
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 从服务器读取碳积分信息失败！");
                ToastUtils.showToast(activity, "服务器故障！");
                finishSwipeRefresh();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.d(TAG, "onResponse: code="+code);

                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: CarbonCreditsInfo: "+responseContent);

                if(code==200){  //服务器成功处理请求

                    carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);
                    Log.d(TAG, "onResponse: CarbonCreditsInfoBean: "+carbonCreditsInfoBean.toString());

                    String msgCode = carbonCreditsInfoBean.getMsg_code();
                    Log.d(TAG, "onResponse: CarbonCreditsBean's msgCode="+msgCode);

                    if(msgCode.equals("0000")){  //成功获取信息
                        availableCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable();
                        totalCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal();
                        readyToGetCarbonCredits = carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday();
                        finishSwipeRefresh();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textUserTotalCarbonCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsTotal()));  //设置用户总碳积分
                                textUserReadyToGetCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsToday()));  //设置用户待领取的碳积分
                                textUserAvailableCredits.setText(String.valueOf(carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable()));  //设置用户可用碳积分
                            }
                        });
                    }

                }else {
                    ToastUtils.showToast(activity, "服务器出错！");
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView(){
        activity = (BottomNavigationActivity)getActivity();
        context = getContext();

        //各控件获取实例
        imageHeadPortrait = view.findViewById(R.id.head_portrait);
        textUserName = view.findViewById(R.id.text_user_name);
        textUserTotalCarbonCredits = view.findViewById(R.id.text_total_credit);
        textUserAvailableCredits = view.findViewById(R.id.text_available_credit);
        textUserReadyToGetCredits = view.findViewById(R.id.text_readyToGet_credit);
        buttonGetCredits = view.findViewById(R.id.buttonGetCredits);
        btnModify = view.findViewById(R.id.btn_modify);

        clCallUs = view.findViewById(R.id.clCallUs);
        clCardPackage = view.findViewById(R.id.clCardPackage);
        clExplain = view.findViewById(R.id.clExplain);
        clMerchant = view.findViewById(R.id.clMerchant);
        clTeam = view.findViewById(R.id.clTeam);
        clCompetition = view.findViewById(R.id.clCompetition);

        //注册监听器
        buttonGetCredits.setOnClickListener(this);
        clTeam.setOnClickListener(this);
        clMerchant.setOnClickListener(this);
        clExplain.setOnClickListener(this);
        clCardPackage.setOnClickListener(this);
        clCallUs.setOnClickListener(this);
        clCompetition.setOnClickListener(this);
        btnModify.setOnClickListener(this);

        initSwipeRefresh();
    }

    /**
     * 初始化用户数据
     */
    private void initData(){
        //通过sp从本地获取用户信息以及碳积分信息
        totalCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_total");
        availableCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_available");
        readyToGetCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_today");
        userNickname = MySharedPreferencesUtils.getString(getContext(), "nickname");
        userHeadPortrait = MySharedPreferencesUtils.getString(getContext(), "img");

        Log.d(TAG, "initData: nickname="+userNickname);
        Log.d(TAG, "initData: userHeadPortrait="+userHeadPortrait);

        if(userNickname.equals("empty")){
            if(!NetworkUtil.isNetworkAvailable(getContext())){
                ToastUtils.showToast(activity, "未连接网络");
                return;
            }
            srUserInfo.post(new Runnable() {
                @Override
                public void run() {
                    srUserInfo.setRefreshing(true);
                }
            });
            queryUserInfo();
            //queryCarbonCreditsInfo();
        }

        if(availableCarbonCredits < 0){
            totalCarbonCredits = 0;
            availableCarbonCredits = 0;
            readyToGetCarbonCredits = 0;

            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_total", totalCarbonCredits);
            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_available", availableCarbonCredits);
            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_today", readyToGetCarbonCredits);
        }

        textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));
        textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
        textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
        textUserName.setText(userNickname);

        if(!userHeadPortrait.equals("empty")){
            Base64Utils.loadBase64Image(userHeadPortrait, imageHeadPortrait);
        }
    }

    private void updateUI(){
        //通过sp从本地获取用户信息以及碳积分信息
        totalCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_total");
        availableCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_available");
        readyToGetCarbonCredits = MySharedPreferencesUtils.getInt(getContext(), "carbon_credits_today");
        userNickname = MySharedPreferencesUtils.getString(getContext(), "nickname");
        userHeadPortrait = MySharedPreferencesUtils.getString(getContext(), "img");


        if(userNickname.equals("empty")){
            if(!NetworkUtil.isNetworkAvailable(getContext())){
                ToastUtils.showToast(activity, "未连接网络");
                return;
            }
            queryUserInfo();
        }

        if(availableCarbonCredits < 0){
            totalCarbonCredits = 0;
            availableCarbonCredits = 0;
            readyToGetCarbonCredits = 0;

            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_total", totalCarbonCredits);
            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_available", availableCarbonCredits);
            MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_today", readyToGetCarbonCredits);
        }

        textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));
        textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
        textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
        textUserName.setText(userNickname);

        if(!userHeadPortrait.equals("empty")){
            Base64Utils.loadBase64Image(userHeadPortrait, imageHeadPortrait);
        }

    }


    @Override
    public void onStart() {
        updateUI();
        super.onStart();
    }

    /**
     * 一键领取待领取的碳积分
     */
    private void getCredits(){
        HttpUtils.getInfo(HttpUtils.getCarbonCreditsUrl+"?user_id="+UserInfo.id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(activity, "服务器故障");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "getCredits: code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "getCredits responseContent="+responseContent);

                    String msgCode = "";
                    String msgMsg = "";
                    try {
                        JSONObject jsonObject = new JSONObject(responseContent);
                        msgCode = jsonObject.getString("msg_code");
                        Log.d(TAG, "getCredits msgCode="+msgCode);
                        msgMsg = jsonObject.getString("msg_message");
                        Log.d(TAG, "getCredits msgMsg="+msgMsg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(msgCode.equals("0000")){
                        availableCarbonCredits += readyToGetCarbonCredits;
                        totalCarbonCredits += readyToGetCarbonCredits;
                        readyToGetCarbonCredits = 0;
                        //更新UI
                        textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
                        textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
                        textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));
                /*
                //更新bean
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsAvailable(availableCarbonCredits);
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsToday(0);
                carbonCreditsInfoBean.getResultBean().setCarbonCreditsTotal(totalCarbonCredits);
                */
                        //更新sp中存储的数据
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_total", totalCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_available", availableCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_today", readyToGetCarbonCredits);

                        ToastUtils.showToast(activity, "领取成功！");
                    }else{
                        ToastUtils.showToast(activity, msgMsg);
                    }
                }else {
                    ToastUtils.showToast(activity, "请求处理失败！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_modify:{
                startActivity(new Intent(activity, UserDataActivity.class));
                break;
            }

            case R.id.buttonGetCredits:{
                if(readyToGetCarbonCredits!=0){
                    showLoadingDialog();

                }

                break;
            }
            case R.id.clCardPackage:{
                Toast.makeText(view.getContext(), "Card Package !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), CardPackageActivity.class));
                break;
            }
            case R.id.clExplain:{
                Toast.makeText(view.getContext(), "Help Info !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.clCallUs:{
                Toast.makeText(view.getContext(), "Call Us !", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.clMerchant:{
                Toast.makeText(view.getContext(), "Merchant In !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MerchantUploadCertificateActivity.class));
                break;
            }
            case R.id.clTeam:{
                Toast.makeText(view.getContext(), "Team !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), TeamActivity.class));
                break;
            }

            case R.id.clCompetition:{
                Toast.makeText(view.getContext(), "Competition", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), CompetitionActivity.class));
                break;
            }

            default:break;
        }
    }

    private void showLoadingDialog(){
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
                ToastUtils.showToast(activity, "领取成功！");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //getCredits();
                        availableCarbonCredits += readyToGetCarbonCredits;
                        totalCarbonCredits += readyToGetCarbonCredits;
                        readyToGetCarbonCredits = 0;

                        //更新UI
                        textUserAvailableCredits.setText(String.valueOf(availableCarbonCredits));
                        textUserReadyToGetCredits.setText(String.valueOf(readyToGetCarbonCredits));
                        textUserTotalCarbonCredits.setText(String.valueOf(totalCarbonCredits));

                        //更新sp中存储的数据
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_total", totalCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_available", availableCarbonCredits);
                        MySharedPreferencesUtils.putInt(getContext(), "carbon_credits_today", readyToGetCarbonCredits);
                    }
                });

            }
        }).start();
    }

}
