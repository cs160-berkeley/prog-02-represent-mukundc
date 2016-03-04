package com.example.mukundc.represent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Bundle extras = getIntent().getExtras();
        Person p = null;
        String name = "";
        String zipcode = "";
        if (getIntent().hasExtra("PERSON"))
        {
            p = (Person) extras.getSerializable("PERSON");
        }
        else {
            name = extras.getString("NAME");
            zipcode = extras.getString("ZIPCODE");

            if (Integer.parseInt(zipcode.substring(0,1)) < 4) {
                if (name.equals("Marco Rubio"))
                    p = new Person("Marco Rubio", "Senator", R.mipmap.marco, "Republican", "Vote for me to make America great again!", "marcorubio@gmail.com", "www.marcorubio.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else if (name.equals("Gus Bilikaris"))
                    p = new Person("Gus Bilikaris", "Senator", R.mipmap.gus, "Republican", "Climate change is a priority!", "gusbilikaris@gmail.com", "www.guss.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else
                    p = new Person("Bill Nelson", "Representative", R.mipmap.bill, "Democrat", "Working on a new healthcare bill.", "bill@gmail.com", "www.bill.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
            }
            else if (Integer.parseInt(zipcode.substring(0,1)) > 6) {
                if (name.equals("Barbara Boxer"))
                    p = new Person("Barbara Boxer", "Senator", R.mipmap.barbara, "Democrat", "I will represent the needs of California!", "barbara@gmail.com", "www.barbara.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else if (name.equals("Diane Feinstein"))
                    p = new Person("Diane Feinstein", "Senator", R.mipmap.diane, "Democrat", "Thanks to everyone for voting!", "diane@gmail.com", "www.diane.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else
                    p = new Person("Zoe Lofgren", "Representative", R.mipmap.zoe, "Democrat", "Working on improved housing in San Jose", "zoelofgren@gmail.com", "www.zoelofgren.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
            }
            else {
                if (name.equals("Kirsten Gillibrand"))
                    p = new Person("Kirsten Gillibrand", "Senator", R.mipmap.kirsten, "Democrat", "We will be enforcing stricter regulations on Uber in New York", "kirsten@gmail.com", "www.kirsten.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else if (name.equals("Chuck Schumer"))
                    p = new Person("Chuck Schumer", "Senator", R.mipmap.chuck, "Democrat", "Great speech by Obama last night", "chuck@gmail.com", "www.chuck.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
                else
                    p = new Person("Lee Zeldin", "Representative", R.mipmap.lee, "Republican", "Working on a new city bill.", "leezeldinl@gmail.com", "www.leezeldin.com", "November 2016", "Clean Water Bill (January 2016), Minimum Wage Bill (December 2015), Military Spending Bill (October 2015), Health Coverage Bill (August 2015)", "Clean Water Committee, Minimum Wage Committee, Military Spending Committee, Health Coverage Committee");
            }
        }
        TextView t1 = (TextView) findViewById(R.id.nameDetailed);
        t1.setText(p.title + " " + p.name);
        ImageView i1 = (ImageView) findViewById(R.id.imageDetailed);
        i1.setImageDrawable(getDrawable(p.photo));
        TextView t2 = (TextView) findViewById(R.id.termDetailed);
        t2.setText("Term Ends: " + p.termEnd);
        TextView t3 = (TextView) findViewById(R.id.committeesDetailed);
        t3.setText("Current Committees: " + p.committees);
        TextView t4 = (TextView) findViewById(R.id.billsDetailed);
        t4.setText("Recent Bills Sponsored: " + p.bills);

    }
}
