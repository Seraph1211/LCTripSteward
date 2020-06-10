package com.example.lctripsteward.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateTeamFragment extends Fragment {
    private static final String TAG = "CreateTeamFragment";
    View view;
    private Button buttonCreateTeam2;
    private EditText editCreateTeam;
    private TeamActivity activity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_team, container, false);

        activity = (TeamActivity)getActivity();

        return view;
    }

    public void initView(){
        buttonCreateTeam2 = view.findViewById(R.id.buttonCreateTeam2);
        editCreateTeam = view.findViewById(R.id.editCreateTeam);

        buttonCreateTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editCreateTeam.getText().toString())){
                    createNewTeam();
                }else {
                    Toast.makeText(getContext(), "请输入队伍名", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void createNewTeam(){
        HttpUtils.getInfo(HttpUtils.createTeamUrl + "?user_id=" + UserInfo.id
                + "&team_name="+editCreateTeam.getText().toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getContext(), "创建失败，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                Toast.makeText(getContext(), "创建成功!", Toast.LENGTH_SHORT).show();

                /*需要服务器返回team_id
                MySharedPreferencesUtils.putInt(getContext(), "team_id", Integer.valueOf(editCreateTeam.getText().toString()));  //更新本地team_id
                */
                activity.initData();  //重新加载TeamActivity
         }
        });

    }
}
