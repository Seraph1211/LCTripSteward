package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.MerchantBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyNormalInfoFragment extends Fragment {
    private static final String TAG = "ModifyNormalInfo";
    View view;
    private EditText editInfo;
    private Button buttonModify;
    private MerchantBean merchantBean;
    private String formerInfo = "";
    private String newInfo = "";
    private int type = -1;
    private ModifyMerchantInfoActivity activity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modify_normal_info, container, false);

        initView();

        return view;
    }



    public void initView(){
        activity = (ModifyMerchantInfoActivity)getActivity();
        merchantBean = activity.getMerchantBean();
        type = activity.getType();

        editInfo = view.findViewById(R.id.editSetMerchantInfo);
        buttonModify = view.findViewById(R.id.buttonSetMerchantInfo);

        //根据type加载EditText中的提示
        switch (type){
            case MerchantInfoActivity.MERCHANT_NAME:{
                formerInfo = merchantBean.getMerchantName();
                editInfo.setText(formerInfo);
                break;
            }
            case MerchantInfoActivity.MERCHANT_PHONE:{
                formerInfo = merchantBean.getMerchantPhoneNumber();
                editInfo.setInputType(InputType.TYPE_CLASS_PHONE);
                editInfo.setText(formerInfo);
                break;
            }
            case MerchantInfoActivity.MERCHANT_ADDRESS:{
                formerInfo = merchantBean.getMerchantAddress();
                editInfo.setText(formerInfo);
                break;
            }
            case MerchantInfoActivity.MERCHANT_INTRODUCTION:{
                formerInfo = merchantBean.getMerchantIntroduce();
                editInfo.setText(formerInfo);
                break;
            }
        }

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newInfo = editInfo.getText().toString();
                if(TextUtils.isEmpty(newInfo)){
                    Toast.makeText(getContext(), "修改内容不能为空！", Toast.LENGTH_SHORT).show();
                }else if(newInfo.equals(formerInfo)){
                    Toast.makeText(getContext(), "未作任何修改！", Toast.LENGTH_SHORT).show();
                }else{
                    //根据type来修改bean
                    switch (type){
                        case MerchantInfoActivity.MERCHANT_NAME:{
                            merchantBean.setMerchantName(newInfo);
                            break;
                        }
                        case MerchantInfoActivity.MERCHANT_PHONE:{
                            merchantBean.setMerchantPhoneNumber(newInfo);

                            break;
                        }
                        case MerchantInfoActivity.MERCHANT_ADDRESS:{
                            merchantBean.setMerchantAddress(newInfo);
                            break;
                        }
                        case MerchantInfoActivity.MERCHANT_INTRODUCTION:{
                            merchantBean.setMerchantIntroduce(newInfo);
                            break;
                        }
                    }
                    postMerchantBean();
                }
            }
        });
    }

    public void postMerchantBean(){

        Log.d(TAG, "postMerchantBean: bean="+merchantBean);
        HttpUtils.postBean(HttpUtils.merchantModifyUrl, merchantBean, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                Toast.makeText(getContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "postMerchantBean code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "onResponse: "+responseContent);

                    String modifyResult = "";

                    try {
                        JSONObject jsonObject = new JSONObject(responseContent);
                        modifyResult = jsonObject.getString("modifyResult");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(!TextUtils.isEmpty(modifyResult)){
                        if(modifyResult.equals("true")){
                            MySharedPreferencesUtils.saveMerchantInfo(getContext(), merchantBean);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            activity.finish();  //结束活动
                        }else {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }else {
                    ToastUtils.showToast(getContext(), "服务器错误");
                }

            }
        });
    }
}
