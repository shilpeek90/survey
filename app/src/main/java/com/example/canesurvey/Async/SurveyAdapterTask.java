package com.example.canesurvey.Async;

import android.os.AsyncTask;
import android.widget.Adapter;

import com.example.canesurvey.Repository.ITaskComplete;

public class SurveyAdapterTask extends AsyncTask <Void,Void,Boolean> {

    ITaskComplete _task;
    Adapter _adapter;
    public SurveyAdapterTask(ITaskComplete task, Adapter adapter)
    {
        _task=task;
        _adapter=adapter;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {


        return null;
    }


}
