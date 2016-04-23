package com.cfto_leipzig;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class Airport {
    int id;
    String airportName;
    String cityName;
    String country;
    String iata;
    String icao;
    double latitude;
    double longitude;
    double altitude;
    double timezone;
    String dst;
    String tz;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAirportName() {
        return airportName;
    }
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getIata() {
        return iata;
    }
    public void setIata(String iata) {
        this.iata = iata;
    }
    public String getIcao() {
        return icao;
    }
    public void setIcao(String icao) {
        this.icao = icao;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
    public double getTimezone() {
        return timezone;
    }
    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }
    public String getDst() {
        return dst;
    }
    public void setDst(String dst) {
        this.dst = dst;
    }
    public String getTz() {
        return tz;
    }
    public void setTz(String tz) {
        this.tz = tz;
    }
    @Override
    public String toString() {
        return "Airport [id=" + id + ", airportName=" + airportName + ", cityName=" + cityName + ", country=" + country
                + ", iata=" + iata + ", icao=" + icao + ", latitude=" + latitude + ", longitude=" + longitude
                + ", altitude=" + altitude + ", timezone=" + timezone + ", dst=" + dst + ", tz=" + tz + "]";
    }
}
