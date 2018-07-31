package com.example.dongwoo.produnt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class SellerActivity extends AppCompatActivity {
    SeekBar seekbar1;
    SeekBar seekbar2;
    SeekBar seekbar3;

    public int data = 0;
    public int price = 0;
    public int connect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        seekbar1 = (SeekBar) findViewById(R.id.seekBar);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekbar3 = (SeekBar) findViewById(R.id.seekBar3);

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                data = seekbar1.getProgress();
                //TODO 전역변수
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                data = seekbar1.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                data = seekbar1.getProgress();
            }
        });

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                price = seekbar2.getProgress();
                //TODO 전역변수
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                price = seekbar2.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                price = seekbar2.getProgress();
            }
        });

        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                connect = seekbar3.getProgress();
                //TODO 전역변수
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                connect = seekbar3.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                connect = seekbar3.getProgress();
            }
        });
    }

    public void onClickCheckButton(View v) {
        Intent intent = new Intent(SellerActivity.this, Seller2Activity.class);
        startActivity(intent);
    }
}