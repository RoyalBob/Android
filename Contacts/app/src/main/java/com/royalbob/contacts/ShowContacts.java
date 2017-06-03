package com.royalbob.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by RoyalBob on 2016/7/19.
 */
public class ShowContacts extends AppCompatActivity{

    private static String path="/sdcard/myHead/";//sd路径
    private String headpath = null, phoneNumber = null;
    private ImageView imageView_show;
    private String mName =null;
    private Button btn_mcall, btn_mmsg;
    private TextView tv_Tel, tv_Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);
        initView();
        Sqlite.initDatabase(this);

        Cursor cursor = Sqlite.mDb.rawQuery("select * from contacts where 姓名='"+ mName + "'", null);
        while(cursor.moveToNext())
        {
            tv_Tel.setText(cursor.getString(1));
            tv_Address.setText(cursor.getString(2));
            phoneNumber = cursor.getString(1);
            headpath = cursor.getString(3);
        }

        Bitmap bt = BitmapFactory.decodeFile(path + headpath);//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            imageView_show.setImageDrawable(drawable);
        }

        btn_mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        btn_mmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri smsToUri = Uri.parse("smsto://" + phoneNumber);
                Intent mIntent = new Intent( android.content.Intent.ACTION_SENDTO, smsToUri );
                startActivity( mIntent );
            }
        });
    }

    private void initView() {
        imageView_show = (ImageView)findViewById(R.id.imageView_show);
        btn_mcall = (Button)findViewById(R.id.btn_mcall);
        btn_mmsg = (Button)findViewById(R.id.btn_mmsg);
        tv_Tel = (TextView)findViewById(R.id.tvTel);
        tv_Address = (TextView)findViewById(R.id.tvAddress);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mName = bundle.getString("Name");

        getSupportActionBar().setTitle(mName);
        getSupportActionBar().setSubtitle("通讯录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(ShowContacts.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_edit) {
            finish();
            Intent intent = new Intent(ShowContacts.this, EditContacts.class);
            intent.putExtra("Name", mName);
            intent.putExtra("HeadPath", headpath);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_del) {
            Sqlite.mDb.delete("contacts","姓名='" + mName + "'", null);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
