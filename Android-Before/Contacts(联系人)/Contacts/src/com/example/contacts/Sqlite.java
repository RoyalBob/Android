package com.example.contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Sqlite {
	public static SQLiteDatabase mDb = null;
	public static void initDatabase(Activity act)
	{
		Log.v("aa","数据库被重置！！！！！！！！！！");
	    String databaseFilename =act.getFilesDir()+"/contacts";
	    File file = new File(act.getFilesDir(),"contacts");   
	    if(!file.exists())//判断文件夹是否存在，不存在就新建一个   
	    {
		    FileOutputStream os = null;   
		    try{   
		    	os = new FileOutputStream(databaseFilename);//得到数据库文件的写入流   
		    }catch(FileNotFoundException e){   
		    	e.printStackTrace();   
		    }   
		    InputStream is = act.getResources().openRawResource(R.raw.contacts);//得到数据库文件的数据流   
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

