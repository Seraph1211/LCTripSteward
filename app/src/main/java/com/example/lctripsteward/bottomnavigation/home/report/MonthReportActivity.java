package com.example.lctripsteward.bottomnavigation.home.report;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lctripsteward.R;
import com.example.lctripsteward.UserInfo;
import com.example.lctripsteward.beans.MonthReportBean;
import com.example.lctripsteward.utils.DateUtils;
import com.example.lctripsteward.utils.HttpUtils;
import com.example.lctripsteward.utils.MySharedPreferencesUtils;
import com.example.lctripsteward.utils.StatusBarUtils;
import com.example.lctripsteward.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MonthReportActivity extends AppCompatActivity {
    private static final String TAG = "MonthReportActivity";

    /*
    二氧化碳减排量折线统计图
     */
    private LineChartView lineChartOfCarbonReduce;
    String[] labelsOfCarbonReduce = {"9月","10月","11月","12月","1月","2月","3月"};//X轴的标注
    int[] scoreOfCarbonReduce = {50,42,90,33,10,74};//图表的数据点
    private List<PointValue> mPointValuesOfCarbonReduce = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValuesOfCarbonReduce = new ArrayList<AxisValue>();

    /*
    排名变化折线统计图
     */
    private LineChartView lineChartOfRank;
    String[] labelsOfRank = {"9月","10月","11月","12月","1月","2月","3月"};//X轴的标注
    int[] scoreOfRank = {120,42,90,33,10,88};//图表的数据点
    private List<PointValue> mPointValuesOfRank = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValuesOfRank = new ArrayList<AxisValue>();

    /*
    饼状图
     */
    private PieChartView pieChart;
    List<SliceValue> sliceValues = new ArrayList<>();
    int[] pieValues = {30,60,5,5};
    String[] pieLabels = {"地铁", "公交", "骑行", "步行"};

    /*
    柱状图
     */
    private ColumnChartView columnChart;
    String[] columnLabels = {"地铁", "公交", "骑行", "步行"};
    int[] columnValues = {15000, 21500, 5200, 6022};
    List<Column> columns = new ArrayList<>();
    List<SubcolumnValue> subColumnValues;
    List<AxisValue> axisValues = new ArrayList<>();


    private TextView textCarbonDioxideReductionLastMonth;  //上月二氧化碳减排量
    private TextView textCarbonDioxideReductionThisMonth;  //本月二氧化碳减排量
    private TextView textUserRankLastMonth;  //上月排名
    private TextView textUserRankThisMonth;  //本月排名
    private ImageButton buttonBack;

    MonthReportBean monthReportBean = null;  //bean

    private SwipeRefreshLayout srReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_report);

        StatusBarUtils.setStatusBarColor(MonthReportActivity.this, R.color.colorWathet);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(MonthReportActivity.this, true, true);  //设置状态栏字体颜色

        init();

        //queryReportInfo();

    }

    private void initSwipeRefresh(){
        srReport = findViewById(R.id.sr_report);

        srReport.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1200);
                            MonthReportActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finishSwipeRefresh();
                                }
                            });
                            ToastUtils.showToast(MonthReportActivity.this, "刷新完成！");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 结束刷新
     */
    private void finishSwipeRefresh(){
        if(srReport.isRefreshing()){
            srReport.post(new Runnable() {
                @Override
                public void run() {
                    srReport.setRefreshing(false);
                }
            });

        }
    }

    public void init(){
        lineChartOfCarbonReduce = findViewById(R.id.lineChartOfCarbonReduce);
        initLineChartOfCarbonReduce();

        lineChartOfRank = findViewById(R.id.lineChartOfRank);
        initLineChartOfRank();

        pieChart = findViewById(R.id.pieChart);
        initPieChart();

        columnChart = findViewById(R.id.columnChart);
        initColumnChart();

        textCarbonDioxideReductionLastMonth = findViewById(R.id.textCarbonDioxideReductionLastMonth);
        textCarbonDioxideReductionThisMonth = findViewById(R.id.textCarbonDioxideReductionThisMonth);
        textUserRankLastMonth = findViewById(R.id.textUserRankLastMonth);
        textUserRankThisMonth = findViewById(R.id.textUserRankThisMonth);
        buttonBack = findViewById(R.id.buttonChartsBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initSwipeRefresh();
    }

    /**
     * 初始化折线统计图
     */
    private void initLineChartOfCarbonReduce(){
        /*
        填充数据
         */
        for(int i=0; i<scoreOfCarbonReduce.length; i++){
            mPointValuesOfCarbonReduce.add(new PointValue(i, scoreOfCarbonReduce[i]));
        }
        for(int i=0; i<labelsOfCarbonReduce.length; i++){
            mAxisXValuesOfCarbonReduce.add(new AxisValue(i).setLabel(labelsOfCarbonReduce[i]));
        }

        /*
        对曲线进行相关设置
         */
        Line line = new Line(mPointValuesOfCarbonReduce)
                .setColor(Color.parseColor("#FFCD41"))  //设置折线颜色为：橙色
                .setShape(ValueShape.CIRCLE)  //设置折线统计图上每个数据点的形状（有三种：ValueShape.SQUARE/CIRCLE/DIAMOND)
                .setCubic(false)  //曲线是否平滑，即是曲线还是折线
                .setFilled(true)  //是否填充曲线以下的面积
                .setHasLabels(true)  //曲线的数据点是否加上标签/备注
                //.setHasLabelsOnlyForSelected(true)  //点击数据点是否提示数据（与setHasLabels(true)冲突）
                .setHasLines(true)  //是否用曲线显示。 false:只有点，没有曲线显示
                .setPointRadius(3)
                .setHasPoints(true); //是否显示圆点。 false:没有圆点只有点显示

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        /*
        对X轴进行相关设置
         */
        Axis axisX = new Axis()   //X轴
                .setHasTiltedLabels(false)  //X轴的标签是否倾斜，true-倾斜
                .setTextColor(Color.BLACK)  //字体颜色
                //.setName("Data")    //表格名称
                .setTextSize(10)   //字体大小
                .setMaxLabelChars(8)  //最多显示几个X轴坐标 ？
                .setValues(mAxisXValuesOfCarbonReduce) //X轴标签的填充
                .setHasLines(true);   //X轴是否有分割线
        lineChartData.setAxisXBottom(axisX); //X轴在底部（可以在上、下）

        /*
        对Y轴进行相关设置（Y轴是根据数据的大小自动设置Y轴的上限）
         */
        Axis axisY = new Axis()  //Y轴
                .setName("")  //Y轴标注
                .setTextSize(10); //字体大小
        lineChartData.setAxisYLeft(axisY); //Y轴在左边（可以在左、右）

        /*
        设置折线统计图的行为属性，支持缩放、滑动、平移
         */
        lineChartOfCarbonReduce.setInteractive(true); //可交互
        lineChartOfCarbonReduce.setZoomType(ZoomType.HORIZONTAL) ; //放大方式
        lineChartOfCarbonReduce.setMaxZoom((float)2); //最大放大比例 ？
        lineChartOfCarbonReduce.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartOfCarbonReduce.setLineChartData(lineChartData);
        lineChartOfCarbonReduce.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化折线统计图
     */
    private void initLineChartOfRank(){
        /*
        填充数据
         */
        for(int i=0; i<scoreOfRank.length; i++){
            mPointValuesOfRank.add(new PointValue(i, scoreOfRank[i]));
        }
        for(int i=0; i<labelsOfRank.length; i++){
            mAxisXValuesOfRank.add(new AxisValue(i).setLabel(labelsOfRank[i]));
        }

        /*
        对曲线进行相关设置
         */
        Line line = new Line(mPointValuesOfRank)
                .setColor(Color.parseColor("#FFCD41"))  //设置折线颜色为：橙色
                .setShape(ValueShape.CIRCLE)  //设置折线统计图上每个数据点的形状（有三种：ValueShape.SQUARE/CIRCLE/DIAMOND)
                .setCubic(false)  //曲线是否平滑，即是曲线还是折线
                .setFilled(true)  //是否填充曲线以下的面积
                .setHasLabels(true)  //曲线的数据点是否加上标签/备注
                //.setHasLabelsOnlyForSelected(true)  //点击数据点是否提示数据（与setHasLabels(true)冲突）
                .setHasLines(true)  //是否用曲线显示。 false:只有点，没有曲线显示
                .setPointRadius(3)
                .setHasPoints(true); //是否显示圆点。 false:没有圆点只有点显示

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        /*
        对X轴进行相关设置
         */
        Axis axisX = new Axis()   //X轴
                .setHasTiltedLabels(false)  //X轴的标签是否倾斜，true-倾斜
                .setTextColor(Color.BLACK)  //字体颜色
                //.setName("Data")    //表格名称
                .setTextSize(10)   //字体大小
                .setMaxLabelChars(8)  //最多显示几个X轴坐标 ？
                .setValues(mAxisXValuesOfRank) //X轴标签的填充
                .setHasLines(true);   //X轴是否有分割线
        lineChartData.setAxisXBottom(axisX); //X轴在底部（可以在上、下）

        /*
        对Y轴进行相关设置（Y轴是根据数据的大小自动设置Y轴的上限）
         */
        Axis axisY = new Axis()  //Y轴
                .setName("")  //Y轴标注
                .setTextSize(10); //字体大小
        lineChartData.setAxisYLeft(axisY); //Y轴在左边（可以在左、右）

        /*
        设置折线统计图的行为属性，支持缩放、滑动、平移
         */
        lineChartOfRank.setInteractive(true); //可交互
        lineChartOfRank.setZoomType(ZoomType.HORIZONTAL) ; //放大方式
        lineChartOfRank.setMaxZoom((float)2); //最大放大比例 ？
        lineChartOfRank.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartOfRank.setLineChartData(lineChartData);
        lineChartOfRank.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart(){

        //计算百分比
        float sum = 0;
        for(int i=0; i<pieLabels.length; i++){
            sum += pieValues[i];
        }
        NumberFormat numberFormat= NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(1);

        //向sliceValues中填充数据
        for(int i=0; i<pieLabels.length; i++){
            String result=numberFormat.format(((double)pieValues[i])/((double)sum));
            SliceValue sliceValue = new SliceValue(pieValues[i], ChartUtils.pickColor()); //新建对象的同时设置数值和颜色
            sliceValue.setLabel(pieLabels[i]+":"+result); //设置标签
            sliceValues.add(sliceValue);
        }

        PieChartData pieChartData = new PieChartData(sliceValues);
        pieChartData.setHasLabels(true); //显示标签，默认不显示
        pieChartData.setHasLabelsOutside(true); //标签的显示位置在饼状图之外
        pieChartData.setHasCenterCircle(true); //显示圆环状饼状图
        pieChartData.setCenterText1("Comparison"); //设置中心圆中的文本
        pieChartData.setCenterText2("月度出行方式比较");
        pieChartData.setCenterText1FontSize(24);
        pieChartData.setCenterText1Color(Color.parseColor("#4876FF"));
        pieChartData.setCenterText2FontSize(12);
        pieChartData.setCenterCircleScale(0.5f);

        pieChart.setPieChartData(pieChartData);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.startDataAnimation();
        pieChart.setCircleFillRatio(0.8f);   //设置饼状图占整个View的比例
        pieChart.setViewportCalculationEnabled(true);  //饼状图自动适应大小
        pieChart.setInteractive(false );
    }

    /**
     * 初始化柱状图
     */
    private void initColumnChart(){
        for(int i=0; i<columnLabels.length; i++){
            subColumnValues = new ArrayList<>();
            SubcolumnValue value = new SubcolumnValue(columnValues[i], ChartUtils.pickColor());
            subColumnValues.add(value);
            Column column = new Column(subColumnValues);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);

            axisValues.add(new AxisValue(i).setLabel(columnLabels[i]));  //给X轴坐标设置描述
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);


        /*
        X轴
         */
        Axis axisX = new Axis();
        axisX.hasLines();
        axisX.setTextColor(Color.BLACK);
        axisX.setValues(axisValues);
        axisX.setHasTiltedLabels(false);

        /*
        Y轴
         */
        Axis axisY = new Axis();
        axisY.hasLines();
        axisY.setTextColor(Color.GRAY);
        axisY.setName("本月出行状况");
        axisY.setHasLines(true);


        columnChartData.setFillRatio(0.8f);//设置组与组之间的间隔比率，取值范围0-1,1表示不留任何间隔
        columnChartData.setValueLabelTextSize(8);//标签文字大小
        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);

        columnChart.setColumnChartData(columnChartData);
        columnChart.setInteractive(false);


    }

    /**
     * 从服务器获取数据
     */
    public void queryReportInfo(){
        Log.d(TAG, "queryReportInfo: url="+ HttpUtils.monthlyReportInfoUrl);
        HttpUtils.getInfo(HttpUtils.monthlyReportInfoUrl+"?user_id="+ UserInfo.id
                        +"&city_id=" + MySharedPreferencesUtils.getString(MonthReportActivity.this, "city_id")
                        + "&start_month="+ DateUtils.getLastMonth()+"&end_month="+DateUtils.getMonth(),
                        new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showToast(MonthReportActivity.this, "服务器故障");
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "MonthReport code="+code);

                if(code==200){
                    String responseContent = response.body().string();
                    Log.d(TAG, "MonthReport responseContent="+responseContent);

                    monthReportBean = new Gson().fromJson(responseContent, MonthReportBean.class);
                    String msgCode = monthReportBean.getMsg_code();
                    if(msgCode.equals("0000")){
                        //更新TextView的数据
                        textCarbonDioxideReductionLastMonth.setText(String.valueOf(monthReportBean.getResult().getCO2_reduction_last_month()));
                        textCarbonDioxideReductionThisMonth.setText(String.valueOf(monthReportBean.getResult().getCO2_reduction_this_month()));
                        textUserRankLastMonth.setText(String.valueOf(monthReportBean.getResult().getUser_rank_last_month()));
                        textUserRankThisMonth.setText(String.valueOf(monthReportBean.getResult().getUser_rank_this_month()));

                        //更新饼状图数据:地铁、公交、骑行、步行
                        pieValues[0] = monthReportBean.getResult().getMileage_subway();
                        pieValues[1] = monthReportBean.getResult().getMileage_bus();
                        pieValues[2] = monthReportBean.getResult().getMileage_bike();
                        pieValues[3] = monthReportBean.getResult().getMileage_walk();
                        //initPieChart();

                        //更新柱状图数据,顺序同上
                        columnValues[0] = monthReportBean.getResult().getMileage_subway();
                        columnValues[1] = monthReportBean.getResult().getMileage_bus();
                        columnValues[2] = monthReportBean.getResult().getMileage_bike();
                        columnValues[3] = monthReportBean.getResult().getMileage_walk();
                        //initColumnChart();

                        MonthReportActivity activity = MonthReportActivity.this;

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initColumnChart();
                                initPieChart();
                            }
                        });

                        //折线统计图数据服务端尚未给出！
                    }else {
                        ToastUtils.showToast(MonthReportActivity.this, monthReportBean.getMsg_message());
                    }
                }else {
                    ToastUtils.showToast(MonthReportActivity.this, "服务器错误");
                }

            }
        });
    }

}
