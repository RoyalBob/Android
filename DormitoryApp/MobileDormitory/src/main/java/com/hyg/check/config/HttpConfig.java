package com.hyg.check.config;

/**
 * Created by hyg on 2015/3/6.
 * 功能描述：访问接口的接口地址
 */
public class HttpConfig {


//    public static final String BASEURL="http://xgxt.kyren.net/";
//    public static final String BASEURL="http://royalbob.6655.la/";
    public static final String BASEURL="http://192.168.191.1/";
    /**
     * 登入接口地址
     */
    public static final String LOGINURL=BASEURL+"android/AndroidLogin.aspx";
    /**
     * 获取学生信息接口地址
     */
    public static final String GET_STUDENT_INFO=BASEURL+"android/DormitoryList.aspx";

    /**
     * 修改寝室床位号
     */
    public static final String CHANGE_CODE=BASEURL+"android/ChangeDormitoryCode.aspx";

    /**
     * 上传成绩
     */
    public static final String SUBMIT_GRADE=BASEURL+"android/DormitoryCheckScore.aspx";

    /**
     * 允许检查的次数
     */

    public static final String Time=BASEURL+"android/GetCheckOpenInfo.aspx";


    public static final String REVIEW=BASEURL+"android/GetReviewList.aspx";

}
