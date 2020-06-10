package com.example.lctripsteward.bottomnavigation.userinfo.merchant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lctripsteward.R;
import com.example.lctripsteward.utils.StatusBarUtils;


public class AddGoodsActivity extends AppCompatActivity {
    private static final String TAG = "AddGoodsActivity";
    private Spinner mSpinner;
    private Button buttonAddGoods;
    private ImageButton buttonBack;

    private int goodType = -1;

    private AddCommodityFragment addCommodityFragment;
    private AddCouponFragment addCouponFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        StatusBarUtils.setStatusBarColor(AddGoodsActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(AddGoodsActivity.this, true, true);  //状态栏字体颜色-黑

        initView();
    }

    public void initView(){
        initSpinner();

        addCommodityFragment = new AddCommodityFragment();
        addCouponFragment = new AddCouponFragment();

        buttonAddGoods = findViewById(R.id.buttonMerchantAddGoods);
        buttonBack = findViewById(R.id.buttonMerchantAddGoodsBack);

        //若是Coupon，提交前要判断过期日期是否合理，不合理则Toast提醒修正
        buttonAddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddGoodsActivity.this, "发布！", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void initSpinner(){
        mSpinner = findViewById(R.id.spinnerGoodsType);

        //原始string数组
        final String[] spinnerItems = {"会员卡","折扣券","满减券","无门槛红包","碳积分抵押（实体商品）"};

        //简单的string数组适配器：样式res，数组
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);

        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //绑定 Adapter到控件
        mSpinner.setAdapter(spinnerAdapter);

        //选择监听
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //parent就是父控件spinner
            //view就是spinner内填充的textView,id=@android:id/text1
            //position是值所在数组的位置
            //id是值所在行的位置，一般来说与position一致
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //设置spinner内的填充文字居中
                Log.d(TAG, "onItemSelected: pos="+pos);
                goodType = pos;
                if(goodType==4){
                    setFragment(addCommodityFragment);
                }else {
                    setFragment(addCouponFragment);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameMerchantAddGoods, fragment);
        fragmentTransaction.commit();
    }

    public Button getButtonAddGoods(){
        return buttonAddGoods;
    }

    public int getGoodType(){
        return goodType;
    }
}
