package com.hyg.check.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 功能描述：SharedPreference工具类
 */
public class SharedPreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private Context context;

	public SharedPreferenceUtil(Context context, String file){
		this.context=context;
        sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
        editor = sp.edit();

	}

    /**
     * area
     */
    public void putArea(String area){
        editor.putString("area",area);
        editor.commit();
    }

    public String getArea(){
        return  sp.getString("area","A");
    }


    /**
     * building
     */

    public void putBuild(String building){
        editor.putString("building",building);
        editor.commit();
    }

    public String  getBuild(){
        return  sp.getString("building","01#");
    }

    public void putCount(String count){
        editor.putString("count",count);
        editor.commit();
    }

    public String getCount(){
        return sp.getString("count","1");
    }


}
