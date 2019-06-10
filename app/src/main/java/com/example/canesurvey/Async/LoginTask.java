package com.example.canesurvey.Async;

import android.os.AsyncTask;
import android.util.Log;
import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.Comman.Path;
import com.example.canesurvey.Repository.ITaskComplete;
import com.example.canesurvey.model.Oprator;
import org.json.JSONObject;
import java.util.HashMap;

public class LoginTask
        extends AsyncTask<Void, Void, String> {
    String _opid, _pass, _imeino;
    ITaskComplete taskobj = null;

    public LoginTask(String email, String password, String Imei, ITaskComplete callerobj) {
        _opid = email;
        _pass = password;
        _imeino = Imei;
        taskobj = callerobj;
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            //Thread.sleep(2000);
            if (_opid.equals("9999") && _pass.equals("admin@123")) {
                CommanData.IsAdmin = true;
                return "Admin";
            } else {
                UrlCalling calling = new UrlCalling();
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("imei", _imeino);
                parameters.put("password", _pass);
                parameters.put("opratorid", _opid);

                return calling.getResult(Path.opratorLogin, UrlCalling.Method.GET, parameters);
            }
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(final String result) {
        Oprator opt = null;
        boolean status=false;
        try {
            if (result != null) {

                if (result.equals("Admin")) {
                    opt = new Oprator(0, "Admin",_imeino);
                } else {
                    JSONObject rootobj=new JSONObject(result);
                    if(rootobj.getBoolean("loginStatus")){
                        JSONObject optde= rootobj.getJSONObject("opratorDetails");
                        opt = new Oprator(optde.getInt("code"), optde.getString("name"),_imeino);
                        status=true;
                        CommanData.conn.oprator.truncateTable();
                        CommanData.conn.oprator.addOprator(opt.getCode(),opt.getName(),_imeino);
                    }
                    else
                    {

                        opt=null;
                    }

                }

            } else {
                Log.i("Login","result is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Login","Error is comming");
        }

        taskobj.OnTaskComplete(status,opt);
    }

    @Override
    protected void onCancelled() {

    }
}

