package com.example.mukundc.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.IntentCompat;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends FragmentActivity {
    ArrayList<Person> persons;
    Vote v = null;
    String zipcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart () {
        super.onStart();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;


        Bundle extras = getIntent().getExtras();
        zipcode = "94539";
        if (extras != null) {
            zipcode = extras.getString("ZIPCODE");
        }

        persons = new ArrayList();
        if (Integer.parseInt(zipcode.substring(0,1)) < 4) {
            v = new Vote("Dade County", "Obama: 60%", "Romney: 40%", "Florida");
            persons.add(new Person("Marco Rubio", "Senator", R.mipmap.marco, "Republican", "Vote for me to make America great again!", "marcorubio@gmail.com", "www.marcorubio.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Gus Bilikaris", "Senator", R.mipmap.gus, "Republican", "Climate change is a priority!", "gusbilikaris@gmail.com", "www.guss.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Bill Nelson", "Representative", R.mipmap.bill, "Democrat", "Working on a new healthcare bill.", "bill@gmail.com", "www.bill.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
        }
        else if (Integer.parseInt(zipcode.substring(0,1)) > 6) {
            v = new Vote("San Jose County", "Obama: 90%", "Romney: 10%", "California");
            persons.add(new Person("Barbara Boxer", "Senator", R.mipmap.barbara, "Democrat", "I will represent the needs of California!", "barbara@gmail.com", "www.barbara.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Diane Feinstein", "Senator", R.mipmap.diane, "Democrat", "Thanks to everyone for voting!", "diane@gmail.com", "www.diane.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Zoe Lofgren", "Representative", R.mipmap.zoe, "Democrat", "Working on improved housing in San Jose", "zoelofgren@gmail.com", "www.zoelofgren.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
        }
        else {
            v = new Vote("Manhattan County", "Obama: 70%", "Romney: 30%", "New York");
            persons.add(new Person("Kirsten Gillibrand", "Senator", R.mipmap.kirsten, "Democrat", "We will be enforcing stricter regulations on Uber in New York", "kirsten@gmail.com", "www.kirsten.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Chuck Schumer", "Senator", R.mipmap.chuck, "Democrat", "Great speech by Obama last night", "chuck@gmail.com", "www.chuck.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
            persons.add(new Person("Lee Zeldin", "Representative", R.mipmap.lee, "Republican", "Working on a new city bill.", "leezeldinl@gmail.com", "www.leezeldin.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee", zipcode));
        }

        final DotsPageIndicator mPageIndicator;
        final GridViewPager mViewPager;


        Person[][] data = {
                { persons.get(0), persons.get(1), persons.get(2), persons.get(0)}};



        // Get UI references
        mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        mViewPager = (GridViewPager) findViewById(R.id.pager);

        // Assigns an adapter to provide the content for this pager
        mViewPager.setAdapter(new GridPagerAdapter(getFragmentManager(), data, this, v));
        mPageIndicator.setPager(mViewPager);
    }



    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {

        Person[][] mData;
        Vote vData;
        private final Context mContext;

        private GridPagerAdapter(FragmentManager fm, Person[][] data, Context ctx, Vote v) {
            super(fm);
            mData = data;
            vData = v;
            mContext = ctx;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            if (column == 3) {
                Fragment f = MyVoteFragment.create("2012 Presidential Vote: " + vData.county + ", " + vData.state, vData.obama + ", " + vData.romney, 0);
                return f;
            }
            Fragment f = MyFragment.create(mData[row][column].name, mData[row][column].party, 0, mData[row][column]);
            return f;
        }

        @Override
        public int getRowCount() {
            return mData.length;
        }

        @Override
        public int getColumnCount(int row) {
            return mData[row].length;
        }

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
                    int currentZipCodeStart = Integer.parseInt(zipcode.substring(0,1));
                    int currentBucket = 0;
                    if (currentZipCodeStart < 4) currentBucket = 0;
                    else if (currentZipCodeStart > 6) currentBucket = 1;
                    else currentBucket = 2;

                    int randInt = randInt(0, 2);
                    while (currentBucket == randInt) {
                        randInt = randInt(0, 2);
                    }
                    String[] validZipCodes = {"34123", "94539", "51237"};
                /*
                    Context context = getApplicationContext();
                    CharSequence text = "Hello toast!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, Float.toString(mAccel), duration);
                    toast.show();
                */
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    ComponentName cn = intent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ZIPCODE", validZipCodes[randInt]);
                    mainIntent.putExtras(bundle);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mainIntent);
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    sendIntent.putExtra("ZIPCODE", validZipCodes[randInt]);
                    startService(sendIntent);
                    MainActivity.this.finish();
                //}
            }
        }

        public int randInt(int min, int max) {

            // Usually this can be a field rather than a method variable
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = rand.nextInt((max - min) + 1) + min;

            return randomNum;
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

class Vote {
    String county;
    String obama;
    String romney;
    String state;

    Vote(String a, String b, String c, String d) {
        county = a;
        obama = b;
        romney = c;
        state = d;
    }
}

class Person implements Serializable {
    String name;
    String title;
    int photo;
    String party;
    String tweet;
    String email;
    String website;

    String termEnd;
    String bills;
    String committees;

    String zipcode;

    Person(String name, String title, int photo, String party, String tweet, String email, String website) {
        this.name = name;
        this.title = title;
        this.photo = photo;
        this.party = party;
        this.tweet = tweet;
        this.email = email;
        this.website = website;
    }

    Person(String name, String title, int photo, String party, String tweet, String email, String website, String termEnd, String bills, String committees, String zipcode) {
        this.name = name;
        this.title = title;
        this.photo = photo;
        this.party = party;
        this.tweet = tweet;
        this.email = email;
        this.website = website;
        this.termEnd = termEnd;
        this.bills = bills;
        this.committees = committees;
        this.zipcode = zipcode;
    }
}

