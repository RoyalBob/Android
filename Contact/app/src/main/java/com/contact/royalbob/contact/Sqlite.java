package com.contact.royalbob.contact;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RoyalBob on 2016/7/16.
 */
public class Sqlite {
    public static SQLiteDatabase mDb = null;
    public static void initDatabase(Activity act)
    {
        Log.v("aa","数据库被重置！！！！！！！！！！");
        String databaseFilename =act.getFilesDir()+"/contacts";
        File file = new File(act.getFilesDir(),"contacts");
        //判断文件夹是否存在，不存在就新建一个
        if(!file.exists())
        {
            FileOutputStream os = null;
            try{
                //得到数据库文件的写入流
                os = new FileOutputStream(databaseFilename);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            //得到数据库文件的数据流
            InputStream is = act.getResources().openRawResource(R.raw.contacts);
            byte[] buffer = new byte[8192];
            int count = 0;
            try{

                while((count=is.read(buffer))>0){
                    os.write(buffer, 0, count);
                    os.flush();
                }
                is.close();
                os.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        mDb=SQLiteDatabase.openDatabase(databaseFilename, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
