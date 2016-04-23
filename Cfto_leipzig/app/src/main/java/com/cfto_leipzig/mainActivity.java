package com.cfto_leipzig;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class mainActivity extends AppCompatActivity implements ConnectionCallbacks{

    private static final String LOG_KEY = "Main";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    EditText ed;
    String nearestAirpotIATA;

    public double LocationLongitude = 0.;
    public double LocationLatitude = 0.;
    private Controller cont;
    final Calendar c = Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        cont = new Controller(this);

        TextView time = (TextView) findViewById(R.id.textView);

        Button search = (Button) findViewById(R.id.button3);
        ed = (EditText) findViewById(R.id.editText);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainActivity.this, display_probability.class));
            }
        });
        Button timeMinus = (Button) findViewById(R.id.button);
        Button timePlus = (Button) findViewById(R.id.button2);

        timeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int hours = c.get(Calendar.HOUR_OF_DAY);
                TextView temp = (TextView) findViewById(R.id.textView);
                int h = Integer.parseInt(temp.getText().toString().split(":")[0]);
                h = h-1 < hours? h:h-1;
                temp.setText(h+":00");
            }
        });

        timePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int hours = c.get(Calendar.HOUR_OF_DAY);
                TextView temp = (TextView) findViewById(R.id.textView);
                int h = Integer.parseInt(temp.getText().toString().split(":")[0]);
                h = h+1 > 24? h:h+1;
                temp.setText(h+":00");
            }
        });

        buildGoogleApiClient();
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LocationLatitude = mLastLocation.getLatitude();
            LocationLongitude = mLastLocation.getLongitude();

            Log.i(LOG_KEY, ""+LocationLatitude);
            Log.i(LOG_KEY, ""+LocationLongitude);

            //Log.i(LOG_KEY, cont.getNearestAirport(LocationLatitude, LocationLongitude));

            nearestAirpotIATA = cont.getNearestAirport(LocationLatitude, LocationLongitude);

            ed.setText(nearestAirpotIATA);
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }
}
