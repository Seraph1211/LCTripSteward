package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CouponResultBean;
import com.example.lctripsteward.beans.MerchantBean;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MerchantHomeFragment extends Fragment implements View.OnClickListener{
    View view;
    private MerchantActivity activity;

    private ImageView imageMerchantScan;  //扫一扫
    private ImageView imageMerchantAddTicket;  //添加券
    private ImageView imageMerchantInfo;  //商家个人信息中心
    private RecyclerView recyclerAddedGoods;  //在首页展示的已添加的优惠券，目前不清楚要展示什么，暂时搁置
    private GoodsAdapter goodsAdapter;
    private List<CouponResultBean.ResultBean.CouponBean> addedCouponList = new ArrayList<>();
    private MerchantBean bean = null;

    private List<Integer> imageUrlList;
    private List<String> titleList;
    private Banner banner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_merchant_home, container, false);

        initView();
        initBanner();
        initRecyclerView();

        //Base64Utils.loadBase64Image("data:image/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAjCAIAAACmdes6AAABRUlEQVR42u3Yyw3CMAwGYEsMwBbswSAwBafeOHNg5FKEQFacOL8TN01RqhyqKJX8KQ+7obnXhyjf4h/uiIR+OP/dM0iDNEiDNEj9JqsWpMt0S7Xo+PvzkW3WisGNpGAUGBi6tWhoSkLiJupjLxWQ+CxZN0k7EqiVC09WqL2feDiJwxxIp+nMmxyRHeBF8lJRELROWnWWlEV4OF6XFoz8dMr+tUjIXsompXKSEnfZqtNTE5Jqg0UoQ/95qkg1J3uUhP+rp0hSu7yTMhsFU2SqHkw3EEHceVJ0QkwevMwzkaInIWfId1K2DU4qK1tx0jvQryrYQhpJqkCSyVNG4qroIuQvEMnRU0PiKpSUUlWWraZUq5M4TPajpPpK3J0kz3e+o7YkZbOtXiugJFM62paUpDrWqZ3eEBX/SnRHsp7dg9QBade3rS+1zQqnEpXIrwAAAABJRU5ErkJggg==", imageMerchantHome);

        return view;
    }

    public void initView(){
        activity = (MerchantActivity) getActivity();

        imageMerchantAddTicket = view.findViewById(R.id.imageMerchantAddCard);
        imageMerchantScan = view.findViewById(R.id.imageMerchantScan);
        imageMerchantInfo = view.findViewById(R.id.imageMerchantInfo);

        imageMerchantScan.setOnClickListener(this);
        imageMerchantAddTicket.setOnClickListener(this);
        imageMerchantInfo.setOnClickListener(this);
    }

    public void initBanner(){
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();

        imageUrlList.add(R.drawable.banner_1);
        imageUrlList.add(R.drawable.banner_2);
        imageUrlList.add(R.drawable.banner_3);
        imageUrlList.add(R.drawable.banner_4);

        titleList.add("低碳环保，新能源出行");
        titleList.add("健康生活，低碳出行");
        titleList.add("爱护地球，人人有责");
        titleList.add("3.30 地球一小时");

        banner = view.findViewById(R.id.bannerMerchantHome);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new MyLoader());
        banner.setImages(imageUrlList);
        banner.setBannerTitles(titleList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(2000);  //切换频率
        banner.isAutoPlay(true);  //自动启动
        banner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        banner.start();

    }

    public void initRecyclerView(){
        for(int i=0; i<10; i++){
            CouponResultBean.ResultBean.CouponBean c = new CouponResultBean.ResultBean.CouponBean();
            c.setCoupon_name("三花聚顶红包");
            c.setValue(20);
            addedCouponList.add(c);
        }

        recyclerAddedGoods = view.findViewById(R.id.recyclerViewGoodsAdded);
        goodsAdapter = new GoodsAdapter(getContext(), addedCouponList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAddedGoods.setLayoutManager(layoutManager);
        recyclerAddedGoods.setAdapter(goodsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageMerchantScan:{
                Toast.makeText(getContext(), "扫一扫！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 2);
                break;
            }
            case R.id.imageMerchantAddCard:{
                Toast.makeText(getContext(), "添加优惠券！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), AddGoodsActivity.class));
                break;
            }
            case R.id.imageMerchantInfo:{
                Toast.makeText(getContext(), "商家信息！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MerchantInfoActivity.class));
                break;
            }
        }
    }

    class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.CouponViewHolder>{
        Context context;
        List<CouponResultBean.ResultBean.CouponBean> couponBeanList;


        public GoodsAdapter(Context context, List<CouponResultBean.ResultBean.CouponBean> couponBeanList){
            this.context = context;
            this.couponBeanList = couponBeanList;
        }

        @NonNull
        @Override
        public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_coupon_merchant, viewGroup, false);
            CouponViewHolder viewHolder = new CouponViewHolder(context, view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CouponViewHolder couponViewHolder, int i) {

            CouponResultBean.ResultBean.CouponBean couponBean = couponBeanList.get(i);
            couponViewHolder.textCouponName.setText(couponBean.getCoupon_name());
            couponViewHolder.textCouponValue.setText(couponBean.getValue()+"");
            /*
             *过期时间、商家尚未设置
             * couponViewHolder.textCouponSill.setText(couponBean.getSill());
             * couponViewHolder.textCouponValue.setText(couponBean.getValue());
             */

            couponViewHolder.buttonDeleteCoupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Delete Coupon!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return couponBeanList.size();
        }

        class CouponViewHolder extends RecyclerView.ViewHolder{

            TextView textCouponValue;
            TextView textCouponName;
            TextView textCouponMerchant;
            TextView textCouponExpirationTime;
            TextView textCouponSill;
            Button buttonDeleteCoupon;

            public CouponViewHolder(Context context, @NonNull View itemView) {
                super(itemView);

                textCouponName = itemView.findViewById(R.id.textCouponName);
                textCouponMerchant = itemView.findViewById(R.id.textCouponMerchant);
                textCouponValue = itemView.findViewById(R.id.textCouponValue);
                textCouponSill = itemView.findViewById(R.id.textCouponSill);
                textCouponExpirationTime = itemView.findViewById(R.id.textCouponExpirationTime);
                buttonDeleteCoupon = itemView.findViewById(R.id.buttonDeleteCoupon);
            }
        }
    }


    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity())
                    .load(path).
                    into(imageView);
        }
    }

}
