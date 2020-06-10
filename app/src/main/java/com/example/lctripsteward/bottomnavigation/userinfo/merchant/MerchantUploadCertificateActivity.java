package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;


public class MerchantUploadCertificateActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int PICK_PHOTO_1 = 467;
    public static final int PICK_PHOTO_2 = 457;
    public static final int PICK_PHOTO_3 = 447;
    public static final int PICK_PHOTO_4 = 437;
    private static final String TAG = "MerchantUploadCertificate";
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;

    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;

    private Button btnNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_upload_certificate);

        if (ContextCompat.checkSelfPermission(MerchantUploadCertificateActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MerchantUploadCertificateActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        StatusBarUtils.setStatusBarColor(MerchantUploadCertificateActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(MerchantUploadCertificateActivity.this, true, true);  //状态栏字体颜色-黑


        initView();
    }


    public void initView() {

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);

        btnNextStep = findViewById(R.id.btn_next_step_register);
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(a && b && c && d){
                    showLoadingDialog();
                }else {
                    ToastUtils.showToast(MerchantUploadCertificateActivity.this, "请按照要求上传照片！");
                }


            }
        });
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img1:{
                openAlbum(PICK_PHOTO_1);
                break;
            }
            case R.id.img2:{
                openAlbum(PICK_PHOTO_2);
                break;
            }
            case R.id.img3:{
                openAlbum(PICK_PHOTO_3);
                break;
            }
            case R.id.img4:{
                openAlbum(PICK_PHOTO_4);
                break;
            }
            case R.id.buttonUploadBack:{
                finish();
                break;
            }

        }
    }

    private void showLoadingDialog(){
        final ProgressDialog dialog = ProgressDialog.show(MerchantUploadCertificateActivity.this, "照片上传中...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(MerchantUploadCertificateActivity.this, "上传成功！");
                startActivity(new Intent(MerchantUploadCertificateActivity.this, MerchantActivity.class));
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case PICK_PHOTO_1:{
                if(resultCode == RESULT_OK){
                    a = true;
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统就使用这个方法处理图片
                        handleImageOnKitKat(data, img1);
                    }else{
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data, img1);
                    }
                }
                break;
            }
            case PICK_PHOTO_2:{
                if(resultCode == RESULT_OK){
                    b = true;
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统就使用这个方法处理图片
                        handleImageOnKitKat(data, img2);
                    }else{
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data, img2);
                    }
                }
                break;
            }
            case PICK_PHOTO_3:{
                c  = true;
                if(resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统就使用这个方法处理图片
                        handleImageOnKitKat(data, img3);
                    }else{
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data, img3);
                    }
                }
                break;
            }
            case PICK_PHOTO_4:{
                d = true;
                if(resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统就使用这个方法处理图片
                        handleImageOnKitKat(data, img4);
                    }else{
                        //4.4以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data, img4);
                    }
                }
                break;
            }
            default:break;
        }
    }

    @SuppressLint("LongLogTag")
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, ImageView imageView){

        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)){
            Log.d(TAG, "handleImageOnKitKat: isDocumentUri");
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.provider.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1]; //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            Log.d(TAG, "handleImageOnKitKat: isContentUri");
            //如果是content类型的Uri,则使用普通的方式处理
            imagePath = getImagePath(uri, null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            Log.d(TAG, "handleImageOnKitKat: isFileUri");
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        Log.d(TAG, "handleImageOnKitKat: "+imagePath);
        displayImage(imagePath, imageView); //根据图片路径显示图片
    }

    @SuppressLint("LongLogTag")
    private void handleImageBeforeKitKat(Intent data, ImageView imageView){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Log.d(TAG, "handleImageBeforeKitKat: "+imagePath);
        displayImage(imagePath, imageView);
    }

    //获取图片的真实路径
    private String getImagePath(Uri uri, String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //将图片展示在界面上
    private void displayImage(String imagePath, ImageView picture){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Glide.with(MerchantUploadCertificateActivity.this)
                    .load(bitmap)
                    .into(picture);
        }else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    //打开相册程序选择照片
    private void openAlbum(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //openAlbum();   //如果用户接受了对SD读写权限的申请，则...
                }else{
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:break;
        }
    }
}
