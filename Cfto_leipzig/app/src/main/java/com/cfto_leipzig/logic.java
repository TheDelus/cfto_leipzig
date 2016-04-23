package com.cfto_leipzig;

import android.util.Log;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Lina on 23/04/16.
 */
public class logic {

    private static final String LOG_KEY = "logic";

    public int logi(HashMap<String,Integer> hmap){

        Log.i(LOG_KEY, ""+hmap.size());

        String intensity = new String("isModerate");
        String descriptor = new String("isPartial");
        String phenomena = new String("isSmoke");
        int impactvalue = 0;

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

        percentage = impactvalue * 16;
        return percentage;

    }
}
