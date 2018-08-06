package com.example.dongwoo.produnt;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class roleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        Button buttonRed = (Button) findViewById(R.id.role_btn_middle) ;
        buttonRed.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(roleActivity.this, RouterActivity.class);
                startActivity(intent);
            }
        }) ;

        Button buttonBuyer = (Button) findViewById(R.id.role_btn_buyer) ;
        buttonBuyer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(roleActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        }) ;

        Button buttonSeller = (Button) findViewById(R.id.role_btn_seller) ;
        buttonSeller.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(roleActivity.this, SellerActivity.class);
                startActivity(intent);
            }
        }) ;

        Button buttonProfile = (Button) findViewById(R.id.see_profile) ;
        buttonProfile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(roleActivity.this, profileActivity.class);
                startActivity(intent);
            }
        }) ;
    }
}
