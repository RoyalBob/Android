package com.hyg.observe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hyg.activity.R;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.StudentEntity;
import com.hyg.observe.adapter.ScoreAdapter;
import com.hyg.observe.data.UserScore;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by hyg on 2015/4/16.
 * 图表展示fragment
 */
public class ScoreFragment extends Fragment{
    private View mView;
    private ListView mListView;
    private ScoreAdapter mAdapter;
    private DAO mDao;
    private String mTime;
    private UserScore userScore;
    private Map<String,Object> map;
    private String[] scoreItem;
    private StudentEntity studentEntity;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_score,container,false);
        initData();
        initView();
        return mView;
    }
    private void initData(){
        mDao=new DAO(getActivity());
        Bundle bundle=getArguments();
        mTime=bundle.getString("time");
        studentEntity=mDao.getUserScore("114173118", "2014", "2", mTime);
        scoreItem=getResources().getStringArray(R.array.score_item);
        map=new HashMap<String,Object>();
        map.put(scoreItem[0],studentEntity.getgBed());
        map.put(scoreItem[1],studentEntity.getgWall());
        map.put(scoreItem[2],studentEntity.getgDesk());
        map.put(scoreItem[3],studentEntity.getgGround());
        map.put(scoreItem[4],studentEntity.getgSafetyConcept());
        map.put(scoreItem[5],studentEntity.getgElectricitySafe());
        map.put(scoreItem[6],studentEntity.getgPublic());
        map.put(scoreItem[7],studentEntity.getgTotal());
        //map.put(scoreItem[8],userScore.getRank());
    }
    private void initView(){
        mListView=(ListView)mView.findViewById(R.id.lv_score);
        mAdapter=new ScoreAdapter(getActivity());
        mAdapter.setAdapterData(map,scoreItem);
        mListView.setAdapter(mAdapter);
    }
}
