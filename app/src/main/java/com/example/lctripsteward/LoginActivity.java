package com.example.lctripsteward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lctripsteward.bottomnavigation.BottomNavigationActivity;
import com.example.lctripsteward.utils.Base64Utils;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.NetworkUtil;
import com.example.lctripsteward.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private CircleImageView imgUser;  //用户头像
    private EditText etAccount;  //用户账号
    private EditText etPassword;  //密码
    private AppCompatCheckBox cbRememberPassword;  //单选框，是否记住密码
    private Button btnLogin;  //登录按钮
    private TextView tvForgetPassword;  //忘记密码
    private TextView tvJumpToRegister;  //跳转至注册界面

    private String account;
    private String password;
    private String img;
    private boolean rememberPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initView();
    }

    public void initView(){
        imgUser = findViewById(R.id.img_user);
        etAccount = findViewById(R.id.et_login_account);
        etPassword = findViewById(R.id.et_login_password);
        cbRememberPassword = findViewById(R.id.cb_remember_password);
        btnLogin = findViewById(R.id.btn_login);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        tvJumpToRegister = findViewById(R.id.tv_jump_to_register);

        btnLogin.setOnClickListener(this);
        tvJumpToRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);


        //刷新信息
        if(!account.equals("empty")){  //如果本地有记录用户信息
            etAccount.setText(account);

            if(!img.equals("empty")){  //用户头像有做本地存储
                Base64Utils.loadBase64Image(img, imgUser);
            }
        }

        if(rememberPwd){
            cbRememberPassword.setChecked(true);
            etPassword.setText(password);
        }else {
            cbRememberPassword.setChecked(false);
        }
    }

    public void initData(){
        account = MySharedPreferencesUtils.getString(LoginActivity.this, "account");
        password = MySharedPreferencesUtils.getString(LoginActivity.this, "password");
        rememberPwd = MySharedPreferencesUtils.getBoolean(LoginActivity.this, "remember_pwd");
        img = MySharedPreferencesUtils.getString(LoginActivity.this, "img");
    }

    @Override
    protected void onDestroy(){
        MySharedPreferencesUtils.putString(LoginActivity.this, "account", etAccount.getText().toString().trim());
        MySharedPreferencesUtils.putBoolean(LoginActivity.this, "remember_pwd", cbRememberPassword.isChecked());
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                login();
                break;
            }

            case R.id.tv_forget_password:{
                Toast.makeText(LoginActivity.this, "那咋办嘛，咱也没办法啊！", Toast.LENGTH_SHORT).show();

                account = etAccount.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                rememberPwd = cbRememberPassword.isChecked();
                MySharedPreferencesUtils.putString(LoginActivity.this, "account", account);
                MySharedPreferencesUtils.putString(LoginActivity.this, "password", password);
                MySharedPreferencesUtils.putBoolean(LoginActivity.this, "remember_pwd", rememberPwd);
                break;
            }

            case R.id.tv_jump_to_register:{
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            }
        }
    }

    /**
     * 登录
     */
    public void login(){
        if(!NetworkUtil.isNetworkAvailable(LoginActivity.this)){
            ToastUtils.showToast(LoginActivity.this, "未连接到网络!");
            return;
        }

        account = etAccount.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        rememberPwd = cbRememberPassword.isChecked();

        Log.d(TAG, "login: [account="+account+", pwd="+password+"]");

        HttpUtils.getInfo(HttpUtils.userLoginUrl+"?user_telephone="+account
                +"&user_password="+password,
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(LoginActivity.this, "服务器故障！");
                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.i(TAG, "code="+code);

                String responseContent = response.body().string();
                Log.i(TAG, "responseContent="+responseContent);

                if(code==200){

                    String msg_code = "";
                    try {
                        msg_code = new JSONObject(responseContent).getString("msg_code");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(msg_code.equals("0000")){
                        ToastUtils.showToast(LoginActivity.this, "登录成功！");
                        startActivity(new Intent(LoginActivity.this, BottomNavigationActivity.class));
                        finish();

                        //登录成功，将用户信息做本地存储
                        Log.d(TAG, "onResponse: [id="+Integer.parseInt(account.substring(0, 10))+", account="+account+
                                ", password="+password+"]");
                        MySharedPreferencesUtils.putInt(LoginActivity.this,"id", Integer.parseInt(account.substring(0, 10)));
                        MySharedPreferencesUtils.putString(LoginActivity.this, "account", account);
                        MySharedPreferencesUtils.putString(LoginActivity.this, "password", password);
                        MySharedPreferencesUtils.putBoolean(LoginActivity.this, "remember_pwd", rememberPwd);
                    }else {
                        ToastUtils.showToast(LoginActivity.this, "账号或密码错误！");
                    }
                }else {
                    ToastUtils.showToast(LoginActivity.this, "服务器出错！");
                }
            }
        });
    }
}
