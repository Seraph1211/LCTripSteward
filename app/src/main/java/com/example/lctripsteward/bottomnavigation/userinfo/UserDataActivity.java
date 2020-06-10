package com.example.lctripsteward.bottomnavigation.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.bottomnavigation.userinfo.cardpackage.CardPackageActivity;
import com.example.lctripsteward.utils.Base64Utils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private final String TAG = "UserDataActivity";

    private final int UPDATE_NICKNAME = 101;
    private final int UPDATE_PWD = 102;

    private RelativeLayout rlHeadPortrait;
    private RelativeLayout rlNickname;
    private RelativeLayout rlPhoneNum;
    private RelativeLayout rlPwd;

    private CircleImageView ivHeadPortrait;
    private TextView tvNickname;
    private TextView tvPhoneNum;

    private String base64HeadPortrait;
    private String nickname;
    private String phoneNum;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        initData();
        initView();

    }

    private void initData(){
        base64HeadPortrait = MySharedPreferencesUtils.getString(UserDataActivity.this, "img");
        phoneNum = MySharedPreferencesUtils.getString(UserDataActivity.this, "account");
        nickname = MySharedPreferencesUtils.getString(UserDataActivity.this, "nickname");
        pwd = MySharedPreferencesUtils.getString(UserDataActivity.this, "password");

        Log.d(TAG, "initData: [nickname="+nickname+", phone="+phoneNum+", pwd="+pwd+"]");
        Log.d(TAG, "initData: base64HeadPortrait="+base64HeadPortrait);
    }

    private void initView(){
        StatusBarUtils.setStatusBarColor(UserDataActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(UserDataActivity.this, true, true);  //状态栏字体颜色-黑

        context = UserDataActivity.this;

        //获取控件实例
        rlHeadPortrait = findViewById(R.id.rl_modify_head_portrait);
        rlNickname = findViewById(R.id.rl_modify_nickname);
        rlPhoneNum = findViewById(R.id.rl_modify_phone);
        rlPwd = findViewById(R.id.rl_modify_pwd);
        ivHeadPortrait = findViewById(R.id.iv_head_portrait);
        tvNickname = findViewById(R.id.tv_nickname);
        tvPhoneNum = findViewById(R.id.tv_phone);

        rlHeadPortrait.setOnClickListener(this);
        rlNickname.setOnClickListener(this);
        rlPhoneNum.setOnClickListener(this);
        rlPwd.setOnClickListener(this);

        if(!base64HeadPortrait.equals("empty")){
            Base64Utils.loadBase64Image(base64HeadPortrait, ivHeadPortrait);
        }
        if(!nickname.equals("empty")){
            tvNickname.setText(nickname);
        }
        if(!phoneNum.equals("empty")){
            tvPhoneNum.setText(phoneNum);
        }
    }

    /**
     * Toolbar上返回键的响应事件
     * @param view
     */
    public void back(View view){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_modify_head_portrait:{
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON) //开启选择器
                        .setActivityTitle("头像裁剪")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)  //选择矩形裁剪
                        .start(UserDataActivity.this);
                break;
            }
            case R.id.rl_modify_nickname:{
                Intent intent = new Intent(UserDataActivity.this, ModifyUserDataActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("content", nickname);
                startActivityForResult(intent, UPDATE_NICKNAME);
                break;
            }
            case R.id.rl_modify_phone:{
                ToastUtils.showToast(UserDataActivity.this, "手机号不可修改！");
                break;
            }
            case R.id.rl_modify_pwd:{
                Intent intent = new Intent(UserDataActivity.this, ModifyUserDataActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("content", pwd);
                startActivityForResult(intent, UPDATE_PWD);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("PhotoActivity", "onActivityResult: resultCode="+resultCode);
        Log.d("PhotoActivity", "onActivityResult: requestCode="+requestCode);

        //用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:{
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Log.d("PhotoActivity", "onActivityResult: CropResultCode="+requestCode);
                if(resultCode==RESULT_OK){
                    final Uri resultUri = result.getUri();
                    Log.d("PhotoActivity", "onActivityResult: resultUri="+resultUri);

                    showLoadingDialog(resultUri);

                    saveHeadPortrait(resultUri);

                }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Log.d("PhotoActivity", "onActivityResult: Error");
                    Exception exception = result.getError();
                }
                break;
            }

            case UPDATE_NICKNAME:{
                String newName = data.getStringExtra("new_name");
                tvNickname.setText(newName);
                break;
            }

            case UPDATE_PWD:{
                break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 头像本地存储
     * base64
     * @param resultUri
     */
    private void saveHeadPortrait(Uri resultUri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        UserInfo.img = Base64Utils.encodeImageToBase64String(bitmap);
        MySharedPreferencesUtils.putString(UserDataActivity.this, "img", UserInfo.img);
        Log.i(TAG, "onActivityResult: base64="+ UserInfo.img);
    }

    private void showLoadingDialog(final Uri resultUri){
        final ProgressDialog dialog = ProgressDialog.show(context, "头像上传中...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

                UserDataActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(UserDataActivity.this)
                                .load(resultUri)
                                .into(ivHeadPortrait);
                    }
                });

                ToastUtils.showToast(UserDataActivity.this, "头像上传成功！");

            }
        }).start();
    }

}
