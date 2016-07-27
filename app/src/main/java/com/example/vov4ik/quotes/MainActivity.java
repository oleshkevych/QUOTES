package com.example.vov4ik.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final String KEY_LANGUAGE = "used_language";
    private static final String KEY_QUOTE = "used_quote";
    private static final String KEY_AUTHOR = "used_author";
    private static final String KEY_TAGS = "used_tags";
    private static final String EXTRA_KEY_FOND = "com.example.vov4ik.quotesSearching_keys";
    private static final String EXTRA_NUMBER = "NUMBER_FOR_THE_FIRST_QUOTE";
    private static List<Quotes> mixedQuotesList, sList;
    private static Boolean reloader = true;
    private static int lengthOfList;
    private static int variableToWriteViews = 0;
    private static int[] mixerForQuotesList;
    private static final int lengthOfReadingInDatabase = 5;
    private static int lengthBeforeReload = 0;
    private static int numberForFirst;

    public static int getNumberForFirst() {
        return numberForFirst;
    }

    public static void setNumberForFirst(int numberForFirst) {
        MainActivity.numberForFirst = numberForFirst;
    }

    public static int getLengthOfReadingInDatabase() {
        return lengthOfReadingInDatabase;
    }

    public static int getVariableToWriteViews() {
        return variableToWriteViews;
    }

    public static void setVariableToWriteViews(int variableToWriteViews) {
        MainActivity.variableToWriteViews = variableToWriteViews;
    }

    public static int getLengthBeforeReload() {
        return lengthBeforeReload;
    }

    public static void setLengthBeforeReload(int lengthBeforeReload) {
        MainActivity.lengthBeforeReload = lengthBeforeReload;
    }

    public static void setLanguage(String language) {
        MainActivity.language = language;
    }

    public static Boolean getReloader() {
        return reloader;
    }

    public static void setReloader(Boolean reloader) {
        MainActivity.reloader = reloader;
    }

    public static String getLanguage() {
        return language;
    }

    private static String language = "mix";

    public static int[] getMixerForQuotesList() {
        return mixerForQuotesList;
    }

    public static void setMixerForQuotesList(int[] mixerForQuotesList){
        MainActivity.mixerForQuotesList = mixerForQuotesList;
    }

    public static List<Quotes> getMixedQuotesList() {
        return mixedQuotesList;
    }

    public static void setMixedQuotesList(List<Quotes> mixedQuotesList) {
        PlaceholderFragment.setCheckingOfExecution(true);
        MainActivity.mixedQuotesList.addAll(mixedQuotesList);
    }

    public static void setLengthOfList(int lengthOfList) {
        int lengthOfArray;
        if(PlaceholderFragment.getSectionNumber()<2) {
            lengthOfArray = lengthOfList;
            }else{
            lengthOfArray = lengthOfList - getMixedQuotesList().size();
        }

        int[]result = new int[lengthOfArray];
        for (int i = 0; i < lengthOfArray; i++) {
            result[i] = i;
        }
        Random rnd = ThreadLocalRandom.current();
        int indexOfNumberForFirst =  rnd.nextInt(lengthOfArray);
        for (int i = lengthOfArray - 1; i > 1; i--)
        {

            int index = rnd.nextInt(i + 1);
            int a = result[index];
            result[index] = result[i];
            result[i] = a;
            if(result[i] == getNumberForFirst()) {
                indexOfNumberForFirst = i;
            }
        }
        int a = result[0];

        result[0] = result[indexOfNumberForFirst];
        result[indexOfNumberForFirst] = a;
        Log.d("Test", Arrays.toString(result));

       /*     for (int i = 1; i <= lengthOfArray; i++) {
                newArray[i] = i;
            }
        int[] result = new int[lengthOfArray];
        for (int i=0; i<lengthOfArray; i++) {
            int index = ran.nextInt(lengthOfArray);
            if ((newArray[index]!=0)){
                result[i] = newArray[index];
                newArray[index]=0;
            }else do {
                if ((index == lengthOfArray) && (newArray[index] == 0)) {
                    i--;
                    break;
                } else {
                    index++;
                    if ((newArray[index] != 0)) {
                        result[i] = newArray[index];
                        newArray[index]=0;
                        break;
                    }
                }
            }while(true);
        }*/

        setMixerForQuotesList(result);
        MainActivity.lengthOfList = lengthOfList;
    }

    public static void refresher(SectionsPagerAdapter adapter, Context context){

        Log.d("Test", "Long size: " + PlaceholderFragment.getSectionNumber());
        if (PlaceholderFragment.getSectionNumber()<2){
            MainActivity.mixedQuotesList.clear();
        }else {
            MainActivity.mixedQuotesList = (getMixedQuotesList().subList(0, PlaceholderFragment.getSectionNumber() - 2));
        }
        setLengthOfList(QuotesKeeper.dataLength(context, getLanguage()) + getMixedQuotesList().size());
        setLengthBeforeReload(getMixedQuotesList().size());
        setMixedQuotesList(QuotesKeeper.getQuotesList(context, getLanguage(), 0, getMixerForQuotesList(), 0));
        setReloader(false);

        setVariableToWriteViews(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            setLanguage(savedInstanceState.getString(KEY_LANGUAGE));
        }


        if(getIntent()!=null){
            int lengthOfTheList = QuotesKeeper.dataLength(getApplicationContext(), getLanguage());
            Random rand = new Random();
            int randomNumber = rand.nextInt(lengthOfTheList);
            setNumberForFirst(getIntent().getIntExtra(EXTRA_NUMBER, randomNumber));

        }
        setLengthOfList(QuotesKeeper.dataLength(getApplicationContext(), getLanguage()));
        List<Quotes> list = QuotesKeeper.getQuotesList(getApplicationContext(), language, 0, getMixerForQuotesList(), 0);

        Log.d("DB", getApplicationContext().getDatabasePath("Quotes.db").toString());

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = getApplicationContext().getDatabasePath("Quotes.db").toString();
                String backupDBPath = "Quotes.db";
                Log.d("DB", "COPYING: "+ sd);

                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    Log.d("DB", "COPYING: "+ dst.toString());
                    src.close();
                    dst.close();

                }
            }

        } catch (Exception e) {
            Log.d("DB", e.toString());
        }




        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mixedQuotesList = list;//= randomQuote(list);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        //new DbHelper(getApplicationContext()).fillData(mQuotesList);
       // Log.d("Test", String.valueOf(lengthOfList));
        //new DbHelper(getApplicationContext()).delete();
        //mixedQuotesList = randomQuote(mQuotesList);
        //lengthOfList = mQuotesList.size();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ukr) {
            // Handle the camera action
            setLanguage("ukr");
            refresher(mSectionsPagerAdapter, getApplicationContext());
        } else if (id == R.id.nav_rus) {
            setLanguage("rus");
            refresher(mSectionsPagerAdapter, getApplicationContext());
        }else if (id == R.id.the_game) {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_mix) {
            setLanguage("mix");
            refresher(mSectionsPagerAdapter, getApplicationContext());
        }else if (id == R.id.search_activity_start) {
            Intent intent = new Intent(getApplication(), SearchActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        // private List<List<Quotes>> mList = new ArrayList<>();
        private Context mContext;
        private static Boolean checkingOfExecution = true;
        private static int sectionNumber;

        public static Boolean getCheckingOfExecution() {
            return checkingOfExecution;
        }

        public static int getSectionNumber() {
            return sectionNumber;
        }

        public static void setSectionNumber(int sectionNumber) {
            PlaceholderFragment.sectionNumber = sectionNumber;
        }

        public static void setCheckingOfExecution(Boolean checkingOfExecution) {
            PlaceholderFragment.checkingOfExecution = checkingOfExecution;
        }


        public PlaceholderFragment() {
        }

        private class FetchQuotesTask extends AsyncTask<Void, Void, List<Quotes>> {
            @Override
            protected List<Quotes> doInBackground(Void... params) {
//A part to start the site loader and parser
                /*
                try{
                    Log.d("Test", "Start");
                    String path = "http://pro-status.com.ua/citaty/citaty.php";
                    String html;
                    html = Catcher.html(path);
                    String[] pathArray = Catcher.pageStealer(html);
                    //pathArray[75]=path;
                    String[] newTags = new String[((int)pathArray.length/2)];
                    String[] newPath = new String[((int)pathArray.length/2)];
                    int j =0;
                    for (int i=1; i<pathArray.length; i=i+2){
                        newTags[j] = pathArray[i];
                        j++;
                    }
                    j =0;
                    for (int i=0; i<pathArray.length; i=i+2){
                        newPath[j] = pathArray[i];
                        j++;
                    }

                    //System.arraycopy(pathArray, 72, newPath, 0, 21);
                    int counter=0;
//                    for (String paths: newPath) {
//                        Log.d("Test", paths+" "+counter);
//                        counter++;}

                        for (j=240; j<449; j++) {

                            List<List<Quotes>> mList = new ArrayList<>();
//                      if ((paths!=null)&&(!paths.equals("http://tsytaty.ukrayinskoyu.pro/http://tsytaty.ukrayinskoyu.pro/chas.html"))&&
//                              (!paths.equals("http://tsytaty.ukrayinskoyu.pro/http://tsytaty.ukrayinskoyu.pro/chas-2.html"))&&(paths.equals())){
                            path = newPath[j].replaceAll(" ","%20");
//                        }
                        for (int i = 0; i < 5; i++) {
                            List<Quotes> q = new ArrayList<Quotes>();
                            html = Catcher.html(path);
                            q = Catcher.purser(html);
                            for (Quotes quote: q){
                                quote.setTags(new String[]{newTags[j]});
                                Log.d("Test","TAGS "+ Arrays.toString(quote.getTags()));
                            }
                            if (q != null) {
                                mList.add(q);
                            }
                            path = Catcher.catcherForNextPage(html);
                            if (path.equals("")){
                                Log.d("Test", "BREAK");
                                break;

                            }

                        }
                        DbHelper mDbHelper = new DbHelper(getContext());
                        counter++;
                        Log.d("Test", "All Done: "+counter);
                        for (List<Quotes> q : mList) {

                            mDbHelper.fillData(q);
                        }
                    }
                }catch (IOException ioe){
                    Log.d("Test", "Failed because: ", ioe);
                }
                return  null;
            }
*/

                List<Quotes> q = new ArrayList<Quotes>();
                q.addAll(QuotesKeeper.getQuotesList(getContext(), getLanguage(), getMixedQuotesList().size()-getLengthBeforeReload(), getMixerForQuotesList(), 0));
                return q;
            }

            @Override
            protected void onPostExecute(List<Quotes> result) {
                // TODO Auto-generated method stub
                setMixedQuotesList(result);
            }
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
            Log.d("Test", "Checking the position: " + String.valueOf(sectionNumber));
            return fragment;
        }

        public void displayLoadedQuote(View rootView) {
            TextView textViewQuote = (TextView) rootView.findViewById(R.id.quote);
            textViewQuote.setText(savedQuote);

            final TextView textViewAuthor = (TextView) rootView.findViewById(R.id.author);
            textViewAuthor.setText(savedAuthor);


            View linearLayout = rootView.findViewById(R.id.tagLayout);
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
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);


            setSectionNumber(getArguments().getInt(ARG_SECTION_NUMBER));
            quote = getMixedQuotesList().get(getArguments().getInt(ARG_SECTION_NUMBER));

            if ((lengthOfList > (getMixedQuotesList().size())) && (getCheckingOfExecution())) {
                Log.d("Test", lengthOfList + " NEXT " + getMixedQuotesList().size());

                setCheckingOfExecution(false);
                new FetchQuotesTask().execute();
//                Log.d("Test", "List = " + l.size() + "   " + l.get(0).getQuote());

            }

            if ((savedInstanceState != null) && (getReloader())) {
                savedQuote = savedInstanceState.getString(KEY_QUOTE);
                savedAuthor = savedInstanceState.getString(KEY_AUTHOR);
                savedTags = savedInstanceState.getStringArray(KEY_TAGS);
            } else {
                savedQuote = quote.getQuote();
                savedAuthor = quote.getAuthor();
                savedTags = quote.getTags();
                if (!getReloader() && (getVariableToWriteViews() > 2)) {
                    setReloader(true);
                } else {
                    setVariableToWriteViews(getVariableToWriteViews() + 1);
                }
            }


            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            displayLoadedQuote(rootView);

            final TextView textViewAuthor = (TextView) rootView.findViewById(R.id.author);
            textViewAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String author = textViewAuthor.getText().toString();
                    startMethodNewActivity(author, "author");
                }
            });


