package com.example.dongwoo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void onClicSellerButton(View v) {
        Intent intent = new Intent(ChoiceActivity.this, SellerActivity.class);
        startActivity(intent);
    }

    public void onClickRouterButton(View v) {
        Intent intent = new Intent(ChoiceActivity.this, RouterActivity.class);
        startActivity(intent);
    }

    public void onClickBuyerButton(View v) {
        //Todo
    }
}
