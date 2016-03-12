package com.example.mukundc.represent;

/**
 * Created by mukundc on 3/1/16.
 */
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by mukundc on 2/29/16.
 */
public class MyVoteFragment extends CardFragment {

    TextView results1, results2, county;
    @Override
    public View onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.vote_fragment_layout, container, false);
        county = (TextView) view.findViewById(R.id.county);
        results1 = (TextView) view.findViewById(R.id.results1);
        results2 = (TextView) view.findViewById(R.id.results2);

        String s1 = (String) getArguments().get("obama");
        String s2 = (String) getArguments().get("romney");

        String s1d = "Obama: " + s1 + "%";
        String s2d = "Romney: " + s2 + "%";

        Double obama = Double.parseDouble(s1);
        Double romney = Double.parseDouble(s2);



        //GridViewPager g = (GridViewPager) getActivity().findViewById(R.id.pager);
        FrameLayout f = (FrameLayout) getActivity().findViewById(R.id.framelayout);

        if (obama == -1.0 || romney == -1.0) {
            results1.setText("Voting results unavailable");
            results2.setVisibility(View.GONE);
            f.setBackgroundColor(0xFF0D3242);
        } else {
            if (obama > romney) {
                f.setBackgroundColor(0xFF466398);
                results1.setText(s1d);
                results2.setText(s2d);
            } else {
                f.setBackgroundColor(0xFFDB5656);
                results1.setText(s2d);
                results2.setText(s1d);
            }

        }

        String s3 = getArguments().get("county") + " (" + getArguments().get("state") + ")";
        county.setText(s3);
        return view;
    }


    public static CardFragment create(String information) {
        CardFragment fragment = new MyVoteFragment();
        Bundle args = new Bundle();
        String[] infoArray = information.split(",");
        String county = infoArray[0];
        String state = infoArray[1];
        String obama = infoArray[2];
        String romney = infoArray[3];


        if(county != null) {
            args.putCharSequence("county", county);
        }
        if(state != null) {
            args.putCharSequence("state", state);
        }
        if(obama != null) {
            args.putCharSequence("obama", obama);
        }
        if(romney != null) {
            args.putCharSequence("romney", romney);
        }

        fragment.setArguments(args);
        return fragment;
    }

}