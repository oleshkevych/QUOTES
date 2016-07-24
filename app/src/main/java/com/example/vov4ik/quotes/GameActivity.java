package com.example.vov4ik.quotes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private String language;
    private String[] authorsArray;
    private int lengthOfTheList;
    private int lengthOfReadingFromDB = 30;
    private static final String EXTRA_KEY = "extraKeyForLoginActivity";
    private static final String EXTRA_KEY_LANGUAGE = "extraKeyForLoginActivityLanguage";
    private String rank;
    private int gameCounter;
    private Boolean rotationMarker = false;
    private Boolean stateMarker;
    private String savedQuote;
    private String decision;
    private int selectedNumber;
    private List<Quotes> list = new ArrayList<>();
    private Boolean checkingOfExecution = true;
    private static final int GREEN = -16711936;
    private static final int RED = -65536;
    private String name;
    private String serializedString;
    private static final String KEY_LANGUAGE ="languageKey";
    private static final String KEY_NAME ="nameKey";
    private static final String KEY_DECISION ="decisionKey";
    private static final String KEY_STATE ="stateKey";
    private static final String KEY_AUTHORS_ARRAY ="authorsArrayKey";
    private static final String KEY_QUOTE ="quoteKey";
    private static final String KEY_MIXER ="mixerKey";
    private static final String KEY_NUMBER ="numberKey";
    private static final String KEY_COUNTER ="counterKey";

    public void setRotationMarker(Boolean rotationMarker) {
        this.rotationMarker = rotationMarker;
    }

    public Boolean getRotationMarker() {
        return rotationMarker;
    }

    public Boolean getStateMarker() {
        return stateMarker;
    }

    public void setStateMarker(Boolean stateMarker) {
        this.stateMarker = stateMarker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCheckingOfExecution() {
        return checkingOfExecution;
    }

    public void setCheckingOfExecution(Boolean checkingOfExecution) {
        this.checkingOfExecution = checkingOfExecution;
    }

    public List<Quotes> getList() {
        return list;
    }

    public void setList(List<Quotes> list) {
        for(Quotes quote: list) {
            if ((quote.getAuthor().length()<30)) {
                this.list.add(quote);

            }
        }
        setCheckingOfExecution(true);
    }

    public int getGameCounter() {
        return gameCounter;
    }

    public void setGameCounter(int gameCounter) {
        this.gameCounter = gameCounter;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLengthOfReadingFromDB() {
        return lengthOfReadingFromDB;
    }

    public String[] getAuthorsArray() {
        return authorsArray;
    }

    public void setAuthorsArray(String[] authorsArray) {
        this.authorsArray = authorsArray;
    }

    public int getLengthOfTheList() {
        return lengthOfTheList;
    }

    public void setLengthOfTheList(int lengthOfTheList) {
        this.lengthOfTheList = lengthOfTheList;
    }

    public int[] getMixer() {
        return mixer;
    }

    public void setMixer(int[] mixer) {
        this.mixer = mixer;
    }

    private int[] mixer;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private void mixerForQuote(int length){
        int[]result = new int[length];
        Random rnd = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            result[i] = i;
        }
        for (int i = length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = result[index];
            result[index] = result[i];
            result[i] = a;
        }
        setMixer(result);
    }

    public void fillTheGameForm(){
        String[] authors = new String[3];
        TextView quoteText = (TextView) findViewById(R.id.quoteGame);
        if (getRotationMarker()){
            Log.d("Test","++++++++");
            authors = getAuthorsArray();
            setRotationMarker(false);
        }else {
            Random rnd = ThreadLocalRandom.current();

            int index = rnd.nextInt(3), i = 0;
            authors[index] = getList().get(getGameCounter()).getAuthor();
            do {

                int j = rnd.nextInt(getList().size());

                if ((!authors[index].equals(getList().get(j).getAuthor()))) {
                    if (i == 0) {
                        if (i != index) {
                            authors[i] = getList().get(j).getAuthor();
                        }
                        i++;
                    } else if ((i == 1) && (!authors[0].equals(getList().get(j).getAuthor()))) {
                        if (i != index) {
                            authors[i] = getList().get(j).getAuthor();
                        }
                        i++;
                    } else if ((i == 2) && (!authors[0].equals(getList().get(j).getAuthor())) &&
                            (!authors[1].equals(getList().get(j).getAuthor()))) {
                        if (i != index) {
                            authors[i] = getList().get(j).getAuthor();
                        }
                        i++;
                    }
                }
            } while (i < 3);

            setAuthorsArray(authors);
        }
            quoteText.setText(getList().get(getGameCounter()).getQuote());


//        TextView author1 = (TextView) findViewById(R.id.authorGame1);
//        author1.setText(authors[0]);
//        TextView author2 = (TextView) findViewById(R.id.authorGame2);
//        author2.setText(authors[1]);
//        TextView author3 = (TextView) findViewById(R.id.authorGame3);
//        author3.setText(authors[2]);
//        author1.setId(Arrays.asList(savedTags).indexOf(savedTag));
//        ((LinearLayout) linearLayout).addView(text);
//        text.setOnClickListener(this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.authorsLayout);
            linearLayout.removeAllViewsInLayout();
            for (String author : authors) {
                TextView text = new TextView(linearLayout.getContext());
                text.setText(author);
                text.setId(Arrays.asList(authors).indexOf(author));
                ((LinearLayout) linearLayout).addView(text);
                text.setOnClickListener(this);
                if(linearLayout.getOrientation() == linearLayout.HORIZONTAL){
                    text.setPaddingRelative(4, 4, 4, 4);
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                    mlp.setMargins(10, 0, 10, 0);

                }

                if(linearLayout.getOrientation() == linearLayout.VERTICAL){
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                    mlp.setMargins(0, 8, 0, 8);
                    text.setPadding(2, 2, 2, 2);


                }
                text.setBackgroundColor(getResources().getColor(R.color.color_background));
            }
            TextView rank = (TextView) findViewById(R.id.playersRank);
            rank.setText(getRank());
        if (!getStateMarker()) {
            Button button = (Button) findViewById(R.id.nextButton);
            button.setEnabled(false);
        }else{
            decisionChecker(decision, selectedNumber);
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setStateMarker(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_game);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        if(savedInstanceState!=null){
            setLanguage(savedInstanceState.getString(KEY_LANGUAGE));
            setName(savedInstanceState.getString(KEY_NAME));
            setRotationMarker(true);

            setAuthorsArray(savedInstanceState.getStringArray(KEY_AUTHORS_ARRAY));
            setStateMarker(savedInstanceState.getBoolean(KEY_STATE));
            decision = savedInstanceState.getString(KEY_DECISION);
            selectedNumber = savedInstanceState.getInt(KEY_NUMBER);
            setGameCounter(savedInstanceState.getInt(KEY_COUNTER));
            setLengthOfTheList(QuotesKeeper.dataLength(getApplicationContext(), getLanguage()));
            setMixer(savedInstanceState.getIntArray(KEY_MIXER));
            setList(stringParser(savedInstanceState.getString(KEY_QUOTE)));
        }else {
            Intent intent = getIntent();
            setName(intent.getStringExtra(EXTRA_KEY));
            setLanguage(intent.getStringExtra(EXTRA_KEY_LANGUAGE));
            setStateMarker(false);
            setGameCounter(0);
            setLengthOfTheList(QuotesKeeper.dataLength(getApplicationContext(), getLanguage()));
            mixerForQuote(getLengthOfTheList());
            setList(QuotesKeeper.getQuotesList(getApplicationContext(), getLanguage(), 0, getMixer(), getLengthOfReadingFromDB()));
            serializedString = "";

        }
        Gson gson = new Gson();
        serializedString = gson.toJson(getList());
        TextView playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(getName());
        setRank(QuotesKeeper.rankFinder(getApplicationContext(), getName()));


        Button button = (Button) findViewById(R.id.nextButton);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateMarker(false);
                setGameCounter(getGameCounter() + 1);
                fillTheGameForm();
            }
        });

        fillTheGameForm();





    }

    private List<Quotes> stringParser(String string) {
        List<Quotes> quotesList = new ArrayList<>();

        Gson gson = new Gson();



        Type collectionType = new TypeToken<Collection<Quotes>>(){}.getType();
                quotesList = gson.fromJson(string,  collectionType);

        return quotesList;
    }

    private Boolean decisionChecker(String selectedAuthor, int number){

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.authorsLayout);
        int i = 0;
        for (String a : getAuthorsArray()){
            linearLayout.findViewById(i).setOnClickListener(null);
            i++;
        }
                Boolean wins = false;
        if (selectedAuthor.equals(getList().get(getGameCounter()).getAuthor())) {
            wins = true;
            linearLayout.findViewById(number).setBackgroundColor((GREEN));

        }else {
            linearLayout.findViewById(number).setBackgroundColor((RED));
            for (int j = 0; j<3; j++){
                if(getAuthorsArray()[j].equals(getList().get(getGameCounter()).getAuthor())){
                    linearLayout.findViewById(j).setBackgroundColor((GREEN));
                    break;
                }
            }
        }
        Button button = (Button) findViewById(R.id.nextButton);
        button.setEnabled(true);
        return  wins;
    }

    @Override
    public void onClick(View v) {
        TextView rank = (TextView) findViewById(R.id.playersRank);
        rank.setText(getRank());
        setStateMarker(true);
        selectedNumber = v.getId();
        decision = getAuthorsArray()[v.getId()];
        int wins, attempts;
        String[] pieces = getRank().split(":");
        wins = Integer.parseInt(pieces[0]);
        attempts = Integer.parseInt(pieces[1]);
        attempts++;

        if(decisionChecker(decision, selectedNumber)){
            wins++;
        }
        setRank(wins + ":" + attempts);
        TextView rank1 = (TextView) findViewById(R.id.playersRank);
        rank1.setText(getRank());
        if ((getLengthOfTheList() > (getList().size())) && (getCheckingOfExecution())) {
            setCheckingOfExecution(false);
            new GetQuotesTask().execute();
        }

    }
    private class GetQuotesTask extends AsyncTask<Void, Void, List<Quotes>> {
        @Override
        protected List<Quotes> doInBackground(Void... params) {
            List<Quotes> q = new ArrayList<Quotes>();
            q.addAll(QuotesKeeper.getQuotesList(getApplicationContext(), getLanguage(), getList().size(), getMixer(), getLengthOfReadingFromDB()));
            return q;
        }

        @Override
        protected void onPostExecute(List<Quotes> result) {
            // TODO Auto-generated method stub

//            for(Quotes quote: result) {
//                serializedString = serializedString+" !BREAK! "+quote.toString();
//            }
            setList(result);
            Gson gson = new Gson();
            serializedString = gson.toJson(getList());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        QuotesKeeper.rankUpdater(getApplicationContext(), getName(), getRank());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_game);
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
            QuotesKeeper.rankUpdater(getApplicationContext(), getName(), getRank());
            setGameCounter(0);
            Intent intent = getIntent();
            intent.putExtra(EXTRA_KEY_LANGUAGE, getLanguage());
            finish();
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_rus) {
            setLanguage("rus");
            QuotesKeeper.rankUpdater(getApplicationContext(), getName(), getRank());
            setGameCounter(0);
            Intent intent = getIntent();
            intent.putExtra(EXTRA_KEY_LANGUAGE, getLanguage());
            finish();
            startActivity(intent);
            return true;

        }else if (id == R.id.nav_rus) {
            setLanguage("mix");
            QuotesKeeper.rankUpdater(getApplicationContext(), getName(), getRank());
            setGameCounter(0);
            Intent intent = getIntent();
            intent.putExtra(EXTRA_KEY_LANGUAGE, getLanguage());
            finish();
            startActivity(intent);
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_game);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_LANGUAGE, getLanguage());
        savedInstanceState.putString(KEY_NAME, getName());
        savedInstanceState.putStringArray(KEY_AUTHORS_ARRAY, getAuthorsArray());
        savedInstanceState.putString(KEY_QUOTE, serializedString);
        savedInstanceState.putString(KEY_DECISION, decision);
        savedInstanceState.putBoolean(KEY_STATE, getStateMarker());
        savedInstanceState.putInt(KEY_NUMBER, selectedNumber);
        savedInstanceState.putInt(KEY_COUNTER, getGameCounter());
        savedInstanceState.putIntArray(KEY_MIXER, getMixer());
        Log.d("Test", "211212"+ serializedString);
    }
}
