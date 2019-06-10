package com.example.canesurvey.Comman;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.canesurvey.DB.DataBaseHelper;
import com.example.canesurvey.R;
import com.example.canesurvey.model.Oprator;

import androidx.fragment.app.Fragment;

public class CommanData {
    public static String loginid="";
    public static String Imeino="";
    public static boolean IsAdmin=false;
    public static DataBaseHelper conn;
    public static Oprator oprator;
    private static AlertDialog dialog;
    public static void ShowWaitDialog(Context context, Fragment fragment)
    {
        View pview=fragment.getLayoutInflater().inflate(R.layout.progresslayout,null);
        dialog=new AlertDialog.Builder(context).setView(pview).setTitle("Wait Till Progress Complete").setCancelable(false).show();
    }

    public static void CloseWaitDialog()
    {
        dialog.dismiss();
    }
}
