package com.example.lctripsteward.bottomnavigation.store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CommodityBean;

import java.util.List;

public class CommodityItemAdapter extends RecyclerView.Adapter<CommodityItemAdapter.StoreViewHolder> {
    private Context context;
    private List<CommodityBean> commodityBeanList;

    static class StoreViewHolder extends RecyclerView.ViewHolder{
        ImageView imageCommodity;  //商品图片
        TextView textCommodityName;  //商品名
        TextView textCommodityPrice;  //商品价格
        TextView tvCreditsCost;  //所需碳积分

        public StoreViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            imageCommodity = itemView.findViewById(R.id.imageCommodity);
            textCommodityName = itemView.findViewById(R.id.textCommodityName);
            tvCreditsCost = itemView.findViewById(R.id.textCommodityCreditsCost);
            textCommodityPrice = itemView.findViewById(R.id.textCommodityPrice);
        }
    }

    public CommodityItemAdapter(Context context, List<CommodityBean> commodityBeanList){
        this.context = context;
        this.commodityBeanList = commodityBeanList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commodity, viewGroup, false);
       StoreViewHolder viewHolder = new StoreViewHolder(view, context);

        return viewHolder;
    }


    //尚未加载商品图片！
    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, final int i) {
        final CommodityBean commodityBean = commodityBeanList.get(i);
        storeViewHolder.tvCreditsCost.setText(commodityBean.getCommodityResultBean().getCarbonCreditsNeeds()+"");  //所需碳积分
        storeViewHolder.textCommodityPrice.setText(String.valueOf(commodityBean.getCommodityResultBean().getCommodityPrice()));  //加载商品价格
        storeViewHolder.textCommodityName.setText(commodityBean.getCommodityResultBean().getCommodityName());  //加载商品名
        Glide.with(context)
                .load(commodityBean.getCommodityResultBean().getCommodityPicture())
                .into(storeViewHolder.imageCommodity);
        storeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, commodityBean.getCommodityResultBean().getCommodityName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CommodityInfoActivity.class);
                intent.putExtra("commodityBean", commodityBean);

                Activity activity = (Activity)context;
                activity.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return commodityBeanList.size();
    }
}
