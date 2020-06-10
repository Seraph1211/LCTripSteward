package com.example.lctripsteward.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.lctripsteward.beans.CarbonCreditsInfoBean;
import com.example.lctripsteward.beans.MerchantBean;
import com.example.lctripsteward.beans.UserInfoBean;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MySharedPreferencesUtils {

    public static void putInt(Context context, String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, -2);  //默认值设为-2
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "empty");  //默认值设为"empty"
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Boolean getBoolean(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);  //默认值设为false
    }

    //保存用户信息的三个方法
    public static void saveUserInfo(Context context, UserInfoBean.UserInfoResultBean userInfoBean){
        if(userInfoBean==null){
            Log.d(TAG, "saveUserInfo: 传入的bean为空！");
        }else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("user_id", 1);
            editor.putString("nickname", userInfoBean.getNickname());  //用户昵称
            editor.putString("user_image_path", userInfoBean.getUserImagePath());  //用户头像路径
            editor.putInt("city_id",userInfoBean.getCityId());  //城市id
            editor.putString("city_name", userInfoBean.getCityName());  //城市名
            editor.putInt("sign_in_num", userInfoBean.getSignInNumber());  //连续签到天数
            editor.putInt("sign_in_today", userInfoBean.getSignInToday());  //今日是否已签到：0-否，1-是
            editor.putInt("user_level", userInfoBean.getUserLevel());  //用户等级
            editor.putInt("user_rank", userInfoBean.getUserRank());  //用户本月排行（在其所在城市中）
            editor.putInt("is_merchant",userInfoBean.getIsStore());  //用户是否是商家：0-否，1-是
            editor.putInt("team_id", userInfoBean.getTeamId());  //用户所在队伍的id,-1表示尚未加入任何队伍

            editor.apply();
        }
    }

    public static void saveUserInfo(Context context, CarbonCreditsInfoBean.CarbonCreditsInfoResultBean creditsInfoBean){
        if(creditsInfoBean==null){
            Log.d(TAG, "saveUserInfo: 传入的bean为空！");
        }else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("carbon_credits_total", creditsInfoBean.getCarbonCreditsTotal());  //用户总碳积分
            editor.putInt("carbon_credits_today", creditsInfoBean.getCarbonCreditsToday());  //待领取的碳积分
            editor.putInt("carbon_credits_available", creditsInfoBean.getCarbonCreditsAvailable());  //可用碳积分
            editor.putInt("mileage_subway_total", creditsInfoBean.getMileageSubwayTotal());  //总地铁里程
            editor.putInt("mileage_bus_total", creditsInfoBean.getMileageBusTotal());  //总公交里程
            editor.putInt("mileage_bike_total", creditsInfoBean.getMileageBikeTotal());  //总骑行里程
            editor.putInt("mileage_walk_total", creditsInfoBean.getMileageWalkTotal());  //总步行里程
            editor.putInt("mileage_subway_yesterday", creditsInfoBean.getMileageSubwayToday());  //昨日地铁里程
            editor.putInt("mileage_bus_yesterday", creditsInfoBean.getMileageBusToday());  //昨日公交里程
            editor.putInt("mileage_bike_yesterday", creditsInfoBean.getMileageBikeToday());  //昨日骑行里程
            editor.putInt("mileage_walk_yesterday", creditsInfoBean.getMileageWalkToday());  //昨日步数

            editor.apply();
        }
    }

    public static void saveUserInfo(Context context, UserInfoBean.UserInfoResultBean userInfoBean,
                                    CarbonCreditsInfoBean.CarbonCreditsInfoResultBean creditsInfoBean){
        if(userInfoBean==null || creditsInfoBean==null){
            Log.d(TAG, "saveUserInfo: 传入的bean为空！");
        }else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("user_id", 1);
            editor.putString("nickname", userInfoBean.getNickname());  //用户昵称
            editor.putString("user_image_path", userInfoBean.getUserImagePath());  //用户头像路径
            editor.putInt("city_id",userInfoBean.getCityId());  //城市id
            editor.putString("city_name", userInfoBean.getCityName());  //城市名
            editor.putInt("sign_in_num", userInfoBean.getSignInNumber());  //连续签到天数
            editor.putInt("sign_in_today", userInfoBean.getSignInToday());  //今日是否已签到：0-否，1-是
            editor.putInt("user_level", userInfoBean.getUserLevel());  //用户等级
            editor.putInt("user_rank", userInfoBean.getUserRank());  //用户本月排行（在其所在城市中）
            editor.putInt("is_merchant",userInfoBean.getIsStore());  //用户是否是商家：0-否，1-是
            editor.putInt("team_id", userInfoBean.getTeamId());  //用户所在队伍的id,-1表示尚未加入任何队伍

            editor.putInt("carbon_credits_total", creditsInfoBean.getCarbonCreditsTotal());  //用户总碳积分
            editor.putInt("carbon_credits_today", creditsInfoBean.getCarbonCreditsToday());  //待领取的碳积分
            editor.putInt("carbon_credits_available", creditsInfoBean.getCarbonCreditsAvailable());  //可用碳积分
            editor.putInt("mileage_subway_total", creditsInfoBean.getMileageSubwayTotal());  //总地铁里程
            editor.putInt("mileage_bus_total", creditsInfoBean.getMileageBusTotal());  //总公交里程
            editor.putInt("mileage_bike_total", creditsInfoBean.getMileageBikeTotal());  //总骑行里程
            editor.putInt("mileage_walk_total", creditsInfoBean.getMileageWalkTotal());  //总步行里程
            editor.putInt("mileage_subway_yesterday", creditsInfoBean.getMileageSubwayToday());  //昨日地铁里程
            editor.putInt("mileage_bus_yesterday", creditsInfoBean.getMileageBusToday());  //昨日公交里程
            editor.putInt("mileage_bike_yesterday", creditsInfoBean.getMileageBikeToday());  //昨日骑行里程
            editor.putInt("mileage_walk_yesterday", creditsInfoBean.getMileageWalkToday());  //昨日步数

            editor.apply();
        }
    }

    public static void saveMerchantInfo(Context context, MerchantBean bean){
        if(bean==null){
            Log.d(TAG, "saveMerchantInfo: bean="+bean.toString());
        }else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //editor.putInt("merchant_id", bean.getMerchantId());  //商家id
            editor.putString("merchant_name", bean.getMerchantName());  //商家名称
            //editor.putString("merchant_password", bean.getMerchantPassword());  //商家密码
            editor.putString("merchant_phone", bean.getMerchantPhoneNumber());  //商家电话
            editor.putString("merchant_email", bean.getMerchantEmail());  //商家电子邮箱
            editor.putString("merchant_address", bean.getMerchantAddress());  //商家地址
            editor.putString("merchant_introduction", bean.getMerchantIntroduce());  //商家介绍

            editor.apply();
        }
    }
}
