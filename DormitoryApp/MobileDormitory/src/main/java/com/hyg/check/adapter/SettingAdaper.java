package com.hyg.check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.check.config.DataConfig;
import com.hyg.activity.R;

import java.util.Map;

/**
 * Created by hyg on 2015/3/16.
 * 功能描述：设置界面listview的适配器
 */
public class SettingAdaper extends BaseAdapter {
    private Context context;
    private Map<String,String> mMap;
    private String[] key={DataConfig.KEY_TITLE_UPDATE,DataConfig.KEY_UPDATE,DataConfig.KEY_TITLE_AREA,DataConfig.KEY_AREA,DataConfig.KEY_TITLE_BUILDING,DataConfig.KEY_BUILDING};
    public SettingAdaper(Context context,Map<String,String> mMap){
        this.context=context;
        this.mMap=mMap;
    }
    @Override
    public int getCount() {
        return mMap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if(key[position].equals(DataConfig.KEY_TITLE_BUILDING)||key[position].equals(DataConfig.KEY_TITLE_AREA)){
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        if(position%2==0){
            view=LayoutInflater.from(context).inflate(R.layout.listview_setting_title,null);
            TextView textView=(TextView)view.findViewById(R.id.listview_item_title);
            textView.setText(mMap.get(key[position]).toString());

        }else{
            view=LayoutInflater.from(context).inflate(R.layout.listview_setting,null);
            TextView textView=(TextView)view.findViewById(R.id.listview_item);
            textView.setText(mMap.get(key[position]).toString());
        }
        return view;
    }
}
