package com.example.canesurvey.DB.Tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by MAP on 2/5/2018.
 */

public class TableWishList extends Table {
    public TableWishList(SQLiteDatabase db) {
        super(db);
        setPrimaryKey(ID);
        setTableName("Wishlist");
    }

    private String ID="ID";
    private String TagNo="TagNo";
    private String Customer="Customer";
    private String AddDate="AddDate";
    private String AddBy="AddBy";
    private String AddFrom="AddFrom";


    public String CreateTable() {
        String query=new StringBuilder("create table "+getTableName()+"  ( "+ID+"  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT")
                .append(","+TagNo+" Text not null")
                .append(","+Customer+" Text not null")
                .append(","+AddDate+" NUMERIC NOT NULL")
                .append(","+AddBy+" Text not null")
                .append(","+AddFrom+" INTEGER NOT NULL DEFAULT 0")
                .append(");").toString();
        return query;
    }


    public boolean addWishListfromweb(String tagno, String customer, String addBy) {
        try{
            ContentValues cv=new ContentValues();
            cv.put(TagNo,tagno);
            cv.put(Customer,customer);
            cv.put(AddBy,addBy);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            cv.put(AddDate,formattedDate);
            cv.put(AddFrom,1);

            db.insert(getTableName(),null,cv);

            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean addWishList(String tagno, String customer, String addBy) {
        try{
            ContentValues cv=new ContentValues();
            cv.put(TagNo,tagno);
            cv.put(Customer,customer);
            cv.put(AddBy,addBy);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            cv.put(AddDate,formattedDate);
            cv.put(AddFrom,0);

            db.insert(getTableName(),null,cv);

            return true;
        }catch (Exception ex){
            return false;
        }
    }

   /* public ArrayList<WishlistItem> getWishlistOfCustomer(String currentCustomer) {
        try {
            ArrayList<WishlistItem> itemlist=new ArrayList<WishlistItem>();
            String query = "select t.tagno,t.itemname,i.simage from wishlist w join tabletags t on  w.tagno=t.tagno join tableimages i on t.tagno=i.tagno  where w.customer='" + currentCustomer + "'";
            Cursor cursor = db.rawQuery(query,null);

            int rowcount = cursor.getCount();
            if (rowcount > 0) {
                //Calendar c = Calendar.getInstance();
                //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    WishlistItem item=new WishlistItem();
                    item.setTagNo(cursor.getString(0));
                    item.setItemType(cursor.getString(1));
                    byte[] imagearray= cursor.getBlob(2);
                    item.setTagImage( BitmapFactory.decodeByteArray(imagearray,0,imagearray.length));
                    itemlist.add(item);

                }
                return itemlist;
            }else{
                return itemlist;
            }

        }catch (Exception ex){
         return new ArrayList<WishlistItem>();
        }
    }*/
}
