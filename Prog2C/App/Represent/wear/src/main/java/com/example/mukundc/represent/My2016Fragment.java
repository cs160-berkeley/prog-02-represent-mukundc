package com.example.mukundc.represent;

/**
 * Created by mukundc on 3/1/16.
 */

import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukundc on 2/29/16.
 */
public class My2016Fragment extends CardFragment {

    TextView results1, county;
    @Override
    public View onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.vote_2016_fragment_layout, container, false);
        county = (TextView) view.findViewById(R.id.county);
        results1 = (TextView) view.findViewById(R.id.results1);

        String s1 = (String) getArguments().get("results");
        results1.setText(s1);

        //GridViewPager g = (GridViewPager) getActivity().findViewById(R.id.pager);
        FrameLayout f = (FrameLayout) getActivity().findViewById(R.id.framelayout);
        f.setBackgroundColor(0xFF0D3242);

        String s3 = getArguments().get("county") + " (" + getArguments().get("state") + ")";
        county.setText(s3);
        return view;
    }


    public static CardFragment create(String information, ArrayList<String> voteData) {
        CardFragment fragment = new My2016Fragment();
        Bundle args = new Bundle();
        String[] infoArray = information.split(",");
        String county = infoArray[0];
        String state = infoArray[1];

        String shortCounty = infoArray[0].split(" ")[0];
        boolean b = false;
        String results = "";
        Log.d("T", shortCounty);
        int count = 0;
        for (int i = 0; i < voteData.size(); i++) {
            String[] s = voteData.get(i).split(",");
            if (shortCounty.toLowerCase().contains(s[1].toLowerCase()) && s[0].toLowerCase().equals(state.toLowerCase())) {
                if (count < 3) {
                    results += s[2] + ": " + s[4] + "\n";
                    b = true;
                }
                else {
                    break;
                }
                count += 1;
            }
        }

        Log.d("row3", Boolean.toString(b));


        if (b) {
            args.putCharSequence("results", results);
        }
        else {
            args.putCharSequence("results", "2016 Primary Data Unavailable. Check back later!");
        }


        if(county != null) {
            args.putCharSequence("county", county);
        }
        if(state != null) {
            args.putCharSequence("state", state);
        }


        fragment.setArguments(args);
        return fragment;
    }

}