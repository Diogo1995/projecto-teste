package com.example.diogo.barraqueiro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Ver o estado do switch
        Switch darkModeSwitch = (Switch) findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(useDarkTheme);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

        Spinner languages = findViewById(R.id.languageDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_entries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languages.setAdapter(adapter);
        languages.setOnItemSelectedListener(this);
    }

    private void toggleTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // TODO idioma escolhido
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO mostrar idioma default (está a mostrar o primeiro idioma da lista agora)
    }

    public void distanceRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.kmButton:
                if (checked)
                    // TODO distância em km
                break;
            case R.id.mileButton:
                if (checked)
                    // TODO distância em milhas
                break;
        }
    }

    public void volumeRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.lButton:
                if (checked)
                    // TODO volume em litros
                    break;
            case R.id.galButton:
                if (checked)
                    // TODO volume em gallons
                    break;
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        return;
    }
}