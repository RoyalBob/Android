package com.hyg.check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.check.Utils.AppTools;
import com.hyg.check.entity.StudentEntity;
import com.hyg.activity.R;

import java.util.List;

/**
 * Created by hyg on 2015/3/18.
 * 功能描述：成绩录入的listview的适配器
 */
public class RoomAdapter extends BaseAdapter{


    private Context context;
    private List<StudentEntity> mEntity;
    private AppTools mAppTools;
    public RoomAdapter(Context context,AppTools mAppTools){
        this.context=context;
        this.mAppTools=mAppTools;

    }

    public void setAdapterData(List<StudentEntity> mEntity){
        this.mEntity=mEntity;
    }


    @Override
    public int getCount() {
        return mEntity.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_room,null);
            holder.mName=(TextView)convertView.findViewById(R.id.lv_room_name);
            holder.mBedNo=(TextView)convertView.findViewById(R.id.lv_room_bed);
            holder.mGrade=(TextView)convertView.findViewById(R.id.lv_room_grade);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.mName.setText(mEntity.get(position).getName());
        if(null!=mEntity.get(position).getBedId()) {
            holder.mBedNo.setText(mAppTools.flagToBed(mEntity.get(position).getBedId()));
        }
        holder.mGrade.setText(""+mEntity.get(position).getgTotal());
        return convertView;
    }

    private class ViewHolder{
        TextView mBedNo;
        TextView mGrade;
        TextView mName;
    }
}
