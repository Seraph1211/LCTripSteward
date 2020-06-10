package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

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
import com.example.lctripsteward.beans.MerchantBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyPasswordFragment extends Fragment {
    private static final String TAG = "ModifyPassword";
    View view;
    private EditText editFormerPassword;
    private EditText editNewPassword;
    private Button buttonModifyPassword;
    private MerchantBean merchantBean;
    private ModifyMerchantInfoActivity activity;

    private String formerPassword;
    private String newPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modify_password, container, false);

        initView();

        return view;
    }

    public void initView(){
        activity = (ModifyMerchantInfoActivity) getActivity();

        editFormerPassword = view.findViewById(R.id.editFormerPassword);
        editNewPassword = view.findViewById(R.id.editNewPassword);
        buttonModifyPassword = view.findViewById(R.id.buttonModifyPassword);

        buttonModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formerPassword = editFormerPassword.getText().toString();
                newPassword = editNewPassword.getText().toString();

                if(TextUtils.isEmpty(formerPassword) || TextUtils.isEmpty(newPassword)){
                    Toast.makeText(getContext(), "旧密码或新密码不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    if(formerPassword.equals(newPassword)){
                        Toast.makeText(getContext(), "新旧密码不能相同！", Toast.LENGTH_SHORT).show();;
                    }else {
                        //发起网络请求
                        modifyPassword();
                    }
                }

            }
        });
    }

    public void modifyPassword(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", UserInfo.id);
        map.put("oldPassword", formerPassword);
        map.put("merchantPassword", newPassword);

        HttpUtils.postMap(HttpUtils.merchantModifyPasswordUrl+"&oldPassword="+formerPassword+"&merchantPassword="+newPassword, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "modifyPassword code="+code);

                String responseContent = response.body().string();
                Log.d(TAG, "onResponse: "+responseContent);

                String verifyResult = "";
                String modifyResult = "";

                try {
                    JSONObject jsonObject = new JSONObject(responseContent);
                    verifyResult = jsonObject.getString("verifyResult");
                    modifyResult = jsonObject.getString("modifyResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(TextUtils.isEmpty(verifyResult) || TextUtils.isEmpty(modifyResult)){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    if(verifyResult.equals("false") && modifyResult.equals("false")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "旧密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if(verifyResult.equals("true") && modifyResult.equals("false")){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "密码修改失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        MySharedPreferencesUtils.putString(getContext(), "merchant_password", newPassword);  //本地存储
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                            }
                        });

                        activity.finish();  //结束活动
                    }
                }
            }
        });
    }

}
