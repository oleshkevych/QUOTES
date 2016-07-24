package com.example.vov4ik.quotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.ArraySet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by vov4ik on 6/8/2016.
 */
public class DbHelper extends SQLiteOpenHelper{

    final static int DB_VERSION = 1;
    final static String DB_NAME = "Quotes.db";
    final static String TABLE_NAME = "quotes";

    final static String COLOM_NAME_ID = "ID";
    final static String COLOM_NAME_AUTHOR = "author";
    final static String COLOM_NAME_QUOTE = "quote";
    final static String COLOM_NAME_TAGS = "tags";
    final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
             COLOM_NAME_ID + " INTEGER PRIMARY KEY,"+ COLOM_NAME_AUTHOR + " TEXT," +
             COLOM_NAME_QUOTE + " TEXT," + COLOM_NAME_TAGS + " TEXT" + ");";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DbHelper mHelper;


    final static String TAGS_TABLE_NAME = "tagsForQuotes";
    final static String QUOTES_AND_AUTHORS_TABLE_NAME = "quotesAndAuthors";
    final static String COLUMN_ID_TAGS_NAME = "idForTagsTable";
    final static String COLUMN_TAGS_NAME = "tagsColumn";
    final static String COLUMN_ID_QUOTES_NAME = "idForQuotesAndAuthors";
    final static String COLUMN_QUOTES_NAME = "quotesInQuotesAndAuthorsTable";
    final static String COLUMN_AUTHORS_NAME = "authorsInQuotesAndAuthorsTable";
    final static String CONNECTION_TABLE = "connectionTableForQuotesAndTagsTables";
    final static String COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE = "idsOfQuotes";
    final static String COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE = "idsOfTags";
    final static String COLUMN_ID_IN_CONNECTION_TABLE = "idsOfConnectionTable";

    final static String CREATE_TABLE_TAGS = "CREATE TABLE IF NOT EXISTS "+TAGS_TABLE_NAME+" (" +
            COLUMN_ID_TAGS_NAME + " INTEGER PRIMARY KEY,"+ COLUMN_TAGS_NAME + " TEXT" + ");";

    final static String CREATE_TABLE_QUOTES_AND_AUTHORS = "CREATE TABLE IF NOT EXISTS "+QUOTES_AND_AUTHORS_TABLE_NAME+" (" +
            COLUMN_ID_QUOTES_NAME + " INTEGER PRIMARY KEY,"+ COLUMN_AUTHORS_NAME + " TEXT," +
            COLUMN_QUOTES_NAME + " TEXT" + ");";

    final static String CREATE_TABLE_CONNECTION = "CREATE TABLE IF NOT EXISTS "+CONNECTION_TABLE+" (" +
            COLUMN_ID_IN_CONNECTION_TABLE + " INTEGER PRIMARY KEY,"+ COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE + " LONG," +
            COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE + " LONG" + ");";


    //Ukrainian database

    final static String UKR_TAGS_TABLE_NAME = "tagsForQuotesUkr";
    final static String UKR_QUOTES_AND_AUTHORS_TABLE_NAME = "quotesAndAuthorsUkr";
    final static String UKR_CONNECTION_TABLE = "connectionTableForQuotesAndTagsTablesUkr";

    final static String UKR_CREATE_TABLE_TAGS = "CREATE TABLE IF NOT EXISTS "+UKR_TAGS_TABLE_NAME+" (" +
            COLUMN_ID_TAGS_NAME + " INTEGER PRIMARY KEY,"+ COLUMN_TAGS_NAME + " TEXT" + ");";

    final static String UKR_CREATE_TABLE_QUOTES_AND_AUTHORS = "CREATE TABLE IF NOT EXISTS "+UKR_QUOTES_AND_AUTHORS_TABLE_NAME+" (" +
            COLUMN_ID_QUOTES_NAME + " INTEGER PRIMARY KEY,"+ COLUMN_AUTHORS_NAME + " TEXT," +
            COLUMN_QUOTES_NAME + " TEXT" + ");";

    final static String UKR_CREATE_TABLE_CONNECTION = "CREATE TABLE IF NOT EXISTS "+UKR_CONNECTION_TABLE+" (" +
            COLUMN_ID_IN_CONNECTION_TABLE + " INTEGER PRIMARY KEY,"+ COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE + " LONG," +
            COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE + " LONG" + ");";


//Database with two languages (+one more column)

    final static String TAGS_TABLE_NAME_TWO_LANGUAGES = "twoLanguagesTagsForQuotes";
    final static String QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES = "twoLanguagesQuotesAndAuthors";
    final static String COLUMN_ID_TAGS_NAME_TWO_LANGUAGES = "twoLanguagesIdForTagsTable";
    final static String COLUMN_TAGS_NAME_TWO_LANGUAGES = "twoLanguagesTagsColumn";
    final static String COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES = "twoLanguagesLanguagesInTagTableColumn";
    final static String COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES = "twoLanguagesIdForQuotesAndAuthors";
    final static String COLUMN_QUOTES_NAME_TWO_LANGUAGES = "twoLanguagesQuotesInQuotesAndAuthorsTable";
    final static String COLUMN_AUTHORS_NAME_TWO_LANGUAGES = "twoLanguagesAuthorsInQuotesAndAuthorsTable";
    final static String COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES = "twoLanguagesLanguagesInQuotesAndAuthorsTable";
    final static String CONNECTION_TABLE_TWO_LANGUAGES = "twoLanguagesConnectionTableForQuotesAndTagsTables";
    final static String COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES = "twoLanguagesIdsOfQuotes";
    final static String COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES = "twoLanguagesIdsOfTags";
    final static String COLUMN_ID_IN_CONNECTION_TABLE_TWO_LANGUAGES = "twoLanguagesIdsOfConnectionTable";


