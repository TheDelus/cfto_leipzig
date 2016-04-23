package com.cfto_leipzig;

import java.util.ArrayList;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class AirportLocator {
    String iata;
    ArrayList<Airport> airports;
    double bestDistance = Double.MAX_VALUE;

    public AirportLocator(ArrayList<Airport> airports) {
        this.airports = airports;
    }

    String getNextAirport(double lat, double lon) {
        for(int i = 0; i < airports.size(); i++)
        {
            double distance = getDistanceFromLatLonInKm
                    (airports.get(i).getLatitude(), airports.get(i).getLongitude(), lat, lon);

            if(distance < bestDistance)
            {
                bestDistance = distance;
                iata = airports.get(i).getIata();
            }

        }

        return iata;
    }

    private double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}
