package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canesurvey.model.VarietyModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by MAP on 2/5/2018.
 */

public class TableVariety extends Table {
    public TableVariety(SQLiteDatabase db) {
        super(db);
        setPrimaryKey(Name);
        setTableName("Variety");
    }

    private String ID = "ID";
    private String Name = "Name";
    private String Code = "Code";
    private String CaneType = "CaneType";


    public String CreateTable() {
        String query = new StringBuilder("create table " + getTableName() + "  (" + ID + " INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append("," + Name + " Text not null")
                .append("," + Code + " INTEGER NOT NULL")
                .append("," + CaneType + " INTEGER not null")
                .append(");").toString();
        return query;
    }

    public boolean Add(VarietyModel rt) {

        ContentValues cv = new ContentValues();
        cv.put(Code, rt.getCode());
        cv.put(Name, rt.getName());
        cv.put(CaneType, rt.getCaneType());
        return Add(cv);
    }

    public String getVarietyName(Integer code) {
        try
        {
            String query = "SELECT " + Name +
                    " FROM " + getTableName() + " where "+Code+"="+code+" ;";
            Cursor cursor = db.rawQuery(query, null);
            //cursor.moveToFirst();
            String name = "";

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                name=cursor.getString(0);
                cursor.close();
                return name;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getCaneTypeCode(int code) {

        try {
            String query = "SELECT " + CaneType +
                    " FROM " + getTableName() + " where " + Code + "=" + code + " ;";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int canetype = cursor.getInt(0);
            return canetype;
        }catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
