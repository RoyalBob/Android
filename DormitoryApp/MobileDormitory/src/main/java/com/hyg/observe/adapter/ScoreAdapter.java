package com.hyg.observe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;

import java.util.Map;

/**
 * Created by hyg on 2015/3/18.
 * 功能描述：成绩录入的listview的适配器
 */
public class ScoreAdapter extends BaseAdapter{


    private Context context;
    private Map<String,Object> userScore;
    private String[] scoreItem;
    public ScoreAdapter(Context context){
        this.context=context;
    }

    public void setAdapterData(Map<String,Object> userScore,String[] scoreItem){
        this.userScore=userScore;
        this.scoreItem=scoreItem;
    }


    @Override
    public int getCount() {
        return userScore.size();
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
        if (null==convertView){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_score,null);
            holder.mItem =(TextView)convertView.findViewById(R.id.tv_item);
            holder.mScore =(TextView)convertView.findViewById(R.id.tv_score);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        if(position%2!=0){
            convertView.setBackgroundColor(Color.rgb(224,224,224));
        }else{
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }
        holder.mItem.setText(scoreItem[position]);
        if(null!=userScore.get(scoreItem[position])) {
            holder.mScore.setText(userScore.get(scoreItem[position]).toString());
        }
        return convertView;
    }

    private class ViewHolder{
        TextView mItem;
        TextView mScore;
    }
}
