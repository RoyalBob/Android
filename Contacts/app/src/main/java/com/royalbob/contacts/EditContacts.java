package com.royalbob.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by RoyalBob on 2016/7/19.
 */
public class EditContacts extends AppCompatActivity{

    private Bitmap head = null;
    private static String path="/sdcard/myHead/";//sd路径
    private EditText edtName, edtTel, edtAddress;
    private String mName =null;
    private String headpath = null;
    private ImageView imageView;
    private Button btn_mok;
    private ContentValues content = new ContentValues();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_editcontacts);
        initView();

        edtName.requestFocus();

        getSupportActionBar().setTitle("通讯录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            Bundle bundle = intent.getExtras();
            mName = bundle.getString("Name");
            headpath = bundle.getString("HeadPath");
            edtName.setText(mName);
        }

        Sqlite.initDatabase(this);
        Cursor cursor = Sqlite.mDb.rawQuery("select * from contacts where 姓名='"+ mName + "'", null);
        while(cursor.moveToNext())
        {
            edtTel.setText(cursor.getString(1));
            edtAddress.setText(cursor.getString(2));
        }

        if(mName!=null) {
            btn_mok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("a","mok被点击！");
                    content.put("姓名", edtName.getText().toString());
                    content.put("电话", edtTel.getText().toString());
                    content.put("地址", edtAddress.getText().toString());
                    Sqlite.mDb.update("contacts", content, "姓名='"+mName+"'", null);
                    onBackPressed();
                }
            });
        } else {
            btn_mok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("".equals(edtName.getText().toString().trim()) || "".equals(edtTel.getText().toString().trim())) {
                        Snackbar.make(getWindow().getDecorView(),"姓名或电话不能为空！",Snackbar.LENGTH_LONG).show();
                    }else{
                        content.put("姓名", edtName.getText().toString());
                        content.put("电话", edtTel.getText().toString());
                        content.put("地址", edtAddress.getText().toString());
                        Sqlite.mDb.insert("contacts", null, content);
                        onBackPressed();
                    }
                }

            });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initView() {
        btn_mok = (Button)findViewById(R.id.btn_mok);
        edtName = (EditText)findViewById(R.id.edtName);
        edtTel = (EditText)findViewById(R.id.edtTel);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        imageView = (ImageView)findViewById(R.id.imageView1);
        Bitmap bt = BitmapFactory.decodeFile(path + headpath);//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            imageView.setImageDrawable(drawable);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        setPicToView(head);//保存在SD卡中
                        imageView.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + mName+".jpg";//图片名字
        content.put("头像", mName+".jpg");
        Sqlite.mDb.insert("contacts", null, content);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
