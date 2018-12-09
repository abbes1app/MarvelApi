package com.example.abbes.marvelapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Lancement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancement);



        final  Intent i = new Intent(this,MainActivity.class);
        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(2500);

                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    finish();
                }
            }

        };
        logoTimer.start();
    }
}
