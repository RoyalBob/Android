package com.example.weatherxml;

import java.io.InputStream;
import java.util.List;

public interface WeatherParser {  
	    /** 
	     * ���������� �õ�Book���󼯺� 
	     * @param is 
	     * @return 
	     * @throws Exception 
	     */  
	    public List<Weather> parse(InputStream is) throws Exception;  
	      
	    /** 
	     * ���л�Book���󼯺� �õ�XML��ʽ���ַ��� 
	     * @param books 
	     * @return 
	     * @throws Exception 
	     */  
	    public String serialize(List<Weather> books) throws Exception;  
}
