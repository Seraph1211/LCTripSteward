package com.example.lctripsteward.bottomnavigation.userinfo.team;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.lctripsteward.utils.MySharedPreferencesUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddTeamFragment extends Fragment {
    private static final String TAG = "AddTeamFragment";
    View view;
    private EditText editAddTeam;
    private Button buttonAddTeam2;
    private String teamName;

    private TeamActivity activity;

    //textWatcher的作用是保证应该输入数字的部分不会输入文本
    private TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {
            String num = s.toString();
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(num);
            if (!m.matches()) {
                Toast.makeText(getContext(), "请输入数字！", Toast.LENGTH_SHORT).show();
                s.clear();

            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_team, container, false);

        activity = (TeamActivity) getActivity();

        return view;
    }

    public void initView(){
        editAddTeam = view.findViewById(R.id.editAddTeam);
        buttonAddTeam2 = view.findViewById(R.id.buttonAddTeam2);

        editAddTeam.addTextChangedListener(textWatcher);
        buttonAddTeam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editAddTeam.getText().toString())){
                    addToTeam();
                }else {
                    Toast.makeText(getContext(), "队伍ID不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addToTeam(){
        HttpUtils.getInfo(HttpUtils.addUserToTeamUrl + "?user_id=" + UserInfo.id
                + "&team_id=" + Integer.valueOf(editAddTeam.getText().toString()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getContext(), "加入队伍失败，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                Toast.makeText(getContext(), "加入成功!", Toast.LENGTH_SHORT).show();
                MySharedPreferencesUtils.putInt(getContext(), "team_id", Integer.valueOf(editAddTeam.getText().toString()));  //更新本地team_id
                activity.initData();  //重新加载TeamActivity
            }
        });
    }
}
