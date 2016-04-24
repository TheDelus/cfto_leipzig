package com.cfto_leipzig;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class AirportLocator {
    private static final String LOG_KEY = "AirportLocator";
    String iata;
    ArrayList<Airport> airports;
    double bestDistance = Double.MAX_VALUE;

    public AirportLocator(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    public String getNextAirport(double lat, double lon) {
        for(int i = 0; i < airports.size(); i++)
        {
            double distance = DistanceCalculator.getDistanceFromLatLonInKm
                    (airports.get(i).getLatitude(), airports.get(i).getLongitude(), lat, lon);

            if(distance < bestDistance)
            {
                bestDistance = distance;
                iata = airports.get(i).getIata();
            }

        }

        return iata;
    }

    public String getAirportNameByIATA (String iata) {
        for(int i = 0; i < airports.size(); i++)
        {
           if(airports.get(i).getIata().equals(iata))
                return airports.get(i).getAirportName();
        }

        return "Airport not found!";
    }

    public String getICAOByIATA(String iata) {

        for(int i = 0; i < airports.size(); i++)
        {
            if(airports.get(i).getIata().equals(iata))
                return airports.get(i).getIcao();
        }

        return "null";
    }

    public String getAirportIATAByName(String name) {

        for(int i = 0; i < airports.size(); i++)
        {
            if(airports.get(i).getCityName().equals(name) || airports.get(i).getAirportName().contains(name) || airports.get(i).getIata().equals(name)) {
                return airports.get(i).getIata();
            }
        }

        return "Airport not found!";
    }
}
