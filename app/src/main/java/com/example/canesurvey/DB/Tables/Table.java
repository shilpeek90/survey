package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by MAP on 1/18/2018.
 */

public class Table {


    String TableName;
    String PrimaryKey;
    final SQLiteDatabase db;

    public Table(SQLiteDatabase db){
        this.db=db;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public String getTableName() {
        return TableName;
    }

    protected void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

    protected void setPrimaryKey(String primaryKey) {
        PrimaryKey = primaryKey;
    }

    public boolean truncateTable() {
        try {
            String query = "delete from " + getTableName();
            db.execSQL(query);
            return true;
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

    }
    protected boolean Add(ContentValues cv)
    {
        try {
            db.insert(getTableName(), null, cv);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public String CreateTable()
    {
        return "";
    }
    public boolean dropTable(SQLiteDatabase db) {
        try {
            String query = "drop table " + getTableName();
            db.execSQL(query);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    public int getTableDataCount() {

        String query = "SELECT count(1) FROM " +getTableName() + " ;";
        try {
            Cursor cursor = db.rawQuery(query, null);
            //cursor.moveToFirst();
            int count = 0;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                count = cursor.getInt(0);
            }
            cursor.close();
            return count;
        }catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return 0;
        }

    }

    public boolean isTableExist(String TableName)
    {
        try{
            Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TableName+"'", null);
            if(cursor!=null) {
                if(cursor.getCount()>0) {
                    cursor.close();
                    return true;
                }
                cursor.close();
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }



}
