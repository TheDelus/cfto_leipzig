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
import android.widget.TextView;


public class display_probability extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_probability);
        Intent i = getIntent();
        int probability = i.getIntExtra("prob",0);
        final String[] text = i.getStringArrayExtra("info");
        ImageButton info = (ImageButton) findViewById(R.id.imageButton);
        info.setImageResource(R.drawable.information);

        info.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //alert
                new AlertDialog.Builder(display_probability.this)
                        .setTitle("Info")
                        .setMessage(text[0])
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
        TextView prob = (TextView) findViewById(R.id.textView2);
        if(probability > 50)prob.setTextColor(Color.RED);
        else if(probability < 25)prob.setTextColor(Color.GREEN);
        else prob.setTextColor(Color.YELLOW);
        prob.setText(String.valueOf(probability));

    }
}