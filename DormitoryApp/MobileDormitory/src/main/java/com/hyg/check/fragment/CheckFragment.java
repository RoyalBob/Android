package com.hyg.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyg.activity.R;
import com.hyg.check.Utils.AppTools;
import com.hyg.check.Utils.SharedPreferenceUtil;
import com.hyg.check.activity.RoomAcitvity;
import com.hyg.check.adapter.FloorAdapter;
import com.hyg.check.app.AppApplication;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.RoomInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Created by hyg on 2015/3/6.
 * 功能描述，根据选项获取该层的房间信息
 */


public class CheckFragment extends Fragment {

    private GridView mRoom;
    private View mView;
    private FloorAdapter mFloorAdapter;
    private DAO mDao;
    private List<RoomInfo> list;
    private AppApplication application;
    private SharedPreferenceUtil spUtil;
    private AppTools mAppTools;
    private int floor=1;
    private Map<String,Object> shardData;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载视图
        if (null!=savedInstanceState){
            floor=savedInstanceState.getInt("floor");
        }
        Log.i(getClass().getSimpleName(),"onCreat");
        Log.i(getClass().getSimpleName(),"floor--->"+floor);
        mView = inflater.inflate(R.layout.fragment_index,container,false);
        //初始化
        initData();
        initView();

        return mView;
    }

    private void initData() {
        application=(AppApplication)getActivity().getApplication();
        shardData=application.getSharedData();
        spUtil=application.getSpUtil();
        mDao=new DAO(getActivity());
        list=new ArrayList<RoomInfo>();
        list=mDao.selectFloorData(spUtil.getArea(),spUtil.getBuild(),1,shardData.get("sex").toString());
        mAppTools=new AppTools(getActivity());

    }

    private void initView() {
        mRoom = (GridView) mView.findViewById(R.id.select_room);
        mFloorAdapter=new FloorAdapter(getActivity());
        mFloorAdapter.setFloorData(list);
        mRoom.setAdapter(mFloorAdapter);
        mRoom.setOnItemClickListener(new SelectRoomListener());

    }

    private class SelectRoomListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), RoomAcitvity.class);
                intent.putExtra("roomNo",list.get(position).getRoomNo());
                startActivity(intent);

        }
    }



    /**
     *显示gridview的学生房间信息
     */
    public void showRoomInfo(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.menu_floor1:
                Log.i(getClass().getSimpleName(),"floor1");
                floor=1;
                break;
            case R.id.menu_floor2:
                Log.i(getClass().getSimpleName(),"floor2");
                floor=2;
                break;
            case R.id.menu_floor3:
                Log.i(getClass().getSimpleName(),"floor3");
                floor=3;
                break;
            case R.id.menu_floor4:
                Log.i(getClass().getSimpleName(),"floor4");
                floor=4;
                break;
            case R.id.menu_floor5:
                Log.i(getClass().getSimpleName(),"floor5");
                floor=5;
                break;
            case R.id.menu_floor6:
                Log.i(getClass().getSimpleName(),"floor6");
                floor=6;
                break;
        }

        notifyData();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(),"onDestroy");
    }

    private void notifyData(){
        list.clear();
        list=mDao.selectFloorData(spUtil.getArea(),spUtil.getBuild(),floor,shardData.get("sex").toString());
        mFloorAdapter.setFloorData(list);
        mFloorAdapter.notifyDataSetChanged();

        Log.i(getClass().getSimpleName(),"list notify");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(),"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(),"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(),"onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("floor",floor);
    }




}
