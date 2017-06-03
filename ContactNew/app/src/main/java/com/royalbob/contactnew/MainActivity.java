package com.royalbob.contactnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Map<String, String>> ListItems = new ArrayList<Map<String, String>>();
        final EditText edtNumber = (EditText) findViewById(R.id.edtNumber);
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        String[] numbers = {"134173328", "134173309"};
        String[] names = {"Royal_Bob", "MUSTFOREVER"};
        for (int i = 0; i < numbers.length; i++) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("number", numbers[i]);
            item.put("name", names[i]);
            ListItems.add(item);
        }


        final BaseAdapter adapter = new SimpleAdapter(this.getApplicationContext(),
                ListItems,
                R.layout.itemlayout,
                new String[]{"number", "name"},
                new int[]{R.id.textView1, R.id.textView2});
        ListView listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Map<String, String> item = (Map<String, String>) arg0.getItemAtPosition(arg2);
                edtNumber.setText(item.get("number").toString());
                edtName.setText(item.get("name").toString());
                iCurrentPos = arg2;
            }
        });

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnModify = (Button) findViewById(R.id.btnModify);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Map<String, String> item = new HashMap<String, String>();
                item.put("number", edtNumber.getText().toString());
                item.put("name", edtName.getText().toString());
                ListItems.add(item);
                adapter.notifyDataSetChanged();
                edtNumber.setText("");
                edtName.setText("");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (iCurrentPos == -1) {
                    Toast.makeText(MainActivity.this, "Nothing selected!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    ListItems.remove(iCurrentPos);
                    adapter.notifyDataSetChanged();
                    iCurrentPos = -1;
                    edtNumber.setText("");
                    edtName.setText("");
                }
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (iCurrentPos == -1) {
                    Toast.makeText(MainActivity.this, "Nothing selected!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Map<String, String> item = (Map<String, String>) adapter.getItem(iCurrentPos);
                    item.put("number", edtNumber.getText().toString());
                    item.put("name", edtName.getText().toString());
                    adapter.notifyDataSetChanged();
                    edtNumber.setText("");
                    edtName.setText("");
                }
            }
        });
    }
}