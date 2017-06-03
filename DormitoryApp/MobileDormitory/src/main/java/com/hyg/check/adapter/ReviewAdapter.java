package com.hyg.check.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;
import com.hyg.check.Utils.AppTools;
import com.hyg.check.entity.ReviewEntity;

import java.util.List;

/**
 * Created by hyg on 2015/5/11.
 */
public class ReviewAdapter extends BaseAdapter{

    private Context context;
    private List<ReviewEntity> list;
    private AppTools mAppTools;


    public ReviewAdapter(Context context){
        this.context=context;
        mAppTools=new AppTools(context);
    }

    public void setData(List<ReviewEntity> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        if (null!=list){
            return list.size();
        }else{
            return 0;
        }

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
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_review,null);
            holder.mRoomNo=(TextView)convertView.findViewById(R.id.lv_review_room);
            holder.mArea=(TextView)convertView.findViewById(R.id.lv_review_area);
            holder.mBuilding=(TextView)convertView.findViewById(R.id.lv_review_building);
            holder.mName=(TextView)convertView.findViewById(R.id.lv_review_name);
            holder.mBed=(TextView)convertView.findViewById(R.id.lv_review_bed);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        String dormitory=list.get(position).getDormitoryId();
        holder.mRoomNo.setText(dormitory.substring(4,dormitory.length()));
        holder.mArea.setText(mAppTools.flagToArea(dormitory.substring(0,1)));
        holder.mBuilding.setText(mAppTools.flagToBuild(dormitory.substring(1,4)));
        if(null!=list.get(position).getBedId()){
            holder.mBed.setText(mAppTools.flagToBed2(list.get(position).getBedId()));
        }
        holder.mName.setText(list.get(position).getStudentName());
        return convertView;
    }

    class ViewHolder{
        private TextView mRoomNo;
        private TextView mArea;
        private TextView mBuilding;
        private TextView mBed;
        private TextView mName;
    }
}
