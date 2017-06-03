package com.hyg.check.Utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hyg.activity.R;

import java.util.List;

/**
 * 功能描述：
 */
public class AppTools {
	
	private Context context;
    private String [] areaItems;
    private String [] buildItems;
    private String [] bedItems;
    private String [] countItems;
    /**
     * 地点所对应的编号
     * "北区二村","西区宿舍","西区公寓","本部宿舍","甬江公寓","北区一村"
     *
     */
    private String[] areaEItems={"A","B","C","D","E","F"};
    /**
     *楼号所对应的编号
     */
    private String[] buildEItems={"01#","02#","03#","04#","05#","06#","07#","08#","09#","10#","11#","12#"};

    /**
     *
     * 床位号对应的编号
     */
    private String[] bedEItems={"A","B","C","D","E","F"};

    private String[] countEItems={"1","2","3","4","5","6","7","8"};

	public AppTools(Context context){
        this.context=context;
        areaItems=context.getResources().getStringArray(R.array.area_items);
        buildItems=context.getResources().getStringArray(R.array.build_items);
        bedItems=context.getResources().getStringArray(R.array.bed_items);
        countItems=context.getResources().getStringArray(R.array.count_items);
	}
   		
	/** 
     * 检测网络是否连接
     *  
     * @return 
     */ 



    public boolean isNetConnected() {  
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm != null) {  
            NetworkInfo[] infos = cm.getAllNetworkInfo();  
            if (infos != null) {  
                for (NetworkInfo ni : infos) {  
                    if (ni.isConnected()) {  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;  
    }  
    
    /** 
     * 检测wifi是否连接
     *  
     * @return 
     */ 
    public boolean isWifiConnected() {  
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm != null) {  
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();  
            if (networkInfo != null 
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
                return true;  
            }  
        }  
        return false;  
    }  
   
    /** 
     * 检测3G是否连接
     *  
     * @return 
     */ 
    public boolean is3gConnected() {  
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm != null) {  
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();  
            if (networkInfo != null 
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {  
                return true;  
            }  
        }  
        return false;  
    }  
    
    
   
    /** 
     * 检测GPS是否打开
     *  
     * @return 
     */ 
    public boolean isGpsEnabled() {  
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        List<String> accessibleProviders = lm.getProviders(true);  
        for (String name : accessibleProviders) {  
            if ("gps".equals(name)) {  
                return true;  
            }  
        }  
        return false;  
    }


    /**
     * 位置信息转化为对应标号
     * @param area 位置信息
     * @return
     */
    public String areaToFlag(String area){

        for (int i=0;i<areaItems.length;i++){
            if(areaItems[i].equals(area)){
                return areaEItems[i];
            }
        }
        return null;
    }

    /**
     * 位置标号转化为位置信息
     * @param flag 位置标号
     * @return
     */

    public String flagToArea(String flag){
        for (int i=0;i<areaEItems.length;i++){
            if(areaEItems[i].equals(flag)){
                return areaItems[i];
            }
        }
        return null;
    }

    /**
     * 楼房信息转化为楼房标号
     * @param build 楼房信息
     * @return
     */
    public String buildToFlag(String build){
        for (int i=0;i<buildItems.length;i++){
            if(buildItems[i].equals(build)){
                return buildEItems[i];
            }
        }
        return null;
    }

    /**
     * 楼房标号转化为楼房信息
     * @param flag 楼房标号
     * @return
     */
    public String flagToBuild(String flag){
        for (int i=0;i<buildEItems.length;i++){
            if(buildEItems[i].equals(flag)){
                return buildItems[i];
            }
        }
        return null;
    }

    /**
     * 床号信息转化为床号标号
     * @param bed
     * @return
     */
    public String bedToFlag(String bed){
        for (int i=0;i<bedItems.length;i++){
            if(bedItems[i].equals(bed)){
                return bedEItems[i];
            }
        }
        return null;
    }

    /**
     * 床号标号转化为床号信息
     * @param flag
     * @return
     */
    public String flagToBed(String flag){
        for (int i=0;i<bedEItems.length;i++){
            if(bedEItems[i].equals(flag)){
                return bedItems[i].substring(0,1);
            }
        }
        return null;
    }

    public String flagToBed2(String flag){
        for (int i=0;i<bedEItems.length;i++){
            if(bedEItems[i].equals(flag)){
                return bedItems[i];
            }
        }
        return null;
    }

    /**
     * 次数标号转化为次数信息
     * @param flag
     * @return
     */
    public String flagToCount(String flag){
        for (int i=0;i<countEItems.length;i++){
            if(countEItems[i].equals(flag)){
                return countItems[i];
            }
        }
        return null;
    }


    public String countToFlag(String count){
        for (int i=0;i<countItems.length;i++){
            if (countItems[i].equals(count)){
                return countEItems[i];
            }
        }
        return null;
    }


}
