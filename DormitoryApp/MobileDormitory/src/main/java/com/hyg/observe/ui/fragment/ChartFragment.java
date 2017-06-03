package com.hyg.observe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyg.activity.R;
import com.hyg.check.app.AppApplication;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.StudentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by hyg on 2015/4/16.
 * 成绩显示fragment
 */
public class ChartFragment extends Fragment{

    private View mView;
    private PieChartView mPieChart;
    private PieChartData mChartData;
    private DAO mDao;
    private String mTime;

    private List<Integer> mList;
    private AppApplication appApplication;
    private Map<String,Object> sharedData;
    private StudentEntity studentEntity;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_chart,container,false);
        initData();
        initView();
        return mView;
    }

    private void initData(){
        mDao=new DAO(getActivity());
        appApplication=(AppApplication)getActivity().getApplication();
        sharedData=appApplication.getSharedData();
        Bundle bundle=getArguments();
        mTime=bundle.getString("time");
        studentEntity=mDao.getUserScore("114173118", "2014", "2", mTime);
        mList=new ArrayList<Integer>();
        mList.add(studentEntity.getgBed());
        mList.add(studentEntity.getgDesk());
        mList.add(studentEntity.getgGround());
        mList.add(studentEntity.getgPublic());
        mList.add(studentEntity.getgWall());
        generateData();

    }
    private void initView(){
        mPieChart=(PieChartView)mView.findViewById(R.id.piechart);
        mPieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {

            }

            @Override
            public void onValueDeselected() {

            }
        });
        mPieChart.setPieChartData(mChartData);
    }

    private void generateData(){
        int numValues = mList.size();
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float)mList.get(i), ChartUtils.pickColor());
            values.add(sliceValue);
        }

        mChartData = new PieChartData(values);
        mChartData.setHasLabels(true);
        mChartData.setHasLabelsOnlyForSelected(false);
        mChartData.setHasCenterCircle(false);
    }
}
