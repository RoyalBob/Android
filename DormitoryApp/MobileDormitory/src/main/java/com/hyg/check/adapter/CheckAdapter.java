package com.hyg.check.adapter;

import android.content.Context;
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
public class CheckAdapter extends BaseAdapter{
    private Context context;
    private String[] checkItems;
    private Map<String,Object> mGrade;
    public CheckAdapter(Context context, Map<String, Object> mGrade){
        this.context=context;
        this.mGrade=mGrade;
        checkItems=context.getResources().getStringArray(R.array.check_item);
    }


    @Override
    public int getCount() {
        return mGrade.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_check,null);
            holder.mCheckItem=(TextView)convertView.findViewById(R.id.lv_check_item);
            holder.mGrade=(TextView)convertView.findViewById(R.id.lv_check_grade);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.mCheckItem.setText(checkItems[position]);
        holder.mGrade.setText("-"+mGrade.get(checkItems[position]).toString());
        return convertView;
    }

    private class ViewHolder{
        TextView mCheckItem;
        TextView mGrade;
    }
}
