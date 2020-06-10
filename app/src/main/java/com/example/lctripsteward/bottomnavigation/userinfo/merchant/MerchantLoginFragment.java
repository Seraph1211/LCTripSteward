package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.utils.Base64Utils;
import com.example.lctripsteward.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MerchantLoginFragment extends Fragment {
    private static final String TAG = "MerchantLogin";
    View view;
    private EditText editPasswordLogin;
    private EditText editSecurityCodeLogin;
    private ImageView imageSecurityCodeLogin;
    private Button buttonMerchantLogin;

    private String passwordLogin;
    private String securityCodeLogin;
    private String base64Code;

    MerchantActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_login, container, false);

        initView();

        return view;
    }

    //初始化各控件
    public void initView(){
        activity = (MerchantActivity)getActivity();

        editPasswordLogin = view.findViewById(R.id.editMerchantPasswordLogin);
        editSecurityCodeLogin = view.findViewById(R.id.editSecurityCodeLogin);
        buttonMerchantLogin = view.findViewById(R.id.buttonLoginMerchant);
        imageSecurityCodeLogin = view.findViewById(R.id.imageSecurityCodeLogin);

        if(activity.getBase64Code()!=null){
            base64Code = activity.getBase64Code();
            Base64Utils.loadBase64Image(base64Code, imageSecurityCodeLogin);

        }
        Log.d(TAG, "initView: base64Code="+base64Code);

        /**
         * 注册监听事件
         * 将验证码和密码提交给服务端，根据服务端返回的信息执行相应的操作
         */
        buttonMerchantLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTextData();
                postLoginInfo();
            }
        });
    }

    //获取EditText中的数据
    public void getEditTextData(){
        passwordLogin = editPasswordLogin.getText().toString();
        securityCodeLogin = editSecurityCodeLogin.getText().toString();
    }

    public void postLoginInfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", UserInfo.id);
        map.put("merchantPassword", passwordLogin);
        map.put("imageCode", securityCodeLogin);
        Log.d(TAG, "postLoginInfo: map:"+map.get("merchantPassword"));
        HttpUtils.postMap(HttpUtils.merchantLoginUrl + "?rememberMe="+true, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: responseContent="+responseContent);

                String merchantResult = "";
                String imageResult = "";
                String token = "";

                try {
                    JSONObject jsonObject = new JSONObject(responseContent);
                    merchantResult = jsonObject.getString("merchantResult");
                    imageResult = jsonObject.getString("imageResult");
                    token = jsonObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!TextUtils.isEmpty(merchantResult)){  //merchantResult不为空
                    if(merchantResult.equals("登陆失败")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });
    }

}
