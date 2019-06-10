package com.example.canesurvey.model;

public class IrrigatiaonModel {
    private int ID;
    private int Code;
    private String Name;

    public IrrigatiaonModel(int code, String name) {
        Code = code;
        Name = name;
    }

    public IrrigatiaonModel(int ID, int code, String name) {
        this.ID = ID;
        Code = code;
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
