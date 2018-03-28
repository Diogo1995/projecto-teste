package com.example.diogo.barraqueiro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final String LOGIN_CREDENTIALS = "login";

    SharedPreferences loginCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loginCredentials = getSharedPreferences(LOGIN_CREDENTIALS, MODE_PRIVATE);
        String message = loginCredentials.getString("Username", null);

        // Set the welcome message with the username
        TextView textView = findViewById(R.id.welcomeMessage);
        textView.setText(textView.getText() + " " + message);

        // Set the buttons
        ImageButton fillUpIcon = (ImageButton)findViewById(R.id.fillUpButton);
        fillUpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillUpActivity();
            }
        });

        ImageButton statsIcon = (ImageButton)findViewById(R.id.statsButton);
        statsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsActivity();
            }
        });

        ImageButton mapsIcon = (ImageButton)findViewById(R.id.mapsButton);
        mapsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapsActivity();
            }
        });

        ImageButton contactsIcon = (ImageButton)findViewById(R.id.contactsButton);
        contactsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsActivity();
            }
        });

        ImageButton settingsIcon = (ImageButton)findViewById(R.id.settingsButton);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsActivity();
            }
        });

        ImageButton logOutIcon = (ImageButton)findViewById(R.id.logOutButton);
        logOutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void fillUpActivity(){
        Intent in = new Intent(this, FillUp.class);
        startActivity(in);
    }

    public void statsActivity(){
        Intent in = new Intent(this, Stats.class);
        startActivity(in);
    }

    public void mapsActivity(){
        Intent in = new Intent(this, Maps.class);
        startActivity(in);
    }

    public void contactsActivity(){
        Intent in = new Intent(this, Contacts.class);
        startActivity(in);
    }

    public void settingsActivity(){
        Intent in = new Intent(this, Settings.class);
        startActivity(in);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        return;
    }
}