package com.example.vov4ik.quotes;

import java.util.ArrayList;

/**
 * Created by vov4ik on 7/10/2016.
 */
public class JsonParser {
    private Success success;
    private Contents contents;

    static class Success {
        private String total;
    }
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

            private String quote;
            private String length;
            private String author;
            private String[] tags;
            private String category;
            private String date;
            private String title;
            private String background;
            private String id;
        }
    }
}
