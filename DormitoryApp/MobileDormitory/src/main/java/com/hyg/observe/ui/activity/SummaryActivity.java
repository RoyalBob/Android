package com.hyg.observe.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.observe.db.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

/**
 * Created by hyg on 2015/4/28.
 * 成绩汇总界面
 */
public class SummaryActivity extends ActionBarActivity{

    private Toolbar mToolbar;
    private TextView mTitle;
    private ComboLineColumnChartView mComboLineColumnChartView;
    private ComboLineColumnChartData data;
    private Dao mDao;
    private List<Map<String,Object>> list;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints ;
    private float[][] randomNumbersTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initData();
        initView();
    }

    private void initData() {
        mDao=new Dao(this);
        list=mDao.getTimes();
        numberOfPoints=list.size();
        randomNumbersTab= new float[maxNumberOfLines][numberOfPoints];
        generateValues();
        generateData();

    }

    private void initView() {
        initToolbar();
        mComboLineColumnChartView=(ComboLineColumnChartView)this.findViewById(R.id.combolinechart);
        mComboLineColumnChartView.setComboLineColumnChartData(data);
        mComboLineColumnChartView.setOnValueTouchListener(new ValueTouchListener());
    }

    private void initToolbar() {
        mToolbar=(Toolbar)this.findViewById(R.id.toolbar);

        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(R.string.toolbar_title_name);
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER;
        mToolbar.addView(mTitle,params);
        //设置回退键
        mToolbar.setNavigationIcon(R.drawable.header_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationClicked();
            }
        });

    }


    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {

        }



    }

    /**
     * 折线图数据
     * @return
     */
    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setCubic(false);
            line.setHasLabels(false);
            line.setHasLines(true);
            line.setHasPoints(false);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }

    /**
     * 设置折线图数据
     */
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = Float.valueOf(list.get(j).get("total").toString());
            }
        }
    }


    private void generateData() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data = new ComboLineColumnChartData(generateColumnData(), generateLineData());
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            axisX.setName("Axis X");
            axisY.setName("Axis Y");
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);

    }

    /**
     * 柱状图数据
     * @return
     */
    private ColumnChartData generateColumnData() {
        int numSubcolumns = 1;
        int numColumns = list.size();
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(Float.valueOf(list.get(i).get("total").toString()), ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }


    private void navigationClicked(){
        this.finish();
    }

}
