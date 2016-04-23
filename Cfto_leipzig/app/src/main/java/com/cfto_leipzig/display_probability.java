package com.cfto_leipzig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class display_probability extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_probability);

        ImageButton info = (ImageButton) findViewById(R.id.imageButton);
        info.setImageResource(R.drawable.information);

        info.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //alert
                new AlertDialog.Builder(display_probability.this)
                        .setTitle("Info")
                        .setMessage("Just look outside!!!1!11")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button back = (Button) findViewById(R.id.button4);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}