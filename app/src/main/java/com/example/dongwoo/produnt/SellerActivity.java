package com.example.dongwoo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class SellerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        SeekBar seekbar1 = (SeekBar)findViewById(R.id.seekBar);
        SeekBar seekbar2 = (SeekBar)findViewById(R.id.seekBar2);
        SeekBar seekbar3 = (SeekBar)findViewById(R.id.seekBar3);

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                @Override
                                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                                                }

                                                @Override
                                                public void onStartTrackingTouch(SeekBar seekBar) {

                                                }

                                                @Override
                                                public void onStopTrackingTouch(SeekBar seekBar) {

                                                }
                                            }
    }
}
