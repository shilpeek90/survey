package com.example.canesurvey.model;

public class Oprator {
    private int Code;
    private String Name;
    private String Imei;

    public Oprator(int id,String name,String imei)
    {
        Code=id;
        Name=name;
        Imei=imei;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int ID) {
        this.Code = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }
}
