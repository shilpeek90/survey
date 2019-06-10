package com.example.canesurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.canesurvey.Async.UrlCalling;
import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.Comman.Path;
import com.example.canesurvey.DB.DataBaseHelper;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DataBaseHelper conn = new DataBaseHelper(this);
        CommanData.conn = conn;

        Log.i("Splash", "Activity Starte");
        if (conn.oprator.getTableDataCount() > 0) {
            Log.i("getting the table count", String.valueOf(conn.oprator.getTableDataCount()));
            String imei = conn.oprator.getImei();
            CheckImeiTask task = new CheckImeiTask(imei);
            task.execute();
            // callLoginActivity();
        } else
            callLoginActivity();

        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               callLoginActivity();
            }
        }, 10000);
*/
    }

    public void callLoginActivity() {
        Intent ind = new Intent(this, LoginActivity.class);
        startActivity(ind);
    }

    public void callMainActivity() {
        Intent ind = new Intent(this, MainActivity.class);
        startActivity(ind);
    }

    public class CheckImeiTask extends AsyncTask<Void, Void, String> {
        String imeino;

        public CheckImeiTask(String im) {
            imeino = im;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String url = Path.opratorchekimei + imeino;
                String result = new UrlCalling().getResult(url, UrlCalling.Method.GET);
                Log.i("splash", result);
                return result;
            } catch (Exception ex) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                if (result != null) {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("loginStatus")) {
                        callMainActivity();
                    } else {
                        callLoginActivity();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
