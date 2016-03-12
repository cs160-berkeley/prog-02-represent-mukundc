package com.example.mukundc.represent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedActivity extends AppCompatActivity {




    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Bundle extras = getIntent().getExtras();
        String committeesString = "";
        String billsString = "";
        JSONArray committeesJSONArray;
        JSONArray billsJSONArray;

        JSONObject person;
        String party = "";
        String title = "";
        String name = "";
        String term = "";
        String bioguide_id = "";

        String data = extras.getString("DATA");

        try {
            person = new JSONObject(data);
            if (person.getString("chamber").equals("house")) {
                title = "Representative";
            } else {
                title = "Senator";
            }
            if (person.getString("party").equals("R")) {
                party = "Republican";
            } else {
                party = "Democrat";
            }

            String[] termEndArray = person.getString("term_end").split("-");
            if (termEndArray.length == 3) {
                term = "Term ends " + termEndArray[1] + "-" + termEndArray[2] + "-" + termEndArray[0];
            }
            else {
                term = "Term ends " +  person.getString("term_end");
            }


            name = person.getString("first_name") + " " + person.getString("last_name");
            //term = person.getString("term_start") + " to " + person.getString("term_end");
            bioguide_id = person.getString("bioguide_id");

        } catch (JSONException e) {}



        try {
            committeesString = extras.getString("COMMITTEES");
            billsString = extras.getString("BILLS");
            committeesJSONArray = (new JSONObject(committeesString)).getJSONArray("results");
            billsJSONArray = (new JSONObject(billsString)).getJSONArray("results");
            int i = 0;
            String final_committees = "";
            String final_bills = "";
            while (i < committeesJSONArray.length() && i < 10) {
                String c = ((JSONObject) committeesJSONArray.get(i)).getString("name");
                final_committees += c;
                final_committees += "\n";
                i+=1;
            }

            i = 0;
            int numBills = 0;
            while (i < billsJSONArray.length() && numBills < 6) {
                if (!((JSONObject) billsJSONArray.get(i)).isNull("short_title")) {
                    String b = ((JSONObject) billsJSONArray.get(i)).getString("short_title");
                    String date = ((JSONObject) billsJSONArray.get(i)).getString("introduced_on");
                    String[] dateArray = date.split("-");
                    String correctDate = "";
                    if (dateArray.length == 3) {
                        correctDate = dateArray[1] + "-" + dateArray[2] + "-" + dateArray[0];
                    } else {
                        correctDate = date;
                    }
                    final_bills += correctDate + "\n";
                    final_bills = final_bills + b;
                    final_bills += "\n\n";
                    numBills+=1;
                }
                i+=1;
            }

            TextView t1 = (TextView) findViewById(R.id.nameDetailed);
            t1.setText(title + " " + name);
            TextView t2 = (TextView) findViewById(R.id.partyDetailed);
            t2.setText(party);
            CircleImageView i1 = (CircleImageView) findViewById(R.id.imageDetailed);
            if (party.equals("Republican")) {
                i1.setBorderColor(0xFFDB5656);
            } else {
                i1.setBorderColor(0xFF466398);

            }

            new DownloadImageTask(i1).execute("https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg");
            TextView t3 = (TextView) findViewById(R.id.termDetailed);
            t3.setText(term);
            TextView t4 = (TextView) findViewById(R.id.committeesDetailed);
            t4.setText(final_committees);
            TextView t5 = (TextView) findViewById(R.id.billsDetailed);
            t5.setText(final_bills);
        }
        catch (JSONException e) {
            Log.e("Detailed Act.: ", "unexpected JSON exception", e);
        }

    }
}
