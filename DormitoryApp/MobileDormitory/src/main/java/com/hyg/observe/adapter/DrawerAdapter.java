package com.hyg.observe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyg.activity.R;


/**
 * Created by hyg on 2015/4/23.
 */
public class DrawerAdapter extends BaseAdapter{
    private Context context;
    private String[] drawerItems;
    public DrawerAdapter(Context context,String[] drawerItems){
        this.context=context;
        this.drawerItems=drawerItems;
    }

    @Override
    public int getCount() {
        return drawerItems.length;
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
        View view=null;
        view= LayoutInflater.from(context).inflate(R.layout.listview_drawer_items,null);
        TextView textView=(TextView)view.findViewById(R.id.tv_drawer_items);
        textView.setText(drawerItems[position]);
        return view;
    }
}
