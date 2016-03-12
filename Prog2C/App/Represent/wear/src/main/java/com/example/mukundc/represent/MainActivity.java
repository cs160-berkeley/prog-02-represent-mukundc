package com.example.mukundc.represent;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends FragmentActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart () {
        super.onStart();
        ArrayList<String> data2016Election = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("results.csv")));
            String line ;
            while ((line = reader.readLine()) != null) {
                data2016Election.add(line);
            }
        }
        catch (IOException e) {
        }







        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        Bundle extras = getIntent().getExtras();
        String data = "";
        if (extras != null) {
            data = extras.getString("DATA");
        }



        if (data.equals(""))  {
            setContentView(R.layout.initial_view);

        } else {

            String[] fragmentData = data.split("[;]");
            Log.d("Fragment 0", fragmentData[0]);
            Log.d("Fragment 1", fragmentData[1]);


            JSONArray representatives = new JSONArray();
            try {
                JSONObject obj = new JSONObject(fragmentData[0]);
                representatives = obj.getJSONArray("results");
            } catch (JSONException j) {

            }
            Log.d("Reps", representatives.toString());

            Log.d("Length", Integer.toString(representatives.length()));



            //Log.d("fragment Data", fragmentData.toString());
            final DotsPageIndicator mPageIndicator;
            final GridViewPager mViewPager;

            //Person[][] data = {
            //       { persons.get(0), persons.get(1), persons.get(2), persons.get(0)}};

            // Get UI references
            mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
            mViewPager = (GridViewPager) findViewById(R.id.pager);

            // Assigns an adapter to provide the content for this pager
            mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), representatives, fragmentData[1], this, data2016Election));
            mPageIndicator.setPager(mViewPager);

        }


    }



    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        JSONArray mData;
        String voteData;
        private final Context mContext;
        ArrayList<String> vote2016;

        private GridPagerAdapter(FragmentManager fm, JSONArray repData, String voteData, Context ctx, ArrayList<String> vote2016) {
            super(fm);
            mData = repData;
            mContext = ctx;
            this.voteData = voteData;
            this.vote2016 = vote2016;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            if (column == mData.length()) {
                Fragment f = MyVoteFragment.create(voteData);
                return f;
            }
            if (column == mData.length()+1) {
                Fragment f = My2016Fragment.create(voteData, vote2016);
                return f;
            }

            Fragment f = new Fragment();
            try {
                f = MyFragment.create((JSONObject) mData.get(column));
            } catch (JSONException j) {}

            return f;
        }



        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount(int row) {
            return mData.length() + 2;
        }

        /*
        @Override
        public final Drawable getFragmentBackground(int row, int column) {
            String background = "";
            try {
                JSONObject person = (JSONObject) mData.get(column);
                if (person.getString("party").equals("R")) {
                    background = "#FF0000";
                } else {
                    background = "#0000FF";
                }
                return  mContext.getDrawable(Color.parseColor(background));
            } catch (JSONException j) {
                ;
            }

        }*/

/*
        @Override
        public Drawable getBackgroundForPage(int row, int col) {
            return mContext.getDrawable(mData[row][col].photo);
        }
*/
    }

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    int count = 0;
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        HashSet<Boolean> h = new HashSet<Boolean>();
        private static final int MIN_DIRECTION_CHANGE = 2;
        private static final int MAX_PAUSE_BETWEEN_DIRECTION_CHANGE = 3000;
        private static final int MAX_TOTAL_DIRECTION_OF_SHAKE = 3000;
        /** Time when the gesture started. */
        private long mFirstDirectionChangeTime = 0;
        /** Time when the last movement started. */
        private long mLastDirectionChangeTime;
        private int mDirectionChangeCount = 0;
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 50)
                h.add(Boolean.TRUE);
            else
                h.add(Boolean.FALSE);
            if (h.contains(Boolean.TRUE) && mAccel < 1) {
                //mDirectionChangeCount++;
                //if (mDirectionChangeCount > 1) {
                //   mDirectionChangeCount = 0;
                    h.clear();
                    int randomIndex = 0;
                    ArrayList<String> options = new ArrayList<String>();
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("us_postal_codes.csv")));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            //Log.d("L", "IN here reading the csv");
                            options.add(line);
                        }
                        randomIndex = (int) (Math.random() * options.size());
                        Log.d("Options size", Integer.toString(options.size()));
                        Log.d("Random Index", Integer.toString(randomIndex));
                    } catch (IOException e) {

                    }

                    String randomLocation = options.get(randomIndex);
                    Double latitude = Double.parseDouble(randomLocation.split("[,]")[0]);
                    Double longitude = Double.parseDouble(randomLocation.split("[,]")[1]);
                    Log.d("L", "Latitude from shake: " + latitude);
                    Log.d("L", "Longitude from shake: " + longitude);

                    //Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    //ComponentName cn = intent.getComponent();
                    //Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable("ZIPCODE", validZipCodes[randInt]);
                    //mainIntent.putExtras(bundle);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(mainIntent);
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("RANDOM", latitude.toString() + "," + longitude.toString());
                    startService(sendIntent);
                    MainActivity.this.finish();
                //}
            }
        }


        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}




