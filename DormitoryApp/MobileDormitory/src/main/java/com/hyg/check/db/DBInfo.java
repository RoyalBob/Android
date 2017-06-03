package com.hyg.check.db;


/**
 * 功能描述：数据库的版本号，数据库名称及数据表的表结构
 */
public class DBInfo {
	public DBInfo(){
		
	}

    /**
     * 数据库版本号
     */
	public static final int VERSION=1;

    /**
     * 数据库名称
     */
	public static final String DB_NAME="dormitory.db";

    /**
     * 表结构
     */
	public static class Table{
        /**
         * 成绩表结构
         */
        public static final String T_GRADE="grade";
        public static final String GRADE_YEAR="year";//学年
        public static final String GRADE_SESSION="session";//学期
        public static final String GRADE_COUNT="frequency";//检查次数
        public static final String GRADE_SID="s_id";//学号
        public static final String GRADE_BED="grade_bed";//床面分数
        public static final String GRADE_WALL="grade_wall";//墙面分数
        public static final String GRADE_DESK="grade_desk";//桌面分数
        public static final String GRADE_GROUND="grade_ground";//地面分数
        public static final String GRADE_SAFETYCONCEPT ="grade_security";//安全意识
        public static final String GRADE_ELECTRICITY="grade_electricity";//用电安全
        public static final String GRADE_AREA="grade_public";//公共区域
        public static final String GRADE_SUM="total";//复查成绩
        public static final String GRADE_RANK="rank";//备注
        public static final String GRADE_Eno="check_id";//检查人学号

        /**
         * 学生信息表结构
         */
        public static final String T_STUDENT="student";
        public static final String STUDENT_COLLEGE="s_college";
        public static final String STUDENT_CLASSES="s_class";
        public static final String STUDENT_DORMITORYNO="dormitory_id";
        public static final String STUDENT_BEDNO="bed_id";
        public static final String STUDENT_SID="s_id";
        public static final String STUDENT_SNAME="s_name";
        public static final String STUDENT_SEX="s_sex";



	}
	
	

}
