package com.example.weatherxml;

import java.io.InputStream;
import java.util.List;

public interface WeatherParser {  
	    /** 
	     * 解析输入流 得到Book对象集合 
	     * @param is 
	     * @return 
	     * @throws Exception 
	     */  
	    public List<Weather> parse(InputStream is) throws Exception;  
	      
	    /** 
	     * 序列化Book对象集合 得到XML形式的字符串 
	     * @param books 
	     * @return 
	     * @throws Exception 
	     */  
	    public String serialize(List<Weather> books) throws Exception;  
}
