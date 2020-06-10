package com.example.lctripsteward.bottomnavigation.userinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyUserDataActivity extends AppCompatActivity {
    private final String TAG = "ModifyActivity";
    private TextView tvTitle;
    private AppCompatEditText etContent;
    private Button btnModify;
    private int type;  //要修改的数据：0-nickname, 1-pwd
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_data);

        initData();
        initView();
    }


    private void initView(){
        tvTitle = findViewById(R.id.tv_modify_user_data);
        etContent = findViewById(R.id.et_modify_user_data);
        btnModify = findViewById(R.id.btn_modify_user_data);

        if(type == 0){
            tvTitle.setText("修改昵称");
        }else {
            tvTitle.setText("修改密码");
        }

        etContent.setHint(content);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etContent.getText().toString().trim();
                if(type == 0){
                    modifyNickname();
                }else {
                    modifyPwd();
                }
            }
        });
    }

    private void initData(){
        type = getIntent().getIntExtra("type", -1);
        content = getIntent().getStringExtra("content");
    }

    public void back(View view){
        finish();
    }

    /**
     * 修改昵称
     */
    private void modifyNickname(){
        if(content.equals("")){
            ToastUtils.showToast(ModifyUserDataActivity.this, "昵称不能为空！");
            return;
        }else if(content.length() > 20){
            ToastUtils.showToast(ModifyUserDataActivity.this, "请输入20个字符以内的昵称！");
            return;
        }

        HttpUtils.getInfo(HttpUtils.userUpdateNameUrl + "?user_id=" + UserInfo.id + "&nickname=" + content,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: " );
                        ToastUtils.showToast(ModifyUserDataActivity.this, "服务器故障");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int code = response.code();
                        Log.i(TAG, "onResponse: code="+code);

                        String responseContent = response.body().string();
                        Log.i(TAG, "onResponse: responseContent="+responseContent);

                        if(code==200){
                            String msg_code = "";
                            try {
                                msg_code = new JSONObject(responseContent).getString("msg_code");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(msg_code.equals("0000")){
                                ToastUtils.showToast(ModifyUserDataActivity.this, "昵称修改成功！");
                                MySharedPreferencesUtils.putString(ModifyUserDataActivity.this, "nickname", content);
                                Intent intent = new Intent();
                                intent.putExtra("new_name", content);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }else {
                            ToastUtils.showToast(ModifyUserDataActivity.this, "服务器出错！");
                        }
                    }
                });
    }

    /**
     * 修改密码
     */
    private void modifyPwd(){
        if(content.equals("")){
            ToastUtils.showToast(ModifyUserDataActivity.this, "密码不能为空！");
            return;
        }else if(content.length()<6 || content.length()>12){
            ToastUtils.showToast(ModifyUserDataActivity.this, "请输入6-12位的密码！");
            return;
        }

        HttpUtils.getInfo(HttpUtils.userUpdatePwdUrl + "?user_id=" + UserInfo.id + "&user_password=" + content,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: " );
                        ToastUtils.showToast(ModifyUserDataActivity.this, "服务器故障");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int code = response.code();
                        Log.i(TAG, "onResponse: code="+code);

                        String responseContent = response.body().string();
                        Log.i(TAG, "onResponse: responseContent="+responseContent);

                        if(code==200){
                            String msg_code = "";
                            try {
                                msg_code = new JSONObject(responseContent).getString("msg_code");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(msg_code.equals("0000")){
                                ToastUtils.showToast(ModifyUserDataActivity.this, "密码修改成功！");
                                MySharedPreferencesUtils.putString(ModifyUserDataActivity.this, "password", content);
                                setResult(RESULT_OK);
                                finish();
                            }
                        }else {
                            ToastUtils.showToast(ModifyUserDataActivity.this, "服务器出错！");
                        }
                    }
                });

    }
}
