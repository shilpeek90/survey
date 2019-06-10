package com.example.canesurvey.model;

public class GrowerModel {
    private int ID;
    private int VCode;
    private int GCode;
    private String GName;
    private String Father;
    private long Uniqcode;
    private long Mobileno;
    private long Aadharno;

    public GrowerModel(int VCode, int GCode, String GName, String father, long uniqcode, long mobileno, long aadharno) {
        this.VCode = VCode;
        this.GCode = GCode;
        this.GName = GName;
        Father = father;
        Uniqcode = uniqcode;
        Mobileno = mobileno;
        Aadharno = aadharno;
    }

    public GrowerModel(int ID, int VCode, int GCode, String GName, String father, long uniqcode, long mobileno, long aadharno) {
        this.ID = ID;
        this.VCode = VCode;
        this.GCode = GCode;
        this.GName = GName;
        Father = father;
        Uniqcode = uniqcode;
        Mobileno = mobileno;
        Aadharno = aadharno;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getVCode() {
        return VCode;
    }

    public void setVCode(int VCode) {
        this.VCode = VCode;
    }

    public int getGCode() {
        return GCode;
    }

    public void setGCode(int GCode) {
        this.GCode = GCode;
    }

    public String getGName() {
        return GName;
    }

    public void setGName(String GName) {
        this.GName = GName;
    }

    public String getFather() {
        return Father;
    }

    public void setFather(String father) {
        Father = father;
    }

    public long getUniqcode() {
        return Uniqcode;
    }

    public void setUniqcode(long uniqcode) {
        Uniqcode = uniqcode;
    }

    public long getMobileno() {
        return Mobileno;
    }

    public void setMobileno(long mobileno) {
        Mobileno = mobileno;
    }

    public long getAadharno() {
        return Aadharno;
    }

    public void setAadharno(long aadharno) {
        Aadharno = aadharno;
    }
}