    final static String CREATE_TABLE_TAGS_TWO_LANGUAGES = "CREATE TABLE IF NOT EXISTS "+TAGS_TABLE_NAME_TWO_LANGUAGES+" (" +
            COLUMN_ID_TAGS_NAME_TWO_LANGUAGES + " INTEGER PRIMARY KEY,"+ COLUMN_TAGS_NAME_TWO_LANGUAGES +
            " TEXT," +COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES +" TEXT" + ");";

    final static String CREATE_TABLE_QUOTES_AND_AUTHORS_TWO_LANGUAGES = "CREATE TABLE IF NOT EXISTS "+QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES+" (" +
            COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES + " INTEGER PRIMARY KEY,"+ COLUMN_AUTHORS_NAME_TWO_LANGUAGES + " TEXT," +
            COLUMN_QUOTES_NAME_TWO_LANGUAGES + " TEXT," + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES + " TEXT" + ");";

    final static String CREATE_TABLE_CONNECTION_TWO_LANGUAGES = "CREATE TABLE IF NOT EXISTS "+CONNECTION_TABLE_TWO_LANGUAGES+" (" +
            COLUMN_ID_IN_CONNECTION_TABLE_TWO_LANGUAGES + " INTEGER PRIMARY KEY,"+ COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES + " LONG," +
            COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES + " LONG" + ");";

//new tables for removing repeats

    final static String TAGS_TABLE_NAME_TWO_LANGUAGES_NEW = "twoLanguagesTagsForQuotesNew";
    final static String QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW = "twoLanguagesQuotesAndAuthorsNew";
    final static String COLUMN_ID_TAGS_NAME_TWO_LANGUAGES_NEW = "twoLanguagesIdForTagsTableNew";
    final static String COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW = "twoLanguagesTagsColumnNew";
    final static String COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesLanguagesInTagTableColumnNew";
    final static String COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES_NEW = "twoLanguagesIdForQuotesAndAuthorsNew";
    final static String COLUMN_QUOTES_NAME_TWO_LANGUAGES_NEW = "twoLanguagesQuotesInQuotesAndAuthorsTableNew";
    final static String COLUMN_AUTHORS_NAME_TWO_LANGUAGES_NEW= "twoLanguagesAuthorsInQuotesAndAuthorsTableNew";
    final static String COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesLanguagesInQuotesAndAuthorsTableNew";
    final static String CONNECTION_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesConnectionTableForQuotesAndTagsTablesNew";
    final static String COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesIdsOfQuotesNew";
    final static String COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesIdsOfTagsNew";
    final static String COLUMN_ID_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW = "twoLanguagesIdsOfConnectionTableNew";


    final static String CREATE_TABLE_TAGS_TWO_LANGUAGES_NEW = "CREATE TABLE IF NOT EXISTS "+TAGS_TABLE_NAME_TWO_LANGUAGES_NEW+" (" +
            COLUMN_ID_TAGS_NAME_TWO_LANGUAGES_NEW+ " INTEGER PRIMARY KEY,"+ COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW +
            " TEXT," +COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW +" TEXT" + ");";

    final static String CREATE_TABLE_QUOTES_AND_AUTHORS_TWO_LANGUAGES_NEW = "CREATE TABLE IF NOT EXISTS "+QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW+" (" +
            COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES_NEW + " INTEGER PRIMARY KEY,"+ COLUMN_AUTHORS_NAME_TWO_LANGUAGES_NEW + " TEXT," +
            COLUMN_QUOTES_NAME_TWO_LANGUAGES_NEW + " TEXT," + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES_NEW + " TEXT" + ");";

    final static String CREATE_TABLE_CONNECTION_TWO_LANGUAGES_NEW = "CREATE TABLE IF NOT EXISTS "+CONNECTION_TABLE_TWO_LANGUAGES_NEW+" (" +
            COLUMN_ID_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW + " INTEGER PRIMARY KEY,"+ COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW + " LONG," +
            COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW + " LONG" + ");";

//Table for the game. Names and ranks of the players
    final static String TABLE_FOR_NAMES_OF_PLAYERS = "tableWithNamesAndRanksOfThePlayersOfTheGame";
    final static String COLUMN_ID_OF_THE_PLAYER = "columnForIdsOfThePlayers";
    final static String COLUMN_PLAYERS_NAMES = "columnForKeepingPlayersNames";
    final static String COLUMN_RANK_OF_THE_PLAYER = "columnForKeepingTheRankOfThePlayer";
    final static String COLUMN_PASSWORD_OF_THE_PLAYER = "columnForKeepingThePasswordOfThePlayer";


    final static String CREATE_TABLE_FOR_THE_GAME = "CREATE TABLE IF NOT EXISTS "+TABLE_FOR_NAMES_OF_PLAYERS+" (" +
            COLUMN_ID_OF_THE_PLAYER+ " INTEGER PRIMARY KEY,"+ COLUMN_PLAYERS_NAMES +
            " TEXT," + COLUMN_PASSWORD_OF_THE_PLAYER + " TEXT," +COLUMN_RANK_OF_THE_PLAYER +" TEXT" + ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        mDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_TAGS);
        db.execSQL(CREATE_TABLE_QUOTES_AND_AUTHORS);
        db.execSQL(CREATE_TABLE_CONNECTION);
        db.execSQL(CREATE_TABLE);


    }
