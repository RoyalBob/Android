package com.royalbob.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by RoyalBob on 2016/7/20.
 */
public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserInfo> arrayList_UserInfo =new ArrayList<UserInfo>();

    public UserAdapter(Context context, ArrayList<UserInfo> arrayList_UserInfo) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return arrayList_UserInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_UserInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {

            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.layout_mainitem, null);
            holder.imageView_mainitem = (ImageView) convertView.findViewById(R.id.imageView_mainitem);
            holder.textView_mainitem = (TextView) convertView.findViewById(R.id.textView_mainitem);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        UserInfo userinfoItem= arrayList_UserInfo.get(position);

        Bitmap bt = BitmapFactory.decodeFile("/sdcard/myHead/head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            holder.imageView_mainitem.setImageDrawable(drawable);
        }

        holder.textView_mainitem.setText(userinfoItem.getmName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView_mainitem;
        TextView textView_mainitem;
    }
}