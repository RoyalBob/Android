package com.hyg.check.entity;

/**
 * Created by hyg on 2015/3/14.
 * 功能描述：寝室信息实例
 * room信息：
 * 1.寝室号
 * 2.班级
 *
 */


public class RoomInfo{
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String roomNo;
    private String className;


    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomNo() {

        return roomNo;
    }
}
