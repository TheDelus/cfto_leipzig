package com.cfto_leipzig;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;

public class mainActivity extends AppCompatActivity implements ConnectionCallbacks {

    private static final String LOG_KEY = "Main";
    private static final int DEPART = 0;
    private static final int ARR = 1;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    EditText ed_depart;
    EditText ed_arriv;
    TextView tv_iata_depart;
    TextView tv_iata_arr;
    ArrayList<EditText> editTexts = new ArrayList<>(); // Container list

    String nearestAirpotIATA;
    String departAirpotIATA;
    String arrAirpotIATA;

    public double LocationLongitude = 0.;
    public double LocationLatitude = 0.;
    private Controller cont;
    final Calendar c = Calendar.getInstance();
    weatherController weacont = new weatherController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        cont = new Controller(this);

        TextView time = (TextView) findViewById(R.id.textView);
        int hours = c.get(Calendar.HOUR_OF_DAY)+1;
        time.setText(hours + ":00");

        ImageView info = (ImageView) findViewById(R.id.imageButton2);
        info.setImageResource(R.drawable.information);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mainActivity.this)
                        .setTitle("Info")
                        .setMessage("This App is created for people who want to know if their flight can have a decent amount of delay.\nFirst you need to settle the right time in hours by pressing \"+\" or \"-\".\nIf your departure airport is not the right one, change it as well as the airport you want to flight to.\nAt last just hit \"GO!\" and the probability of getting delay will be calculated!")
                        .setPositiveButton("Understood!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button search = (Button) findViewById(R.id.button3);
        ed_depart = (EditText) findViewById(R.id.editText);
        ed_arriv = (EditText) findViewById(R.id.editText2);

        tv_iata_depart = (TextView) findViewById(R.id.iata_depart);
        tv_iata_arr = (TextView) findViewById(R.id.iata_arr);

        final String t = "heavy rain\ncloudy";
        editTexts.add(ed_depart);
        editTexts.add(ed_arriv);

    search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tv_iata_arr.getText().toString().equals("-") &&
                        !tv_iata_arr.getText().toString().equals("-")) {

                    cont.setIata_dep(tv_iata_depart.getText().toString());
                    cont.setIata_arr(tv_iata_arr.getText().toString());

                    if(cont.getMetarDataDep().getWeatherConditions().size() > 0)
                        Log.i(LOG_KEY, "" + cont.getMetarDataDep().getWeatherCondition(0).getNaturalLanguageString());

                    if(cont.getMetarDataArr().getWeatherConditions().size() > 0)
                        Log.i(LOG_KEY, "" + cont.getMetarDataArr().getWeatherCondition(0).getNaturalLanguageString());

                    Log.i(LOG_KEY, "" + cont.computeSigWeather(cont.getMetarDataDep(),cont.getMetarDataArr()));

                    Intent intent = new Intent(mainActivity.this, display_probability.class);
                    intent.putExtra("prob", cont.computeSigWeather(cont.getMetarDataDep(),cont.getMetarDataArr()));
                    intent.putExtra("info", t);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(cont.getMainActivity(), "Please input airports", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                h = h-2 < hours? h:h-1;
                temp.setText(h+":00");
            }
        });

        timePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TextView temp = (TextView) findViewById(R.id.textView);
                int h = Integer.parseInt(temp.getText().toString().split(":")[0]);
                h = h+1 > 24? h:h+1;
                temp.setText(h+":00");
            }
        });

        for (final EditText editText : editTexts) { //need to be final for custom behaviors
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //Apply general behavior for all editTexts

                    if (editText == editTexts.get(0)) {
                        if(!editText.getText().toString().equals(departAirpotIATA)) {

                            departAirpotIATA = cont.getAiportIATAByName(editText.getText().toString());
                            tv_iata_depart.setText(departAirpotIATA);

                            if(departAirpotIATA.length() == 3) {
                                cont.fetchMetarData(cont.getAirportICAOByIATA(departAirpotIATA), DEPART);
                            }
                        }
                    }

                    if (editText == editTexts.get(1)) {
                        if(!editText.getText().toString().equals(arrAirpotIATA)) {

                            arrAirpotIATA = cont.getAiportIATAByName(editText.getText().toString());
                            tv_iata_arr.setText(arrAirpotIATA);

                            if(arrAirpotIATA.length() == 3) {
                                cont.fetchMetarData(cont.getAirportICAOByIATA(arrAirpotIATA), ARR);
                            }
                        }
                    }
                }
            });

        }

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

            //weacont.getWeather(LocationLatitude, LocationLongitude, "14");

            Log.i(LOG_KEY, ""+LocationLatitude);
            Log.i(LOG_KEY, ""+LocationLongitude);

            //Log.i(LOG_KEY, cont.getNearestAirport(LocationLatitude, LocationLongitude));

            nearestAirpotIATA = cont.getNearestAirport(LocationLatitude, LocationLongitude);

            ed_depart.setText(cont.getAiportNameByIATA(nearestAirpotIATA));
            //cont.fetchMetarData(cont.getAirportICAOByIATA(nearestAirpotIATA));

        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }
}
