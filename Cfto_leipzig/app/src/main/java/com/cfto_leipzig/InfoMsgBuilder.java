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
        msg += "\r" + stringToAdd;
    }

    public void addStringSpace(String stringToAdd) {
        msg += " " + stringToAdd;
    }

    public void addMetarWeatherMsg(Metar metar) {
        if( metar.getWeatherConditions().size() > 0)
            msg += "\r" + metar.getWeatherCondition(0).getNaturalLanguageString();
    }
}
