package com.example.vov4ik.quotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.TypeInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final String KEY_QUOTE = "used_quote";
    private static final String KEY_AUTHOR = "used_author";
    private static final String KEY_TAGS = "used_tags";
    private static final String EXTRA_KEY_FOND = "com.example.vov4ik.quotesSearching_keys";
    private static List<Quotes> mixedQuotesList;

    public static List<Quotes> getMixedQuotesList() {
        return mixedQuotesList;
    }

    public List<Quotes> randomQuote(List<Quotes> array) {
        Random ran = new Random();
        List<Quotes> newArray = new ArrayList<>();
        while(array.size()>0){
            int index = ran.nextInt(array.size());
            newArray.add(array.get(index));
            array.remove(index);
        }
        return newArray;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        QuotesKeeper qk = new QuotesKeeper();
        List<Quotes> foundList = qk.getQuotesList();
        mixedQuotesList = randomQuote(foundList);

    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener

    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private String savedQuote;
        private String savedAuthor;
        private String[] savedTags;
        private Quotes quote;

        public List<Quotes> wholeArray(){
            QuotesKeeper qk = new QuotesKeeper();
            return qk.getQuotesList();
        }


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);



            quote = getMixedQuotesList().get(getArguments().getInt(ARG_SECTION_NUMBER));;
            if (savedInstanceState != null){
                savedQuote = savedInstanceState.getString(KEY_QUOTE);
                savedAuthor = savedInstanceState.getString(KEY_AUTHOR);
                savedTags = savedInstanceState.getStringArray(KEY_TAGS);
            }else{
                savedQuote = quote.getQuote();
                savedAuthor = quote.getAuthor();
                savedTags = quote.getTags();
            }



            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textViewQuote = (TextView) rootView.findViewById(R.id.quote);
            textViewQuote.setText(savedQuote);

            final TextView textViewAuthor = (TextView) rootView.findViewById(R.id.author);
            textViewAuthor.setText(savedAuthor);


            View linearLayout = rootView.findViewById(R.id.tagLayout);
            for (String savedTag : savedTags) {
              TextView text = new TextView(linearLayout.getContext());
                text.setText(savedTag + "   ");
                text.setId(Arrays.asList(savedTags).indexOf(savedTag));
                ((LinearLayout) linearLayout).addView(text);
                text.setOnClickListener(this);
            }

            textViewAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String author = textViewAuthor.getText().toString();
                    startMethodNewActivity(author, "author");


                }
            });



            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString(KEY_QUOTE, savedQuote);
            savedInstanceState.putString(KEY_AUTHOR, savedAuthor);
            savedInstanceState.putStringArray(KEY_TAGS, savedTags);

        }


        @Override
        public void onClick(View v) {
            String tag = savedTags[v.getId()];
            startMethodNewActivity(tag, "tags");
        }


    public void startMethodNewActivity(String word, String key){
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        String[] transport = {word, key};
        intent.putExtra(EXTRA_KEY_FOND, transport);
        startActivity(intent);
    }
}
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            QuotesKeeper qk = new QuotesKeeper();
            return qk.getQuotesList().size();

        }


    }
}
