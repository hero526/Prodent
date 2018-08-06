package com.example.dongwoo.produnt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Seller2Activity extends AppCompatActivity {
    static double rand = 0.0;
    static double data = 0.0;
    static double cash = 0.0;
    static int count = 0;
    static int connect = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller2);

        final TextView dataText = (TextView)findViewById(R.id.sDataText);
        final TextView cashText = (TextView)findViewById(R.id.sCashText);
        final TextView conText = (TextView)findViewById(R.id.sConText);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Seller2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rand = Math.random();

                        data += rand * connect;
                        cash += (rand / 8.0) * connect;
                        count++;

                        if (count > 20) {
                            if (connect < 5)
                                connect++;
                                count = 0;
                        }

                        dataText.setText(String.format("%.4f", data));
                        cashText.setText(String.format("%.4f", cash));
                        conText.setText(String.valueOf(connect));
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt, 0, 500);
    }
}
