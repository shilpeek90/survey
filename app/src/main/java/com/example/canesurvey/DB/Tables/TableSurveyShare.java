package com.example.canesurvey.DB.Tables;

import android.database.sqlite.SQLiteDatabase;

public class TableSurveyShare extends Table {
    public TableSurveyShare(SQLiteDatabase db)
    {
        super(db);
        setPrimaryKey(ID);
        setTableName("SurveyShare");
    }

    private  String ID="ID";
    private  String SurveyID="SId";
    private  String GrowerID="GrowerID";
    private  String Percent="Percent";

    public String CreateTable()
    {
        String query=new StringBuilder("create table "+getTableName()+"  ("+ID+" INTEGER not null  PRIMARY KEY AUTOINCREMENT ")
                .append(","+SurveyID+" INTEGER not null")
                .append(","+GrowerID+" INTEGER not null")
                .append(","+Percent+" INTEGER not null")

                .append(");").toString();
        return query;
    }
}
