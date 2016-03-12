package com.example.mukundc.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.List;

/**
 * Created by mukundc on 2/29/16.
 */
public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private List mRows;

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }

    static final int[] BG_IMAGES = new int[] {
            R.drawable.card_background, R.drawable.card_background, R.drawable.card_background};

    //static final int[] BG_IMAGES = new int[] {};

    // A simple container for static data in each page
    private static class Page {
        // static resources
        String title;
        String text;

        Page(String t1, String t2) {
            title = t1;
            text = t2;
        }
    }

    // Create a static set of pages in a 2D array
    Page p = new Page("Marco", "Senator");
    Page p2 = new Page("Bill", "Senator");
    Page p3 = new Page("Mukund", "Representative");
    private final Page[][] PAGES = { };
            // Override methods in FragmentGridPagerAdapter
            // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        Page page = PAGES[row][col];
        String title = page.title != null ? page.title : null;
        String text = page.text != null ? page.text : null;
        CardFragment fragment = CardFragment.create(title, text);
        fragment.getView().setBackgroundColor(Color.WHITE);
        return fragment;
    }


/*
    //Obtain the background image for the row
    @Override
    public Drawable getBackgroundForRow(int row) {
        return mContext.getResources().getDrawable(R.mipmap.marco, null);
    }
*/
    // Obtain the background image for the specific page
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        if( row == 2 && column == 1) {
            // Place image at specified position
            return mContext.getResources().getDrawable(R.mipmap.marco, null);
        } else {
            // Default to background image for row
            return mContext.getResources().getDrawable(R.mipmap.marco, null);
        }
    }



    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }

}