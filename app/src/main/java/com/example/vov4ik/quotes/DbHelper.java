package com.example.vov4ik.quotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vov4ik on 6/8/2016.
 */
public class DbHelper extends SQLiteOpenHelper{

    final static int DB_VERSION = 1;
    final static String DB_NAME = "Quotes.db";
    final static String TABLE_NAME = "quotes";

    final static String DATA_FILE_NAME = "data.txt";
    final static String COLOM_NAME_ID = "ID";
    final static String COLOM_NAME_AUTHOR = "author";
    final static String COLOM_NAME_QUOTE = "quote";
    final static String COLOM_NAME_TAGS = "tags";
    final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
             COLOM_NAME_ID + " INTEGER PRIMARY KEY,"+ COLOM_NAME_AUTHOR + " TEXT," +
             COLOM_NAME_QUOTE + " TEXT," + COLOM_NAME_TAGS + " TEXT" + ")";
    Context mContext;
    private SQLiteDatabase mDatabase;
    private DbHelper mHelper;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);


    }
    public void fillData(List<Quotes> mQuotesList) {
        for (Quotes quote:mQuotesList) {
            open();
            ContentValues values = new ContentValues();
            values.put(COLOM_NAME_AUTHOR, quote.getAuthor());
            values.put(COLOM_NAME_QUOTE, quote.getQuote());
            values.put(COLOM_NAME_TAGS, ((quote.getTags().length > 2) ?
                    quote.getTags()[0] + " " + quote.getTags()[1] + " " + quote.getTags()[2] : quote.getTags()[0] + " " + quote.getTags()[1]
            ));
            mDatabase.insert(
                    DbHelper.TABLE_NAME,
                    null,
                    values);
            mDatabase.close();
            Log.d("Test", "Finish");
    }
    }

    private DbHelper open() throws SQLException {
        mHelper = new DbHelper (mContext);
        mDatabase = mHelper.getWritableDatabase();
        return this;
    }


    public void delete(){
      //  mDatabase.delete(TABLE_NAME, *, *);
        mDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public List<Quotes> getData(){
        List<Quotes> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ TABLE_NAME;
        open();
        Cursor cursor = mDatabase.rawQuery(selectQuery, null);
        Log.d("Test", "1");
        cursor.moveToFirst();
            do{
                Log.d("Test", "2+1");
                Log.d("Test", String.valueOf(cursor.getColumnIndex(COLOM_NAME_AUTHOR))+" "+ String.valueOf(cursor.getColumnIndex(COLOM_NAME_ID))+" +"+String.valueOf(cursor.getColumnIndex(COLOM_NAME_QUOTE))
                +"++ ++ "+ String.valueOf(cursor.getColumnIndex(COLOM_NAME_TAGS)));
                Log.d("Test", cursor.getString(0)+ cursor.getString(1)+ String.valueOf(make_array(cursor.getString(2))));
                Quotes quote = new Quotes(cursor.getString(1), cursor.getString(2), make_array(cursor.getString(3)));
                Log.d("Test", "3");
                list.add(quote);
                Log.d("Test", quote.getAuthor() + quote.getQuote()+quote.getTags()[0] + quote.getTags()[1]);

            }while(cursor.moveToNext());
        Log.d("Test", "finish");
        mDatabase.close();
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
}
