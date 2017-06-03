package com.hyg.check.app;

import android.app.Application;

import com.hyg.check.Utils.SharedPreferenceUtil;
import com.hyg.check.config.DataConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyg on 2015/3/6.
 * 功能描述：
 * AsyncHttpClient网络访问实例的获取；
 * SharedPreferenceUtil实例的获取
 * cookies信息的设置
 * sharedData应用共享数据实例的获取
 *
 */
public class AppApplication extends Application{
    /*------------  Http client --------------*/
    private AsyncHttpClient client;
    public static final int TIME_OUT = 10000;
    public static final int MAX_CONNECTIONS = 5;
    private SharedPreferenceUtil spUtil;
    private PersistentCookieStore cookieStore;
    public final AsyncHttpClient getHttpClient()
    {
        return client;
    }
    public PersistentCookieStore getCookieStore(){return cookieStore;};

    /**
     * 应用共享数据
     */
    private Map<String, Object> sharedData;


    /**
     * 获取共享数据
     *
     * 数据处理完成后记得及时删除
     *
     * @return
     */
    public final Map<String, Object> getSharedData()
    {
        return sharedData;
    }

    @Override
    public void onCreate()
    {

        super.onCreate();

        client = new AsyncHttpClient();

        cookieStore = new PersistentCookieStore(this);
        client.setCookieStore(cookieStore);
        client.setTimeout(TIME_OUT);
        client.setMaxConnections(MAX_CONNECTIONS);

        sharedData = new HashMap();
        spUtil=new SharedPreferenceUtil(this, DataConfig.DORMITORY_INFO);
    }

    public SharedPreferenceUtil getSpUtil(){
        return spUtil;
    }
}
