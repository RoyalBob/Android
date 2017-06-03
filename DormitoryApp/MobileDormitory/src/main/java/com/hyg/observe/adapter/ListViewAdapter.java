package com.hyg.observe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.observe.utils.AppTools;

import java.util.List;
import java.util.Map;


/**
 * Created by hyg on 2015/4/16.
 */
public class ListViewAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String,Object>> mTimes;
    private AppTools appTools;


    public ListViewAdapter(Context context){
        this.context=context;
        appTools=new AppTools(context);
    }

    public void setData(List<Map<String,Object>> mTimes){
        this.mTimes=mTimes;
    }
    @Override
    public int getCount() {
        return mTimes.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_time,null);
            holder.mTime=(TextView)convertView.findViewById(R.id.lv_time);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        List<String> count=appTools.flagToCount(mTimes);
        holder.mTime.setText(count.get(position).toString());
        return convertView;
    }

    class ViewHolder{
        private TextView mTime;
    }
}
