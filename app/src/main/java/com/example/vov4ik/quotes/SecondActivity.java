package com.example.vov4ik.quotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
    private static final String EXTRA_KEY_ID_SECTION_FOR_FINISH_FIRST = "com.example.vov4ik.quotes_Section_for_id_first";
    private static final String EXTRA_KEY_ID_SECTION_FOR_FINISH_SECOND = "com.example.vov4ik.quotes_Section_for_id_second";
    private static final String KEY_AUTHOR = "used_author_found";
    private static final String KEY_TAGS = "used_tags_found";
    private static final String KEY_QUOTE = "used_quote_found";
    private static String searchingWord;
    private static String searchingKey;
    private static String destroyPreviousFirst;
    private static String destroyPreviousFirstSection;
    private static String destroyPreviousSecond;
    private static String destroyPreviousSecondSection;
    private static List<Quotes> mixedQuotesList;
    private DbHelper mDbHelper;
    private static int lengthOfList;
    private static String language;
    private static int layoutBackground;



    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    BroadcastReceiver broadcastReceiver;

    private static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        SecondActivity.language = language;
    }

    public static String getSearchingWord() {
        return searchingWord;
    }

    public static String getSearchingKey() {
        return searchingKey;
    }

    public static String getDestroyPreviousFirstSection() {
        return destroyPreviousFirstSection;
    }

    public static String getDestroyPreviousSecondSection() {
        return destroyPreviousSecondSection;
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
        setLanguage(transport[2]);

        List<Quotes> foundList = QuotesKeeper.find(searchingWord, searchingKey, getApplicationContext(), getLanguage());
        mixedQuotesList = randomQuote(foundList);
        lengthOfList = mixedQuotesList.size();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), lengthOfList);//QuotesKeeper.findLength(getSearchingWord(), getSearchingKey(), getApplicationContext(), getLanguage()));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        Integer[] image = {R.drawable.main_theme_for_found, R.drawable.main_theme_2,
                R.drawable.main_theme_1, R.drawable.main_theme_3
//                R.drawable.land_theme,R.drawable.land_theme_for_found,R.drawable.land_theme_1,
//                R.drawable.land_theme_2,R.drawable.land_theme_3,R.drawable.land_theme_4
        };

        Random random = new Random();
        int i = random.nextInt(image.length);
        layoutBackground = image[i];
        destroyPreviousFirst = intent.getStringExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST);
        destroyPreviousSecond = intent.getStringExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND);
        destroyPreviousFirstSection = intent.getStringExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_FIRST);
        destroyPreviousSecondSection = intent.getStringExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_SECOND);

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
        registerReceiver(broadcastReceiver, new IntentFilter(getSearchingKey()));

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
            Drawable drawable = getResources().getDrawable(layoutBackground);
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
                if (savedTag.length()>3){
                    TextView text = new TextView(linearLayout.getContext());
                    text.setText(savedTag);
                    text.setId(Arrays.asList(savedTags).indexOf(savedTag));
                    ((LinearLayout) linearLayout).addView(text);
                    text.setOnClickListener(this);
                    text.setPadding(10, 4, 10, 4);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                    mlp.setMargins(10, 0, 10, 0);
                    text.setBackground(getResources().getDrawable(R.drawable.shape));
                }
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
            String[] transport = {word, key, getLanguage()};
            intent.putExtra(EXTRA_KEY_FOND, transport);

            if (Objects.equals(word, getSearchingWord())){
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getDestroyPreviousFirst());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND,getDestroyPreviousSecond());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_FIRST, getDestroyPreviousFirstSection());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_SECOND, getDestroyPreviousSecondSection());
            } else
            if (Objects.equals(word, getDestroyPreviousFirst())) {
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getSearchingWord());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND,getDestroyPreviousSecond());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_FIRST, getSearchingKey());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_SECOND,getDestroyPreviousSecondSection());
            }else
            {
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_FIRST, getSearchingWord());
                intent.putExtra(EXTRA_KEY_ID_FOR_FINISH_SECOND, getDestroyPreviousFirst());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_FIRST, getSearchingKey());
                intent.putExtra(EXTRA_KEY_ID_SECTION_FOR_FINISH_SECOND, getDestroyPreviousFirstSection());
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
        Log.d("Test", "SecondStop " + getSearchingWord());
        super.onStop();

