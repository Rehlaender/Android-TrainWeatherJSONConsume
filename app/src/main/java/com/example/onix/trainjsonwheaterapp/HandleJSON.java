package com.example.onix.trainjsonwheaterapp;

import android.annotation.SuppressLint;
import android.renderscript.ScriptGroup;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by onix on 25/07/16.
 */
public class HandleJSON {
    private String country = "county";
    private String temperature = "temperature";
    private String humidity = "humidity";
    private String pressure = "pressure";
    private String urlString = null;
    public volatile boolean parsingComplete = true;

    public HandleJSON(String url) {
        this.urlString = url;
    }

    public String getCountry() {
        return country;
    }


    public String getTemperature() {
        return temperature;
    }


    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject reader = new JSONObject(in);

            JSONObject sys = reader.getJSONObject("sys");
            country = sys.getString("country");

            JSONObject main = reader.getJSONObject("main");
            temperature = main.getString("temp");
            pressure = main.getString("pressure");
            humidity = main.getString("humidity");

            parsingComplete = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(1000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
