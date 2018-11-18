package com.codeian.sobjanta.Models;

public class courseInfo {
    private String code;
    private String title;
    private String teacher;
    private String lastDay;
    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String room;

    public void setCode(String code)
    {
        this.code=code;
    }

    public void setTitle(String title)
    {
        this.title=title;
    }

    public void setTeacher(String teacher)
    {
        this.teacher=teacher;
    }

    public void setLastDay(String lastDay)
    {
        this.lastDay=lastDay;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCode() {
        return code;
    }

    public String getLastDay() {
        return lastDay;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTitle() {
        return title;
    }

    public String getFri() {
        return fri;
    }

    public String getMon() {
        return mon;
    }

    public String getSun() {
        return sun;
    }

    public String getSat() {
        return sat;
    }

    public String getThu() {
        return thu;
    }

    public String getTue() {
        return tue;
    }

    public String getWed() {
        return wed;
    }

    public String getRoom() {
        return room;
    }
    public String getSchedule(String day)
    {
        if(day.contains("sun"))
        {
            return this.getSun();
        }
        else if(day.contains("mon"))
        {
            return this.getMon();
        }
        else if(day.contains("tue"))
        {
            return this.getTue();
        }
        else if(day.contains("wed"))
        {
            return this.getWed();
        }
        else if(day.contains("thu"))
        {
            return this.getThu();
        }
        else if(day.contains("fri"))
        {
            return this.getFri();
        }
        else return this.getSat();
    }
}
