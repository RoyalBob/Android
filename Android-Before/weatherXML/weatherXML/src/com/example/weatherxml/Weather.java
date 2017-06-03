package com.example.weatherxml;

public class Weather {   
    private String date, weather, wind, temperature ;  
      
    public String getDate() {  
        return date;  
    }  
    public void setDate(String date) {  
        this.date = date;  
    }
 
    public String getWind() {  
        return wind;  
    }  
    public void setWeather(String weather) {  
        this.weather = weather;  
    }
  
    public String getWeather() {  
        return weather;  
    }
    public void setWind(String wind) {  
        this.wind = wind;  
    }

    public String getTemperature() {  
        return temperature;  
    }
    public void setTemperature(String temperature) {  
        this.temperature = temperature;  
    }

    @Override  
    public String toString() {  
        return "日期:" + date + ", 天气:" + weather + ", 风力:" + wind + ", 温度:" + temperature;  
    }  
}  