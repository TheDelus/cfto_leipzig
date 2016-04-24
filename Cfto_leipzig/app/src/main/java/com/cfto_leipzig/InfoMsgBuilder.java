package com.cfto_leipzig;

import com.cfto_leipzig.metarparser.Metar;

/**
 * Created by TheDelus on 24.04.2016.
 */
public class InfoMsgBuilder {
    private String msg = "";

    public String getMsg() {
        return msg;
    }

    public void addString(String stringToAdd) {
        msg += stringToAdd;
    }

    public void addStringNewLine(String stringToAdd) {
        msg += "\n" + stringToAdd;
    }

    public void addStringSpace(String stringToAdd) {
        msg += " " + stringToAdd;
    }

    public void addMetarWeatherMsg(Metar metar) {
        if( metar.getWeatherConditions().size() > 0)
            msg += "\n" + metar.getWeatherCondition(0).getNaturalLanguageString();
        else msg += "\n" + "No significant weather detected!";
    }

    public void addMetarCloudMsg(Metar metar) {
        if( metar.getSkyConditions().size() > 0)
            msg += "\n" + metar.getSkyCondition(0).getNaturalLanguageString();
        else msg += "\n" + "No significant clouds detected!";
    }

    public void addMetarTemperature(Metar metar) {
        if( metar.getTemperatureInCelsius() != null)
            msg += "\n" + "Temperature: " + metar.getTemperatureInCelsius() + "°C";
    }

    public void addMetarVisibility(Metar metar) {
        if( metar.getVisibilityInKilometers() != null)
            if(metar.getVisibilityInKilometers() > 6)
                msg += "\n" + "Visibility: " + ">10km";
            else
                msg += "\n" + "Visibility: " + metar.getVisibilityInKilometers() + "km";
    }
}
