package com.royalbob.test_swiperefreshlayout;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ScrollView scrollView;
    private TextView textView;
    String str = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("Title");//设置主标题
        toolbar.setSubtitle("Subtitle");//设置子标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Snackbar.make(getWindow().getDecorView() , "R.string.menu_search" , Snackbar.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_notification) {
                    Snackbar.make(getWindow().getDecorView() , "R.string.menu_notifications" , Snackbar.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item1) {
                    Snackbar.make(getWindow().getDecorView() , "R.string.item_01" , Snackbar.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Snackbar.make(getWindow().getDecorView() , "R.string.item_02" , Snackbar.LENGTH_SHORT).show();

                }
                return true;
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        scrollView = (ScrollView) findViewById(R.id.scrollview1);
        textView = (TextView)findViewById(R.id.textView1);
        for(int i=0 ; i<80; i++)
        {
            str+= i + "\n";
            textView.setText(str);
        }
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(false);
                final Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "Hello", Snackbar.LENGTH_LONG);
                snackbar.setAction("Action",new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        //intent.setAction(Intent.ACTION_GET_CONTENT);
                        //intent.setType("vnd.android.cursor.item/phone");
                        //startActivity(intent); // 启动Activity
                        snackbar.dismiss();
                    }
                }).setActionTextColor(Color.parseColor("#99cc33")).show();

            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
