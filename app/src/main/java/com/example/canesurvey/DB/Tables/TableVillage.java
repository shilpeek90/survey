package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.canesurvey.model.Oprator;
import com.example.canesurvey.model.VarietyModel;
import com.example.canesurvey.model.VillageModel;

public class TableVillage extends Table {
    public TableVillage(SQLiteDatabase db)
    {
        super(db);
        setPrimaryKey(ID);
        setTableName("Village");
    }

    private String ID="ID";
    private String VillageCode="VillageCode";
    private String VillageName="VillageName";

    public String CreateTable() {
        String query=new StringBuilder("create table "+getTableName()+"  ("+ID+" INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append(","+VillageCode+" INTEGER not null")
                .append(","+VillageName+" NUMERIC NOT NULL")
                .append(");").toString();
        return query;
    }


    public boolean Add(VillageModel rt) {

        ContentValues cv = new ContentValues();
        cv.put(VillageCode, rt.getCode());
        cv.put(VillageName, rt.getName());

        return Add(cv);
    }

    public String getVillageName(int villagecode) {
        try
        {
            String query = "SELECT " + VillageName +
                    " FROM " + getTableName() + " where "+VillageCode+"="+villagecode+" ;";
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
}
