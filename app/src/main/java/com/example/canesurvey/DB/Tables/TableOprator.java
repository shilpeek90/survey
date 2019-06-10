package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canesurvey.model.Oprator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableOprator extends Table {
    public TableOprator(SQLiteDatabase db) {
        super(db);
        setPrimaryKey(ID);
        setTableName("Oprator");
    }

    private String ID = "ID";
    private String Imei = "Imei";
    private String Code = "Code";
    private String Name = "Name";

    public String CreateTable() {
        String query = new StringBuilder("create table " + getTableName() + "  (" + ID + " INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append("," + Imei + " Numeric not null")
                .append("," + Code + " Text not null")
                .append("," + Name + " Text not null")

                .append(");").toString();
        return query;
    }

    public String getImei() {
        String query = "SELECT " + Imei + " FROM " + getTableName() + " ;";
        try {
            Cursor cursor = db.rawQuery(query, null);
            //cursor.moveToFirst();
            String imei = "";
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                imei = cursor.getString(0);
            }
            cursor.close();
            return imei;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return "";
        }
        //return null;
    }

    public boolean addOprator(int code, String name,String imei) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Imei, imei);
            cv.put(Code, code);
            cv.put(Name, name);

            if (db.insert(getTableName(), null, cv) > 0) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Oprator getOprator() {
        try
        {
            String query = "SELECT " + Imei + "," +Code+","+Name+
                    " FROM " + getTableName() + " ;";
            Cursor cursor = db.rawQuery(query, null);
            //cursor.moveToFirst();
            String imei = "";

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                Oprator op=new Oprator(Integer.valueOf(cursor.getString(1)),cursor.getString(2),cursor.getString(0));
                cursor.close();
                return op;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
