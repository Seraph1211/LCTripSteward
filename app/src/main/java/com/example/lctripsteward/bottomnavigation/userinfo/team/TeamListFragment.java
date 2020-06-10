package com.example.lctripsteward.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.TeamBean;

import java.util.ArrayList;
import java.util.List;

public class TeamListFragment extends Fragment {
    private static final String TAG = "TeamListFragment";
    private View view;
    private RecyclerView recyclerViewMemberList;
    private List<TeamBean.ResultBean.UserListBean> memberList = new ArrayList<>();
    private TeamActivity activity;
    private TextView textTeamCarbonCredits;
    private TextView textTeamRanking;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_list, container, false);

        init();

        return view;
    }

    public void init(){
        activity = (TeamActivity)getActivity();
        textTeamCarbonCredits = view.findViewById(R.id.textTeamCarbonCredits);
        textTeamRanking = view.findViewById(R.id.textTeamRanking);

        /*
        activity.setTeamName(activity.getTeamBean().getResult().getTeamName());  //将toolbar上的标题设为队伍名
        textTeamRanking.setText(activity.getTeamBean().getResult().getTeamRank()+"");
        textTeamCarbonCredits.setText(activity.getTeamBean().getResult().getTeamCarbonCredits()+"");
         */

        activity.setTeamName("爱猫啥都队");
        textTeamRanking.setText("1");
        textTeamCarbonCredits.setText(33300+"");

        TeamBean.ResultBean.UserListBean bean1 = new TeamBean.ResultBean.UserListBean();
        bean1.setImg(R.drawable.head_img_4);
        bean1.setNickname("Joker");
        memberList.add(bean1);

        TeamBean.ResultBean.UserListBean bean2 = new TeamBean.ResultBean.UserListBean();
        bean2.setImg(R.drawable.head_img_9);
        bean2.setNickname("背对疾风吧");
        memberList.add(bean2);

        TeamBean.ResultBean.UserListBean bean3 = new TeamBean.ResultBean.UserListBean();
        bean3.setImg(R.drawable.head_img_7);
        bean3.setNickname("不想起床");
        memberList.add(bean3);

        TeamBean.ResultBean.UserListBean bean4 = new TeamBean.ResultBean.UserListBean();
        bean4.setImg(R.drawable.head_img_2);
        bean4.setNickname("Loser");
        memberList.add(bean4);

        TeamBean.ResultBean.UserListBean bean5 = new TeamBean.ResultBean.UserListBean();
        bean5.setImg(R.drawable.head_img_11);
        bean5.setNickname("巴里巴里");
        memberList.add(bean5);


        //初始化recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMemberList = view.findViewById(R.id.recyclerViewTeamList);
        recyclerViewMemberList.setLayoutManager(layoutManager);
        //memberList = activity.getMemberList();
        recyclerViewMemberList.setAdapter(new TeamMemberAdapter(getContext(), memberList));

    }

}
