package com.example.lctripsteward.bottomnavigation.home.sign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.CarbonCreditsInfoBean;
import com.example.lctripsteward.beans.UserInfoBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.NetworkUtil;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 不从本地读取签到信息，从服务器获取
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SignInActivity";

    private SwipeRefreshLayout srSignIn;

    private ImageView[] checkImageList = new ImageView[7]; //存放7张check图片的数组，累计签到n次，点亮n张图片
    private Button buttonSignIn;  //签到按钮
    private Button buttonToStore;  //跳转至积分商城的按钮
    private ImageButton buttonBack; //返回
    private TextView textAvailableCredits;  //可用碳积分
    private TextView textSignInNum;  //连续签到天数

    private int signInNumber = 0;  //累计签到天数
    private int signInToday = 0; //今日是否签到
    private int carbonCreditsAvailable = -1;  //可用碳积分

    //签到奖励，还需考虑后续的更新
    private final int promotionalCrditsForOneDay = 0;
    private final int promotionalCrditsForThreeDay = 0;
    private final int promotionalCrditsForSevenDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        StatusBarUtils.setStatusBarColor(SignInActivity.this, R.color.colorAqua);  //设置状态栏颜色

        init();

    }

    public void init(){
        buttonSignIn = findViewById(R.id.buttonSignIn);
        //buttonToStore = findViewById(R.id.buttonToStore);
        buttonBack = findViewById(R.id.buttonSignInBack);
        //textAvailableCredits = findViewById(R.id.textAvailableCreditsSignIn);
        textSignInNum = findViewById(R.id.textSignInNumber);

        checkImageList[0] = findViewById(R.id.imageCheck1);
        checkImageList[1] = findViewById(R.id.imageCheck2);
        checkImageList[2] = findViewById(R.id.imageCheck3);
        checkImageList[3] = findViewById(R.id.imageCheck4);
        checkImageList[4] = findViewById(R.id.imageCheck5);
        checkImageList[5] = findViewById(R.id.imageCheck6);
        checkImageList[6] = findViewById(R.id.imageCheck7);

        buttonSignIn.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        initSwipeRefresh();

       initData();

    }

    public void initData(){
        srSignIn.post(new Runnable() {
            @Override
            public void run() {
                srSignIn.setRefreshing(true);
            }
        });
        signInToday = MySharedPreferencesUtils.getInt(SignInActivity.this, "sign_in_today");
        signInNumber = MySharedPreferencesUtils.getInt(SignInActivity.this, "sign_in_num");
        queryUserInfo();
        if(signInNumber == -2){
            signInNumber = 0;
        }
        reloadCheckImages();
        textSignInNum.setText(signInNumber+"天");

    }

    public void reloadCheckImages(){

        if(signInNumber == 0){
            for (int i=0; i<checkImageList.length; i++){
                checkImageList[i].setImageResource(R.drawable.check_gray);
            }
        }else {
            for(int i=0; i<signInNumber; i++){
                checkImageList[i].setImageResource(R.drawable.check_blue);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignIn:{
                //签到满7天后重置签到天数的问题未考虑,未告知服务器今日已签到
                if(signInNumber>-1 && signInNumber<7 && signInToday!=1){  //如果
                    signInToServer();
                }else{
                    Toast.makeText(SignInActivity.this, "今日已签到！", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.buttonSignInBack:{
                finish();
                break;
            }

            default:break;
        }
    }

    //从服务端获取用户已连续签到的天数、今日是否已经签到
    public void queryUserInfo(){
        if(!NetworkUtil.isNetworkAvailable(SignInActivity.this)){
            ToastUtils.showToast(SignInActivity.this, "未连接网络");
            finishSwipeRefresh();
            return;
        }

        HttpUtils.getInfo(HttpUtils.userInfoUrl+"?user_id="+ UserInfo.id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                finishSwipeRefresh();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finishSwipeRefresh();

                int code = response.code();
                Log.d(TAG, "onResponse: code="+code);

                if(code==200){  //服务器成功处理请求
                    String responseContent = response.body().string();
                    Log.d(TAG, "onResponse: responseContent="+responseContent);
                    UserInfoBean userInfoBean = new Gson().fromJson(responseContent, UserInfoBean.class);

                    String msgCode = userInfoBean.getMsg_code();
                    if(msgCode.equals("0000")){
                        //MySharedPreferencesUtils.saveUserInfo(SignInActivity.this, userInfoBean.getResultBean());
                        //获取已连续签到天数、今日是否已签到
                       // signInNumber =  userInfoBean.getResultBean().getSignInNumber();
                        //signInToday = userInfoBean.getResultBean().getSignInToday();
                        //textSignInNum.setText(signInNumber+"天");
                    }else {
                        ToastUtils.showToast(SignInActivity.this, userInfoBean.getMsg_message());
                    }

                }else{
                    ToastUtils.showToast(SignInActivity.this, "服务器错误");
                }

            }
        });
    }

    //从服务器获取用户可用碳积分
    public void queryCarbonCreditsInfo(){
        HttpUtils.getInfo(HttpUtils.carbonCreditsInfoUrl+"&mileage_walk_today="+MySharedPreferencesUtils.getInt(SignInActivity.this, "step_count_today")*0.00065,
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                CarbonCreditsInfoBean carbonCreditsInfoBean = new Gson().fromJson(responseContent, CarbonCreditsInfoBean.class);

                if(carbonCreditsInfoBean!=null){
                    MySharedPreferencesUtils.saveUserInfo(SignInActivity.this, carbonCreditsInfoBean.getResultBean());  //本地存储4
                    carbonCreditsAvailable = carbonCreditsInfoBean.getResultBean().getCarbonCreditsAvailable();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(carbonCreditsAvailable!=-2){
                                textAvailableCredits.setText(String.valueOf(carbonCreditsAvailable));
                            }

                        }
                    });
                }else {
                    Log.d(TAG, "onResponse: 碳积分信息获取失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this, "碳积分信息获取失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public void signInToServer(){
        HttpUtils.getInfo(HttpUtils.userSignInUrl + "?user_id=" + UserInfo.id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(SignInActivity.this, "服务器错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "signIn code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "SignIn responseContent="+responseContent);

                    signInNumber++;
                    signInToday = 1;
                    reloadCheckImages();
                    textSignInNum.setText(signInNumber+"天");
                    MySharedPreferencesUtils.putInt(SignInActivity.this,"sign_in_num", signInNumber);
                    MySharedPreferencesUtils.putInt(SignInActivity.this, "sign_in_today", signInToday);

                    int readyCredits = MySharedPreferencesUtils.getInt(SignInActivity.this, "carbon_credits_today");

                    MySharedPreferencesUtils.putInt(SignInActivity.this, "carbon_credits_today", readyCredits+10);  //签到成功加10积分

                    ToastUtils.showToast(SignInActivity.this, "签到成功");
                }else {
                    ToastUtils.showToast(SignInActivity.this, "服务器错误，签到失败");
                }


            }
        });
    }

    public void initSwipeRefresh(){
        srSignIn = findViewById(R.id.sr_sign_in);

        /*
        srHome.post(new Runnable() {
            @Override
            public void run() {
                srHome.setRefreshing(true);
            }
        });
         */

        srSignIn.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //queryCarbonCreditsInfo();
                queryUserInfo();
            }
        });
    }

    /**
     * 结束刷新
     */
    public void finishSwipeRefresh(){
        if(srSignIn.isRefreshing()){
            srSignIn.post(new Runnable() {
                @Override
                public void run() {
                    srSignIn.setRefreshing(false);
                }
            });

        }
    }

}
