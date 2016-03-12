package com.example.mukundc.represent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "NwUtF2MGuGoazHFHfqrQuiFH6";
    private static final String TWITTER_SECRET = "JxoUx5xiQJQw5plGHFt0HTFCNaxF3Rd8lG13PfbVcIkk0YFHQZ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("T", "In main activity phone with extras");
            moveToCongressionalCurrentLocation(extras.getDouble("LATITUDE"), extras.getDouble("LONGITUDE"));
        }

        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ImageView zipButton =  (ImageView) findViewById(R.id.zipCodeButton);
        zipButton.bringToFront();
        zipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveToCongressionalZipCode();
            }
        });

        Button locationButton = (Button) findViewById(R.id.currentLocationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGoogleApiClient.connect();
            }
        });
    }

    /*

    private static Asset createAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }

    private void sendImageToWear() {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.marco);
                    Asset asset = createAssetFromBitmap(bitmap);
                    PutDataMapRequest dataMap = PutDataMapRequest.create("/image");
                    dataMap.getDataMap().putAsset("profileImage", asset);
                    PutDataRequest request = dataMap.asPutDataRequest();
                    PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                            .putDataItem(mGoogleApiClient, request);;
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }
        });
        thread.start();

        Log.d("TAG", "Image send");
    }
    */

    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Double latitude = 0.0;
        Double longitude = 0.0;
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
        Log.d("Current Latitude", Double.toString(latitude));
        Log.d("Current Longitude", Double.toString(longitude));

        moveToCongressionalCurrentLocation(latitude, longitude);
        //sendImageToWear();
    }

    public void moveToCongressionalCurrentLocation(Double latitude, Double longitude) {
        Intent intent = new Intent(this, CongressionalActivity.class);
        String representatives = getRepresentativesFromLocation(latitude, longitude);
        if (representatives == null) {
            Toast.makeText(this, "No network connection available. Couldn't retrieve data", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("Representatives cur", representatives);

        try {
            JSONObject obj = new JSONObject(representatives);
            if (obj.getJSONArray("results").length() == 0) {
                Toast.makeText(this, "Your location is not valid", Toast.LENGTH_SHORT).show();
                //mGoogleApiClient.disconnect();
                return;
            }
        }
        catch (JSONException j) {}


        intent.putExtra("DATA", representatives);
        intent.putExtra("ZIPCODE", "none");
        startActivity(intent);
        moveToCongressionalCurrentLocationWatch(latitude, longitude, representatives);
    }

    public void moveToCongressionalCurrentLocationWatch(Double latitude, Double longitude, String representatives) {
        String s = getCountyFromLocation(latitude, longitude);
        if (s == null) {
            Toast.makeText(this, "Cannot retrieve county information. Connect to Wifi.", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONArray a;
        String county = "";
        String state = "";
        try {
            a =  ((JSONObject)(new JSONObject(s).getJSONArray("results").get(0))).getJSONArray("address_components");
            //Log.d("A", a.toString());
            int i = 0;
            while (i < a.length()) {
                JSONObject j = (JSONObject) a.get(i);
                //Log.d("J", j.toString());
                JSONArray typesArray = (JSONArray) j.get("types");
                for (int k = 0; k < typesArray.length(); k++) {
                    String temp = (String) typesArray.get(k);
                    if (temp.equals("administrative_area_level_2")) {
                        county = j.getString("long_name");
                    }
                }
                for (int k = 0; k < typesArray.length(); k++) {
                    String temp = (String) typesArray.get(k);
                    if (temp.equals("administrative_area_level_1")) {
                        state = j.getString("short_name");
                    }
                }

                if ((!county.equals("")) && (!state.equals(""))) {
                    break;
                }
                i+=1;
            }

        } catch (JSONException j) {

        }

        Log.d("T", "Printing the county and state");
        Log.d("T", county);
        Log.d("T", state);

        JSONArray jsonArrayVotingData;
        Double obama = -1.0;
        Double romney = -1.0;

        try {
            InputStream stream = getAssets().open("election-county-2012.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String jsonStringVotingData = new String(buffer, "UTF-8");
            jsonArrayVotingData = new JSONArray(jsonStringVotingData);
            Log.d("voting data", jsonArrayVotingData.toString());


            for(int i = 0; i < jsonArrayVotingData.length(); i++){
                JSONObject j = (JSONObject) jsonArrayVotingData.get(i);
                if(county.toLowerCase().contains(j.getString("county-name").toLowerCase()) && state.toLowerCase().equals(j.getString("state-postal").toLowerCase()) ) {
                    Log.d("Percentage", "found it! obama-percentage" + j.get("obama-percentage") + " romney-percentage " + j.get("romney-percentage"));
                    obama = j.getDouble("obama-percentage");
                    romney = j.getDouble("romney-percentage");
                    break;
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("DATA", representatives);
        sendIntent.putExtra("COUNTY", county);
        sendIntent.putExtra("STATE", state);
        sendIntent.putExtra("OBAMA", obama);
        sendIntent.putExtra("ROMNEY", romney);
        startService(sendIntent);
    }


    public void moveToCongressionalZipCode() {
        Intent intent = new Intent(this, CongressionalActivity.class);
        EditText editText = (EditText) findViewById(R.id.zipLocation);
        String zipcode;
        if (editText.getText() == null) {
            Toast.makeText(this, "Enter a valid zipcode", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            zipcode = editText.getText().toString();
        }

        /*boolean isValid = isValidZipCode(zipcode);
        Log.d("boolean valid", Boolean.toString(isValid));
        if (!isValid) {
            Toast.makeText(this, "Enter a valid zipcode", Toast.LENGTH_SHORT).show();
            return;
        }*/

        String representatives = getRepresentativesFromZipCode(zipcode);
        if (representatives == null) {
            Toast.makeText(this, "No network connection available. Couldn't retrieve data", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("Representatives", representatives);
        try {
            JSONObject obj = new JSONObject(representatives);
            if (obj.getJSONArray("results").length() == 0) {
                Toast.makeText(this, "Enter a valid zipcode", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        catch (JSONException j) {}


        intent.putExtra("DATA", representatives);
        intent.putExtra("ZIPCODE", zipcode);
        startActivity(intent);


        String temp = getLocationFromZipCode(zipcode);
        if (temp == null) {
            Toast.makeText(this, "Enter a valid zipcode", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject a, b;
        Double latitude = 0.0;
        Double longitude = 0.0;
        try {
            a =  ((JSONObject)(new JSONObject(temp).getJSONArray("results").get(0))).getJSONObject("geometry");
            b = a.getJSONObject("location");
            Log.d("A", a.toString());
            Log.d("B", b.toString());
            latitude = b.getDouble("lat");
            longitude = b.getDouble("lng");
            Log.d("LAT", latitude.toString());
            Log.d("LONG", longitude.toString());

        } catch (JSONException j) {

        }

        moveToCongressionalCurrentLocationWatch(latitude, longitude, representatives);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently
        // ...
        if (result.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                result.startResolutionForResult(this, 9000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("T", "Location services connection failed with code " + result.getErrorCode());
        }
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }


    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public String getRepresentativesFromLocation(Double latitude, Double longitude) {
        String url = "http://congress.api.sunlightfoundation.com/legislators/locate?latitude=" + latitude + "&longitude=" + longitude + "&apikey=d99ae20504cc40bfa59036503e2b5326";
        return getRepresentatives(url);
    }

    public String getRepresentativesFromZipCode(String zipcode) {
        String url = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipcode + "&apikey=d99ae20504cc40bfa59036503e2b5326";
        return getRepresentatives(url);
    }

    public String getRepresentatives(String stringUrl) {
        String result = "";
        JSONArray resultArray = new JSONArray();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                result = (new DownloadWebpageTask()).execute(stringUrl).get();
            }
            catch  (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
        if (result != null){
            Log.d("T", result);
        }
        return result;
    }

    public String getLocationFromZipCode(String zipcode) {
        String stringUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&key=AIzaSyC6pF52gbbxVjkVg7_fexyRHpyGqBxddcQ";

        String result = "";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                result = (new DownloadWebpageTask()).execute(stringUrl).get();
            }
            catch  (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
        //Log.d("T", result);
        return result;
    }

    public String getCountyFromLocation(Double latitude, Double longitude) {
        String stringUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyC6pF52gbbxVjkVg7_fexyRHpyGqBxddcQ";
        String result = "";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                result = (new DownloadWebpageTask()).execute(stringUrl).get();
            }
            catch  (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
        //Log.d("T", result);
        return result;
    }

    public boolean isValidZipCode(String zipcode) {

        /*
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("us_postal_codes.csv")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                int zipcodeInt = Integer.parseInt(zipcode);
                try {
                    int lineZipcodeInt = Integer.parseInt(line.split("[,]")[0]);
                    if (lineZipcodeInt == zipcodeInt) {
                        // found it
                        Log.d("Zipcode", "Zipcode found!");
                        return true;
                    }
                }
                catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        catch (IOException e) {
        }
        return false;
        */
        return false;
    }
}


