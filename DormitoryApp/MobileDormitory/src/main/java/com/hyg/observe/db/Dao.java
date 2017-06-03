package com.hyg.observe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hyg.observe.data.UserScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/4/18.
 */
public class Dao {
    private Context context;
    private DataBaseHelper helper;
    private Cursor cursor;
    public Dao(Context context){
        helper=new DataBaseHelper(context);
    }


    //获取用户成绩
    public UserScore getUserScore(String time,String userId){
        SQLiteDatabase db=helper.getWritableDatabase();
        cursor=db.rawQuery("select * from usergrade where frequency=? and s_id=?", new String[]{time,userId});
        UserScore userScore=null;
        while (cursor.moveToNext()){
            userScore=new UserScore();
            userScore.setBed(cursor.getInt(cursor.getColumnIndex("grade_bed")));
            userScore.setWall(cursor.getInt(cursor.getColumnIndex("grade_wall")));
            userScore.setDesk(cursor.getInt(cursor.getColumnIndex("grade_desk")));
            userScore.setGround(cursor.getInt(cursor.getColumnIndex("grade_ground")));
            userScore.setSecurity(cursor.getInt(cursor.getColumnIndex("grade_security")));
            userScore.setElectricity(cursor.getInt(cursor.getColumnIndex("grade_electricity")));
            userScore.setPublicArea(cursor.getInt(cursor.getColumnIndex("grade_public")));
            userScore.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
            userScore.setRank(cursor.getString(cursor.getColumnIndex("rank")));
        }
        return userScore;
    }

    public void setUserScore(){

    }

    //获取用户全部成绩
    public List<UserScore> getAllUserScore(){

        return null;
    }

    //获取检查次数
    public List<Map<String,Object>> getTimes(){
        SQLiteDatabase db=helper.getWritableDatabase();
        cursor=db.rawQuery("select * from usergrade group by frequency",null);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        while (cursor.moveToNext()){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("frequency",cursor.getInt(cursor.getColumnIndex("frequency")));
            map.put("total",cursor.getInt(cursor.getColumnIndex("total")));
            list.add(map);
        }
        return list;
    }

    public void setTimes(List<Map<String,Object>> mTimes){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL("delete from usergrade");
        ContentValues values=null;
        for (int i=0;i<mTimes.size();i++){
            values=new ContentValues();
            values.put("frequency",mTimes.get(i).get("frequency").toString());
            values.put("total",mTimes.get(i).get("total").toString());
            db.insert("usergrade","frequency",values);
        }

        db.close();
    }

    //
}
