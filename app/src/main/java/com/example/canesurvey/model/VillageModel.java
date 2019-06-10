package com.example.canesurvey.model;

public class VillageModel {

    private int ID;
    private int Code;
    private String Name;

    public VillageModel(int code, String name) {
        Code = code;
        Name = name;
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
