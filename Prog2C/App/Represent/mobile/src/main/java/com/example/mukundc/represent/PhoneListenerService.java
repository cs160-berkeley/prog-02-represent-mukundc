package com.example.mukundc.represent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String SHAKE = "/send_shake";
    private static final String DETAILED = "/send_detailed";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(DETAILED) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String data = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Log.d("info in phone list", data);

            JSONObject person;
            String bioguide_id = "";
            try {
                person = new JSONObject(data);
                bioguide_id = person.getString("bioguide_id");

            } catch (JSONException e) {}

            String committees = "";
            String bills = "";
            try {
                String url = "http://congress.api.sunlightfoundation.com/committees?member_ids=" + bioguide_id + "&apikey=d99ae20504cc40bfa59036503e2b5326";
                committees = (new DownloadWebpageTask()).execute(url).get();
                String billurl = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide_id + "&apikey=d99ae20504cc40bfa59036503e2b5326";
                bills = (new DownloadWebpageTask()).execute(billurl).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, DetailedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("DATA", data);
            bundle.putString("COMMITTEES", committees);
            bundle.putString("BILLS", bills);
            intent.putExtras(bundle);
            startActivity(intent);

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        }
        else if (messageEvent.getPath().equalsIgnoreCase(SHAKE)){
            String randomData = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Double latitude = Double.parseDouble(randomData.split("[,]")[0]);
            Double longitude = Double.parseDouble(randomData.split("[,]")[1]);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("LATITUDE", latitude);
            intent.putExtra("LONGITUDE", longitude);
            //you need to add this flag since you're starting a new activity from a service
            startActivity(intent);
        }
        else {
            super.onMessageReceived( messageEvent );
        }

    }
}