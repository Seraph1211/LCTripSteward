package com.example.lctripsteward.bottomnavigation.home.rank;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.RankBean;

import java.util.List;

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.RankViewHolder> {

    private Context context;
    private List<RankBean.ResultBean.UserListBean> rankItemList;

    static class RankViewHolder extends RecyclerView.ViewHolder{
        TextView textRanking;  //排名
        ImageView imageHeadPortrait; //头像
        TextView textUserName; //用户名
        TextView textCredits; //积分，总积分或月度积分

        public RankViewHolder(View view, Context context){
            super(view);
            textRanking = view.findViewById(R.id.text_ranking);
            imageHeadPortrait = view.findViewById(R.id.image_ranking);
            textUserName = view.findViewById(R.id.text_ranking_name);
            textCredits = view.findViewById(R.id.text_ranking_credits);
        }
    }

    public RankRecyclerViewAdapter(Context context, List<RankBean.ResultBean.UserListBean> rankItemList){
        this.context = context;
        this.rankItemList = rankItemList;
    }


    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rank, viewGroup, false);
        RankViewHolder viewHolder = new RankViewHolder(view, context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder viewHolder, int i) {
        RankBean.ResultBean.UserListBean rankItem = rankItemList.get(i);
        viewHolder.textRanking.setText(String.valueOf(rankItem.getUserRank()));
        viewHolder.imageHeadPortrait.setImageResource(rankItem.getUserImagePath());
        viewHolder.textUserName.setText(rankItem.getNickname());
        viewHolder.textCredits.setText(String.valueOf(rankItem.getCarbonCredits()));
    }

    @Override
    public int getItemCount() {
        return rankItemList.size();
    }
}
