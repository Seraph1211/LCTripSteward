package com.example.lctripsteward.bottomnavigation.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CouponResultBean;

import java.util.ArrayList;
import java.util.List;

public class CouponFragment extends Fragment {
    private View view;
    RecyclerView rvCoupon;
    List<CouponResultBean.ResultBean.CouponBean> couponList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coupon, container, false);

        initView();

        return view;
    }

    public void initView(){

        initList();
        rvCoupon = view.findViewById(R.id.rv_coupon);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvCoupon.setLayoutManager(mLinearLayoutManager);
        rvCoupon.setAdapter(new CouponItemAdapter(getContext(), couponList));

    }


    public void initList(){
        String couponNames[] = {"饿了么无门槛红包", "美团无门槛红包", "肯德基优惠券", "麦当劳满减券","西门烧烤抵扣券","饿了么无门槛红包", "美团无门槛红包", "肯德基优惠券", "麦当劳满减券","西门烧烤抵扣券"};
        int couponValues[] = {10, 6, 20, 15, 8, 10, 6, 20, 15, 8};
        int couponCost[] = {100, 80, 160, 120, 150, 100, 80, 160, 120, 150};
        for (int i=0; i<10; i++){
            CouponResultBean.ResultBean.CouponBean bean = new CouponResultBean.ResultBean.CouponBean();
            bean.setValue(couponValues[i]);
            bean.setCoupon_name(couponNames[i]);
            bean.setCoupon_cost(couponCost[i]);
            couponList.add(bean);
        }
    }
}
