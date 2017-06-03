package com.hyg.check.entity;

/**
 * Created by hyg on 2015/3/18.
 * 功能描述：学生信息实例
 */
public class StudentEntity {

    /**
     * college 学院
     * dormitoryId 寝室号
     * bedId 床号
     * name 姓名
     * gBed 床的分数
     * gWall 墙面分数
     * gDesk 桌面分数
     * gGround 地面分数
     * gPublic 公共区域分数
     * gSafe 安全分数
     * Rank 备注
     * total 总分
     * studentId 学号
     */
    private int gBed;
    private int gWall;
    private int gDesk;
    private int gGround;
    private int gPublic;
    private int gElectricitySafe;
    private int gSafetyConcept;
    private String Rank;
    private String college;
    private String dormitoryId;
    private String bedId;
    private String name;
    private int gTotal;

    public int getgElectricitySafe() {
        return gElectricitySafe;
    }

    public void setgElectricitySafe(int gElectricitySafe) {
        this.gElectricitySafe = gElectricitySafe;
    }

    public int getgSafetyConcept() {
        return gSafetyConcept;
    }

    public void setgSafetyConcept(int gSafetyConcept) {
        this.gSafetyConcept = gSafetyConcept;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    private String studentId;

    public int getgTotal() {
        return gTotal;
    }

    public void setgTotal(int gTotal) {
        this.gTotal = gTotal;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDormitoryId() {
        return dormitoryId;
    }

    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getgBed() {
        return gBed;
    }

    public void setgBed(int gBed) {
        this.gBed = gBed;
    }

    public int getgWall() {
        return gWall;
    }

    public void setgWall(int gWall) {
        this.gWall = gWall;
    }

    public int getgDesk() {
        return gDesk;
    }

    public void setgDesk(int gDesk) {
        this.gDesk = gDesk;
    }

    public int getgGround() {
        return gGround;
    }

    public void setgGround(int gGround) {
        this.gGround = gGround;
    }

    public int getgPublic() {
        return gPublic;
    }

    public void setgPublic(int gPublic) {
        this.gPublic = gPublic;
    }



    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

}
