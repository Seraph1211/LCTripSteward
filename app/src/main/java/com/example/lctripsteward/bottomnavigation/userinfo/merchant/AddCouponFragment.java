package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CouponResultBean;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AddGoodsActivity中添加coupon(优惠券)的Fragment
 */
public class AddCouponFragment extends Fragment {
    private static final String TAG = "AddCouponFragment";

    private int useStoreId;  //优惠券对应的商店
    private String couponName;  //优惠券名称
    private int couponType;  //优惠券类型
    private int useType;  //优惠券使用类型
    private int couponCost;  //所需碳积分
    private int couponSill;  //使用门槛
    private int couponValue;  //优惠券价值
    private DateTime expirationTime;  //过期时间

    private EditText editCouponName;
    private EditText editCouponCost;
    private EditText editCouponSill;
    private EditText editCouponValue;
    private TextView textCouponExpirationTime;

    private View view;
    private AddGoodsActivity activity;
    private Date date = new Date();
    private CouponResultBean.ResultBean.CouponBean bean;

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
        view = inflater.inflate(R.layout.fragment_add_coupon, container, false);

        initView();

        return view;
    }

    public void initView(){
        activity = (AddGoodsActivity)getActivity();
        couponType = activity.getGoodType();

        editCouponName = view.findViewById(R.id.editSetCouponName);
        editCouponCost = view.findViewById(R.id.editSetCouponCreditsNeed);
        textCouponExpirationTime = view.findViewById(R.id.textSetCouponExpirationTime);
        editCouponSill = view.findViewById(R.id.editSetCouponSill);
        editCouponValue = view.findViewById(R.id.editSetCouponValue);

        editCouponCost.addTextChangedListener(textWatcher);
        editCouponSill.addTextChangedListener(textWatcher);
        editCouponValue.addTextChangedListener(textWatcher);

        textCouponExpirationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog(activity, 2, textCouponExpirationTime, date);

                //提交的时候再将date转为dateTime
                //expirationTime = new DateTime(date.getTime());

            }
        });

        //在fragment中设置Activity中button的点击事件
        activity.getButtonAddGoods().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getTextData()){
                    return;
                }
                Log.d(TAG, "onClick: 546465456");


            }
        });
    }

    /**
     * 获取优惠券的信息
     * @return 信息不完全返回false
     */
    public boolean getTextData(){

        if(TextUtils.isEmpty(editCouponCost.getText().toString()) || TextUtils.isEmpty(editCouponName.getText().toString())
        || TextUtils.isEmpty(editCouponSill.getText().toString()) || TextUtils.isEmpty(editCouponValue.getText().toString())){
            Toast.makeText(getContext(), "请填写全部的优惠券信息！", Toast.LENGTH_SHORT).show();
            return false;
        }

        couponName = editCouponName.getText().toString();
        couponCost = Integer.parseInt(editCouponCost.getText().toString());
        couponSill = Integer.parseInt(editCouponSill.getText().toString());
        couponValue = Integer.parseInt(editCouponValue.getText().toString());

        return true;
    }

    /**
     * 用HashMap来封装Coupon信息
     * @return
     */
    public HashMap getCouponInfoMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("coupon_name", couponName);
        map.put("coupon_type", couponType);
        map.put("coupon_cost", couponCost);
        map.put("coupon_sill", couponSill);
        map.put("coupon_value", couponValue);
        map.put("expiration_time", new DateTime(date.getTime()));

        return map;
    }

    /**
     * 向后台提交要添加的优惠券的相关数据
     */
    public void postCouponInfo(){

    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param date
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, final Date date) {

        final Calendar calendar = Calendar.getInstance(Locale.CHINA);

        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText( + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                date.setYear(year);
                date.setMonth(monthOfYear);
                date.setDate(dayOfMonth);
                Log.d(TAG, "onDateSet: date="+date.toString());
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
