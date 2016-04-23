package com.cfto_leipzig;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class AirportParser {
    private static final String LOG_KEY = "Airport";
    Context main;
    String line;

    public AirportParser(mainActivity mainActivity) {
        this.main = mainActivity;
    }

    public ArrayList<Airport> getAirports() {

        InputStream rawRes = main.getResources().openRawResource(R.raw.airports);
        Reader r = new InputStreamReader(rawRes);
        BufferedReader in = new BufferedReader(r);
        ArrayList<Airport> airports = new ArrayList<>();

    do {
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line != null) {
            String[] parts = line.split(",");
            Airport ap = new Airport();

            ap.setId(Integer.parseInt(parts[0].replaceAll("\"", "")));
            ap.setAirportName(parts[1].replaceAll("\"", ""));
            ap.setCityName(parts[2].replaceAll("\"", ""));
            ap.setCountry(parts[3].replaceAll("\"", ""));
            ap.setIata(parts[4].replaceAll("\"", ""));
            ap.setIcao(parts[5].replaceAll("\"", ""));
            ap.setLatitude(Double.parseDouble(parts[6].replaceAll("\"", "")));
            ap.setLongitude(Double.parseDouble(parts[7].replaceAll("\"", "")));
            ap.setAltitude(Double.parseDouble(parts[8].replaceAll("\"", "")));
            ap.setTimezone(Double.parseDouble(parts[9].replaceAll("\"", "")));
            ap.setDst(parts[10].replaceAll("\"", ""));
            ap.setTz(parts[11].replaceAll("\"", ""));

            Log.i(LOG_KEY, ap.toString());

            airports.add(ap);
        }

    } while (line != null);

        return airports;
    }

}
