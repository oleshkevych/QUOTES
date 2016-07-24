package com.example.vov4ik.quotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView searchingField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String[] authors, tags;
        authors = QuotesKeeper.getForSearchingActivity(getApplicationContext(), "author");
        tags = QuotesKeeper.getForSearchingActivity(getApplicationContext(), "tags");
        searchingField = (AutoCompleteTextView) findViewById(R.id.search_this_word);

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SearchActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        searchingField.setAdapter(adapter);
    }
}
