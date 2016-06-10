package com.example.vov4ik.quotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final String EXTRA_KEY_FOND = "com.example.vov4ik.quotesSearching_keys";
    private static final String EXTRA_KEY_ID_FOR_FINISH_FIRST = "com.example.vov4ik.quotes_Word_for_id_first";
    private static final String EXTRA_KEY_ID_FOR_FINISH_SECOND = "com.example.vov4ik.quotes_Word_for_id_second";
    private static final String KEY_AUTHOR = "used_author_found";
    private static final String KEY_TAGS = "used_tags_found";
    private static final String KEY_QUOTE = "used_quote_found";
    private static String searchingWord;
    private static String searchingKey;
    private static String destroyPreviousFirst;
    private static String destroyPreviousSecond;
    private static List<Quotes> mixedQuotesList;
    private DbHelper mDbHelper;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    BroadcastReceiver broadcastReceiver;

    public static String getSearchingWord() {
        return searchingWord;
    }

    public static String getSearchingKey() {
        return searchingKey;
    }

    public static String getDestroyPreviousFirst() {
        return destroyPreviousFirst;
    }

    public static String getDestroyPreviousSecond() {
        return destroyPreviousSecond;
    }

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
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String[] transport = intent.getStringArrayExtra(EXTRA_KEY_FOND);
        searchingWord = transport[0];
        searchingKey = transport[1];
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        QuotesKeeper qk = new QuotesKeeper();
        List<Quotes> foundList = qk.find(searchingWord, searchingKey, getApplicationContext());
        mixedQuotesList = randomQuote(foundList);
        destroyPreviousFirst = intent.getStringExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST);
        destroyPreviousSecond = intent.getStringExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND);


        broadcastReceiver = new BroadcastReceiver() {
            String previous = getSearchingWord();


            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals(previous)) {
                    finish();
                }
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(getSearchingWord()));

    }






    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        private static final String ARG_IMAGE = "Background_themes";
        private static final String ARG_COUNTER = "argument counter for quotes";
        private String savedQuote;
        private String savedAuthor;
        private String[] savedTags;
        private Quotes quote;






        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int position) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            Integer[] image = {R.drawable.main_theme_for_found, R.drawable.land_theme, R.drawable.land_theme_for_found};
            Random random = new Random();
            int i = random.nextInt(image.length);
            args.putInt(ARG_IMAGE, image[i]);
            args.putInt(ARG_COUNTER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_second, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getSearchingWord());

            RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.second_layout);
            Drawable drawable = getResources().getDrawable(getArguments().getInt(ARG_IMAGE));
            layout.setBackground(drawable);

            quote = getMixedQuotesList().get(getArguments().getInt(ARG_COUNTER));
            if (savedInstanceState != null) {
                savedQuote = savedInstanceState.getString(KEY_QUOTE);
                savedAuthor = savedInstanceState.getString(KEY_AUTHOR);
                savedTags = savedInstanceState.getStringArray(KEY_TAGS);
            } else {
                savedQuote = quote.getQuote();
                savedAuthor = quote.getAuthor();
                savedTags = quote.getTags();
            }





            TextView textViewQuote = (TextView) rootView.findViewById(R.id.quoteFound);
            textViewQuote.setText(savedQuote);

            final TextView textViewAuthor = (TextView) rootView.findViewById(R.id.authorFound);
            textViewAuthor.setText(savedAuthor);

            // final TextView textViewTags = (TextView) rootView.findViewById(R.id.tag);
            View linearLayout = rootView.findViewById(R.id.tagLayoutFound);
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

            if (Objects.equals(word, getSearchingWord())){
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getDestroyPreviousFirst());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND,getDestroyPreviousSecond());
            } else
            if (Objects.equals(word, getDestroyPreviousFirst())) {
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getSearchingWord());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND,getDestroyPreviousSecond());
            }else
            {
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getSearchingWord());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND, getDestroyPreviousFirst());
            }
            if (Objects.equals(word, getSearchingWord())){
                Log.d("Test", "1start for getSearchingWord() "+ getSearchingWord());
                getActivity().finish();
            } else
            if (Objects.equals(word, getDestroyPreviousFirst())){
                Log.d("Test", "1start for getDestroyPreviousFirst() "+ getDestroyPreviousFirst());
                Intent intentForFinish = new Intent(getDestroyPreviousFirst());
                getActivity().sendBroadcast(intentForFinish);
            } else {
                Intent intentForFinish = new Intent(getDestroyPreviousSecond());
                Log.d("Test", "3start for getDestroyPreviousSecond() "+ getDestroyPreviousSecond());

                getActivity().sendBroadcast(intentForFinish);
            }
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
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
            QuotesKeeper qk = new QuotesKeeper();
            List<Quotes> foundList = qk.find(searchingWord, searchingKey, getApplicationContext());
            return foundList.size();
        }


    }
}
