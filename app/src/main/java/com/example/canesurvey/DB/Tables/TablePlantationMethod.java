package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canesurvey.model.PlantationModel;
import com.example.canesurvey.model.VillageModel;

import java.util.ArrayList;

public class TablePlantationMethod extends Table {
    public TablePlantationMethod(SQLiteDatabase db) {
        super(db);
        setPrimaryKey(ID);
        setTableName("PlantationMethod");
    }

    private String ID = "ID";
    private String Code = "Code";
    private String Method = "Method";

    public String CreateTable() {
        String query = new StringBuilder("create table " + getTableName() + "  (" + ID + " INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append("," + Code + " integer not null")
                .append("," + Method + " Text not null")

                .append(");").toString();
        return query;
    }


    public boolean Add(PlantationModel rt) {

        ContentValues cv = new ContentValues();
        cv.put(Code, rt.getCode());
        cv.put(Method, rt.getName());

        return Add(cv);
    }

    public ArrayList<String> getNameList() {

        try {
            String query = "SELECT " + Method +
                    " FROM " + getTableName() + " ;";
            Cursor cursor = db.rawQuery(query, null);
            //cursor.moveToFirst();
            ArrayList<String> namelist = new ArrayList<>();

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                namelist.add(cursor.getString(0));

            }
            cursor.close();
            return namelist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
