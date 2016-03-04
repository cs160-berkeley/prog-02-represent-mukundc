package com.example.mukundc.represent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class CongressionalActivity extends AppCompatActivity {

    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);
        persons = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        String zipcode = "";
        if (extras != null) {
            zipcode = extras.getString("ZIPCODE");
        }

        //ADDING dummy data here for now

        if (Integer.parseInt(zipcode.substring(0,1)) < 4) {
            persons.add(new Person("Marco Rubio", "Senator", R.mipmap.marco, "Republican", "Vote for me to make America great again!", "marcorubio@gmail.com", "www.marcorubio.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Gus Bilikaris", "Senator", R.mipmap.gus, "Republican", "Climate change is a priority!", "gusbilikaris@gmail.com", "www.guss.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Bill Nelson", "Representative", R.mipmap.bill, "Democrat", "Working on a new healthcare bill.", "bill@gmail.com", "www.bill.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
        }
        else if (Integer.parseInt(zipcode.substring(0,1)) > 6) {
            persons.add(new Person("Barbara Boxer", "Senator", R.mipmap.barbara, "Democrat", "I will represent the needs of California!", "barbara@gmail.com", "www.barbara.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Diane Feinstein", "Senator", R.mipmap.diane, "Democrat", "Thanks to everyone for voting!", "diane@gmail.com", "www.diane.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Zoe Lofgren", "Representative", R.mipmap.zoe, "Democrat", "Working on improved housing in San Jose", "zoelofgren@gmail.com", "www.zoelofgren.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
        }
        else {
            persons.add(new Person("Kirsten Gillibrand", "Senator", R.mipmap.kirsten, "Democrat", "We will be enforcing stricter regulations on Uber in New York", "kirsten@gmail.com", "www.kirsten.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Chuck Schumer", "Senator", R.mipmap.chuck, "Democrat", "Great speech by Obama last night", "chuck@gmail.com", "www.chuck.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
            persons.add(new Person("Lee Zeldin", "Representative", R.mipmap.lee, "Republican", "Working on a new city bill.", "leezeldinl@gmail.com", "www.leezeldin.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee"));
        }

        final CardView card1 = (CardView) findViewById(R.id.cv1);
        final TextView title1 = (TextView) findViewById(R.id.person_title1);
        final TextView name1 = (TextView) findViewById(R.id.person_name1);
        final TextView party1 = (TextView) findViewById(R.id.person_party1);
        final TextView contact1 = (TextView) findViewById(R.id.person_contact1);
        final ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        final TextView tweet1 = (TextView) findViewById(R.id.latestTweet1);
        image1.setImageDrawable(getDrawable(persons.get(0).photo));
        title1.setText(persons.get(0).title);
        name1.setText(persons.get(0).name);
        party1.setText(" - " + persons.get(0).party);
        contact1.setText(persons.get(0).email + ", " + persons.get(0).website);
        tweet1.setText("Latest Tweet: " + persons.get(0).tweet);
        if (persons.get(0).party.equals("Republican")) {
            card1.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }
        else {
            card1.setCardBackgroundColor(Color.parseColor("#0000FF"));
        }

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PERSON", persons.get(0));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final CardView card2 = (CardView) findViewById(R.id.cv2);
        final TextView title2 = (TextView) findViewById(R.id.person_title2);
        final TextView name2 = (TextView) findViewById(R.id.person_name2);
        final TextView party2 = (TextView) findViewById(R.id.person_party2);
        final TextView contact2 = (TextView) findViewById(R.id.person_contact2);
        final ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        final TextView tweet2 = (TextView) findViewById(R.id.latestTweet2);
        image2.setImageDrawable(getDrawable(persons.get(1).photo));
        title2.setText(persons.get(1).title);
        name2.setText(persons.get(1).name);
        party2.setText(" - " + persons.get(1).party);
        contact2.setText(persons.get(1).email + ", " + persons.get(1).website);
        tweet2.setText("Latest Tweet: " + persons.get(1).tweet);
        if (persons.get(1).party.equals("Republican")) {
            card2.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }
        else {
            card2.setCardBackgroundColor(Color.parseColor("#0000FF"));
        }

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PERSON", persons.get(1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final CardView card3 = (CardView) findViewById(R.id.cv3);
        final TextView title3 = (TextView) findViewById(R.id.person_title3);
        final TextView name3 = (TextView) findViewById(R.id.person_name3);
        final TextView party3 = (TextView) findViewById(R.id.person_party3);
        final TextView contact3 = (TextView) findViewById(R.id.person_contact3);
        final ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        final TextView tweet3 = (TextView) findViewById(R.id.latestTweet3);
        image3.setImageDrawable(getDrawable(persons.get(2).photo));
        title3.setText(persons.get(2).title);
        name3.setText(persons.get(2).name);
        party3.setText(" - " + persons.get(2).party);
        contact3.setText(persons.get(2).email + ", " + persons.get(2).website);
        tweet3.setText("Latest Tweet: " + persons.get(2).tweet);
        if (persons.get(2).party.equals("Republican")) {
            card3.setCardBackgroundColor(Color.parseColor("#FF0000"));
        }
        else {
            card3.setCardBackgroundColor(Color.parseColor("#0000FF"));
        }

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getBaseContext(), DetailedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PERSON", persons.get(2));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });






    }

}

class Person implements Serializable{
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

    Person(String name, String title, int photo, String party, String tweet, String email, String website) {
        this.name = name;
        this.title = title;
        this.photo = photo;
        this.party = party;
        this.tweet = tweet;
        this.email = email;
        this.website = website;
    }

    Person(String name, String title, int photo, String party, String tweet, String email, String website, String termEnd, String bills, String committees) {
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
    }
}
