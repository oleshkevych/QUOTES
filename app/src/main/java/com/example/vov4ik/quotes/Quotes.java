package com.example.vov4ik.quotes;

import java.util.List;
import java.util.UUID;

/**
 * Created by vov4ik on 5/9/2016.
 */
public class Quotes {


    private UUID mId;



    private String mAuthor;
    private String mQuote;
    private String[] mTags;

    public Quotes(String mAuthor, String mQuote, String[] mTags){

        this.mAuthor = mAuthor;
        this.mQuote = mQuote;
        this.mTags = mTags;
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String[] getTags() {
        return mTags;
    }

    public String getQuote() {
        return mQuote;
    }


    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public void setQuote(String quote) {
        this.mQuote = quote;
    }

    public void setTags(String[] tags) {
        this.mTags = tags;
    }
}
