package com.royalbob.contacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static String path="/sdcard/myHead/";//sd路径
    private ImageView imageView;
    private Drawable drawable;
    private ListView listview;
    private EditText et_search;
    private UserAdapter adapter;
    private ArrayList<UserInfo> arrayList_UserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
    }

    private void initView() {
        Sqlite.initDatabase(this);
        Sqlite.loadUserInfo(arrayList_UserInfo);
        listview = (ListView) findViewById(R.id.listView1);
        et_search = (EditText) findViewById(R.id.et_search);
        imageView = (ImageView)findViewById(R.id.imageView_mainitem);
        adapter = new UserAdapter(this, arrayList_UserInfo);
        listview.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("通讯录");//设置主标题
        toolbar.setSubtitle("联系人");//设置子标题
        setSupportActionBar(toolbar);

//        Sqlite.initDatabase(this);
//        final List<Map<String,Object>> ListItems = new ArrayList<Map<String,Object>>();
//        Cursor cursor = Sqlite.mDb.rawQuery("select * from contacts", null);
//        arrayList_UserInfo = new ArrayList<UserInfo>();
//        while(cursor.moveToNext())
//        {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("姓名", cursor.getString(0));
//            Bitmap bt = BitmapFactory.decodeFile(path + cursor.getString(3));//从Sd中找头像，转换成Bitmap
//            if(bt!=null){
//                drawable = new BitmapDrawable(bt);//转换成drawable
////                imageView.setImageDrawable(drawable);
//                item.put("头像", drawable);
//            }else {
//                item.put("头像", R.drawable.am);
//            }
//            ListItems.add(item);
//        }

//        final BaseAdapter adapter = new SimpleAdapter(this.getApplicationContext(),
//                ListItems,
//                R.layout.layout_mainitem,
//                new String[]{"头像","姓名"},
//                new int[]{R.id.imageView_mainitem, R.id.textView_mainitem});

        listview.setAdapter(adapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Snackbar.make(getWindow().getDecorView(),"Text Changed!",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, String> item = (Map<String, String>) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, ShowContacts.class);
                intent.putExtra("Name",item.get("姓名").toString());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditContacts.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Snackbar.make(getWindow().getDecorView(), "Setting", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
