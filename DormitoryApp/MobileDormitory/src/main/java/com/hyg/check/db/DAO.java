package com.hyg.check.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hyg.check.entity.ReviewEntity;
import com.hyg.check.entity.RoomInfo;
import com.hyg.check.entity.StudentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 功能描述：数据库操作
 */
public class DAO {
    private Context context;
    private DatabaseHelper helper;
    private Cursor cursor;

    //正则表达式匹配是否是字母
    private  String reg = "[a-zA-Z]";



    public DAO(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context, DBInfo.DB_NAME);

    }



    // 查询寝室
    public List<RoomInfo> selectFloorData(String area,String building, int floor,String sex) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(area).append(building).append(floor);
        List<RoomInfo> list=new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select s_class,dormitory_id from student group by dormitory_id having dormitory_id like ? and s_sex=?",
                new String[] { buffer.toString() + '%' ,sex});
        while (cursor.moveToNext()) {
            RoomInfo ri=new RoomInfo();
            ri.setClassName(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_CLASSES)));
            String dormitoryId=cursor.getString(
                    cursor.getColumnIndex(DBInfo.Table.STUDENT_DORMITORYNO));
            ri.setRoomNo(dormitoryId
                        .substring(4,dormitoryId.length()));
            list.add(ri);
        }
        cursor.close();
        db.close();
        return list;
    }

    // 查询房间具体同学信息
    public List<StudentEntity> selectRoomData(String dormitory) {

        List<StudentEntity> list = new ArrayList<StudentEntity>();
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db
                .rawQuery(
                        "select s_id,s_name,bed_id,s_college from student where dormitory_id=?;",
                        new String[] { dormitory });
       /* cursor = db
                .rawQuery(
                        "select s_college,s_id,s_name,bed_id,total from student where dormitory_id=?",
                        new String[] { dormitory });*/
        while (cursor.moveToNext()) {
            StudentEntity entity=new StudentEntity();
            entity.setStudentId(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_SID)));
            entity.setName(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_SNAME)));
            entity.setBedId(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_BEDNO)));
            entity.setCollege(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_COLLEGE)));
            list.add(entity);
        }
        cursor.close();
        db.close();

        return list;

        }


    //设置总成绩
    public void setScore(List<StudentEntity> mEntity,String year,String session,String frequency){
        SQLiteDatabase db=helper.getWritableDatabase();
        for(int i=0;i<mEntity.size();i++){
            cursor=db.rawQuery("select total from grade where s_id=? and year=? and session=? and frequency=?;",new String[]{mEntity.get(i).getStudentId(),year,session,frequency});
            while (cursor.moveToNext()){
                mEntity.get(i).setgTotal((cursor.getInt(cursor.getColumnIndex("total"))));
            }
        }
    }


    // 更新数据
    public  void updateRoomData(List<Map<String, Object>> result) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from student");
        db.execSQL("delete from grade");
        db.beginTransaction();
        for (int i = 0; i < result.size(); i++) {

            ContentValues studentValues=new ContentValues();
            studentValues.put(DBInfo.Table.STUDENT_CLASSES,result.get(i).get("class").toString());
            String dormitoryId=result.get(i).get("dormitory_id").toString();
            if(dormitoryId.substring(dormitoryId.length()-1).matches(reg)){
                studentValues.put(DBInfo.Table.STUDENT_DORMITORYNO,dormitoryId.substring(0,dormitoryId.length()-1));
                studentValues.put(DBInfo.Table.STUDENT_BEDNO,dormitoryId.substring(dormitoryId.length()-1));
            }else{
                studentValues.put(DBInfo.Table.STUDENT_DORMITORYNO,dormitoryId);
            }
            studentValues.put(DBInfo.Table.STUDENT_SID,result.get(i).get("s_id").toString());
            studentValues.put(DBInfo.Table.STUDENT_SNAME,result.get(i).get("s_name").toString());
            studentValues.put(DBInfo.Table.STUDENT_SEX,result.get(i).get("sex").toString());
            db.insert(DBInfo.Table.T_STUDENT,DBInfo.Table.STUDENT_SNAME,studentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }



    // 检查床位号是否重合
    public  boolean inspectBedNumber(String dormitory) {

        DatabaseHelper helper = new DatabaseHelper(context, DBInfo.DB_NAME);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db
                .rawQuery(
                        "select count(bed_id),bed_id from student where dormitory_id=? group by bed_id",
                        new String[] { dormitory });
        while (cursor.moveToNext()) {
            if ((!cursor.getString(cursor.getColumnIndex("count(bed_id)"))
                    .equals("1"))||(cursor.getString(cursor.getColumnIndex("bed_id")).equals("")))
                return false;
        }
        return true;
    }

    //判断是否存在成绩
    public boolean isExist(String Sno,String year,String session,String frequency){
        SQLiteDatabase db = helper.getWritableDatabase();
        cursor=db.rawQuery("select * from grade where s_id=? and year=? and session=? and frequency=?;",new String[]{Sno,year,session,frequency});
        if(0==cursor.getCount()){
            db.close();
            return false;
        }else{
            db.close();
            return true;
        }

    }

    // 保存成绩
    public  void insertGrade(String year,String term,String times,int bed, int wall, int desk,int gpublic,
                             int ground, int security,int electricity,
                             String Sno, int total) {
        SQLiteDatabase db = null;
        if(isExist(Sno,year,term,times)){
            db = helper.getWritableDatabase();

            db.execSQL(
                    "update grade set grade_bed=?,grade_wall=?,grade_desk=?,grade_public=?,grade_ground=?,grade_security=?,grade_electricity=?,total=? where year=? and session=? and frequency=? and s_id=?",
                    new String[]{String.valueOf(bed), String.valueOf(wall), String.valueOf(desk), String.valueOf(gpublic),
                            String.valueOf(ground), String.valueOf(security), String.valueOf(electricity),String.valueOf(total),
                            year,term, times,Sno});
        }else{
            db = helper.getWritableDatabase();
            db.execSQL(
                    "insert into grade values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Integer[]{Integer.parseInt(year), Integer.parseInt(term), Integer.parseInt(times),Integer.parseInt(Sno),bed, wall, desk, ground, security, electricity, gpublic,
                            total});
        }
        db.close();
    }

    //更新床位号
    public void updateBedNo(String student,String bed){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        db.execSQL("update student set bed_id=? where s_id=?",new String[]{String.valueOf(bed),student});
        db.close();
    }


    /**
     * 获取学生的成绩
     */

    public StudentEntity getUserScore(String studentId,String year,String session,String frequency){

        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db
                .rawQuery(
                        "select * from grade where s_id=? and year=? and session=? and frequency=?;",
                        new String[] {studentId,year,session,frequency});
        StudentEntity mEntity=null;
        while(cursor.moveToNext()) {
            mEntity = new StudentEntity();
            mEntity.setStudentId(cursor.getString(cursor.getColumnIndex(DBInfo.Table.STUDENT_SID)));

            mEntity.setgPublic(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_AREA)));
            mEntity.setgElectricitySafe(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_ELECTRICITY)));
            mEntity.setgSafetyConcept(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_SAFETYCONCEPT)));
            mEntity.setgBed(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_BED)));
            mEntity.setgWall(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_WALL)));
            mEntity.setgDesk(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_DESK)));
            mEntity.setgGround(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_GROUND)));
            mEntity.setgTotal(cursor.getInt(cursor.getColumnIndex(DBInfo.Table.GRADE_SUM)));
            return mEntity;
        }
        cursor.close();
        db.close();

        return null;
    }

    //保存复查名单
    public void setReview(List<Map<String,Object>> reviewList){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.beginTransaction();
        for (int i = 0; i < reviewList.size(); i++) {

            ContentValues studentValues=new ContentValues();
            //studentValues.put("s_college",reviewList.get(i).get("college").toString());
            String dormitoryId=reviewList.get(i).get("dormitory_id").toString();
            if(dormitoryId.substring(dormitoryId.length()-1).matches(reg)){
                studentValues.put("dormitory_id",dormitoryId.substring(0,dormitoryId.length()-1));
                studentValues.put("bed_id",dormitoryId.substring(dormitoryId.length()-1));
            }else{
                studentValues.put("dormitory_id",dormitoryId.substring(0,dormitoryId.length()));
            }
            studentValues.put("year",reviewList.get(i).get("year").toString());
            studentValues.put("session",reviewList.get(i).get("session").toString());
            studentValues.put("frequency",reviewList.get(i).get("frequency").toString());
            studentValues.put("s_id",reviewList.get(i).get("s_id").toString());
            studentValues.put("s_name",reviewList.get(i).get("name").toString());
            studentValues.put("s_sex",reviewList.get(i).get("sex").toString());
            db.insert("review","s_id",studentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }



    /**
     * 获取复查名单
     */

    public List<ReviewEntity> getReview(){

        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from review;", null);
        List<ReviewEntity> list=new ArrayList<ReviewEntity>();
        ReviewEntity mEntity=null;
        while(cursor.moveToNext()) {
            mEntity = new ReviewEntity();
            mEntity.setDormitoryId(cursor.getString(cursor.getColumnIndex("dormitory_id")));
            mEntity.setStudentId(cursor.getString(cursor.getColumnIndex("s_id")));
            mEntity.setStudentName(cursor.getString(cursor.getColumnIndex("s_name")));
            mEntity.setBedId(cursor.getString(cursor.getColumnIndex("bed_id")));
            list.add(mEntity);

        }
        cursor.close();
        db.close();
        return list;
    }

    //删除复查名单
    public void deleteReview(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("delete from review");
    }

}
