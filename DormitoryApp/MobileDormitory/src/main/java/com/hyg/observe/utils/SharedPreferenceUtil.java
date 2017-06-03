package com.hyg.observe.utils;

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

    public void putCount(String count){
        editor.putString("count",count);
        editor.commit();
    }

    public String getCount(){
        return sp.getString("count","0");
    }


}
