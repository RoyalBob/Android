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
		Log.v("aa","���ݿⱻ���ã�������������������");
	    String databaseFilename =act.getFilesDir()+"/contacts";
	    File file = new File(act.getFilesDir(),"contacts");   
	    if(!file.exists())//�ж��ļ����Ƿ���ڣ������ھ��½�һ��   
	    {
		    FileOutputStream os = null;   
		    try{   
		    	os = new FileOutputStream(databaseFilename);//�õ����ݿ��ļ���д����   
		    }catch(FileNotFoundException e){   
		    	e.printStackTrace();   
		    }   
		    InputStream is = act.getResources().openRawResource(R.raw.contacts);//�õ����ݿ��ļ���������   
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

