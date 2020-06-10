package com.example.lctripsteward.bottomnavigation.home.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CommodityBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GameFragment extends Fragment {
    private static final String TAG = "GameFragment";
    private CommodityBean commodityBean = new CommodityBean();
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        imageView = view.findViewById(R.id.imageView2);
        queryCommodityInfoTestDrive(view);



        return view;
    }

    public void queryCommodityInfoTestDrive(final View view){

        HttpUtils.getInfo("http://121.36.4.52:8090/carbon_credits_system/good/getGoods?page_no=1&page_size=5&good_type=2", new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败！");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: 请求成功！");


                String responseContent = response.body().string();

                Log.d(TAG, "onResponse: "+responseContent);

                commodityBean = new Gson().fromJson(responseContent, CommodityBean.class);

                Activity activity = (Activity)view.getContext();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(GameFragment.this)
                                .load("https://i0.hdslb.com/bfs/archive/1ca6eb4bb8a7f9103e22a5820c827e3718259bce.jpg@1100w_484h_1c_100q.jpg")
                                .error(R.drawable.error)
                                .placeholder(R.drawable.img1)
                                .into(imageView);
                    }
                });

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: GameFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: GameFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //imageViewPager.thread.stop();  //停止自动播放的线程
        Log.d(TAG, "onDetach: GameFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: GameFragment");
    }
}


