package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canesurvey.model.GrowerModel;
import com.example.canesurvey.model.VillageModel;

public class TableGrower extends Table {
    public TableGrower(SQLiteDatabase db) {
        super(db);
        setPrimaryKey(ID);
        setTableName("Grower");
    }

    private String ID = "ID";
    private String GrowerCode = "GrowerCode";
    private String VillageCode = "VillageCode";
    private String GrowerName = "GrowerName";
    private String FatherName = "FatherName";
    private String Unicode = "Unicode";
    private String MobileNo = "MobileNo";
    private String Aadharno = "Aadharno";

    public String CreateTable() {
        String query = new StringBuilder("create table " + getTableName() + "  (" + ID + " INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append("," + VillageCode + " INTEGER not null")
                .append("," + GrowerCode + " INTEGER NOT NULL")
                .append("," + GrowerName + " Text NOT NULL")
                .append("," + FatherName + " Text NOT NULL")
                .append("," + Unicode + " NUMERIC NOT NULL")
                .append("," + MobileNo + " NUMERIC NOT NULL")
                .append("," + Aadharno + " NUMERIC NOT NULL")
                .append(");").toString();
        return query;
    }


    public boolean Add(GrowerModel rt) {

        ContentValues cv = new ContentValues();
        cv.put(VillageCode, rt.getVCode());
        cv.put(GrowerCode, rt.getGCode());
        cv.put(GrowerName, rt.getGName());
        cv.put(FatherName, rt.getFather());
        cv.put(Unicode, rt.getUniqcode());
        cv.put(MobileNo, rt.getMobileno());
        cv.put(Aadharno, rt.getAadharno());

        return Add(cv);
    }


    public String getGrowerName(int villcode,int growercode) {
        try {
            String query = "SELECT " + GrowerName +
                    " FROM " + getTableName() + " where " + GrowerCode + "=" + growercode + " and "+VillageCode+"="+villcode+" ;";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            String name = "";
            name = cursor.getString(0);
            cursor.close();
            return name;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getFatherName(int villcode,int growercode) {
        try {
            String query = "SELECT " + FatherName +
                    " FROM " + getTableName() + " where " + GrowerCode + "=" + growercode + " and "+VillageCode+"="+villcode+" ;";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            String name = "";
            name = cursor.getString(0);
            cursor.close();
            return name;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
