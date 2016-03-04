package com.example.mukundc.represent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mukundc on 2/29/16.
 */
public class MyFragment extends CardFragment implements View.OnClickListener {

    Button b;
    TextView t, t2;
    Person p;
    ImageView image;
    BoxInsetLayout box;
    @Override
    public View onCreateContentView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        b = (Button) view.findViewById(R.id.button);
        t = (TextView) view.findViewById(R.id.text);
        t2 = (TextView) view.findViewById(R.id.title);
        image = (ImageView) view.findViewById(R.id.imageViewPhone);
        p = (Person) getArguments().get("PERSON");
        //Context c=getActivity();
        //location.setOnClickListener(c.getApplicationContext().set);
        b.setOnClickListener(this);
        String s = (String) getArguments().get("CardFragment_text");
        t.setText(s);
        t.setTextColor(Color.parseColor("#000000"));
        String s2 = (String) getArguments().get("CardFragment_title");
        t2.setText(s2);
        t2.setTextColor(Color.parseColor("#000000"));

        image.setImageDrawable(getActivity().getDrawable(p.photo));
        //box = (BoxInsetLayout) view.findViewById(R.id.box);
        //box.setBackground(getActivity().getDrawable(R.mipmap.barb));
        return view;
    }


    @Override
    public void onClick(View v) {
        // Perform action on click
        Intent sendIntent = new Intent(getActivity().getBaseContext(), WatchToPhoneService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PERSON", p);
        sendIntent.putExtras(bundle);
        getActivity().startService(sendIntent);
    }

    public static CardFragment create(CharSequence title, CharSequence text, int iconRes, Person p) {
        CardFragment fragment = new MyFragment();
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

        args.putSerializable("PERSON", p);

        fragment.setArguments(args);
        return fragment;
    }

}