//            Button databaseFiller = (Button) rootView.findViewById(R.id.databaseFiller);
//            databaseFiller.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DbHelper mHelper = new DbHelper (getContext());
//                    SQLiteDatabase mDatabase = mHelper.getWritableDatabase();
//                    mDatabase.execSQL("delete from quotes");
//                    mDatabase.execSQL("DROP TABLE IF EXISTS quotes");
//
//                    Log.d("Test", "DELETE table started");
//                    Log.d("Test", "quote "+mHelper.getData().get(0).getQuote());
//

//
//                    List<Quotes> list = new ArrayList<Quotes>();
//                    list.addAll(mixedQuotesList.subList(22, 26));
//                    list.addAll(mixedQuotesList.subList(31, 34));
//                    list.addAll(mixedQuotesList.subList(57, 271));
//                    Log.d("Test", "LIST: " + list.size() + " " + list.get(5).getQuote());
//
//
                    //Log.d("test", "quote: "+ new DbHelper(getContext()).properlyDataGetterForTwoLanguages(0, language).get(2).getQuote());
                    //new DbHelper(getContext()).killDoubles();
                    // new DbHelper(getContext()).getLengthOfList("mix");
//                    new DbHelper(getContext()).normalRemoverForDoules();
                    //                   Log.d("Test", "FINAL LENGTH OF THE NEW BD" + (new DbHelper(getContext()).getQ("ukr") + new DbHelper(getContext()).getQ("rus")));
