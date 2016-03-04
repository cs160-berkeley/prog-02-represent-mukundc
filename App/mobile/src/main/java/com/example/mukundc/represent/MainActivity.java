package com.example.mukundc.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> zipcodes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipcodes.add("94704");
        zipcodes.add("33134");
        zipcodes.add("54642");
    }

    public void moveToCongressionalCurrentLocation(View view) {
        Intent intent = new Intent(this, CongressionalActivity.class);
        intent.putExtra("ZIPCODE", zipcodes.get(0));
        startActivity(intent);
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("ZIPCODE", zipcodes.get(0));
        startService(sendIntent);
    }

    public void moveToCongressionalZipCode(View view) {
        Intent intent = new Intent(this, CongressionalActivity.class);
        EditText editText = (EditText) findViewById(R.id.zipLocation);
        String location = "";
        if (editText.getText() == null) {
            location = "94704";
        }
        else {
            location = editText.getText().toString();
        }
        Log.d("T", location);
        intent.putExtra("ZIPCODE", location);
        startActivity(intent);
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("ZIPCODE", location);
        startService(sendIntent);
    }
}
