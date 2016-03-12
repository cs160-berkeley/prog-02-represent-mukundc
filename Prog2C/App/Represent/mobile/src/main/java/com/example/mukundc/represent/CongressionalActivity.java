package com.example.mukundc.represent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CongressionalActivity extends AppCompatActivity {

    ArrayList<Person> persons;

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
        setContentView(R.layout.activity_congressional);
        persons = new ArrayList<>();
        Bundle extras = getIntent().getExtras();

        JSONArray representatives = new JSONArray();

        String result = extras.getString("DATA");
        /*
        if (zip.equals("none")) {
            getActionBar().setTitle("Congressman for Zipcode - " + zip);
        } else {
            getActionBar().setTitle("Congressman for Your Location");
        }*/
        try {
            JSONObject obj = new JSONObject(result);
            representatives = obj.getJSONArray("results");
            Log.d("T", "Set the result array");
        } catch (Exception e) {}


        try {
            final JSONObject person = (JSONObject) representatives.get(0);
            final String twitter_name = person.getString("twitter_id");

            final CardView card1 = (CardView) findViewById(R.id.cv1);
            final TextView title1 = (TextView) findViewById(R.id.person_title1);
            final TextView name1 = (TextView) findViewById(R.id.person_name1);
            final TextView party1 = (TextView) findViewById(R.id.person_party1);
            final TextView website1 = (TextView) findViewById(R.id.person_website1);
            final TextView email1 = (TextView) findViewById(R.id.person_email1);

            final ImageView image1 = (ImageView) findViewById(R.id.imageView1);
            addInformation(person, card1, title1, name1, party1, website1, email1, image1);
            showTweet(twitter_name, 1);
            ImageView button1 = (ImageView) findViewById(R.id.button1);
            button1.bringToFront();
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   moveToDetailed(person);
                }
            });
        } catch (JSONException e) {

        }


        try {
            final JSONObject second_person = (JSONObject) representatives.get(1);
            final String second_twitter_name = second_person.getString("twitter_id");

            final CardView card2 = (CardView) findViewById(R.id.cv2);
            final TextView title2 = (TextView) findViewById(R.id.person_title2);
            final TextView name2 = (TextView) findViewById(R.id.person_name2);
            final TextView party2 = (TextView) findViewById(R.id.person_party2);
            final TextView website2 = (TextView) findViewById(R.id.person_website2);
            final TextView email2 = (TextView) findViewById(R.id.person_email2);

            final ImageView image2 = (ImageView) findViewById(R.id.imageView2);
            addInformation(second_person, card2, title2, name2, party2, website2, email2, image2);
            showTweet(second_twitter_name, 2);
            ImageView button2 = (ImageView) findViewById(R.id.button2);
            button2.bringToFront();
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    moveToDetailed(second_person);
                }
            });

        } catch (JSONException e) {

        }


        try {
            final JSONObject third_person = (JSONObject) representatives.get(2);
            final String third_twitter_name = third_person.getString("twitter_id");

            final CardView card3 = (CardView) findViewById(R.id.cv3);
            final TextView title3 = (TextView) findViewById(R.id.person_title3);
            final TextView name3 = (TextView) findViewById(R.id.person_name3);
            final TextView party3 = (TextView) findViewById(R.id.person_party3);
            final TextView website3 = (TextView) findViewById(R.id.person_website3);
            final TextView email3 = (TextView) findViewById(R.id.person_email3);
            final ImageView image3 = (ImageView) findViewById(R.id.imageView3);

            addInformation(third_person, card3, title3, name3, party3, website3, email3, image3);
            showTweet(third_twitter_name, 3);
            ImageView button3 = (ImageView) findViewById(R.id.button3);
            button3.bringToFront();
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    moveToDetailed(third_person);
                }
            });
        } catch (JSONException e) {

        }


        try {
            if (representatives.length() > 3) {
                final JSONObject fourth_person = (JSONObject) representatives.get(3);
                final String fourth_twitter_name = fourth_person.getString("twitter_id");

                final CardView card4 = (CardView) findViewById(R.id.cv4);
                card4.setVisibility(View.VISIBLE);
                final TextView title4 = (TextView) findViewById(R.id.person_title4);
                final TextView name4 = (TextView) findViewById(R.id.person_name4);
                final TextView party4 = (TextView) findViewById(R.id.person_party4);
                final TextView website4 = (TextView) findViewById(R.id.person_website4);
                final TextView email4 = (TextView) findViewById(R.id.person_email4);
                ImageView image4 = (ImageView) findViewById(R.id.imageView4);

                addInformation(fourth_person, card4, title4, name4, party4, website4, email4, image4);
                showTweet(fourth_twitter_name, 4);
                ImageView button4 = (ImageView) findViewById(R.id.button4);
                button4.bringToFront();
                button4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        moveToDetailed(fourth_person);
                    }
                });
            }
        } catch (JSONException e) {

        }

    }



    public void addInformation(JSONObject person, CardView card, TextView title, TextView name, TextView party, TextView website, TextView email, ImageView image) {
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
            new DownloadImageTask(image).execute("https://theunitedstates.io/images/congress/225x275/" + person.getString("bioguide_id") + ".jpg");
            title.setText(t);
            name.setText(person.getString("first_name") + " " + person.getString("last_name"));
            //textview.setPaintFlags(textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            party.setText(p);
            website.setText(person.getString("website"));
            email.setText(person.getString("oc_email"));

            //tweet.setText("Latest Tweet: " + "This is a sample tweet until I integrate Twitter");
            if (p.equals("Republican")) {
                card.setCardBackgroundColor(Color.parseColor("#DB5656"));
            } else {
                card.setCardBackgroundColor(Color.parseColor("#466398"));
            }
        } catch (JSONException e) {

        }

    }

    public void moveToDetailed(JSONObject person) {
        // Perform action on click
        String committees = "";
        String bills = "";

        try {
            String bioguide_id = person.getString("bioguide_id");
            String url = "http://congress.api.sunlightfoundation.com/committees?subcommittee=false&member_ids=" + bioguide_id + "&apikey=d99ae20504cc40bfa59036503e2b5326";
            committees = (new DownloadWebpageTask()).execute(url).get();
            String billurl = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=" + bioguide_id + "&apikey=d99ae20504cc40bfa59036503e2b5326";
            bills = (new DownloadWebpageTask()).execute(billurl).get();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("DATA", person.toString());
        bundle.putString("COMMITTEES", committees);
        bundle.putString("BILLS", bills);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showTweet(String n, final int num) {
        final String name = n;
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result result) {
                final AppSession guestAppSession = (AppSession) result.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(guestAppSession);
                StatusesService statusesService = twitterApiClient.getStatusesService();
                Log.d("T", "here");
                statusesService.userTimeline(null, name, null, (long) 1, null, null, null, null, null, new Callback<List<Tweet>>() {
                    final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();

                    @Override
                    public void success(Result<List<Tweet>> result) {
                        Log.d("T", "Succeeded in user timeline");
                        final CompactTweetView tweetView = new CompactTweetView(CongressionalActivity.this, result.data.get(0), R.style.tw__TweetDarkStyle);
                        tweetView.setGravity(Gravity.BOTTOM);
                        LinearLayout myLayout;
                        if (num == 1) {
                            myLayout = (LinearLayout) findViewById(R.id.tweetlinear1);
                        } else if (num == 2) {
                            myLayout = (LinearLayout) findViewById(R.id.tweetlinear2);
                        } else if (num == 3) {
                            myLayout = (LinearLayout) findViewById(R.id.tweetlinear3);
                        } else {
                            myLayout = (LinearLayout) findViewById(R.id.tweetlinear4);
                        }
                        //RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        //ViewGroup.LayoutParams.WRAP_CONTENT);

                        //p.addRule(RelativeLayout.BELOW, R.id.cv1);
                        //p.addRule(RelativeLayout.ABOVE, R.id.cv2);
                        //tweetView.setLayoutParams(p);
                        myLayout.removeAllViews();
                        myLayout.addView(tweetView);
                        myLayout.setVisibility(View.VISIBLE);
                        //R.layout.activity_congressionalcv.addView(tweetView);
                                    /*
                                    tweetView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            tweetView.setVisibility(View.GONE);
                                        }
                                    }, 4000);
                                    Log.d("T", "Succeeded in tweetView");*/
                    }

                    public void failure(TwitterException exception) {
                        //Do something on failure
                        Log.d("f", "Failing to load");
                        exception.printStackTrace();
                    }

                });
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(CongressionalActivity.this, "Failed to log in as guest", Toast.LENGTH_LONG).show();
            }
        });
    }










        /*
        // Get the x and y position after the button is draw on screen
        // (It's important to note that we can't get the position in the onCreate(),
        // because at that stage most probably the view isn't drawn yet, so it will return (0, 0))
        @Override
        public void onWindowFocusChanged(boolean hasFocus) {

            int[] location = new int[2];
            Button button = (Button) findViewById(R.id.button1);

            // Get the x, y location and store it in the location[] array
            // location[0] = x, location[1] = y.
            button.getLocationOnScreen(location);

            //Initialize the Point with x, and y positions
            p = new Point();
            p.x = location[0];
            p.y = location[1];
        }

        // The method that displays the popup.
        private void showPopup(final Activity context, Point p, Tweet tweet) {
            int popupWidth = 200;
            int popupHeight = 150;

            // Inflate the popup_layout.xml
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.tweet_popup, null);

            CompactTweetView tweetView = new CompactTweetView(CongressionalActivity.this, tweet, R.style.tw__TweetDarkWithActionsStyle);

            Context mContext = getApplicationContext();
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.popup);
            // Create a new LinearLayout
            LinearLayout newLinear = new LinearLayout(mContext);
            newLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // first , I add button to the LinearLayout
            newLinear.addView(tweetView);
            // Then, I add layout to the inflated layout
            mainLayout.addView(newLinear);

            // Creating the PopupWindow
            final PopupWindow popup = new PopupWindow(context);
            popup.setContentView(layout);
            popup.setWidth(popupWidth);
            popup.setHeight(popupHeight);
            popup.setFocusable(true);

            // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
            int OFFSET_X = 30;
            int OFFSET_Y = 30;

            // Clear the default translucent background
            popup.setBackgroundDrawable(new BitmapDrawable());

            // Displaying the popup at the specified location, + offsets.
            popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

            // Getting a reference to Close button, and close the popup when clicked.
            Button close = (Button) layout.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
        }
        */
}

