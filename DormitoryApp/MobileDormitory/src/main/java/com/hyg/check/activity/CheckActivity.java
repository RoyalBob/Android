package com.hyg.check.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hyg.activity.R;
import com.hyg.check.Utils.AppTools;
import com.hyg.check.Utils.HttpUtil;
import com.hyg.check.adapter.CheckAdapter;
import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.config.DataConfig;
import com.hyg.check.db.DAO;
import com.hyg.check.entity.StudentEntity;
import com.hyg.check.view.GridViewDialog;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyg on 2015/3/7.
 *  功能描述：成绩的录入及上传功能
 */

public class CheckActivity extends Activity{
    private Toolbar mToolbar;
    private ListView mList;
    private TextView mTitle;
    private RadioGroup securityGroup;
    private RadioGroup electricityGroup;
    private String[] checkItems;
    private String[] checkGrade;
    /** 存放哪项检查，多少分数。项目名作为Key，分数作为Value*/
    private Map<String,Object> mGrade;
    private CheckAdapter mAdapter;
    private StudentEntity mItems;
    private String mRoomNo;
    private int mCount;//寝室学生个数
    private Intent intent;
    private String mName;
    private String mBedId;
    private String mStudentId;
    private String mDormitoryId;
    private String review;
    private AppTools appTools;
    private StudentEntity mEntity;
    private AppApplication application;
    private Map<String,Object> sharedData;
    private DAO mDao;
    private ActionProcessButton mActionButton,mNotCheckButton;
    private GridViewDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);
        initData();
        initView();
        initToolbar();

    }

    /**
     * 注册广播
     */

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter();
        filter.addAction(ActionConfig.SUBMIT_GRADE_SUCCESS);
        filter.addAction(ActionConfig.SUBMIT_GRADE_FAIL);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void initData() {
        //获取application实例
        application=(AppApplication)getApplication();
        sharedData=application.getSharedData();
        //接收姓名，床号，学生id；
        intent=getIntent();
        mName=intent.getStringExtra("name");
        mBedId=intent.getStringExtra("bedId");
        mStudentId=intent.getStringExtra("studentId");
        review=intent.getStringExtra("review");
        mDormitoryId=intent.getStringExtra("dormitoryId");

        appTools=new AppTools(this);
        mDao=new DAO(this);
        //初始化学生寝室信息（成绩）
        initEntity();
        //初始化listview显示信息
        initItemData();
    }


    private void initItemData() {
        //获取listview的item信息
        checkItems=getResources().getStringArray(R.array.check_item);
        mGrade=new HashMap<String,Object>();
        mGrade.put(checkItems[0],mEntity.getgBed());
        mGrade.put(checkItems[1],mEntity.getgWall());
        mGrade.put(checkItems[2],mEntity.getgDesk());
        mGrade.put(checkItems[3],mEntity.getgGround());
        mGrade.put(checkItems[4],mEntity.getgPublic());
        mGrade.put(checkItems[5],mEntity.getgSafetyConcept());
    }

    /**
     * 初始化学生寝室信息（成绩）
     */
    private void initEntity() {

        if(null==mDao.getUserScore(mStudentId,sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString())){
            mEntity=new StudentEntity();
            mEntity.setStudentId(mStudentId);
            mEntity.setBedId(mBedId);
            mEntity.setgPublic(0);
            mEntity.setgBed(0);
            mEntity.setgWall(0);
            mEntity.setgDesk(0);
            mEntity.setgGround(0);
            mEntity.setgSafetyConcept(0);
            mEntity.setgElectricitySafe(1);//安全用电，默认为1，表示安全，0表示不安全
            mEntity.setgTotal(0);
        }else{
            mEntity=mDao.getUserScore(mStudentId,sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString());
        }
    }

    /**
     * 初始化view
     */
    private void initView() {

        mToolbar=(Toolbar)this.findViewById(R.id.check_toolbar);
        electricityGroup=(RadioGroup)this.findViewById(R.id.electricity_group);
        mActionButton=(ActionProcessButton)this.findViewById(R.id.upload);
        mNotCheckButton = (ActionProcessButton)this.findViewById(R.id.notcheckupload);
        //默认为是true
        if(mEntity.getgElectricitySafe()==1)
            electricityGroup.check(R.id.electricity_true);
        else
            electricityGroup.check(R.id.electricity_false);
        mList=(ListView)this.findViewById(R.id.check_item);
        mAdapter=new CheckAdapter(this,mGrade);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new ItemClickedListener());