//
    }

    @Override
    protected void onDestroy() {
        Log.d("Test", "destroy!!!!!!!!!!!! ");


        mViewPager.setAdapter(null);
        if (destroyPreviousSecond!=null) {
            searchingWord = destroyPreviousFirst;
            searchingKey = destroyPreviousFirstSection;
            setLanguage(getLanguage());

            List<Quotes> foundList = QuotesKeeper.find(searchingWord, searchingKey, getApplicationContext(), getLanguage());
            mixedQuotesList = randomQuote(foundList);
            lengthOfList = mixedQuotesList.size();
            destroyPreviousFirst = destroyPreviousSecond;
            destroyPreviousSecond = null;
            destroyPreviousFirstSection = destroyPreviousSecondSection;
            destroyPreviousSecondSection = null;
        }else if(destroyPreviousFirst != null){
            searchingWord = destroyPreviousFirst;
            searchingKey = destroyPreviousFirstSection;
            setLanguage(getLanguage());

            List<Quotes> foundList = QuotesKeeper.find(searchingWord, searchingKey, getApplicationContext(), getLanguage());
            mixedQuotesList = randomQuote(foundList);
            lengthOfList = mixedQuotesList.size();
            destroyPreviousFirst = null;
            destroyPreviousSecond = null;
            destroyPreviousFirstSection = null;
            destroyPreviousSecondSection = null;
        }
        mSectionsPagerAdapter.setLength(QuotesKeeper.findLength(getSearchingWord(), getSearchingKey(), getApplicationContext(), getLanguage()));
        mSectionsPagerAdapter.notifyDataSetChanged();


        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("Test", "onPause "+getSearchingWord());
        super.onPause();
//        mViewPager.setAdapter(null);
//        if (destroyPreviousSecond!=null) {
//            searchingWord = destroyPreviousFirst;
//            searchingKey = destroyPreviousFirstSection;
//            setLanguage(getLanguage());
//
//            List<Quotes> foundList = QuotesKeeper.find(searchingWord, searchingKey, getApplicationContext(), getLanguage());
//            mixedQuotesList = randomQuote(foundList);
//            lengthOfList = mixedQuotesList.size();
//            destroyPreviousFirst = destroyPreviousSecond;
//            destroyPreviousSecond = null;
//            destroyPreviousFirstSection = destroyPreviousSecondSection;
//            destroyPreviousSecondSection = null;
//        }else if(destroyPreviousFirst != null){
//            searchingWord = destroyPreviousFirst;
//            searchingKey = destroyPreviousFirstSection;
//            setLanguage(getLanguage());
//
//            List<Quotes> foundList = QuotesKeeper.find(searchingWord, searchingKey, getApplicationContext(), getLanguage());
//            mixedQuotesList = randomQuote(foundList);
//            lengthOfList = mixedQuotesList.size();
//            destroyPreviousFirst = null;
//            destroyPreviousSecond = null;
//            destroyPreviousFirstSection = null;
//            destroyPreviousSecondSection = null;
//        }
//        mSectionsPagerAdapter.setLength(QuotesKeeper.findLength(getSearchingWord(), getSearchingKey(), getApplicationContext(), getLanguage()));
//        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Log.d("Test", "onResume " + getSearchingWord());
        super.onResume();
//        mSectionsPagerAdapter.setLength(QuotesKeeper.findLength(getSearchingWord(), getSearchingKey(), getApplicationContext(), getLanguage()));
//        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int length;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public SectionsPagerAdapter(FragmentManager fm, int length) {
            super(fm);
            this.length = length;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //Log.d("Test", "getItem "+position);
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount(){
            //Log.d("Test", "getCount " + lengthOfList + " " + getSearchingWord() + " " + getSearchingKey());
            //int l = QuotesKeeper.findLength(getSearchingWord(), getSearchingKey(), getApplicationContext(), getLanguage());
            //Log.d("Test", "getCount N "+l);
            Log.d("Test", "    "+getLength());
            return getLength();
        }


    }
}
