package com.codeian.sobjanta.Models;

public class EnrolledDataModel {
    public String name;
    public String code;

    public EnrolledDataModel() {

    }

    public EnrolledDataModel(String name, String code) {
        this.name = name;
        this.code = code;

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

}
