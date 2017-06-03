package com.hyg.check.Utils;

import android.content.Intent;
import android.util.Log;

import com.hyg.check.app.AppApplication;
import com.hyg.check.config.ActionConfig;
import com.hyg.check.config.HttpConfig;
import com.hyg.check.db.DAO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;
import java.util.Map;

/**
 * Created by hyg on 2015/3/6.
 */
public class HttpUtil {
    private static String sessionId=null;
    private static org.apache.http.client.CookieStore mCookieStrore=null;

    /**
     * 登入功能
     * @param loginApplication
     * @param
     */
    public static void doLogin(final AppApplication loginApplication,RequestParams params){
        AsyncHttpClient client=loginApplication.getHttpClient();

        client.post(HttpConfig.LOGINURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(ActionConfig.LOGIN_SUCCESS);
                intent.putExtra(ActionConfig.LOGIN_SUCCESS, new String(bytes));
                loginApplication.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.LOGIN_FAIL);
                throwable.printStackTrace();
                loginApplication.sendBroadcast(intent);
            }
        });



    }

    /**
     * 获取学生信息
     * @param application
     * @param params
     */

    public static void getStudentInfo(final AppApplication application,RequestParams params){
        AsyncHttpClient client=application.getHttpClient();
        client.setTimeout(10000);
        client.post(HttpConfig.GET_STUDENT_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.GETINFO_SUCCESS);
                //intent.putExtra(ActionConfig.GETINFO_SUCCESS, new String(bytes));
                //Log.i(getClass().getSimpleName(), new String(bytes));
                application.sendBroadcast(intent);
                DAO mDao=new DAO(application.getBaseContext());
                List<Map<String,Object>> list= JsonTools.getData(new String(bytes));
                mDao.updateRoomData(list);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.GETINFO_FAIL);
                throwable.printStackTrace();
                application.sendBroadcast(intent);
                throwable.printStackTrace();
            }


        });
    }

    /**
     * 修改床号
     * @param roomActivityApplication
     * @param params
     */

    public static final void changeBedNo(final AppApplication roomActivityApplication,RequestParams params){



        AsyncHttpClient client=roomActivityApplication.getHttpClient();
        client.post(HttpConfig.CHANGE_CODE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(ActionConfig.CHANGE_BED_SUCCESS);
                intent.putExtra(ActionConfig.CHANGE_BED_SUCCESS, new String(bytes));
                roomActivityApplication.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.CHANGE_BED_FAIL);
                throwable.printStackTrace();
                roomActivityApplication.sendBroadcast(intent);
            }
        });
    }

    /**
     * 提交成绩
     * @param checkActivityApplication
     * @param params
     */
    public static final void submitGrade(final AppApplication checkActivityApplication,RequestParams params){

        AsyncHttpClient client=checkActivityApplication.getHttpClient();
        client.post(HttpConfig.SUBMIT_GRADE, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(ActionConfig.SUBMIT_GRADE_SUCCESS);
                intent.putExtra(ActionConfig.SUBMIT_GRADE_SUCCESS, new String(bytes));
                checkActivityApplication.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.SUBMIT_GRADE_FAIL);
                throwable.printStackTrace();
                checkActivityApplication.sendBroadcast(intent);
            }
        });
    }

    /**
     * 获取允许检查的次数
     * @param application
     * @param params
     */
    public static final void getTimePermition(final AppApplication application,RequestParams params){

        AsyncHttpClient client=application.getHttpClient();
        client.post(HttpConfig.Time, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(ActionConfig.Time_SUCCESS);
                intent.putExtra(ActionConfig.Time_SUCCESS, new String(bytes));
                Log.i(getClass().getSimpleName(),new String(bytes));
                application.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.Time_FAIL);
                throwable.printStackTrace();
                application.sendBroadcast(intent);
            }
        });
    }


    /**
     * 获取复查信息
     * @param application
     * @param params
     */
    public static final void getReviewInfo(final AppApplication application,RequestParams params){

        AsyncHttpClient client=application.getHttpClient();
        client.post(HttpConfig.REVIEW, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Intent intent = new Intent();
                intent.setAction(ActionConfig.REVIEW_SUCCESS);
                intent.putExtra(ActionConfig.REVIEW_SUCCESS, new String(bytes));
                Log.i(getClass().getSimpleName(),new String(bytes));
                application.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Intent intent = new Intent();
                intent.setAction(ActionConfig.REVIEW_FAIL);
                throwable.printStackTrace();
                application.sendBroadcast(intent);
            }
        });
    }





}
