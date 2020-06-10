package com.example.lctripsteward.bottomnavigation.userinfo.team;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.TeamBean;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.ToastUtils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.TeamMemberViewHolder> {
    private static final String TAG = "TeamMemberAdapter";
    private Context context;
    private List<TeamBean.ResultBean.UserListBean> memberList;

    static class TeamMemberViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageMemberHeadPortrait;
        TextView textMemberName;
        Button buttonSendCredits;

        public TeamMemberViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            imageMemberHeadPortrait = itemView.findViewById(R.id.imageMemberHeadPortrait);
            textMemberName = itemView.findViewById(R.id.textMemberName);
            buttonSendCredits = itemView.findViewById(R.id.buttonSendCredits);
        }
    }

    public TeamMemberAdapter(Context context, List<TeamBean.ResultBean.UserListBean> memberList){
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public TeamMemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team_meber, viewGroup, false);
        TeamMemberViewHolder memberViewHolder = new TeamMemberViewHolder(view, context);

        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMemberViewHolder teamMemberViewHolder, int i) {
        final TeamBean.ResultBean.UserListBean bean = memberList.get(i);
        teamMemberViewHolder.textMemberName.setText(bean.getNickname());
        teamMemberViewHolder.imageMemberHeadPortrait.setImageResource(bean.getImg());
        teamMemberViewHolder.buttonSendCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "赠送碳积分给："+bean.getNickname(), Toast.LENGTH_SHORT).show();
                giveAwayCredits(bean.getUserId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void giveAwayCredits(final int granteeId){
        View view = LayoutInflater.from(context).inflate(R.layout.view_give_away_credits, null);
        final EditText editCreditsNum = view.findViewById(R.id.editNumOfCredits);
        //textWatcher的作用是保证应该输入数字的部分不会输入文本
        TextWatcher textWatcher= new TextWatcher() {
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
                    Toast.makeText(context, "请输入数字！", Toast.LENGTH_SHORT).show();
                    s.clear();

                }
            }
        };
        editCreditsNum.addTextChangedListener(textWatcher);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("赠送碳积分")
                .setView(view)
                .setPositiveButton("赠送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(editCreditsNum.getText().toString())){  //不为空
                            int creditsNum = Integer.valueOf(editCreditsNum.getText().toString());

                            int availableCredits = MySharedPreferencesUtils.getInt(context, "carbon_credits_available");

                            if(availableCredits > creditsNum){
                                showLoadingDialog();
                                MySharedPreferencesUtils.putInt(context, "carbon_credits_available", availableCredits-creditsNum);
                            }else {
                                Toast.makeText(context, "积分不足！", Toast.LENGTH_SHORT).show();
                            }



                            //网络请求
                            HttpUtils.getInfo(HttpUtils.giveAwayCreditsUrl + "?user_id=" + UserInfo.id
                                            + "&grantee_id=" + granteeId + "&carbon_credits=" + creditsNum,
                                    new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Log.d(TAG, "onFailure: ");
                                            Activity activity = (Activity)context;
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //Toast.makeText(context, "赠送失败，请重试", )
                                                }
                                            });
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            Log.d(TAG, "onResponse: ");
                                        }
                                    });
                        }



                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    private void showLoadingDialog(){
        final ProgressDialog dialog = ProgressDialog.show(context, "正在加载中...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(context, "赠送成功！");

            }
        }).start();
    }

}
