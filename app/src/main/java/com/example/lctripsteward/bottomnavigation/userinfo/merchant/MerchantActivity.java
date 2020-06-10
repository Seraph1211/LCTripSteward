package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lctripsteward.R;
import com.example.lctripsteward.RegisterActivity;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 一个Activity,多个Fragment。
 * 返回上一个Fragment: activity.setFragment(LastFragment);
 * 从服务端拿到数据后再setFragment
 */
public class MerchantActivity extends AppCompatActivity {

    private Context context = MerchantActivity.this;
    private static final String TAG = "MerchantActivity";
    private TextView textMerchant;
    private ImageButton buttonBack;

    private String base64Code = null;  //从服务端获取的base64格式的图片信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        StatusBarUtils.setStatusBarColor(MerchantActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(MerchantActivity.this, true, true);  //状态栏字体颜色-黑

        initView();

        //queryMerchantInfo();

        setFragment(new MerchantRegisterFragment());
        //setFragment(new MerchantHomeFragment());
    }

    public void initView(){
        textMerchant = findViewById(R.id.textMerchant);
        buttonBack = findViewById(R.id.buttonMerchantBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 根据user_id向服务端查询应该加载哪一个碎片(同时更改toolbar上的文字)
     * 未注册商家：注册界面
     * 已注册但已超出登录时限：登录界面
     * 已注册且未超出登录时限：商家首页
     */
    public void setFragment(String result){
        //根据服务器返回的结果来选择加载的碎片
        if(!TextUtils.isEmpty(result)){
            if(result.equals("未注册")){
                setFragment(new MerchantRegisterFragment());
            }else if(result.equals("已注册")){
                setFragment(new MerchantLoginFragment());
            }else{
                setFragment(new MerchantHomeFragment());
            }
        }
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameMerchant, fragment);
        fragmentTransaction.commit();
    }

    public void queryMerchantInfo(){
        HashMap<String, Object> map = new HashMap<>();
        String token = MySharedPreferencesUtils.getString(MerchantActivity.this, "token");
        if(token.equals("empty")){
            //则新建一个token（为空）并用sp存起来
            MySharedPreferencesUtils.putString(MerchantActivity.this, "token", "");
        }
        int userId = UserInfo.id;

        //map.put("user_id", userId);
        map.put("token", token);

        HttpUtils.postMap(HttpUtils.merchantHomeUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "queryMerchantInfo code="+code);

                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                if(code==200){

                    String result = null;
                    //使用JSONObject解析服务器返回的json字符串
                    if (!TextUtils.isEmpty(responseContent)){
                        try {
                            JSONObject jsonObject = new JSONObject(responseContent);
                            result = jsonObject.getString("merchantResult");
                            if(!TextUtils.isEmpty(result)){
                                if(result.equals("已注册") || result.equals("未注册")){
                                    base64Code = jsonObject.getString("image");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: 使用JSONObject解析数据时出错！");
                            Toast.makeText(MerchantActivity.this, "从服务器获取数据失败!", Toast.LENGTH_SHORT).show();
                        }

                        setFragment(result);
                        setFragment(new MerchantRegisterFragment());

                    }else {
                        MerchantActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MerchantActivity.this, "访问网络数据失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }

    public TextView getTextMerchant() {
        return textMerchant;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: [requestCode="+requestCode+",resultCode="+requestCode+"]");
        switch (requestCode) {
            case 2: {
                Log.d(TAG, "onActivityResult: 2");
                if (data != null) {
                    Log.d(TAG, "onActivityResult: data!=null");
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        Log.d(TAG, "onActivityResult: bundle==null");
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Toast.makeText(MerchantActivity.this, "解析结果：" + result, Toast.LENGTH_SHORT).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(MerchantActivity.this, "解析二维码失败！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onActivityResult: data==null");
                }

                break;
            }
        }
    }

    public String getBase64Code(){
        return base64Code;
    }



}
