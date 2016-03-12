package com.example.mukundc.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;

/**
 * Created by joleary on 2/19/16.
 */
public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    /*
    public String addInformation(JSONObject person) {
        String result = "";
        try {
            String t = "";
            String p  = "";
            if (person.getString("chamber").equals("house")) {
                t = "Representative";
            } else {
                t = "Senator";
            }
            if (person.getString("party").equals("R")) {
                p = "Republican";
            } else {
                p = "Democrat";
            }
            result = result + p + "," + t + "," + person.getString("first_name") + " " + person.getString("last_name");
            result = result + "," + person.getString("term_start") + " to " + person.getString("term_end");
            result = result + "," + person.getString("bioguide_id");
        } catch (JSONException e) {

        }
        return result;
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Log.d("T", "IN THE PHONE TO WATCH SERVICE");
        String temp = "";
        JSONArray representatives = new JSONArray();
        Bundle extras = intent.getExtras();
        final String county = extras.getString("COUNTY");
        final String state = extras.getString("STATE");
        final Double obama = extras.getDouble("OBAMA");
        final Double romney = extras.getDouble("ROMNEY");


        final String d = extras.getString("DATA");
        try {
            //JSONObject obj = new JSONObject(d);
            //representatives = obj.getJSONArray("results");
            /*for(int i = 0; i < representatives.length(); i++){
                JSONObject person = (JSONObject) representatives.get(i);
                temp += addInformation(person);
                temp += ";";
            }*/
            temp = temp + d + ";";
            temp = temp + county + ",";
            temp = temp + state + ",";
            temp = temp + obama + ",";
            temp = temp + romney;
        } catch (Exception e) {

        }
        final String data = temp;

        // Send the message with the cat name
        new Thread(new Runnable() {

            @Override
            public void run() {
                //first, connect to the apiclient
                mApiClient.connect();
                //now that you're connected, send a massage with the cat name
                sendMessage("/data", data);
            }
        }).start();

        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBiner
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {
        //one way to send message: start a new thread and call .await()
        //see watchtophoneservice for another way to send a message
        final Service _this = this;

        new Thread( new Runnable() {
            @Override
            public void run() {

                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                    _this.stopSelf();

                }
            }
        }).start();

    }

}