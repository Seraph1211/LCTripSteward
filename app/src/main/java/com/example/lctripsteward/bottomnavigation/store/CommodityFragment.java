package com.example.lctripsteward.bottomnavigation.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lctripsteward.R;
import com.example.lctripsteward.beans.CommodityBean;

import java.util.ArrayList;
import java.util.List;

public class CommodityFragment extends Fragment {
    private View view;
    private RecyclerView rvCommodity;
    private List<CommodityBean> commodityList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commodity, container, false);

        initView();

        return view;
    }

    public void initView(){

        initList();
        rvCommodity = view.findViewById(R.id.rv_commodity);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        rvCommodity.setLayoutManager(mLinearLayoutManager);
        rvCommodity.setAdapter(new CommodityItemAdapter(getContext(), commodityList));

    }


    public void initList(){
        String commodityNames[]={"Apple/苹果 10.5 英寸 iPad Air",  "小米米家空气净化器2S", "Apple/苹果 Macbook Pro ","华为平板华为MatePad Pro平板电脑", "LANVIN/浪凡 经典男女长围巾流苏羊毛防风防寒保暖柔软", "Apple/苹果 10.5 英寸 iPad Air",  "小米米家空气净化器2S", "Apple/苹果 Macbook Pro ","华为平板华为MatePad Pro平板电脑", "LANVIN/浪凡 经典男女长围巾流苏羊毛防风防寒保暖柔软"};
        int commodityPicRes[] = {R.drawable.commodity, R.drawable.commodity2, R.drawable.commodity3, R.drawable.commodity4, R.drawable.commodity5, R.drawable.commodity, R.drawable.commodity2, R.drawable.commodity3, R.drawable.commodity4, R.drawable.commodity5};
        int commodityPrices[] = {2200,  200,9000,2500, 180, 2200,  200,9000,2500, 180};
        int commodityCreditsCost[] = {300,  90, 1000,200, 100, 300, 90, 1000,200, 100};
        for (int i=0; i<10; i++){
            CommodityBean commodityBean = new CommodityBean();
            commodityBean.getCommodityResultBean().setCommodityName(commodityNames[i]);
            commodityBean.getCommodityResultBean().setCommodityPrice(commodityPrices[i]);
            commodityBean.getCommodityResultBean().setCarbonCreditsNeeds(commodityCreditsCost[i]);
            commodityBean.getCommodityResultBean().setCommodityPicture(commodityPicRes[i]);
            commodityBean.getCommodityResultBean().setCommodityIntroduce("......");
            commodityList.add(commodityBean);
        }
    }

}
