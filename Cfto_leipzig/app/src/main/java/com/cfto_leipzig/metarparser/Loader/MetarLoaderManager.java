package com.cfto_leipzig.metarparser.Loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.cfto_leipzig.metarparser.Metar;

/**
 * Created by User on 23.04.2016.
 */
public class MetarLoaderManager implements LoaderManager.LoaderCallbacks<Metar> {

    private static final java.lang.String AIRPORT = "airport";
    private static final String LOG_TAG = "MetarLoaderManger";
    Context context;
    private Metar metar;
    private Boolean dataReceived = false;

    public MetarLoaderManager(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Metar> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Loader created");

        if(args.containsKey(AIRPORT)) {
            Log.i(LOG_TAG, "code found");
            return new MetarLoader(context, args.getString(AIRPORT));
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Metar> loader, Metar data) {
        Log.i(LOG_TAG, "Data received! LoaderID: " + loader.getId());

        if(data != null) {
            dataReceived = true;
            metar = data;
        }
    }

    @Override
    public void onLoaderReset(Loader<Metar> loader) {

    }

    public Metar getMetar() {
        return metar;
    }

    public Boolean getDataReceived() {
        return dataReceived;
    }
}
