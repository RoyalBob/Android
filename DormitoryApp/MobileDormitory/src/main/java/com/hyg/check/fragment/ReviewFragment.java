package com.hyg.check.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyg.activity.R;
import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.Utils.JsonTools;
import com.hyg.check.activity.CheckActivity;
import com.hyg.check.adapter.ReviewAdapter;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.ReviewEntity;
import com.loopj.android.http.RequestParams;

import java.util.List;
import java.util.Map;


/**
 * Created by hyg on 2015/3/23.
 */
public class ReviewFragment extends Fragment{
    private View mView;
    private SwipeRefreshLayout mRefresh;
    private ListView mListView;
    private ReviewAdapter mAdapter;
    private AppApplication mApplication;
    private Map<String,Object> shardData;
    private DAO mDao;
    private List<ReviewEntity> reviewList;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_review,container,false);
        initData();
        initView();
        return mView;
    }

    private void initData() {
        mApplication=(AppApplication)getActivity().getApplication();
        shardData=mApplication.getSharedData();
        mDao=new DAO(getActivity());
        reviewList=mDao.getReview();
    }


    /**
     * 添加过滤器并注册广播
     */
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filters=new IntentFilter();
        filters.addAction(ActionConfig.REVIEW_SUCCESS);
        filters.addAction(ActionConfig.REVIEW_FAIL);
        mApplication.registerReceiver(receiver,filters);
    }

    private void initView(){
        mListView=(ListView)mView.findViewById(R.id.review);
        mAdapter=new ReviewAdapter(getActivity());
        mAdapter.setData(reviewList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ItemClicked());
        mRefresh=(SwipeRefreshLayout)mView.findViewById(R.id.review_refresh);
        mRefresh.setColorScheme(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpUtil.getReviewInfo(mApplication,setParams());
            }
        });
    }

    private RequestParams setParams(){
        RequestParams params=new RequestParams();
        params.put("year",shardData.get("year"));
        params.put("term",shardData.get("session"));
        params.put("times",shardData.get("count"));
        if("男".equals(shardData.get("sex"))){
            params.put("sex","M");
        }else{
            params.put("sex","F");
        }

        return params;
    }


    /**
     * 接收网络请求发出的广播
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ActionConfig.REVIEW_SUCCESS.equals(intent.getAction())){
                String mReviewInfo=intent.getStringExtra(ActionConfig.REVIEW_SUCCESS);
                Log.i(getClass().getSimpleName(), mReviewInfo);
                List<Map<String,Object>> review=JsonTools.getReview(mReviewInfo);
                mDao.deleteReview();
                mDao.setReview(review);
                reviewList=mDao.getReview();
                mAdapter.setData(reviewList);
                mAdapter.notifyDataSetChanged();
                mRefresh.setRefreshing(false);

            }else if(ActionConfig.REVIEW_FAIL.equals(intent.getAction())){
                mRefresh.setRefreshing(false);
                Toast.makeText(context, R.string.login_service_err, Toast.LENGTH_SHORT).show();
            }

        }
    };


    private class ItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), CheckActivity.class);
                intent.putExtra("studentId",reviewList.get(position).getStudentId());
                intent.putExtra("bedId",reviewList.get(position).getBedId());
                intent.putExtra("dormitoryId",reviewList.get(position).getDormitoryId()+reviewList.get(position).getBedId());
                intent.putExtra("name",reviewList.get(position).getStudentName());
                intent.putExtra("review","1");
                startActivity(intent);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mApplication.unregisterReceiver(receiver);
    }





}
