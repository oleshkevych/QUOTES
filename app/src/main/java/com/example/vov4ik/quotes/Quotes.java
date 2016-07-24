package com.example.vov4ik.quotes;

import java.util.Arrays;
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

//    @Override
//    public String toString() {
//        String tags = "";
//        for (int i=0; i<getTags().length; i++){
//            if (getTags()[i].length()>3) {
//                if (i < getTags().length - 1) {
//                    tags = tags + "\"" + getTags()[i] + "\",";
//                } else {
//                    tags = tags + "\"" + getTags()[i] + "\"";
//                }
//            }
//        }
//        if (tags.length()<3){
//            tags = "\" \"";
//        }
//        return "\"contents\": {\n" +
//                "        \"quotes\": [\n" +
//                "            {\n" +
//                "                \"author\": \"" + mAuthor + "\","+
//                "                \"quote\": \""+ mQuote +"\",\n" +
//                "                \"tags\": [\n" +tags+
//                "                ]\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "}";
//    }

    @Override
    public String toString() {
        return "Quotes{" +
                "Author='" + mAuthor + '\'' +
                ", Quote='" + mQuote + '\'' +
                ", Tags=" + Arrays.toString(mTags) +
                '}';
    }

    public void setTags(String[] tags) {
        this.mTags = tags;

    }
}
