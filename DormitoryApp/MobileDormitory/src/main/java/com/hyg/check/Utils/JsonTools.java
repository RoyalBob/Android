package com.hyg.check.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/3/16.
 * 功能描述：json解析工具类
 */
public class JsonTools {

    //获取学生信息
    public static List<Map<String,Object>> getData(String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray array=object.getJSONArray("rows");
            for(int i=0;i<array.length();i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("s_id", array.getJSONObject(i).getString("学号"));
                map.put("s_name", array.getJSONObject(i).getString("学生姓名"));
                map.put("dormitory_id", array.getJSONObject(i).getString("住宿床位编号"));
                map.put("class", array.getJSONObject(i).getString("班级名称"));
                map.put("sex", array.getJSONObject(i).getString("性别"));
                list.add(map);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    //获取检查次数
    public static List<Map<String,Object>> getTime(String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray array=object.getJSONArray("rows");
            for(int i=0;i<array.length();i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("college", array.getJSONObject(i).getString("部门编号"));
                map.put("year", array.getJSONObject(i).getString("学年"));
                map.put("session", array.getJSONObject(i).getString("学期"));
                map.put("sex",array.getJSONObject(i).getString("性别"));
                map.put("frequency", array.getJSONObject(i).getString("次数"));
                map.put("flag", array.getJSONObject(i).getString("复查标记"));
                list.add(map);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }


    //获取检查次数(observe)
    public static List<Map<String,Object>> getCount(String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray array=object.getJSONArray("rows");
            for(int i=0;i<array.length();i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("frequency",array.getJSONObject(i).getInt("次数"));
                map.put("total",array.getJSONObject(i).getInt("总分"));
                list.add(map);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    //解析复查名单
    public static List<Map<String,Object>> getReview(String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray array=object.getJSONArray("rows");
            for(int i=0;i<array.length();i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("s_id",array.getJSONObject(i).getString("学号"));
                map.put("name",array.getJSONObject(i).getString("学生姓名"));
                map.put("dormitory_id",array.getJSONObject(i).getString("床号"));
                map.put("year",array.getJSONObject(i).getString("学年"));
                map.put("session",array.getJSONObject(i).getString("学期"));
                map.put("frequency",array.getJSONObject(i).getString("次数"));
                map.put("sex",array.getJSONObject(i).getString("性别"));
                list.add(map);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }
}
