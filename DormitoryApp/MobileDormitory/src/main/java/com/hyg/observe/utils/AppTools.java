package com.hyg.observe.utils;

import android.content.Context;

import com.hyg.activity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by hyg on 2015/4/30.
 */
public class AppTools {
    private Context context;
    private String[] countEItems={"1","2","3","4","5","6","7","8"};
    private String[] countItems;

    public AppTools(Context context){
        this.context=context;
        countItems=context.getResources().getStringArray(R.array.count_items);
    }


    /**
     * 次数标号转化为次数信息
     * @param flag
     * @return
     */
    public List<String> flagToCount(List<Map<String,Object>> flag){
        List<String> list=new ArrayList<String>();
        for (int i=0;i<flag.size();i++){
            for (int j=0;j<countEItems.length;j++){
                if(flag.get(i).get("frequency").toString().equals(countEItems[j])){
                    list.add(countItems[j]);
                }

            }
        }
        return list;
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
