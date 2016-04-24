package com.cfto_leipzig;

import android.util.Log;

import com.cfto_leipzig.metarparser.Metar;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Lina on 23/04/16.
 */
public class logic {

    private static final String LOG_KEY = "logic";
    private Metar metarDep;
    private Metar metarArr;
    private HashMap<String, Integer> hmap;


    public int logiSigWeather(String weather){

        Log.i(LOG_KEY, ""+hmap.size());

        String[] stringArr = new String[3];

        int impactvalue = 0;

        stringArr = weather.split(" ");

        String intensity = stringArr[0];
        String descriptor = stringArr[1];
        String phenomena = stringArr[2];

        Set<String> keys = hmap.keySet();

        for(String key:keys){
            if(key.equals(intensity)){
                impactvalue += hmap.get(key);
            }
            if(key.equals(descriptor)){
                impactvalue += hmap.get(key);
            }
            if(key.equals(phenomena)){
                impactvalue += hmap.get(key);
            }

        }
        System.out.println(impactvalue);
        return percentProbability(impactvalue);
    }

    public int percentProbability(int impactvalue){
        int percentage = 0;

        percentage = (impactvalue) * 16;
        return percentage;

    }

    public void setMetarDep(Metar metarDep) {
        this.metarDep = metarDep;
    }

    public void setMetarArr(Metar metarArr) {
        this.metarArr = metarArr;
    }

    private int windToProbability(int impactvalue) {

        float windSpe = metarDep.getWindSpeedInKnots();

        double calcMPH = 1.15078;

        double inMPH = windSpe * calcMPH;

        if(inMPH > 100){
            impactvalue += 3;
        }
        return percentProbability(impactvalue);
    }

    private int windToProbabilityArr(int impactvalue) {

        float windSpe = metarArr.getWindSpeedInKnots();

        double calcMPH = 1.15078;

        double inMPH = windSpe * calcMPH;

        if(inMPH > 45){
            impactvalue += 3;
        }
        return percentProbability(impactvalue);
    }

    public int calculatePerc() {
        return (logiSigWeather(metarToString(metarDep)) + logiSigWeather(metarToString(metarArr)))/2;
    }

    private String metarToString(Metar metar) {

        String intensity = "";
        String descriptor = "";
        String phenomena = "";


        if(metar.getWeatherConditions().size() > 0) {
            if (metar.getWeatherCondition(0).isLight()) {
                intensity += "Light";
            } else if (metar.getWeatherCondition(0).isHeavy()) {
                intensity += "Heavy";
            } else {
                intensity += "Moderate";
            }

            if (metar.getWeatherCondition(0).isShallow()) {
                descriptor += " Shallow";
            } else if (metar.getWeatherCondition(0).isPartial()) {
                descriptor += " Partial";
            } else if (metar.getWeatherCondition(0).isPatches()) {
                descriptor += " Patches";
            } else if (metar.getWeatherCondition(0).isLowDrifting()) {
                descriptor += " Low Drifting";
            } else if (metar.getWeatherCondition(0).isBlowing()) {
                descriptor += " Blowing";
            } else if (metar.getWeatherCondition(0).isShowers()) {
                descriptor += " Showers";
            } else if (metar.getWeatherCondition(0).isThunderstorms()) {
                descriptor += " Thunderstorms";
            } else if (metar.getWeatherCondition(0).isFreezing()) {
                descriptor += " Freezing";
            } else {
                // shouldn't get here
            }

            if (metar.getWeatherCondition(0).isDrizzle()) {
                phenomena += " Drizzle";
            } else if (metar.getWeatherCondition(0).isRain()) {
                phenomena += " Rain";
            } else if (metar.getWeatherCondition(0).isSnow()) {
                phenomena += " Snow";
            } else if (metar.getWeatherCondition(0).isSnowGrains()) {
                phenomena += " Snow Grains";
            } else if (metar.getWeatherCondition(0).isIceCrystals()) {
                phenomena += " Ice Crystals";
            } else if (metar.getWeatherCondition(0).isIcePellets()) {
                phenomena += " Ice Pellets";
            } else if (metar.getWeatherCondition(0).isHail()) {
                phenomena += " Hail";
            } else if (metar.getWeatherCondition(0).isSmallHail()) {
                phenomena += " Small Hail";
            } else if (metar.getWeatherCondition(0).isUnknownPrecipitation()) {
                phenomena += " Unknown Precipitation";
            } else if (metar.getWeatherCondition(0).isMist()) {
                phenomena += " Mist";
            } else if (metar.getWeatherCondition(0).isFog()) {
                phenomena += " Fog";
            } else if (metar.getWeatherCondition(0).isSmoke()) {
                phenomena += " Smoke";
            } else if (metar.getWeatherCondition(0).isVolcanicAsh()) {
                phenomena += " Volcanic Ash";
            } else if (metar.getWeatherCondition(0).isWidespreadDust()) {
                phenomena += " Widespread Dust";
            } else if (metar.getWeatherCondition(0).isSand()) {
                phenomena += " Sand";
            } else if (metar.getWeatherCondition(0).isHaze()) {
                phenomena += " Haze";
            } else if (metar.getWeatherCondition(0).isSpray()) {
                phenomena += " Spray";
            } else if (metar.getWeatherCondition(0).isDustSandWhirls()) {
                phenomena += " Well-developed Dust/Sand Whirls";
            } else if (metar.getWeatherCondition(0).isSqualls()) {
                phenomena += " Squalls";
            } else if (metar.getWeatherCondition(0).isFunnelCloud()) {
                phenomena += " Funnel Cloud/Tornado/Waterspout";
            } else if (metar.getWeatherCondition(0).isSandstorm()) {
                phenomena += " Sandstorm";
            } else if (metar.getWeatherCondition(0).isDuststorm()) {
                phenomena += " Duststorm";
            }

            return intensity + descriptor + phenomena;
        } else {
            return "x x x";
        }
    }

    public void setRules(HashMap<String,Integer> rules) {
        this.hmap = rules;
    }
}
