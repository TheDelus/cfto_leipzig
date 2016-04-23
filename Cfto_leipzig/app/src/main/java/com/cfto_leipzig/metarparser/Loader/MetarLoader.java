package com.cfto_leipzig.metarparser.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.cfto_leipzig.metarparser.Metar;
import com.cfto_leipzig.metarparser.MetarFetcher;
import com.cfto_leipzig.metarparser.MetarParseException;
import com.cfto_leipzig.metarparser.MetarParser;

/**
 * Created by TheDelus on 23.04.2016.
 */
public class MetarLoader extends AsyncTaskLoader<Metar> {
    private static final String LOG_TAG = "MetarLoader";
    String airport;

    public MetarLoader(Context context, String string) {
        super(context);
        this.airport = string;

        Log.i(LOG_TAG, "Created");
    }

    @Override
    public Metar loadInBackground() {
        Log.i(LOG_TAG, "Loading");

        try {
            return MetarParser.parse(MetarFetcher.fetch(airport));
        } catch (MetarParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
