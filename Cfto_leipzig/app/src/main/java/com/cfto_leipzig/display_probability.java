package com.cfto_leipzig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class display_probability extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_probability);
        Intent i = getIntent();
        int probability = i.getIntExtra("prob", 0);
        final String text = i.getStringExtra("info");
        TextView info = (TextView) findViewById(R.id.textView3);
        probability = 10;
        double tempProb = probability;

        View back = info.getRootView();
        int red = 255, green = 255;
        if(probability <= 50)red = (int)(255 * (tempProb/50));
        else green = (int)(255 * (1 - (tempProb-50)/50));

        back.setBackgroundColor(Color.parseColor("#"+Integer.valueOf(String.valueOf(red), 16)+Integer.valueOf(String.valueOf(green), 16)+"00"));

        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new AlertDialog.Builder(display_probability.this)
                        .setTitle("Info")
                        .setMessage(text)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button newFlight = (Button) findViewById(R.id.button4);
        newFlight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        TextView prob = (TextView) findViewById(R.id.textView2);
        prob.setTextColor(Color.WHITE);
        prob.setText(String.valueOf(probability)+"%");

    }
}