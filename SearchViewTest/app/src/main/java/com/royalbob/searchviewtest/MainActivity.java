package com.royalbob.searchviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private SearchView srv1;
    private ListView lv1;
    private ArrayAdapter<String> aadapter;
    private String[] names;
    private ArrayList<String> alist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srv1=(SearchView)findViewById(R.id.srv1);
        names=new String[]{"ad","dffa","uyiu","rqer","qwgt","afrgb","rtyr"};
        lv1=(ListView)findViewById(R.id.lv1);
        aadapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, names);

        lv1.setAdapter(aadapter);
        lv1.setTextFilterEnabled(true);

        srv1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Toast.makeText(MainActivity.this, "1111", Toast.LENGTH_LONG).show();
                if(newText.length()!=0){
                    lv1.setFilterText(newText);
                }else{
                    lv1.clearTextFilter();
                }
                return false;
            }
        });
    }

}