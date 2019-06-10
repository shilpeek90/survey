package com.example.canesurvey.Async;

import android.os.AsyncTask;

import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.Comman.Path;
import com.example.canesurvey.Repository.ITaskComplete;
import com.example.canesurvey.model.VarietyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAllvarietyTask extends AsyncTask<Void,Void,Boolean> {

    String errormsg="";
    String msg="";
    ITaskComplete task;
    public GetAllvarietyTask(ITaskComplete Task)
    {
        task=Task;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        String result=new UrlCalling().getResult(Path.getvarieties,UrlCalling.Method.GET);
        if(result.length()>5)
        {
            try {

                JSONArray rootarr=new JSONArray(result);

                CommanData.conn.variety.truncateTable();

                for(int i=0;i<rootarr.length();i++) {
                    JSONObject dataobj = rootarr.getJSONObject(i);
                    VarietyModel rt = new VarietyModel(
                            dataobj.getInt("code"),
                            dataobj.getString("name"),
                            dataobj.getInt("canetype")

                    );
                    CommanData.conn.variety.Add(rt);
                }
                msg="All Variety Imported.";
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                errormsg=e.getMessage();
                return false;
            }
        }

        return false;
    }

    protected  void onPostExecute(Boolean success)
    {
        if(success)
        {
         task.OnTaskComplete(success,msg);
        }
        else
        {
            task.OnTaskComplete(success,errormsg);
        }
    }
}
