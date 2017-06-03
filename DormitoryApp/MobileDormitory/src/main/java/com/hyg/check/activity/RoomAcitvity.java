package com.hyg.check.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyg.activity.R;
import com.hyg.check.Utils.AppTools;
import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.Utils.SharedPreferenceUtil;
import com.hyg.check.adapter.RoomAdapter;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.StudentEntity;
import com.loopj.android.http.RequestParams;

import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/3/25.
 */
public class RoomAcitvity extends ActionBarActivity{
    private Toolbar mToolbar;
    private String mRoomNo;
    private ListView mList;
    private RoomAdapter mAdapter;
    private DAO mDao;
    private List<StudentEntity> mEntity;
    private String[] bedItems;
    private AppApplication roomActivityApplication;
    private int mItemNo; //选择修改的项号
    private AppTools mAppTools;
    private int bedNumber=0;
    private Map<String,Object> sharedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        initData();
        initView();
        initToolbar();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        mEntity=mDao.selectRoomData(getDormitoryNo());
        mDao.setScore(mEntity,sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString());
        mAdapter.setAdapterData(mEntity);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ActionConfig.CHANGE_BED_SUCCESS);
        filter.addAction(ActionConfig.CHANGE_BED_FAIL);
        registerReceiver(receiver, filter);
    }

    private void initView() {
        mToolbar=(Toolbar)this.findViewById(R.id.room_toolbar);
        mList=(ListView)this.findViewById(R.id.room_item);
        mAdapter=new RoomAdapter(this,mAppTools);
        mAdapter.setAdapterData(mEntity);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new ItemClickedListener());
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mItemNo=position;
                setBedNo(position);
                return true;

            }
        });
    }

    private void initData() {
        mDao=new DAO(this);
        mAppTools=new AppTools(this);
        roomActivityApplication =(AppApplication)getApplication();
        sharedData=roomActivityApplication.getSharedData();
        mRoomNo=getIntent().getStringExtra("roomNo");
        mEntity=mDao.selectRoomData(getDormitoryNo());
        mDao.setScore(mEntity,sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString());
        bedItems=getResources().getStringArray(R.array.bed_items);
    }

    private void initToolbar(){
        mToolbar.setNavigationIcon(R.drawable.header_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomAcitvity.this.finish();
            }
        });
        mToolbar.setTitle(mRoomNo);


    }

    private class ItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //当无床位号，网络请求设置床位号
            if(null==mEntity.get(position).getBedId()){
                mItemNo=position;
                setBedNo(position);
            }
            else if(false==mDao.inspectBedNumber(getDormitoryNo())){
                Toast.makeText(RoomAcitvity.this,R.string.toast_repeat,Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent();
                intent.setClass(RoomAcitvity.this,CheckActivity.class);
                intent.putExtra("name", mEntity.get(position).getName());
                intent.putExtra("studentId",mEntity.get(position).getStudentId());
                intent.putExtra("bedId",mEntity.get(position).getBedId());
                intent.putExtra("dormitoryId",getDormitoryNo()+mEntity.get(position).getBedId());
                intent.putExtra("review","0");
                startActivity(intent);
            }
        }
    }


    private String getDormitoryNo(){
        StringBuffer buffer=new StringBuffer();

        AppApplication application=(AppApplication)getApplication();
        SharedPreferenceUtil spUtil=application.getSpUtil();
        buffer.append(spUtil.getArea()).append(spUtil.getBuild()).append(mRoomNo);
        return buffer.toString();
    }


    //网络请求设置床位号
    private void setBedNo(final int position){

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle(R.string.dialog_title_bed).setSingleChoiceItems(bedItems,0,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bedNumber=which;
                Toast.makeText(RoomAcitvity.this,bedItems[which],Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton(R.string.dialog_ok,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestParams params=new RequestParams();
                params.add("number",mEntity.get(position).getStudentId());
                params.add("code",getDormitoryNo()+mAppTools.bedToFlag(bedItems[bedNumber]));
                HttpUtil.changeBedNo(roomActivityApplication,params);

            }
        }).setNegativeButton(R.string.dialog_cancel,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }


    /**
     * 网络请求设置床位号成功后修改数据库并更新界面
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ActionConfig.CHANGE_BED_SUCCESS.equals(intent.getAction())){
                String str=intent.getStringExtra(ActionConfig.CHANGE_BED_SUCCESS);
                Log.i(getClass().getSimpleName(),str);
                if ("1".equals(str)){
                    //修改数据库床号
                    mDao.updateBedNo(mEntity.get(mItemNo).getStudentId(),mAppTools.bedToFlag(bedItems[bedNumber]));
                    //显示数据
                    mEntity=mDao.selectRoomData(getDormitoryNo());
                    mAdapter.setAdapterData(mEntity);
                    mAdapter.notifyDataSetChanged();
                    //床号对话框初始为1号床
                    bedNumber=0;
                    Toast.makeText(context,R.string.toast_update_success,Toast.LENGTH_SHORT).show();
                }else if("0".equals(str)){
                    Toast.makeText(context,R.string.toast_update_error,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,R.string.toast_update_error,Toast.LENGTH_SHORT).show();
                }

            }else if(ActionConfig.CHANGE_BED_FAIL.equals(intent.getAction())){
                Toast.makeText(context,R.string.login_service_err,Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
