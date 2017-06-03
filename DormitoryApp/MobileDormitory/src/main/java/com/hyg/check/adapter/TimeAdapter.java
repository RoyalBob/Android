package com.hyg.check.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.check.Utils.AppTools;

import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/3/18.
 * 功能描述：成绩录入的listview的适配器
 */
public class TimeAdapter extends BaseAdapter{
    private Context context;
    private String[] checkItems;
    private AppTools appTools;

    private List<Map<String,Object>> checkItem;
    public TimeAdapter(Context context){
        this.context=context;
        appTools=new AppTools(context);
    }

    public void setData(List<Map<String,Object>> checkItem){
        this.checkItem=checkItem;
    }


    @Override
    public int getCount() {
        return checkItem.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.dialog_select_time,null);
            holder.mCount=(TextView)convertView.findViewById(R.id.dialog_time_count);
            holder.mCount.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            holder.mSex=(TextView)convertView.findViewById(R.id.dialog_time_sex);
            holder.mSex.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            holder.mCheck=(TextView)convertView.findViewById(R.id.dialog_time_check);
            holder.mCheck.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.mCount.setText(appTools.flagToCount(checkItem.get(position).get("frequency").toString()));
        holder.mSex.setText(checkItem.get(position).get("sex").toString());
        if("0".equals(checkItem.get(position).get("flag")))
            holder.mCheck.setText("检查");
        if("1".equals(checkItem.get(position).get("flag")))
            holder.mCheck.setText("复查");
        return convertView;
    }

    private class ViewHolder{
        TextView mCount;
        TextView mSex;
        TextView mCheck;
    }
}
