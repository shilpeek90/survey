package com.example.canesurvey.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.canesurvey.DB.Tables.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * Created by MAP on 1/14/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {


    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME ="designs";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private ArrayList<Table> tableList=new ArrayList<>();

    public TableVariety variety;
    public TableGrower grower;
    public TableVillage village;
    public TableSurvey survey;
    public TablePlantationMethod plantationMethod;
    public TableSurveyShare surveyShare;
    public TablePlotLocation plotlocation;
    public TableIrrigation irrigation;
    public TableOprator oprator;

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 12);// 1? Its database Version
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mDataBase=getWritableDatabase();
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        //Log.i("dbpath",DB_PATH);
        TableInit();

        this.mContext = context;
    }

    public void TableInit()
    {
        if(variety==null) {
            variety = new TableVariety(mDataBase);
            grower=new TableGrower(mDataBase);
            village=new TableVillage(mDataBase);
            survey=new TableSurvey(mDataBase);
            plantationMethod=new TablePlantationMethod(mDataBase);
            surveyShare=new TableSurveyShare(mDataBase);
            plotlocation=new TablePlotLocation(mDataBase);
            irrigation=new TableIrrigation(mDataBase);
            oprator=new TableOprator(mDataBase);

            tableList.add(variety);
            tableList.add(grower);
            tableList.add(village);
            tableList.add(survey);
            tableList.add(plantationMethod);
            tableList.add(surveyShare);
            tableList.add(plotlocation);
            tableList.add(irrigation);
            tableList.add(oprator);

        }
    }


    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TableInit();
        for (Table tbl:tableList) {
            db.execSQL(tbl.CreateTable());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        TableInit();
        for (Table tbl:tableList) {
            tbl.dropTable(db);
        }

        onCreate(db);
    }


    public Cursor getCursor(String query) {
        Cursor cursor= mDataBase.rawQuery(query,null);
        return cursor;
    }
}
