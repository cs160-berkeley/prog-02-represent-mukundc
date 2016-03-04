package com.example.mukundc.represent;

/**
 * Created by mukundc on 3/1/16.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mukundc on 2/29/16.
 */
public class MyVoteFragment extends CardFragment {

    TextView t, t2;
    @Override
    public View onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.vote_fragment_layout, container, false);
        t = (TextView) view.findViewById(R.id.voteResults);
        t2 = (TextView) view.findViewById(R.id.voteCounty);
        String s = (String) getArguments().get("CardFragment_text");
        t.setText(s);
        t.setTextColor(Color.parseColor("#000000"));
        String s2 = (String) getArguments().get("CardFragment_title");
        t2.setText(s2);
        t2.setTextColor(Color.parseColor("#000000"));
        return view;
    }


    public static CardFragment create(CharSequence title, CharSequence text, int iconRes) {
        CardFragment fragment = new MyVoteFragment();
        Bundle args = new Bundle();
        if(title != null) {
            args.putCharSequence("CardFragment_title", title);
        }

        if(text != null) {
            args.putCharSequence("CardFragment_text", text);
        }

        if(iconRes != 0) {
            args.putInt("CardFragment_icon", iconRes);
        }

        fragment.setArguments(args);
        return fragment;
    }

}