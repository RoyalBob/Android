package com.hyg.observe.utils;

import android.content.Intent;
import android.util.Log;

import com.hyg.check.app.AppApplication;
import com.hyg.observe.config.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by hyg on 2015/4/16.
 */
public class HttpUtil {



    public static final void getUserScore(final AppApplication application){

        AsyncHttpClient client=application.getHttpClient();
        client.post(Config.GETSCORE_URL, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(Config.GETSCORE_SUCCESS);
                intent.putExtra(Config.GETSCORE_SUCCESS, new String(bytes));
                Log.i(getClass().getSimpleName(), new String(bytes));
                application.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(Config.GETSCORE_FAIL);
                throwable.printStackTrace();
                application.sendBroadcast(intent);
            }
        });
    }
}
