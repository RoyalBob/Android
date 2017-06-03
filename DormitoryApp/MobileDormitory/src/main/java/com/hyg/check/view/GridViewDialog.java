package com.hyg.check.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hyg.activity.R;

/**
 * Created by hyg on 2015/4/22.
 */
public class GridViewDialog extends Dialog{


    private TextView mTitleTextView;
    private GridView mGridView;
    private String mTitleText;
    private Context context;
    private AdapterView.OnItemClickListener mItemClickedListener;
    private String[] items;

    public GridViewDialog(Context context, int theme) {
        super(context, theme);
    }

    public GridViewDialog(Context context) {
        this(context, R.style.Dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gridview);
        mTitleTextView=(TextView)this.findViewById(R.id.dialog_title);
        mTitleTextView.setText(mTitleText);
        mGridView=(GridView)this.findViewById(R.id.dialog_girdview);
        mGridView.setAdapter(new ItemAdapter());
        mGridView.setOnItemClickListener(mItemClickedListener);

    }

    public GridViewDialog setTitleText(String title){
        mTitleText=title;
        return this;
    }

    public GridViewDialog setTitleText(int id){
        mTitleText=context.getResources().getString(id);
        return this;
    }
    public GridViewDialog setChoiceItems(String[] items,AdapterView.OnItemClickListener listener){
        mItemClickedListener=listener;
        this.items=items;
        return this;
    }

    private class ItemAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
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
            View view= LayoutInflater.from(context).inflate(R.layout.gridview_room,null);
            TextView textView=(TextView)view.findViewById(R.id.lv_room_no);
            textView.setText(items[position]);
            return view;
        }
    }





}