//                    new DbHelper(getContext()).removeDB();
//                    new DbHelper(getContext()).properlyFilling(new DbHelper(getContext()).getQ("ukr"), "ukr");
//                    new DbHelper(getContext()).properlyFilling(new DbHelper(getContext()).getQ("rus"), "rus");
                    //new DbHelper(getContext()).delete();
                    // new FetchQuotesTask().execute();
                    // new DbHelper(getContext()).killDoubles();
//                    String path = Environment.getExternalStorageDirectory().toString()+"/FQuotesArray.txt";//"\\Phone\\Books"
//                    Log.d("Test", Arrays.toString((getMixedQuotesList().get(1).getTags())));

                    //new DbHelper(getContext()).properlyFilling(getMixedQuotesList(), "ukr");
//                    FileWriter.WriteSettings(getContext(), getMixedQuotesList(), path);


//                }
//            });


            return rootView;
        }


        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
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


        public void startMethodNewActivity(String word, String key) {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            String[] transport = {word, key, getLanguage()};
            intent.putExtra(EXTRA_KEY_FOND, transport);
            startActivity(intent);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_LANGUAGE, getLanguage());

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {//FragmentPagerAdapter {

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
            Log.d("Test", String.valueOf(lengthOfList));
            return lengthOfList;

        }
        public int getItemPosition(Object object){

            return  SectionsPagerAdapter.POSITION_NONE;
        }


    }
}
