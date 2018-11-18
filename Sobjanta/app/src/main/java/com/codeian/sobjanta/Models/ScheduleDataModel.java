package com.codeian.sobjanta.Models;

public class ScheduleDataModel {
    public String name;
    public String code;
    public String time;
    public String teacher;
    public String room;
    public boolean cancelled;

    public ScheduleDataModel() {

    }

    public ScheduleDataModel(String name, String code, String time, String teacher, String room, boolean cancelled) {
        this.name = name;
        this.code = code;
        this.time = time;
        this.teacher = teacher;
        this.room = room;
        this.cancelled = cancelled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
