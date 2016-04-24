package com.cfto_leipzig;

import android.os.Bundle;
import android.util.Log;

import com.cfto_leipzig.metarparser.Loader.MetarLoaderManager;
import com.cfto_leipzig.metarparser.Metar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class Controller {
    private static final String AIRPORT = "airport";
    private static final int METAR_LOADER_DEP = 0;
    private static final int METAR_LOADER_ARR = 1;
    private static final String LOG_TAG = "Controller";
    private mainActivity mainActivity;
    private MetarLoaderManager mlm_dep;
    private MetarLoaderManager mlm_arr;

    private AirportParser ap;
    private AirportLocator al;
    private xml_parser xl;
    private logic logic;

    private String iata_dep;
    private String iata_arr;

    private int time;

    public Controller(mainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mlm_dep = new MetarLoaderManager(mainActivity, METAR_LOADER_DEP);
        mlm_arr = new MetarLoaderManager(mainActivity, METAR_LOADER_ARR);
        ap = new AirportParser(mainActivity);
        al = new AirportLocator(getAirports());
        xl = new xml_parser(mainActivity);
        rules = xl.getHmap();

        logic = new logic();
        logic.setRules(rules);
        logic.setTime(time);



        //ap.getAirports();

    }

    public String getIata_arr() {
        return iata_arr;
    }

    public void setIata_arr(String iata_arr) {
        this.iata_arr = iata_arr;
    }

    public String getIata_dep() {
        return iata_dep;
    }

    public void setIata_dep(String iata_dep) {
        this.iata_dep = iata_dep;
    }

    HashMap<String,Integer> rules;

    public void fetchMetarData(String airportCodeICAO, int code) {
        Log.i(LOG_TAG, "Start Fetch");

        Bundle args = new Bundle();
        args.putString(AIRPORT, airportCodeICAO);
        if(code == 0)
            mainActivity.getSupportLoaderManager().restartLoader(METAR_LOADER_DEP, args, mlm_dep);

        if(code == 1)
            mainActivity.getSupportLoaderManager().restartLoader(METAR_LOADER_ARR, args, mlm_arr);

        Log.i(LOG_TAG, "End Fetch");

    }

    public Metar getMetarDataDep() {
        if(mlm_dep.getDataReceived())
        {
            return mlm_dep.getMetar();
        } else {
            return null;
        }
    }

    public Metar getMetarDataArr() {
        if(mlm_arr.getDataReceived())
        {
            return mlm_arr.getMetar();
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

    public String getAiportNameByIATA(String iata) {
        return al.getAirportNameByIATA(iata);
    }

    public String getAiportIATAByName(String name) {
        return al.getAirportIATAByName(name);
    }

    public String getAirportICAOByIATA(String iata) {
        return al.getICAOByIATA(iata);
    }

    public com.cfto_leipzig.mainActivity getMainActivity() {
        return mainActivity;
    }

    public int computeSigWeather (Metar weatherDep, Metar weatherArr) {
        int perc = 0;

        logic.setMetarDep(weatherDep);
        logic.setMetarArr(weatherArr);

        perc = logic.calculatePerc();

        return perc;
    }

    public void setTime(int time) {
        this.time = time;
        logic.setTime(time);
    }
}
