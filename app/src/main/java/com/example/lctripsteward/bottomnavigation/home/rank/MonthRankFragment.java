package com.example.lctripsteward.bottomnavigation.home.rank;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.RankBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MonthRankFragment extends Fragment {
    private RankActivity activity;
    private View view;
    private RecyclerView recyclerView;
    List<RankBean.ResultBean.UserListBean> monthRankItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month_rank, container, false);

        activity = (RankActivity)getActivity();
        initRecyclerView();
        //queryMonthRankingList();

        return view;
    }


    /**
     * 初始化RecyclerView
     */
    public void initRecyclerView(){
        initList();

        recyclerView = view.findViewById(R.id.recycler_view_month);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RankRecyclerViewAdapter(getContext(), monthRankItemList));
    }

    /**
     * 初始化List
     */
    public void initList(){
        RankBean.ResultBean.UserListBean rankItem1 = new RankBean.ResultBean.UserListBean();
        rankItem1.setUserRank(1);
        rankItem1.setNickname("江南");
        rankItem1.setUserImagePath(R.drawable.head_img_1);
        rankItem1.setCarbonCredits(15469);
        monthRankItemList.add(rankItem1);

        RankBean.ResultBean.UserListBean rankItem2 = new RankBean.ResultBean.UserListBean();
        rankItem2.setUserRank(2);
        rankItem2.setNickname("老刘不开车");
        rankItem2.setUserImagePath(R.drawable.head_img_2);
        rankItem2.setCarbonCredits(14478);
        monthRankItemList.add(rankItem2);

        RankBean.ResultBean.UserListBean rankItem3 = new RankBean.ResultBean.UserListBean();
        rankItem3.setUserRank(3);
        rankItem3.setNickname("晴天里");
        rankItem3.setUserImagePath(R.drawable.head_img_3);
        rankItem3.setCarbonCredits(14367);
        monthRankItemList.add(rankItem3);

        RankBean.ResultBean.UserListBean rankItem4 = new RankBean.ResultBean.UserListBean();
        rankItem4.setUserRank(4);
        rankItem4.setNickname("江花红似火");
        rankItem4.setUserImagePath(R.drawable.head_img_4);
        rankItem4.setCarbonCredits(14177);
        monthRankItemList.add(rankItem4);

        RankBean.ResultBean.UserListBean rankItem5 = new RankBean.ResultBean.UserListBean();
        rankItem5.setUserRank(5);
        rankItem5.setNickname("Joker");
        rankItem5.setUserImagePath(R.drawable.head_img_5);
        rankItem5.setCarbonCredits(13905);
        monthRankItemList.add(rankItem5);

        RankBean.ResultBean.UserListBean rankItem6 = new RankBean.ResultBean.UserListBean();
        rankItem6.setUserRank(6);
        rankItem6.setNickname("豆豆");
        rankItem6.setUserImagePath(R.drawable.head_img_6);
        rankItem6.setCarbonCredits(13790);
        monthRankItemList.add(rankItem6);

        RankBean.ResultBean.UserListBean rankItem7 = new RankBean.ResultBean.UserListBean();
        rankItem7.setUserRank(7);
        rankItem7.setNickname("海绵爸爸");
        rankItem7.setUserImagePath(R.drawable.head_img_7);
        rankItem7.setCarbonCredits(13659);
        monthRankItemList.add(rankItem7);

        RankBean.ResultBean.UserListBean rankItem8 = new RankBean.ResultBean.UserListBean();
        rankItem8.setUserRank(8);
        rankItem8.setNickname("枣子哥");
        rankItem8.setUserImagePath(R.drawable.head_img_8);
        rankItem8.setCarbonCredits(12562);
        monthRankItemList.add(rankItem8);

        RankBean.ResultBean.UserListBean rankItem9 = new RankBean.ResultBean.UserListBean();
        rankItem9.setUserRank(9);
        rankItem9.setNickname("奶粉");
        rankItem9.setUserImagePath(R.drawable.head_img_9);
        rankItem9.setCarbonCredits(12306);
        monthRankItemList.add(rankItem9);

        RankBean.ResultBean.UserListBean rankItem10 = new RankBean.ResultBean.UserListBean();
        rankItem10.setUserRank(10);
        rankItem10.setNickname("采姑娘的小蘑菇");
        rankItem10.setUserImagePath(R.drawable.head_img_10);
        rankItem10.setCarbonCredits(12003);
        monthRankItemList.add(rankItem10);

        RankBean.ResultBean.UserListBean rankItem11 = new RankBean.ResultBean.UserListBean();
        rankItem11.setUserRank(11);
        rankItem11.setNickname("当打之人");
        rankItem11.setUserImagePath(R.drawable.head_img_11);
        rankItem11.setCarbonCredits(11059);
        monthRankItemList.add(rankItem11);

        RankBean.ResultBean.UserListBean rankItem12 = new RankBean.ResultBean.UserListBean();
        rankItem12.setUserRank(12);
        rankItem12.setNickname("东方没赢过");
        rankItem12.setUserImagePath(R.drawable.head_img_12);
        rankItem12.setCarbonCredits(15469);
        monthRankItemList.add(rankItem12);

    }

    /**
     * 从服务器获取月榜数据
     */
    public void queryMonthRankingList(){
        HttpUtils.getInfo(HttpUtils.userRankingInfoUrl+"?user_id="+ UserInfo.id
                +"&city_id=" + MySharedPreferencesUtils.getString(getContext(), "city_id")
                + "&rank_type=" + 0, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(activity, "服务器故障");
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "queryTotalRankingList code="+code);

                String responseContent = response.body().string();
                Log.d(TAG, "queryTotalRankingList responseContent="+responseContent);

                if(code==200){
                    RankBean bean = new Gson().fromJson(responseContent, RankBean.class);
                    if(bean.getMsg_code().equals("0000")){
                        monthRankItemList = bean.getResult().getUser_list();
                        initRecyclerView();
                    }else {
                        ToastUtils.showToast(activity, bean.getMsg_message());
                    }
                }else {
                    ToastUtils.showToast(activity, "服务器错误");
                }
            }
        });
    }

}
