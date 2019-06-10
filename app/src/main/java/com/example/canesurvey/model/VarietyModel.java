package com.example.canesurvey.model;

public class VarietyModel {
    private int ID;
    private String Name;
    private int Code;
    private int CaneType;

    public VarietyModel(int ID, String name, int code, int caneType) {
        this.ID = ID;
        Name = name;
        Code = code;
        CaneType = caneType;
    }

    public VarietyModel( int code,String name, int caneType) {
        Name = name;
        Code = code;
        CaneType = caneType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public int getCaneType() {
        return CaneType;
    }

    public void setCaneType(int caneType) {
        CaneType = caneType;
    }
}
