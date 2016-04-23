package com.cfto_leipzig;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.net.URL;
import java.text.SimpleDateFormat;

public class weatherController{
    private String url = "https://api.forecast.io/forecast/287a0885ca9326567db06f7a0b5c232d/";
    private String data = "";
    private Calendar c;

    public weatherController(){}

    public weatherController(String url) {
        this.url = url;
    }

    public String getWeather(String code, String time){
        c = Calendar.getInstance(); //[YYYY]-[MM]-[DD]T[HH]:[MM]:[SS]
        SimpleDateFormat df = new SimpleDateFormat("[yyyy]-[MM]-[dd]T");
        String formattedDate = df.format(c.getTime());
        formattedDate += "["+time+"]:[00]:[00]";
        
        new Fetcher().execute(url+"51.3288,12.371,[2016]-[04]-[23]T[16]:[00]:[00]");
        return(data);     //https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE,TIME
    }

    class Fetcher extends AsyncTask<String,Void,Void> {
        protected Void doInBackground (String... urls){

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder str = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        str.append(line).append("\n");
                    }
                    br.close();
                    data = str.toString();
                } finally {
                    conn.disconnect();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
    }
    
}