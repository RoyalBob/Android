package com.hyg.observe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;

import java.util.Map;


/**
 * Created by hyg on 2015/4/28.
 */
public class UserInfoAdapter extends BaseAdapter{

    private Context context;
    private Map<String,Object> map;
    private String[] userItem;
    public UserInfoAdapter(Context context){
        this.context=context;
    }

    public void setData(Map<String,Object> map,String[] userItem){
        this.map=map;
        this.userItem=userItem;
    }
    @Override
    public int getCount() {
        return userItem.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null==convertView){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_userinfo,null);
            holder.userItem=(TextView)convertView.findViewById(R.id.user_item);
            holder.userMessage=(TextView)convertView.findViewById(R.id.user_message);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.userItem.setText(userItem[position]);
        holder.userMessage.setText(map.get(userItem[position]).toString());
        return convertView;
    }

    private class ViewHolder{
        TextView userItem;
        TextView userMessage;
    }
}
