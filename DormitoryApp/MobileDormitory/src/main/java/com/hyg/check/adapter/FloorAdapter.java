package com.hyg.check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.check.entity.RoomInfo;

import java.util.List;

/**
 * Created by hyg on 2015/3/7.
 * 功能描述：房间信息gridview的适配器
 */
public class FloorAdapter extends BaseAdapter{
    private Context context;
    private List<RoomInfo> list;


    public FloorAdapter(Context context){
        this.context=context;

    }

    public void setFloorData(List<RoomInfo> list){
        this.list=list;
    }
    @Override
    public int getCount() {

        return list.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.gridview_room,null);
            holder.mRoomNo=(TextView)convertView.findViewById(R.id.lv_room_no);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.mRoomNo.setText(list.get(position).getRoomNo());
        return convertView;
    }

    class ViewHolder{
        private TextView mRoomNo;

    }
}