//methods for the game
    public void addNewPlayer(String nameOfThePlayer, String password){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYERS_NAMES, nameOfThePlayer);
        contentValues.put(COLUMN_PASSWORD_OF_THE_PLAYER, password);
        contentValues.put(COLUMN_RANK_OF_THE_PLAYER, "0:0");
        mDatabase.insert(
                TABLE_FOR_NAMES_OF_PLAYERS, null, contentValues);
        mDatabase.close();
    }
    public List<Game> getPlayers(){
        open();
        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TABLE_FOR_NAMES_OF_PLAYERS, null);
        cursor.moveToFirst();
        List<Game> gameList = new ArrayList<>();
        if (cursor.isFirst()) {
            do {
                gameList.add(new Game(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        mDatabase.close();
        return gameList;
    }
    public String rankFinder(String name){
        open();
        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TABLE_FOR_NAMES_OF_PLAYERS + " WHERE " + COLUMN_PLAYERS_NAMES + "='" + name+"'" , null);
        cursor.moveToFirst();
        String rank = cursor.getString(3);
        cursor.close();
        mDatabase.close();
        return rank;
    }

    public void rankUpdater(String name, String rank){
        open();
        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TABLE_FOR_NAMES_OF_PLAYERS + " WHERE " + COLUMN_PLAYERS_NAMES + "='" + name+"'" , null);
        cursor.moveToFirst();
        mDatabase.execSQL("DELETE FROM " + TABLE_FOR_NAMES_OF_PLAYERS + " WHERE " + COLUMN_ID_OF_THE_PLAYER + "=" + cursor.getLong(0));
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYERS_NAMES, name);
        contentValues.put(COLUMN_PASSWORD_OF_THE_PLAYER, cursor.getString(2));
        contentValues.put(COLUMN_RANK_OF_THE_PLAYER, rank);
        mDatabase.insert(
                TABLE_FOR_NAMES_OF_PLAYERS, null, contentValues);
        cursor.close();
        mDatabase.close();
    }
//method for SearchingActivity
    public String[] getAuthorsForSearching(){

        open();
        Set<String> set = new ArraySet<>();
        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM "
                + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES , null);
        cursor.moveToFirst();
        do{
            set.add(cursor.getString(1));
        }while ((cursor.moveToNext()));
        cursor.close();
        mDatabase.close();
        String[] returnAuthor = new String[set.size()];
        int i=0;
        for(String s: set){
            returnAuthor[i] = s;
            i++;
        }

        return returnAuthor;
    }
    public String[] getTagsForSearching(){
        open();
        Set<String> set = new ArraySet<>();
        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM "
                + TAGS_TABLE_NAME_TWO_LANGUAGES , null);
        cursor.moveToFirst();
        do{
            set.add(cursor.getString(1));
        }while ((cursor.moveToNext()));
        cursor.close();
        mDatabase.close();
        String[] returnTags = new String[set.size()];
        int i=0;
        for(String s: set){
            returnTags[i] = s;
            i++;
        }
       return returnTags;
    }
//recording data to a single table;
    public void fillData(List<Quotes> mQuotesList) {
        open();
        for (Quotes quote:mQuotesList) {
            String[] tagsExperiment = new String[quote.getTags().length];
            for (int i = 0; i<quote.getTags().length; i++){
                if ((i == 0)&&(quote.getTags().length>1)){
                    tagsExperiment[i] = quote.getTags()[i]+" ";
                }else
                if (i!=0){
                    tagsExperiment[i] = (tagsExperiment[i-1]+" "+quote.getTags()[i]);
                }
            }
            String tags = String.valueOf(quote.getTags()[0]); //(quote.getTags().length > 0)?(tagsExperiment[quote.getTags().length-1]):(""));
            ContentValues values = new ContentValues();
            values.put(COLOM_NAME_AUTHOR, quote.getAuthor());
            values.put(COLOM_NAME_QUOTE, quote.getQuote());
            values.put(COLOM_NAME_TAGS,  tags);
//            Log.d("Test", "start insert");
//            Log.d("Test", "ABOUT " + quote.getAuthor() + " " + quote.getQuote() + "++" + tags);
            mDatabase.insert(
                    DbHelper.TABLE_NAME,
                    null,
                    values);

    } mDatabase.close();
    }
//recording data into the table with the language
    public void properlyFilling(List<Quotes> mQuotesList, String language){
        open();
        int j=0;
        for (Quotes quote : mQuotesList){
            String[] tags = quote.getTags();
            j++;
            for(int i=0; i<tags.length; i++){
//                if ((tags[i].equals("null"))||(tags[i].equals(" "))||(tags[i].equals("цитаты")||(tags[i].equals("жизнь")))){
//                    tags[i] = "";
//                }
                tags[i]=tags[i].toLowerCase();
                }

            if(j>299&&!tags[0].contains("'")) {
                ContentValues valuesQuoteAndAuthor = new ContentValues();
                valuesQuoteAndAuthor.put(COLUMN_QUOTES_NAME_TWO_LANGUAGES, quote.getQuote());
                valuesQuoteAndAuthor.put(COLUMN_AUTHORS_NAME_TWO_LANGUAGES, quote.getAuthor());
                valuesQuoteAndAuthor.put(COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES, language);
                long idOfQuote = mDatabase.insert(
                        QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES,
                        null,
                        valuesQuoteAndAuthor);
                Log.d("Test", "Quote number: " + idOfQuote);
                if ((tags.length > 0)) {
                    long[] idsOfTags = new long[tags.length];
                    int i = 0;
                    for (String tag : tags) {
                        Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TAGS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_TAGS_NAME_TWO_LANGUAGES + "='" + tag + "' AND " +
                                COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES + "='" + language + "'", null);
                        cursor.moveToFirst();
                        if ((cursor.getCount() > 0)) {
                            //Log.d("Test", "Tag found: " + cursor.getString(cursor.getColumnIndex(COLUMN_TAGS_NAME)));
                            idsOfTags[i] = cursor.getLong(0);
                            cursor.close();
                        } else {
                            ContentValues valuesTags = new ContentValues();
                            valuesTags.put(COLUMN_TAGS_NAME_TWO_LANGUAGES, tag);
                            valuesTags.put(COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES, language);
                            idsOfTags[i] = mDatabase.insert(
                                    TAGS_TABLE_NAME_TWO_LANGUAGES,
                                    null,
                                    valuesTags);
                            //Log.d("Test", "Tag added: "+ Arrays.toString(idsOfTags));
                        }
                        i++;
                    }

                    for (long idOfTag : idsOfTags) {
                        ContentValues valuesConnection = new ContentValues();
                        valuesConnection.put(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES, idOfQuote);
                        valuesConnection.put(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES, idOfTag);
                        mDatabase.insert(
                                CONNECTION_TABLE_TWO_LANGUAGES,
                                null,
                                valuesConnection);
                        //Log.d("Test", "Adding in connection table: "+ idOfQuote+idOfTag);
                    }
                }
            }
        }
        mDatabase.close();
    }

