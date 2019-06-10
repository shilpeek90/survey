package com.example.canesurvey.Async;

import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.canesurvey.Comman.CommanData;

import java.util.ArrayList;

public class GetSpinnerData extends AsyncTask<Void, Void, Boolean> {

    Object obj;
    String _tableName;
    ArrayAdapter _adapter;
    boolean _isOnline;
    ArrayList<String> _list;

    public GetSpinnerData(ArrayList<String> list, ArrayAdapter adapter, String tablename, boolean isOnline) {
        _list = list;
        _adapter = adapter;
        _tableName = tablename;
        _isOnline = isOnline;
        obj = new Object();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        if (!_isOnline) {

            if (_tableName.equals(CommanData.conn.plantationMethod.getTableName())) {
                _list = CommanData.conn.plantationMethod.getNameList();
                return true;
            } else if (_tableName.equals(CommanData.conn.irrigation.getTableName())) {
                _list = CommanData.conn.irrigation.getNameList();
                return true;
            }

        }
        return false;
    }

    protected void onPostExecute(Boolean success) {
        if (success) {
            _adapter.notifyDataSetChanged();
        } else {

        }
    }
}
