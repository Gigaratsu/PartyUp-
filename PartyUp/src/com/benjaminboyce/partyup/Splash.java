package com.benjaminboyce.partyup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash extends Activity {

	 // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i("PartyUp - Splash","Beginning Splash Activity");
 
        new Handler().postDelayed(new Runnable() {
 
 
            @Override
            public void run() {
                
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
