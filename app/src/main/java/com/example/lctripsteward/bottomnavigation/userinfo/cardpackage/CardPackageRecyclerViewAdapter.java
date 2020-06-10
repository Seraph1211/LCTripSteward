package com.example.lctripsteward.bottomnavigation.userinfo.cardpackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CardPackageBean;

import java.util.List;

public class CardPackageRecyclerViewAdapter extends RecyclerView.Adapter<CardPackageRecyclerViewAdapter.CardViewHolder> {
    private Context context;
    private List<CardPackageBean.ResultBean.CouponBagBean> cardInfoBeanList;


    static class CardViewHolder extends RecyclerView.ViewHolder{

        TextView textCardValue;
        TextView textCardName;
        TextView textCardMerchant;
        TextView textCardExpirationTime;
        TextView textCardSill;
        Button buttonUseCard;

        public CardViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            textCardValue = itemView.findViewById(R.id.textCardValue);
            textCardName = itemView.findViewById(R.id.textCardName);
            textCardExpirationTime = itemView.findViewById(R.id.textCardExpirationTime);
            textCardMerchant = itemView.findViewById(R.id.textCardMerchant);
            textCardSill = itemView.findViewById(R.id.textCardSill);
            buttonUseCard = itemView.findViewById(R.id.buttonUseCard);

        }
    }

    public CardPackageRecyclerViewAdapter(Context context, List<CardPackageBean.ResultBean.CouponBagBean> cardInfoBeanList){
        this.context = context;
        this.cardInfoBeanList = cardInfoBeanList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_card, viewGroup, false);
        CardViewHolder viewHolder = new CardViewHolder(view, context);
        return viewHolder;
    }

    //多个属性未设置
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i) {
        final CardPackageBean.ResultBean.CouponBagBean couponBagBean= cardInfoBeanList.get(i);
        cardViewHolder.textCardName.setText(couponBagBean.getCoupon_name());
        cardViewHolder.textCardValue.setText(""+couponBagBean.getValue());

        /*尚未设置的属性  超多的 couponBagBean
        cardViewHolder.textCardValue.setText(String.);
        cardViewHolder.textCardMerchant.setText(cardInfoBean.getCardInfoResultBean().getCardList().getUseStoreId());
        cardViewHolder.textCardExpirationTime.setText(cardInfoBean.getCardInfoResultBean().getCardList().getDateTime().toString());
        cardViewHolder.textCardSill.setText(cardInfoBean.getCardInfoResultBean().getCardList().getSill());
        */

        /**
         * 为button注册点击事件
         * 将coupon_id传给QRCodeActivity
         */
        cardViewHolder.buttonUseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Use Card !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, QRCodeActivity.class);
                intent.putExtra("coupon_id", couponBagBean.getCoupon_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardInfoBeanList.size();
    }
}
