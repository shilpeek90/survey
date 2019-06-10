package com.example.canesurvey.DB.Tables;

import android.database.sqlite.SQLiteDatabase;

public class TablePlotLocation extends Table {
    public TablePlotLocation(SQLiteDatabase db)
    {
        super(db);
        setPrimaryKey(ID);
        setTableName("PlotLocation");
    }

    private  String ID="ID";
    private  String SurveyID="SID";
    private  String Corner="Corner";
    private  String Lat="Lat";
    private  String Lang="Lang";
    private  String Meter="Meter";

    public String CreateTable()
    {
        String query=new StringBuilder("create table "+getTableName()+"  ("+ID+" INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append(","+SurveyID+" INTEGER not null")
                .append(","+Corner+" INTEGER not null")
                .append(","+Lat+" numeric not null")
                .append(","+Lang+" numeric not null")
                .append(","+Meter+" numeric not null")

                .append(");").toString();
        return query;
    }
}
