package com.codeian.sobjanta.Models;

import java.util.ArrayList;

/**
 * Created by shuha on 12/08/2018.
 */

public class userInfo {
    private String name;
    private String reg;
    private String email;
    private String role;
    private String courses;
    public ArrayList<String> courseList;
    private String uid;

    public userInfo(){
        courseList = new ArrayList<String>();
    }
    public userInfo(String name, String reg, String email, String role,String courses){
        this.name = name;
        this.reg = reg;
        this.email = email;
        this.role = role;
        this.courses = courses;
        courseList = new ArrayList<String>();
    }

    public String getName(){
        return this.name;
    }
    public String getReg(){
        return this.reg;
    }
    public String getEmail(){
        return this.email;
    }
    public String getRole(){
        return this.role;
    }
    public String getCourses() {
        return courses;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String s){
        this.name = s;
    }
    public void setEmail(String s){
        this.email = s;
    }
    public void setReg(String s){
        this.reg = s;
    }
    public void setRole(String s){
        this.role = s;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

}
