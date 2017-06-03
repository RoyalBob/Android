package com.hyg.check.fragment;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyg.check.Utils.AppTools;
import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.Utils.JsonTools;
import com.hyg.check.Utils.SharedPreferenceUtil;
import com.hyg.check.adapter.SettingAdaper;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.DataConfig;
import com.hyg.check.db.DAO;
import com.hyg.check.config.ActionConfig;
import com.hyg.activity.R;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by hyg on 2015/3/6.
 * 功能描述：设置界面
 */

public class SettingFragment extends Fragment{
    private View mView;
    private ListView mList;
    private SettingAdaper mAdapter;
   // private String mStudent;
    private SweetAlertDialog mSweetDialog;
   // private DAO mDao;
    private Map<String,String> mMap;
    private AppApplication application;
    private String[] areaItems;
    private String[] buildItems;
    private int position=0;//item的项号
    private SharedPreferenceUtil spUtils;
    private AppTools mAppTools;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_setting,container,false);
        initData();
        initView();

        return mView;
    }
    private void initData(){
        application=(AppApplication)getActivity().getApplication();
        spUtils=application.getSpUtil();
        mAppTools=new AppTools(getActivity());
        String[] str=getActivity().getResources().getStringArray(R.array.setting_values);
        areaItems=getActivity().getResources().getStringArray(R.array.area_items);
        buildItems=getActivity().getResources().getStringArray(R.array.build_items);
        mMap=new HashMap<>();
        mMap.put(DataConfig.KEY_TITLE_UPDATE,str[0]);
        mMap.put(DataConfig.KEY_UPDATE,str[1]);
        mMap.put(DataConfig.KEY_TITLE_AREA,str[2]);
        mMap.put(DataConfig.KEY_AREA,mAppTools.flagToArea(spUtils.getArea()));
        mMap.put(DataConfig.KEY_TITLE_BUILDING,str[3]);
        mMap.put(DataConfig.KEY_BUILDING,mAppTools.flagToBuild(spUtils.getBuild()));

        //mDao=new DAO(getActivity());


    }

    private void initView(){
        mList=(ListView)mView.findViewById(R.id.lvSetting);
        mAdapter=new SettingAdaper(getActivity(),mMap);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new ItemClickedListener());

    }

    private class ItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 1:
                    showSweetDialog();
                    AppApplication application=(AppApplication)getActivity().getApplication();
                    RequestParams params=new RequestParams();
                    params.add("area", "ALL");
                    params.add("code",application.getSharedData().get(DataConfig.COLLEGE_NO).toString());
                    HttpUtil.getStudentInfo(application, params);

                    break;
                case 3:
                    showDialog(R.string.dialog_title_area,areaItems,0);

                    break;
                case 5:
                    showDialog(R.string.dialog_title_build,buildItems,1);
                    break;



            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(),"onStart");
        IntentFilter flilter=new IntentFilter();
        flilter.addAction(ActionConfig.GETINFO_SUCCESS);
        flilter.addAction(ActionConfig.GETINFO_FAIL);
        flilter.addAction(ActionConfig.GETINFO_PROGRESS);
        getActivity().registerReceiver(receiver,flilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(),"onDestroy");
        getActivity().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ActionConfig.GETINFO_SUCCESS.equals(intent.getAction())){

               // Log.i(getClass().getSimpleName(),intent.getStringExtra(ActionConfig.GETINFO_SUCCESS));
                //mStudent=intent.getStringExtra(ActionConfig.GETINFO_SUCCESS);
               // mStudent = application.data;
                //
//                List<Map<String,Object>> list= JsonTools.getData(mStudent);
//                mDao.updateRoomData(list);
                mSweetDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                mSweetDialog.setTitleText("Sucess");
                Toast.makeText(getActivity(),R.string.toast_update_success,Toast.LENGTH_SHORT).show();
                //application.data=null;
            }else if(ActionConfig.GETINFO_FAIL.equals(intent.getAction())){
                mSweetDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                mSweetDialog.setTitleText("Error");
                Toast.makeText(getActivity(),R.string.toast_update_error,Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void showSweetDialog(){
        mSweetDialog=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        mSweetDialog.setTitle(R.string.update_dialog_title);
        mSweetDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mSweetDialog.setTitleText("Loading");
        mSweetDialog.setCancelable(false);
        mSweetDialog.show();
    }

    private void showDialog(int title, final String[] item, final int flag){
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle(title)
        .setPositiveButton(R.string.dialog_ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(getClass().getSimpleName(),"----->"+which);
                SharedPreferenceUtil spUtil = application.getSpUtil();
                if(flag==0) {
                    mMap.put(DataConfig.KEY_AREA,item[position]);
                    spUtil.putArea(mAppTools.areaToFlag(item[position]));
                }else if(flag==1){
                    mMap.put(DataConfig.KEY_BUILDING,item[position]);
                    spUtil.putBuild(mAppTools.buildToFlag(item[position]));
                }
                dialog.dismiss();
                mAdapter.notifyDataSetChanged();
            }
        }).setSingleChoiceItems(item,position,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position=which;
            }
        })
        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }


}