//dataGetter from 3 tables without language-field
    public List<Quotes> properlyDataGetter(int startReading){
        List<Quotes> quotesList = new ArrayList<>();
        String selectQueryForConnectionTable = "SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME;
        open();
        Cursor cursorForQuotesTable = mDatabase.rawQuery(selectQueryForConnectionTable, null);
        if (startReading==0){
            startReading=1;
        }
            cursorForQuotesTable.moveToPosition(startReading);
            do {
                quotesList.add(getTagsFromConnectionTable(cursorForQuotesTable));
                if (quotesList.size()>500){
                    break;
                }
            } while (cursorForQuotesTable.moveToNext());
        cursorForQuotesTable.close();
        mDatabase.close();
        return quotesList;
    }

    private Quotes getTagsFromConnectionTable(Cursor cursorForQuotesTable){
            long quoteID = cursorForQuotesTable.getLong(0);
            Cursor cursorForConnectionTable = mDatabase.rawQuery("SELECT *  FROM "
                    + CONNECTION_TABLE + " WHERE " + COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE + "=" + quoteID , null);
            cursorForConnectionTable.moveToFirst();
        int lengthOfTagArray = 0;
        do{
            lengthOfTagArray++;
        }while (cursorForConnectionTable.moveToNext());
           // Log.d("Test", "Connection taken: " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE) + " " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE));
        String[] tags = null;
        if (lengthOfTagArray>0) {
            tags = new String[lengthOfTagArray];
            int i = 0;
            do {
                if ((cursorForConnectionTable.getCount() > 0)) {
                    long tagID = cursorForConnectionTable.getLong(cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE));
                    Cursor cursorForTagsTable = mDatabase.rawQuery("SELECT *  FROM "
                            + TAGS_TABLE_NAME + " WHERE " + COLUMN_ID_TAGS_NAME + "=" + tagID, null);
                    cursorForTagsTable.moveToFirst();
                    if ((cursorForTagsTable.getCount() > 0)) {
                        tags[i] = cursorForTagsTable.getString(cursorForTagsTable.getColumnIndex(COLUMN_TAGS_NAME));
                    }
                    cursorForTagsTable.close();
                    i++;
                }
            } while (cursorForConnectionTable.moveToNext());
        }
            Quotes quote = new Quotes(cursorForQuotesTable.getString(1), cursorForQuotesTable.getString(2), tags);

            cursorForConnectionTable.close();
            return  quote;
    }

    private DbHelper open() throws SQLException {
        mDatabase = getWritableDatabase();
        mDatabase.execSQL(CREATE_TABLE);
        mDatabase.execSQL(CREATE_TABLE_TAGS);
        mDatabase.execSQL(CREATE_TABLE_QUOTES_AND_AUTHORS);
        mDatabase.execSQL(CREATE_TABLE_CONNECTION);
        mDatabase.execSQL(CREATE_TABLE_TAGS_TWO_LANGUAGES);
        mDatabase.execSQL(CREATE_TABLE_QUOTES_AND_AUTHORS_TWO_LANGUAGES);
        mDatabase.execSQL(CREATE_TABLE_CONNECTION_TWO_LANGUAGES);
        mDatabase.execSQL(CREATE_TABLE_TAGS_TWO_LANGUAGES_NEW);
        mDatabase.execSQL(CREATE_TABLE_QUOTES_AND_AUTHORS_TWO_LANGUAGES_NEW);
        mDatabase.execSQL(CREATE_TABLE_CONNECTION_TWO_LANGUAGES_NEW);
        mDatabase.execSQL(CREATE_TABLE_FOR_THE_GAME);
        return this;
    }


    public void delete(){
        Log.d("Test", "DELETE DB opened");
        open();
//        mDatabase.execSQL("delete from " + "Name of the table");
//
//        mDatabase.execSQL("DROP TABLE IF EXISTS " + "Name of the table");
        mDatabase.delete(QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW, null, null);
        mDatabase.delete(TAGS_TABLE_NAME_TWO_LANGUAGES_NEW, null, null);
        mDatabase.delete(CONNECTION_TABLE_TWO_LANGUAGES_NEW, null, null);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + TAGS_TABLE_NAME_TWO_LANGUAGES_NEW);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + CONNECTION_TABLE_TWO_LANGUAGES_NEW);
        mDatabase.delete(TABLE_NAME, null, null);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


    }

    public List<Quotes> find(String searchWord, String searchSection, String language) {
        List<Quotes> found = new ArrayList<>();
        open();
        if (Objects.equals(searchSection, "author")){
            Cursor cursorForQuotesTable;
            if (language.equals("mix")){
                cursorForQuotesTable = mDatabase.rawQuery("SELECT *  FROM "
                        + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_AUTHORS_NAME_TWO_LANGUAGES
                        + "='" + searchWord+"'", null);
            }else {
                cursorForQuotesTable = mDatabase.rawQuery("SELECT *  FROM "
                        + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_AUTHORS_NAME_TWO_LANGUAGES
                        + "='" + searchWord + "' AND " + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES + "='" + language + "'", null);
            }
            cursorForQuotesTable.moveToFirst();
            do{
                found.add(getTagsFromConnectionTableForTwoLanguages(cursorForQuotesTable));
            }while(cursorForQuotesTable.moveToNext());
            cursorForQuotesTable.close();
        }else{
            Cursor cursorForTagsTable;
            if (language.equals("mix")) {
                cursorForTagsTable = mDatabase.rawQuery("SELECT *  FROM "
                        + TAGS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_TAGS_NAME_TWO_LANGUAGES + "='" + searchWord + "'", null);
            }else{
                cursorForTagsTable = mDatabase.rawQuery("SELECT *  FROM "
                        + TAGS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_TAGS_NAME_TWO_LANGUAGES + "='" + searchWord + "' AND "
                        + COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES + "='" + language + "'", null);
            }
            cursorForTagsTable.moveToFirst();
            long idOfSearchingTag = cursorForTagsTable.getInt(cursorForTagsTable.getColumnIndex(COLUMN_ID_TAGS_NAME_TWO_LANGUAGES));
            Cursor cursorForConnectionTable = mDatabase.rawQuery("SELECT *  FROM "
                    + CONNECTION_TABLE_TWO_LANGUAGES + " WHERE " + COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES + "=" + idOfSearchingTag , null);
            cursorForConnectionTable.moveToFirst();
            do{
                long idOfQuotes =  cursorForConnectionTable.getLong(cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES));
                Cursor cursorQuotes = mDatabase.rawQuery("SELECT *  FROM "
                        + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES + "=" + idOfQuotes , null);
                cursorQuotes.moveToFirst();
                found.add(getTagsFromConnectionTableForTwoLanguages(cursorQuotes));
                cursorQuotes.close();
            }while(cursorForConnectionTable.moveToNext());
            cursorForConnectionTable.close();
            cursorForTagsTable.close();
            Log.d("Test", "find 2");
        }
        mDatabase.close();
        return found;
    }
    public List<Quotes> getData(){
        open();
        List<Quotes> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
            do{
                //Log.d("Test", "Quote number: "+cursor.getString(1));
                Quotes quote = new Quotes(cursor.getString(1), cursor.getString(2), new String[]{cursor.getString(3)});
                list.add(quote);

            }while(cursor.moveToNext());
        mDatabase.close();
        cursor.close();
        return list;
    }


    private String[] make_array(String string) {
        return string.split(" ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
   /* private ArrayList<String> getData() {
        InputStream stream = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            stream = mContext.getAssets().open(DATA_FILE_NAME);
        }
        catch (IOException e) {
            Log.d("test", e.getMessage());
        }

        DataInputStream dataStream = new DataInputStream(stream);
        String data = "";
        try {
            while( (data=dataStream.readLine()) != null ) {
                list.add(data);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }*/
//Getter from table with two languages
    public List<Quotes> properlyDataGetterForTwoLanguages(int[] arrayOfReadingPositions, String language){
        String selectQuery;
        if (language.equals("mix")){
            selectQuery = "SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES;
        }else{
            selectQuery = "SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES + " WHERE "
                    + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES + "='" + language + "'";
        }
        List<Quotes> quotesList = new ArrayList<>();
        open();
        Cursor cursorForQuotesTable = mDatabase.rawQuery(selectQuery, null);
        for (int i=0; i<arrayOfReadingPositions.length; i++) {
            cursorForQuotesTable.moveToPosition(arrayOfReadingPositions[i]);
            quotesList.add(getTagsFromConnectionTableForTwoLanguages(cursorForQuotesTable));
        }
        cursorForQuotesTable.close();
        mDatabase.close();
        return quotesList;
    }
    private Quotes getTagsFromConnectionTableForTwoLanguages(Cursor cursorForQuotesTable){
        long quoteID = cursorForQuotesTable.getLong(0);
        Cursor cursorForConnectionTable = mDatabase.rawQuery("SELECT *  FROM "
                + CONNECTION_TABLE_TWO_LANGUAGES + " WHERE " + COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES + "=" + quoteID , null);
        cursorForConnectionTable.moveToFirst();
        int lengthOfTagArray = 0;
        do{
            lengthOfTagArray++;
        }while (cursorForConnectionTable.moveToNext());
        cursorForConnectionTable.moveToFirst();
        // Log.d("Test", "Connection taken: " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE) + " " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE));
        String[] tags = null;
        if (lengthOfTagArray>0) {
            tags = new String[lengthOfTagArray];
            int i = 0;
            do {
                if ((cursorForConnectionTable.getCount()>0)) {
                    long tagID = cursorForConnectionTable.getLong(cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES));
                    Cursor cursorForTagsTable =  mDatabase.rawQuery("SELECT *  FROM "
                            + TAGS_TABLE_NAME_TWO_LANGUAGES + " WHERE " + COLUMN_ID_TAGS_NAME_TWO_LANGUAGES + "=" + tagID , null);
                    cursorForTagsTable.moveToFirst();
                    if ((cursorForTagsTable.getCount() > 0)) {
                        tags[i] = cursorForTagsTable.getString(cursorForTagsTable.getColumnIndex(COLUMN_TAGS_NAME_TWO_LANGUAGES));
                    }
                    cursorForTagsTable.close();
                    i++;
                }
            } while (cursorForConnectionTable.moveToNext());
        }
        Quotes quote = new Quotes(cursorForQuotesTable.getString(1), cursorForQuotesTable.getString(2), tags);

        cursorForConnectionTable.close();
        return  quote;
    }

//method for getting length of full collection
    public int getLengthOfList(String language) {
        open();
        int length = 0;
        Cursor cursor;
        if (language.equals("mix")) {
            cursor = mDatabase.rawQuery("SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES, null);
        } else {
            cursor = mDatabase.rawQuery("SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES + " WHERE "
                    + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES + "='" + language + "'", null);
        }
        cursor.moveToFirst();
        do {
            length++;
        } while (cursor.moveToNext());
        cursor.close();
        mDatabase.close();
        return length;
    }
/**
    public void killDoubles(){

        for (int i=1140; i<70957; i++) {
            try{
            open();
            Cursor cursorForQuote = mDatabase.rawQuery("SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES+" WHERE "
                    +COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES+"="+i, null);
                if(i%1000 == 0){
                    Log.d("Test", "START" + i);
                }
            cursorForQuote.moveToFirst();

            //Log.d("Test", "Quote: "+ cursorForQuote.getString(2)+" "+cursorForQuote.getLong(0));
//            Cursor cursorToFind = mDatabase.rawQuery("SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES, null);
//            cursorToFind.moveToFirst();
//            do{
//                if((cursorToFind.getString(2).equals(cursorForQuote.getString(2)))&&(cursorForQuote.getLong(0)!=cursorToFind.getLong(0))){
//                    //DELETE FROM table_name WHERE some_column = some_value;
//                    //Log.d("Test", "Quote DELETE: "+ cursorToFind.getString(2)+" "+cursorToFind.getLong(0));
//                    mDatabase.execSQL("DELETE FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES
//                            + " WHERE " + COLUMN_ID_QUOTES_NAME_TWO_LANGUAGES + "=" + cursorToFind.getLong(0));
//                    mDatabase.execSQL("DELETE FROM "+CONNECTION_TABLE_TWO_LANGUAGES
//                            +" WHERE "+COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES+"="+cursorToFind.getLong(0));
//                }
//            }while (cursorToFind.moveToNext());
            //Log.d("Test", "FINISH");
//            cursorToFind.close();
            cursorForQuote.close();}
            catch(Exception e){
                Log.d("Test", "Failed because: ", e);
            }
        }

        mDatabase.close();
    }

    public void normalRemoverForDoules() {

        String language;
        List<Quotes> mQuotesList = properlyDataGetterForTwoLanguages(0, "ukr");
        language = "ukr";
        open();
        for (Quotes quote : mQuotesList) {
            String[] tags = quote.getTags();
            ContentValues valuesQuoteAndAuthor = new ContentValues();
            valuesQuoteAndAuthor.put(COLUMN_QUOTES_NAME_TWO_LANGUAGES_NEW, quote.getQuote());
            valuesQuoteAndAuthor.put(COLUMN_AUTHORS_NAME_TWO_LANGUAGES_NEW, quote.getAuthor());
            valuesQuoteAndAuthor.put(COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES_NEW, language);
            long idOfQuote = mDatabase.insert(
                    QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW,
                    null,
                    valuesQuoteAndAuthor);
            Log.d("Test", "Quote number: " + idOfQuote);
            if ((tags.length > 0)) {
                long[] idsOfTags = new long[tags.length];
                int i = 0;
                for (String tag : tags) {
                    Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TAGS_TABLE_NAME_TWO_LANGUAGES_NEW + " WHERE " + COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW + "='" + tag + "' AND " +
                            COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW + "='" + language + "'", null);
                    cursor.moveToFirst();
                    if ((cursor.getCount() > 0)) {
                        //Log.d("Test", "Tag found: " + cursor.getString(cursor.getColumnIndex(COLUMN_TAGS_NAME)));
                        idsOfTags[i] = cursor.getLong(0);
                        cursor.close();
                    } else {
                        ContentValues valuesTags = new ContentValues();
                        valuesTags.put(COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW, tag);
                        valuesTags.put(COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW, language);
                        idsOfTags[i] = mDatabase.insert(
                                TAGS_TABLE_NAME_TWO_LANGUAGES_NEW,
                                null,
                                valuesTags);
                        //Log.d("Test", "Tag added: "+ Arrays.toString(idsOfTags));
                    }
                    i++;
                }

                for (long idOfTag : idsOfTags) {
                    ContentValues valuesConnection = new ContentValues();
                    valuesConnection.put(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW, idOfQuote);
                    valuesConnection.put(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW, idOfTag);
                    mDatabase.insert(
                            CONNECTION_TABLE_TWO_LANGUAGES_NEW,
                            null,
                            valuesConnection);
                    //Log.d("Test", "Adding in connection table: "+ idOfQuote+idOfTag);
                }
            }
        }
        mQuotesList = properlyDataGetterForTwoLanguages(0, "rus");
        language = "rus";
        open();
        for (Quotes quote : mQuotesList) {
            String[] tags = quote.getTags();
            ContentValues valuesQuoteAndAuthor = new ContentValues();
            valuesQuoteAndAuthor.put(COLUMN_QUOTES_NAME_TWO_LANGUAGES_NEW, quote.getQuote());
            valuesQuoteAndAuthor.put(COLUMN_AUTHORS_NAME_TWO_LANGUAGES_NEW, quote.getAuthor());
            valuesQuoteAndAuthor.put(COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES_NEW, language);
            long idOfQuote = mDatabase.insert(
                    QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW,
                    null,
                    valuesQuoteAndAuthor);
            Log.d("Test", "Quote number: " + idOfQuote);
            if ((tags.length > 0)) {
                long[] idsOfTags = new long[tags.length];
                int i = 0;
                for (String tag : tags) {
                    Cursor cursor = mDatabase.rawQuery("SELECT *  FROM " + TAGS_TABLE_NAME_TWO_LANGUAGES_NEW + " WHERE " + COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW + "='" + tag + "' AND " +
                            COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW + "='" + language + "'", null);
                    cursor.moveToFirst();
                    if ((cursor.getCount() > 0)) {
                        //Log.d("Test", "Tag found: " + cursor.getString(cursor.getColumnIndex(COLUMN_TAGS_NAME)));
                        idsOfTags[i] = cursor.getLong(0);
                        cursor.close();
                    } else {
                        ContentValues valuesTags = new ContentValues();
                        valuesTags.put(COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW, tag);
                        valuesTags.put(COLUMN_LANGUAGES_IN_TAG_TABLE_TWO_LANGUAGES_NEW, language);
                        idsOfTags[i] = mDatabase.insert(
                                TAGS_TABLE_NAME_TWO_LANGUAGES_NEW,
                                null,
                                valuesTags);
                        //Log.d("Test", "Tag added: "+ Arrays.toString(idsOfTags));
                    }
                    i++;
                }

                for (long idOfTag : idsOfTags) {
                    ContentValues valuesConnection = new ContentValues();
                    valuesConnection.put(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW, idOfQuote);
                    valuesConnection.put(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW, idOfTag);
                    mDatabase.insert(
                            CONNECTION_TABLE_TWO_LANGUAGES_NEW,
                            null,
                            valuesConnection);
                    //Log.d("Test", "Adding in connection table: "+ idOfQuote+idOfTag);
                }
            }
        }
        Log.d("Test", "FINISH COPY");

        mDatabase.close();
    }

    public List<Quotes> getQ(String language){
        String selectQuery;

            selectQuery = "SELECT * FROM " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES_NEW+ " WHERE "
                    + COLUMN_LANGUAGES_IN_AUTHOR_AND_QUOTES_TABLE_TWO_LANGUAGES_NEW + "='" + language + "'";

        List<Quotes> quotesList = new ArrayList<>();
        open();

        Cursor cursorForQuotesTable = mDatabase.rawQuery(selectQuery, null);

        cursorForQuotesTable.moveToFirst();
        do {
            quotesList.add(getTagsFromConnectionTableForq(cursorForQuotesTable));
        } while (cursorForQuotesTable.moveToNext());
        cursorForQuotesTable.close();
        Log.d("Test", "LENGTH OF THE NEW LIST" + quotesList.size());
        mDatabase.close();
        return quotesList;
    }
    private Quotes getTagsFromConnectionTableForq(Cursor cursorForQuotesTable){
        open();
        long quoteID = cursorForQuotesTable.getLong(0);
        Cursor cursorForConnectionTable = mDatabase.rawQuery("SELECT *  FROM "
                + CONNECTION_TABLE_TWO_LANGUAGES_NEW + " WHERE " + COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW + "=" + quoteID , null);
        cursorForConnectionTable.moveToFirst();
        // Log.d("Test", "Connection taken: " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE) + " " + cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_QUOTES_IN_CONNECTION_TABLE));
        String tags = "";
        do {
            if ((cursorForConnectionTable.getCount()>0)) {
                long tagID = cursorForConnectionTable.getLong(cursorForConnectionTable.getColumnIndex(COLUMN_ID_OF_TAGS_IN_CONNECTION_TABLE_TWO_LANGUAGES_NEW));
                Cursor cursorForTagsTable =  mDatabase.rawQuery("SELECT *  FROM "
                        + TAGS_TABLE_NAME_TWO_LANGUAGES_NEW + " WHERE " + COLUMN_ID_TAGS_NAME_TWO_LANGUAGES_NEW + "=" + tagID , null);
                cursorForTagsTable.moveToFirst();
                if ((cursorForTagsTable.getCount()>0)) {
                    tags = tags + " " + cursorForTagsTable.getString(cursorForTagsTable.getColumnIndex(COLUMN_TAGS_NAME_TWO_LANGUAGES_NEW));
                }
                cursorForTagsTable.close();
            }
        }while(cursorForConnectionTable.moveToNext());
        Quotes quote = new Quotes(cursorForQuotesTable.getString(1), cursorForQuotesTable.getString(2), make_array(tags));
        cursorForConnectionTable.close();
        return  quote;
    }
    public void removeDB(){
        open();
        mDatabase.delete(QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES, null, null);
        mDatabase.delete(TAGS_TABLE_NAME_TWO_LANGUAGES, null, null);
        mDatabase.delete(CONNECTION_TABLE_TWO_LANGUAGES, null, null);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + QUOTES_AND_AUTHORS_TABLE_NAME_TWO_LANGUAGES);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + TAGS_TABLE_NAME_TWO_LANGUAGES);
        mDatabase.execSQL("DROP TABLE IF EXISTS " + CONNECTION_TABLE_TWO_LANGUAGES);

    }
**/
public void correction (Cursor cursorForQuote2, String quote, String author){
    List<Quotes> mQuotesList = new ArrayList<>();
    if ((author.length()>0)&&author.charAt(0)=='.'){
        author = author.replaceFirst("\\.", "");
    }
    if (author.contains("(")){
        author = author.replaceAll("\\(", "");
        author = author.replaceAll("\\)", "");
    }
    if(!Objects.equals(cursorForQuote2.getString(1), "")&& Objects.equals(cursorForQuote2.getString(1).substring(0, 1), " ")){
        author = author.replaceFirst(" ", "");
    }

    Log.d("Test", "TRY "+ quote+ " AUTHOR " + author);

    mQuotesList.add(new Quotes(author, quote, new String[]{cursorForQuote2.getString(3)}));
    fillData(mQuotesList);
    open();
    mDatabase.execSQL("DELETE FROM " + TABLE_NAME
            + " WHERE " + COLOM_NAME_ID + "=" + cursorForQuote2.getLong(0));
}
public void killDoubles(){

   //for (int i=1; i<23000; i++) {
    int i=0;
        try{
            open();
           Cursor cursorForQuote2 = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME
//                   +" WHERE "+COLOM_NAME_ID+"="+i
                   , null);
            i++;
            if(i%1000 == 0){
                Log.d("Test", "START " + i);
            }
            cursorForQuote2.moveToFirst();
//            Log.d("Test", "DELETE TRY" + cursorForQuote1.getString(2) + " " + cursorForQuote1.getLong(0));
//            Cursor cursorForQuote2 = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//            cursorForQuote2.moveToPosition(i);
//            do{
//                if((cursorForQuote1.getString(2).equals(cursorForQuote2.getString(2)))&&(cursorForQuote1.getLong(0)!=cursorForQuote2.getLong(0))) {
//                    mDatabase.execSQL("DELETE FROM " + TABLE_NAME
//                            + " WHERE " + COLOM_NAME_ID + "=" + cursorForQuote2.getLong(0));
//                    Log.d("Test", "DELETE TRY" + cursorForQuote1.getString(2) + " " + cursorForQuote1.getLong(0));
//                    Log.d("Test", "DELETE QUOTE" + cursorForQuote2.getString(2) + " " + cursorForQuote2.getLong(0));
//                }

//                if(i==600){
//                    break;
//                }
            do{/*
                if(cursorForQuote2.getString(2).contains(". -")&&(String.valueOf(cursorForQuote2.getString(2).charAt(cursorForQuote2.getString(2).lastIndexOf("-")) + 3))
                        .equals((String.valueOf(cursorForQuote2.getString(2).charAt(cursorForQuote2.getString(2).lastIndexOf("-")))).toUpperCase())){
                    String quote = cursorForQuote2.getString(2).substring(0, cursorForQuote2.getString(2).lastIndexOf(". -"));
                    String author = cursorForQuote2.getString(2).substring(cursorForQuote2.getString(2).lastIndexOf(". -"))+" " +cursorForQuote2.getString(1);
                   correction(cursorForQuote2, quote, author);
                } if(cursorForQuote2.getString(2).contains(" - ")&&(cursorForQuote2.getString(2).lastIndexOf(" -")<31)
                        && (String.valueOf(cursorForQuote2.getString(2).charAt(cursorForQuote2.getString(2).lastIndexOf("-")) + 3))
                        .equals((String.valueOf(cursorForQuote2.getString(2).charAt(cursorForQuote2.getString(2).lastIndexOf("-")))).toUpperCase())){
                    String quote = cursorForQuote2.getString(2).substring(0, cursorForQuote2.getString(2).lastIndexOf(". -"));
                    String author = cursorForQuote2.getString(2).substring(cursorForQuote2.getString(2).lastIndexOf(". -"))+" " +cursorForQuote2.getString(1);
                    correction(cursorForQuote2, quote, author);
                } */if(
                Objects.equals(cursorForQuote2.getString(2).substring(cursorForQuote2.getString(2).lastIndexOf(".")-1, cursorForQuote2.getString(2).lastIndexOf(".")),
                        cursorForQuote2.getString(2).substring(cursorForQuote2.getString(2).lastIndexOf(".")-1, cursorForQuote2.getString(2).lastIndexOf(".")).toUpperCase())){
                    String quote = cursorForQuote2.getString(2).substring(0, cursorForQuote2.getString(2).lastIndexOf(".") - 2);
                    String author = cursorForQuote2.getString(2).substring((cursorForQuote2.getString(2).lastIndexOf("."))-2)+" " +cursorForQuote2.getString(1);

                    correction(cursorForQuote2, quote, author);
                } /*if(cursorForQuote2.getString(2).contains("(")) {
                    String quote = cursorForQuote2.getString(2).substring(0, cursorForQuote2.getString(2).lastIndexOf("("));
                    String author = cursorForQuote2.getString(2).substring((cursorForQuote2.getString(2).lastIndexOf("("))) + " " + cursorForQuote2.getString(1);
                    correction(cursorForQuote2, quote, author);
                } if(cursorForQuote2.getString(2).contains("/")) {
                    String quote = cursorForQuote2.getString(2).substring(0, cursorForQuote2.getString(2).lastIndexOf("/"));
                    String author = cursorForQuote2.getString(2).substring((cursorForQuote2.getString(2).lastIndexOf("/")+1)) + " " + cursorForQuote2.getString(1);
                    correction(cursorForQuote2, quote, author);
                }
                if(cursorForQuote2.getString(1)
                        .contains("Автор")) {
                    String quote = cursorForQuote2.getString(2);
                    String author = "";
                    correction(cursorForQuote2, quote, author);
                }
                if(!cursorForQuote2.getString(1)
                        .equals("")&&(cursorForQuote2.getString(1).contains("(")||
                        Objects.equals(cursorForQuote2.getString(1).substring(0, 1), " "))) {
                    String quote = cursorForQuote2.getString(2);
                    String author = cursorForQuote2.getString(1);
                    correction(cursorForQuote2, quote, author);
                }*/
//                if (cursorForQuote2.getString(1)
//                        .equals(" ")||cursorForQuote2.getString(1)
//                        .equals("  ")||cursorForQuote2.getString(1)
//                        .equals("   ")){
//                    String quote = cursorForQuote2.getString(2);
//                    String author = "";
//                    correction(cursorForQuote2, quote, author);
//                }
//            if(cursorForQuote2.getString(1)
//                    .equals("")||(!Objects.equals(cursorForQuote2.getString(1).substring(0, 1), cursorForQuote2.getString(1).substring(0, 1).toUpperCase()))) {
//                mDatabase.execSQL("DELETE FROM " + TABLE_NAME
//                        + " WHERE " + COLOM_NAME_ID + "=" + cursorForQuote2.getLong(0));
//            }

                    Log.d("Test", "author " + cursorForQuote2.getString(1) + " " + cursorForQuote2.getLong(0));


        }while (cursorForQuote2.moveToNext());
        Log.d("Test", "FINISH");
//            cursorToFind.close();
//        cursorForQuote1.close();
        cursorForQuote2.close();
        }

    catch(Exception e){
        Log.d("Test", "Failed because: ", e);
    }


    mDatabase.close();
}





}
