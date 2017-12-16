package com.mykmovies.android.mykmovieworld;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yeswa on 02-12-2017.
 */

public class JsonAsyncTask extends AsyncTask<String, String, String> {
    HttpURLConnection httpURLConnection;
    BufferedReader bufferedReader;

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url=new URL(params[0]);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer=new StringBuffer();
            String line="";
            while((line=bufferedReader.readLine())!=null)
            {
                buffer.append(line);
            }
            String jsonUrl=buffer.toString();
            return jsonUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
            }
        }
        return null;
    }
}