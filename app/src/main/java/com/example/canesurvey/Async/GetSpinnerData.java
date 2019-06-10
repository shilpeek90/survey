package com.example.canesurvey.Async;

import android.os.AsyncTask;
import android.widget.Adapter;

import com.example.canesurvey.Comman.CommanData;

import java.util.ArrayList;

public class GetSpinnerData extends AsyncTask<Void, Void, Boolean> {

    String _tableName;
    Adapter _adapter;
    boolean _isOnline;
    ArrayList<String> _list;

    public GetSpinnerData(ArrayList<String> list, Adapter adapter, String tablename, boolean isOnline) {
        _list = list;
        _adapter = adapter;
        _tableName = tablename;
        _isOnline = isOnline;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        if (!_isOnline) {

            if (_tableName.equals(CommanData.conn.plantationMethod.getTableName())) {
                _list = CommanData.conn.plantationMethod.getNameList();
            } else if (_tableName.equals(CommanData.conn.irrigation.getTableName())) {
                _list = CommanData.conn.irrigation.getNameList();
            }
            _adapter.notifyAll();
        }
        return false;
    }
}
