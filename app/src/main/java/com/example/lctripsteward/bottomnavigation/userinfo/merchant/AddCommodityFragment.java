package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.lctripsteward.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AddGoodsActivity中添加commodity(碳积分+rmb商品)的Fragment
 */

public class AddCommodityFragment extends Fragment {
    private String picturePath;  //图片路径
    private String commodityName;  //商品名
    private int commodityType;  //商品种类
    private String commodityIntroduction;  //介绍
    private int commodityOriginalPrice;  //原价
    private int commodityPrice;  //现价
    private int commodityCreditsNeed;  //所需碳积分
    private int commodityRemaining;  //剩余数量

    private EditText editCommodityName;
    private EditText editCommodityType;
    private EditText editCommodityIntroduce;
    private EditText editCommodityOriginalPrice;
    private EditText editCommodityPrice;
    private EditText editCommodityCreditsNeed;
    private EditText editCommodityRemaining;

    View view;
    AddGoodsActivity activity;

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
        view = inflater.inflate(R.layout.fragment_add_commodity, container, false);

        initView();

        return view;
    }

    private void initView(){
        activity = (AddGoodsActivity)getActivity();
        commodityType = 4;

        editCommodityName = view.findViewById(R.id.editSetCommodityName);
        editCommodityRemaining = view.findViewById(R.id.editSetCommodityRemaining);
        editCommodityOriginalPrice = view.findViewById(R.id.editSetCommodityOriginalPrice);
        editCommodityPrice = view.findViewById(R.id.editSetCommodityPrice);
        editCommodityCreditsNeed = view.findViewById(R.id.editSetCommodityCreditsNeed);
        editCommodityIntroduce = view.findViewById(R.id.editSetCommodityIntroduction);

        editCommodityCreditsNeed.addTextChangedListener(textWatcher);
        editCommodityOriginalPrice.addTextChangedListener(textWatcher);
        editCommodityPrice.addTextChangedListener(textWatcher);
        editCommodityRemaining.addTextChangedListener(textWatcher);

        activity.getButtonAddGoods().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getTextData()){
                    return;
                }
            }
        });
    }

    /**
     * 获取优惠券的信息
     * @return 信息不完全返回false
     */
    public boolean getTextData(){
        if(TextUtils.isEmpty(editCommodityRemaining.getText().toString()) || TextUtils.isEmpty(editCommodityPrice.getText().toString())
                || TextUtils.isEmpty(editCommodityOriginalPrice.getText().toString()) || TextUtils.isEmpty(editCommodityCreditsNeed.getText().toString())
        || TextUtils.isEmpty(editCommodityIntroduce.getText().toString()) || TextUtils.isEmpty(editCommodityRemaining.getText().toString())){
            Toast.makeText(getContext(), "请填写全部的商品信息！", Toast.LENGTH_SHORT).show();
            return false;
        }

        commodityName = editCommodityName.getText().toString();
        commodityIntroduction = editCommodityIntroduce.getText().toString();
        commodityCreditsNeed = Integer.valueOf(editCommodityCreditsNeed.getText().toString());
        commodityOriginalPrice = Integer.valueOf(editCommodityOriginalPrice.getText().toString());
        commodityPrice = Integer.valueOf(editCommodityPrice.getText().toString());
        commodityRemaining = Integer.valueOf(editCommodityRemaining.getText().toString());

        return true;
    }

    /**
     * 用HashMap来封装Commodity的信息
     * @return
     */
    public HashMap getCouponInfoMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("commodity_picture", "");
        map.put("commodity_name", commodityName);
        map.put("commodity_type", commodityType);
        map.put("commodity_introduce", commodityIntroduction);
        map.put("commodity_price_original", commodityOriginalPrice);
        map.put("commodity_price", commodityPrice);
        map.put("commodity_carbon_credits_need", commodityCreditsNeed);
        map.put("commodity_remaining", commodityRemaining);

        return map;
    }

    /**
     * 将commodity的信息传给后台
     */
    public void postCommodityInfo(){

    }


}
