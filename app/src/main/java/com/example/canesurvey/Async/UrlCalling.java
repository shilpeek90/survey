package com.example.canesurvey.Async;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MAP on 1/27/2018.
 */

public class UrlCalling {
    public enum Method {POST, GET,PUT,DELETE}

    public String getResult(String url, Method urlmethod) {
       return getResult(url, urlmethod, null);
    }

    public String getResult(String url, Method urlmethod, HashMap<String, String> params) {
        URL myUrl = null;
        String Lasttagno = "";
        int lastindex = 0;
        HttpURLConnection connection;
        InputStreamReader streamReader;
        BufferedReader reader;
        StringBuilder stringBuilder;
        String inputLine = "";
        String result = "";
        try {
            if(urlmethod==Method.GET && params!=null)
            {
                url+="?"+getPostDataString(params);
            }
            myUrl = new URL(url);
            connection = (HttpURLConnection) myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(urlmethod.toString());
            //Connect to our url

            if (params != null && urlmethod!=urlmethod.GET) {
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(params));

                writer.flush();
                writer.close();
                os.close();
            }

            connection.connect();
            //Create a new InputStreamReader
            streamReader = new
                    InputStreamReader(connection.getInputStream());

            //Create a new buffered reader and String Builder
            reader = new BufferedReader(streamReader);
            stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            inputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String post(String url, HashMap<String, String> params) {
        URL myUrl = null;
        String Lasttagno = "";
        int lastindex = 0;
        HttpURLConnection connection;
        InputStreamReader streamReader;
        BufferedReader reader;
        StringBuilder stringBuilder;
        String inputLine = "";
        String result = "";
        try {
            myUrl = new URL(url);
            connection = (HttpURLConnection) myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod("POST");
            //Connect to our url


            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();

            connection.connect();
            //Create a new InputStreamReader
            streamReader = new
                    InputStreamReader(connection.getInputStream());

            //Create a new buffered reader and String Builder
            reader = new BufferedReader(streamReader);
            stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            inputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public byte[] getBytesFromBase64(String data) {
        byte[] bytes = Base64.decode(data, Base64.DEFAULT);
        return bytes;
    }
}
