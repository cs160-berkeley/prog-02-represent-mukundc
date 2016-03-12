package com.example.mukundc.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mukundc on 2/29/16.
 */
public class MyFragment extends CardFragment {

    Button b;
    CircleImageView image;


    @Override
    public View onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle args) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        b = (Button) view.findViewById(R.id.button);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView party = (TextView) view.findViewById(R.id.party);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
                Bundle bundle = new Bundle();
                String infoToSendToPhone = getArguments().getString("PHONE");
                bundle.putSerializable("DATA", infoToSendToPhone);
                sendIntent.putExtras(bundle);
                getActivity().startService(sendIntent);
            }
        });

        String s =  (String) getArguments().get("party");
        String sDispalyed = "(" + (String) getArguments().get("party") + ")";
        party.setText(sDispalyed);

        /*
        if (s.toLowerCase().equals("d")) {
            s = "Democrat";
        } else {
            s = "Republican";
        }*/

        //GridViewPager g = (GridViewPager) getActivity().findViewById(R.id.pager);
        FrameLayout f = (FrameLayout) getActivity().findViewById(R.id.framelayout);

        if (s.equals("R")) {
            f.setBackgroundColor(0xFFDB5656);
            //image.setBorderColor(0xFFDB5656);
        } else {
            f.setBackgroundColor(0xFF466398);
            //image.setBorderColor(0xFF466398);


        }


        /*
        String t  = (String) getArguments().get("title");
        if (t.equals("Representative")) {
            title = "Rep.";
        }
        else {
            title = "Sen.";
        }*/

        title.setText((String) getArguments().get("title"));
        name.setText(getArguments().getString("name"));
        return view;
    }


    public static CardFragment create(JSONObject person) {
        CardFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        String party = "";
        String title = "";
        String name = "";

        try {
            if (person.getString("chamber").equals("house")) {
                title = "Representative";
            } else {
                title = "Senator";
            }
            if (person.getString("party").equals("R")) {
                party = "R";
            } else {
                party = "D";
            }
            name =  person.getString("first_name") + " " + person.getString("last_name");
        } catch (JSONException e) {

        }

        if(title != null) {
            args.putCharSequence("title", title);
        }

        if(party != null) {
            args.putCharSequence("party", party);
        }

        if(party != null) {
            args.putCharSequence("name", name);
        }

        args.putString("PHONE", person.toString());
        fragment.setArguments(args);
        return fragment;
    }

}

