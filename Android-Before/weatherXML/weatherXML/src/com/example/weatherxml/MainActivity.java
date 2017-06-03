package com.example.weatherxml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

public class MainActivity extends Activity {
	private static final String TAG = "XML";  
	private WeatherParser parser;  
	private List<Weather> weathers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button getweatherBtn = (Button)findViewById(R.id.getweatherBtn);
		final EditText weathertextView= (EditText)findViewById(R.id.weathertextView);
		getweatherBtn.setOnClickListener(new View.OnClickListener() {  
		        @Override  
		        public void onClick(View v) {  
		        	String strXML="";
		            try {  
		                InputStream is = getAssets().open("weather.xml");  
		                 parser =  new DomWeatherParser();  //����DomBookParserʵ��
		               // parser = new PullBookParser();//����PullBookParserʵ��
		                weathers = parser.parse(is);  //����������  
		               
		                for (Weather weather : weathers) {  
		                    Log.i(TAG, weather.toString());  
		                    strXML+=weather.toString()+"\n";
		                } 
		                weathertextView.setText(strXML);
		            } catch (Exception e) {  
		                Log.e(TAG, e.getMessage());  
		            }  
		        }  
		    });  
//		String xml;
//		try {
//			xml = parser.serialize(weathers);
//			File sdCardDir = Environment.getExternalStorageDirectory();//��ȡSDCardĿ¼
//			File saveFile = new File(sdCardDir, "weather.xml");
//			FileOutputStream fos = new FileOutputStream(saveFile);  
//			fos.write(xml.getBytes("UTF-8"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  //���л�  
        
	}
}