//        securityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId){
//                    //选择是
//                    case R.id.security_true:
//                        mEntity.setgSafetyConcept(1);
//                        break;
//                    //选择否
//                    case R.id.security_false:
//                        mEntity.setgSafetyConcept(0);
//                        break;
//                }
//            }
//        });
        electricityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    //选择是
                    case R.id.electricity_true:
                        mEntity.setgElectricitySafe(1);
                        break;
                    //选择否
                    case R.id.electricity_false:
                        mEntity.setgElectricitySafe(0);
                        break;
                }
            }
        });

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDao.insertGrade(sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString(),
                        mEntity.getgBed(),mEntity.getgWall(),mEntity.getgDesk(),mEntity.getgPublic(),mEntity.getgGround()
                        ,mEntity.getgSafetyConcept(),mEntity.getgElectricitySafe(),mEntity.getStudentId(),getTotal());
                HttpUtil.submitGrade(application, getRequestParams());
                mActionButton.setProgress(50);
            }
        });

        mNotCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDao.insertGrade(sharedData.get("year").toString(),sharedData.get("session").toString(),sharedData.get("count").toString(),
                        mEntity.getgBed(),mEntity.getgWall(),mEntity.getgDesk(),mEntity.getgPublic(),mEntity.getgGround()
                        ,mEntity.getgSafetyConcept(),mEntity.getgElectricitySafe(),mEntity.getStudentId(), DataConfig.NOT_CHECKED_FLAG);
                HttpUtil.submitGrade(application, getRequestParams());
                mNotCheckButton.setProgress(50);
            }
        });
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar(){
        mTitle=new TextView(this);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        mTitle.setText(mName);
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

        mToolbar.setTitle(appTools.flagToBed2(mBedId));
        mToolbar.setSubtitle("");
    }



    private class ItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkGradeDialog(position);

        }
    }



    /**
     * 导航键点击事件，上一个
     */
    private void navigationClicked(){

        this.finish();
    }


    /**
     * 选择成绩的对话框
     * @param position listview的第几项
     */
    private void checkGradeDialog(final int position){
        //从strings.xml获取当前可以扣分的选项值，初始为1~14，再加0，共15项
        checkGrade=getResources().getStringArray(R.array.check_grade);
        //GridViewDialog，是个扣分的弹出对话框，扣分值直接在该对话框选择
        mDialog=new GridViewDialog(CheckActivity.this);
        mDialog.setTitleText(R.string.mdialog_title_grade);
        mDialog.setChoiceItems(checkGrade,new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                mGrade.put(checkItems[position],checkGrade[which]);
                mAdapter.notifyDataSetChanged();
                putItemGrade(position,which);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    /**
     * 将选取的分数存入学生成绩信息实例
     * @param position
     * @param which
     */
    public void putItemGrade(int position,int which){

        switch (position){
            //床面分数
            case 0:
                mEntity.setgBed(Integer.parseInt(checkGrade[which]));
                break;
            //墙面分数
            case 1:
                mEntity.setgWall(Integer.parseInt(checkGrade[which]));
                break;
            //桌面分数
            case 2:
                mEntity.setgDesk(Integer.parseInt(checkGrade[which]));
                break;
            //地面分数
            case 3:
                mEntity.setgGround(Integer.parseInt(checkGrade[which]));
                break;
            //公共区域分数
            case 4:
                mEntity.setgPublic(Integer.parseInt(checkGrade[which]));
                break;
            case 5:
                mEntity.setgSafetyConcept(Integer.parseInt(checkGrade[which]));
                break;
        }
        Log.i(getClass().getSimpleName(),mEntity.toString());

    }

    /**
     * 设置请求参数
     * @return
     */
    private RequestParams getRequestParams(){
        RequestParams params=new RequestParams();
        params.put("number",mEntity.getStudentId());
        params.put("year",sharedData.get("year"));
        params.put("term",sharedData.get("session"));
        params.put("times",sharedData.get("count").toString());
        params.put("bedid",mDormitoryId);
        params.put("bed",mEntity.getgBed());
        params.put("wall",mEntity.getgWall());
        params.put("desktop",mEntity.getgDesk());
        params.put("ground",mEntity.getgGround());
        params.put("security",mEntity.getgSafetyConcept());
        params.put("public",mEntity.getgPublic());
        params.put("electricity",mEntity.getgElectricitySafe());
        //注意数据库存储的都是正分数，处理时要变成扣分
        params.put("score",mEntity.getgTotal());
        params.put("review",review);
        return params;
    }


    /**
     * 上传成绩广播接收
     */

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(ActionConfig.SUBMIT_GRADE_SUCCESS.equals(intent.getAction())){
                String[] tips=getResources().getStringArray(R.array.submit_tips);
                String result=intent.getStringExtra(ActionConfig.SUBMIT_GRADE_SUCCESS);

                if("score error".equals(result)){
                    result=tips[0];
                    mActionButton.setProgress(-1);
                }else if("permission deny".equals(result)){
                    result=tips[1];
                    mActionButton.setProgress(-1);
                }else if("closed".equals(result)){
                    result=tips[2];
                    mActionButton.setProgress(-1);
                }else if("not login".equals(result)){
                    result=tips[3];
                    mActionButton.setProgress(-1);
                }
                else if ("A".equals(result)||"B".equals(result)||"C".equals(result)){
                    result=tips[4];

                    mActionButton.setProgress(100);
                    CheckActivity.this.finish();
                }else if("config error".equals(result)){
                    result=tips[5];
                    mActionButton.setProgress(-1);

                }else if("incomplete data or param error".equals(result)){
                    result=tips[6];
                    mActionButton.setProgress(-1);
                }else if("unknown error".equals(result)) {
                    result = tips[7];
                    mActionButton.setProgress(-1);
                }else if("Z".equals(result)) {
                    result = tips[8];
                    mNotCheckButton.setProgress(100);
                    CheckActivity.this.finish();
                }
                Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
            }else if(ActionConfig.SUBMIT_GRADE_FAIL.equals(intent.getAction())){
                Toast.makeText(context, R.string.login_service_err, Toast.LENGTH_SHORT).show();
                mActionButton.setProgress(-1);
            }
        }
    };

    /**
     * 获取总分
     * @return  如果用电安全为0即不安全，则总分设置为-2（-1用于表示本学生本次不能检查）
     */
    public int getTotal(){
        if(mEntity.getgElectricitySafe()==0){
            mEntity.setgTotal(-2);
            return -2;
        }else{
            int total =100-(mEntity.getgBed()+mEntity.getgDesk()+mEntity.getgGround()
                    +mEntity.getgWall()+mEntity.getgPublic()+mEntity.getgSafetyConcept());
            mEntity.setgTotal(total);
            return total;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
