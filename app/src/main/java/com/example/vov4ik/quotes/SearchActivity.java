package com.example.vov4ik.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView searchingField;
    private TextView authorTextView;
    private TextView tagsTextView;
    private TextView showText;
    private  LinearLayout linearLayout;
    private final String EXTRA_KEY_FOND = "com.example.vov4ik.quotesSearching_keys";
    private final String KEY_STATE_FOR_CATEGORY = "category";
    private final String KEY_STATE_FOR_ENTERED_TEXT = "enteredText";
    private final String KEY_STATE_FOR_SHOWALL_BUTTON = "checkerForShowAllButton";
    String[] array;
    private String category = "";
    private String enteredText;
    private Boolean checkShowing = false;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getCheckShowing() {
        return checkShowing;
    }

    public void setCheckShowing(Boolean checkShowing) {
        this.checkShowing = checkShowing;
    }

    public String getEnteredText() {
        return enteredText;
    }

    public void setEnteredText(String enteredText) {
        this.enteredText = enteredText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final String[] authors, tags;
        authors = QuotesKeeper.getForSearchingActivity(getApplicationContext(), "author");
        tags = QuotesKeeper.getForSearchingActivity(getApplicationContext(), "tags");
        for (int i=0;i<authors.length; i++){
            if ((authors[i].length()<3)||(authors[i].length()>50)){
                authors[i] = "";
            }
        }
        Arrays.sort(authors);
        Arrays.sort(tags);

        Log.d("Test", "length: "+ authors.length+" "+tags.length);
        searchingField = (AutoCompleteTextView) findViewById(R.id.search_this_word);
        authorTextView = (TextView) findViewById(R.id.search_the_author);
        assert authorTextView != null;
        authorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText.setText(R.string.showAllText);
                if (linearLayout!=null) {
                    linearLayout.removeAllViews();
                }
                setCategory("author");
                List<String> list = new ArrayList<String>();
                Collections.addAll(list, authors);
                searchingField.setHint(R.string.hint_author_search);
                addValuesToAutoComplete(list, "author");
            }
        });
        tagsTextView = (TextView) findViewById(R.id.search_the_tag);
        assert tagsTextView != null;
        tagsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText.setText(R.string.showAllText);
                if (linearLayout!=null) {
                    linearLayout.removeAllViews();
                }
                setCategory("tags");
                List<String> list = new ArrayList<String>();
                Collections.addAll(list, tags);
                searchingField.setHint(R.string.hint_tag_for_search);
                addValuesToAutoComplete(list, "tags");

            }
        });

        showText = (TextView) findViewById(R.id.showAll);
        assert showText != null;
        showText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!authorTextView.isEnabled()) {
                    array = authors;
                } else if (!tagsTextView.isEnabled()) {
                    array = tags;
                } else {
                    array = null;
                }

                linearLayout = (LinearLayout) findViewById(R.id.layoutForShowing);
                assert linearLayout != null;
                if (array != null) {
                if (!(showText.getText().equals(getResources().getString(R.string.hideAllText)))) {
                    setCheckShowing(true);
                    showText.setText(R.string.hideAllText);
                    Log.d("Test", "onClick on button");
                    fillLayout(array);

                    }else{
                    showText.setText(R.string.showAllText);
                    linearLayout.removeAllViews();
                    setCheckShowing(false);
                }
                }else{
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text = "Select the category!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        final Button startSearching = (Button) findViewById(R.id.startSearching);
        assert startSearching != null;
        startSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "onClick start searching");
                String searchingWord = searchingField.getText().toString();
                String searchingSection = "nothing";
                startNewSearching(searchingWord, searchingSection);
            }
        });

        if (savedInstanceState!=null){
            if(!(savedInstanceState.getString(KEY_STATE_FOR_CATEGORY).equals(""))){
                if (savedInstanceState.getString(KEY_STATE_FOR_CATEGORY).equals("author")) {
                    authorTextView.setEnabled(false);
                } else {
                    tagsTextView.setEnabled(false);
                }
            }
            searchingField.setText(savedInstanceState.getString(KEY_STATE_FOR_ENTERED_TEXT));
            if (savedInstanceState.getBoolean(KEY_STATE_FOR_SHOWALL_BUTTON)){
                if (!authorTextView.isEnabled()) {
                    array = authors;
                } else if (!tagsTextView.isEnabled()) {
                    array = tags;
                }
                showText.setText(R.string.hideAllText);
                fillLayout(array);

            }
            setCategory(savedInstanceState.getString(KEY_STATE_FOR_CATEGORY));
            setCheckShowing(savedInstanceState.getBoolean(KEY_STATE_FOR_SHOWALL_BUTTON));
            setEnteredText(savedInstanceState.getString(KEY_STATE_FOR_ENTERED_TEXT));
        }
    }
    private void fillLayout(String[] array){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutForShowing);
        for (String s : array) {
            if(s.length()>2) {
                TextView text = new TextView(linearLayout.getContext());
                text.setText(s);
                text.setId(Arrays.asList(array).indexOf(s));
                ((LinearLayout) linearLayout).addView(text);
                text.setOnClickListener(this);
                text.setPadding(10, 10, 10, 10);
                //text.setTextSize(R.dimen.big_text);
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
                mlp.setMargins(0, 15, 0, 15);
                text.setBackground(getResources().getDrawable(R.drawable.shape));
            }
        }
    }
    private void addValuesToAutoComplete(List<String> collection, String string) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        if (string.equals("author")){
            authorTextView.setEnabled(false);
            tagsTextView.setEnabled(true);
            setCategory("author");
        }else if(string.equals("tags")){
            authorTextView.setEnabled(true);
            tagsTextView.setEnabled(false);
            setCategory("tags");
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SearchActivity.this,
                        android.R.layout.simple_dropdown_item_1line, collection);

        searchingField.setAdapter(adapter);
    }



    private void startNewSearching(String searchingWord, String searchingSection){
        Context context = getApplicationContext();
        Log.d("Test", "NEW");

        int duration = Toast.LENGTH_SHORT;
        if (!authorTextView.isEnabled()){
            searchingSection = "author";
        }else if(!tagsTextView.isEnabled()) {
            searchingSection = "tag";
        }
        if (searchingSection.equals("nothing")){
            CharSequence text = "Select the category!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else if(searchingWord.equals("")){
            CharSequence text = "Enter text for searching!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else if(!searchingWord.equals("")){
            List<Quotes> list = QuotesKeeper.find(searchingWord, searchingSection, getApplicationContext(), "mix");
            if (list.size()>0) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                String[] transport = {searchingWord, searchingSection, "mix"};
                intent.putExtra(EXTRA_KEY_FOND, transport);
                startActivity(intent);
                finish();
            }else {
                CharSequence text = "Searching word didn't find!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("Test", "onClick layout");
        String searchingWord = array[v.getId()];
        String searchingSection = "nothing";
        startNewSearching(searchingWord, searchingSection);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_STATE_FOR_CATEGORY, getCategory());
        savedInstanceState.putString(KEY_STATE_FOR_ENTERED_TEXT, getEnteredText());
        savedInstanceState.putBoolean(KEY_STATE_FOR_SHOWALL_BUTTON, getCheckShowing());
    }

    @Override
    protected void onStop() {
        if ("Show All".equals(showText.getText().toString())){
         setCheckShowing(false);
        }else{
            setCheckShowing(true);
        }
        setEnteredText(searchingField.getText().toString());
        super.onStop();
    }
}
