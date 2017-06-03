package com.contact.royalbob.contact;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int iCurrentPos = -1;
    private ContentValues content = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sqlite.initDatabase(this);
        final List<Map<String,String>> ListItems = new ArrayList<Map<String,String>>();
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtTel = (EditText) findViewById(R.id.edtTel);
        final EditText edtMail = (EditText) findViewById(R.id.edtMail);
        Cursor cursor = Sqlite.mDb.rawQuery("select * from contacts", null);
        while(cursor.moveToNext())
        {
            Map<String, String> item = new HashMap<String, String>();
            item.put("姓名", cursor.getString(0));
            item.put("电话", cursor.getString(1));
            item.put("邮箱", cursor.getString(2));
            ListItems.add(item);
        }
        final BaseAdapter adapter = new SimpleAdapter(this.getApplicationContext(),
                ListItems,
                R.layout.itemlayout,
                new String[]{"姓名","电话","邮箱"},
                new int[]{R.id.tvNames,R.id.tvTels,R.id.tvMails});
        ListView listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, String> item = (Map<String, String>) arg0.getItemAtPosition(arg2);
                edtName.setText(item.get("姓名").toString());
                edtTel.setText(item.get("电话").toString());
                edtMail.setText(item.get("邮箱").toString());
                iCurrentPos = arg2;
            }
        });
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(edtName.getText().toString().equals("") && iCurrentPos == -1)
                {
                    Toast.makeText(MainActivity.this, "Nothing to add OR Name can't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    content.put("姓名", edtName.getText().toString());
                    content.put("电话", edtTel.getText().toString());
                    content.put("邮箱", edtMail.getText().toString());
                    Sqlite.mDb.insert("contacts", null, content);
                    Map<String,String> item = new HashMap<String, String>();
                    item.put("姓名", edtName.getText().toString());
                    item.put("电话", edtTel.getText().toString());
                    item.put("邮箱", edtMail.getText().toString());
                    ListItems.add(item);
                    adapter.notifyDataSetChanged();
                    iCurrentPos = -1;
                    edtName.setText("");
                    edtTel.setText("");
                    edtMail.setText("");
                }
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                Map<String, String> item = (Map<String, String>) arg0.getItemAtPosition(arg2);
                Sqlite.mDb.delete("contacts", "姓名='"+item.get("姓名").toString()+"'", null);
                ListItems.remove(arg2);
                adapter.notifyDataSetChanged();
                iCurrentPos = -1;
                edtName.setText("");
                edtTel.setText("");
                edtMail.setText("");
                return false;
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(iCurrentPos == -1)
                {
                    Toast.makeText(MainActivity.this, "Nothing To Update!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    content.put("姓名", edtName.getText().toString());
                    content.put("电话", edtTel.getText().toString());
                    content.put("邮箱", edtMail.getText().toString());
                    Sqlite.mDb.update("contacts", content, "姓名='"+edtName.getText().toString()+"'", null);
                    Map<String, String> item = (Map<String, String>) adapter.getItem(iCurrentPos);
                    item.put("姓名", edtName.getText().toString());
                    item.put("电话", edtTel.getText().toString());
                    item.put("邮箱", edtMail.getText().toString());
                    adapter.notifyDataSetChanged();
                    iCurrentPos = -1;
                    edtName.setText("");
                    edtTel.setText("");
                    edtMail.setText("");
                }
            }
        });
    }
}
