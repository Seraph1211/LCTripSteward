package com.example.lctripsteward.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lctripsteward.R;

public class NoTeamFragment extends Fragment {
    View view;
    TeamActivity teamActivity;
    private Button buttonAddTeam;  //加入队伍
    private Button buttonCreateTeam;  //创建队伍

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_no_team, container, false);

        teamActivity = (TeamActivity)getActivity();
        buttonAddTeam = view.findViewById(R.id.buttonAddTeam);
        buttonCreateTeam = view.findViewById(R.id.buttonCreateTeam);

        //为button注册点击事件
        buttonAddTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                teamActivity.setFragment(new AddTeamFragment());
            }
        });

        buttonCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamActivity.setFragment(new CreateTeamFragment());
            }
        });

        return view;
    }
}
