package com.cfto_leipzig;

import android.os.Bundle;
import android.util.Log;

import com.cfto_leipzig.metarparser.Loader.MetarLoaderManager;
import com.cfto_leipzig.metarparser.Metar;

import java.util.ArrayList;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class Controller {
    private static final String AIRPORT = "airport";
    private static final int METAR_LOADER = 0;
    private static final String LOG_TAG = "Controller";
    mainActivity mainActivity;
    MetarLoaderManager mlm;
    AirportParser ap;
    AirportLocator al;

    public Controller(mainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mlm = new MetarLoaderManager(mainActivity);
        ap = new AirportParser(mainActivity);
        al = new AirportLocator(getAirports());

        //ap.getAirports();

    }

    public void fetchMetarData(String airportCode) {
        Log.i(LOG_TAG, "Start Fetch");


        Bundle args = new Bundle();
        args.putString(AIRPORT, airportCode);
        mainActivity.getSupportLoaderManager().restartLoader(METAR_LOADER, args, mlm);
        Log.i(LOG_TAG, "End Fetch");

    }

    public Metar getMetarData() {
        if(mlm.getDataReceived())
        {
            return mlm.getMetar();
        } else {
            return null;
        }
    }

    public ArrayList<Airport> getAirports() {
        return ap.getAirports();
    }

    public String getNearestAirport(double lat, double lon) {
       return al.getNextAirport(lat, lon);
    }
}
