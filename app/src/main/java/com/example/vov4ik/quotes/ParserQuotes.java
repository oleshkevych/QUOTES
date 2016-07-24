package com.example.vov4ik.quotes;

import java.util.ArrayList;

/**
 * Created by vov4ik on 7/20/2016.
 */
public class ParserQuotes {
    private Contents contents;
    public Contents getContents() {
        return contents;
    }

    static class Contents {
        private ArrayList<Quote> quotes;

        public ArrayList<Quote> getQuotes() {
            return quotes;
        }

        static class Quote {

            public String getQuote() {
                return quote;
            }

            public String getAuthor() {
                return author;
            }

            public String[] getTags() {
                return tags;
            }
            private String author;
            private String quote;

            private String[] tags;
        }
    }
}
