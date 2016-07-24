package com.example.vov4ik.quotes;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vov4ik on 7/10/2016.
 */
public class JsonGetterAndParser {
public  static List<Quotes> jsonGetterAndParser(Context context) {
    final List<Quotes> q = new ArrayList<Quotes>();

    String url = "http://quotes.rest/qod.json";
    int i = 0;
    final int[] j = {0};
    do {
        i++;
        // new RetrieveStream().execute(url);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Test", "Response: " + response.toString());
                        Gson gson = new Gson();
                        JsonParser jp = gson.fromJson(response.toString(), JsonParser.class);
                        q.add(new Quotes(jp.getContents().getQuotes().get(0).getAuthor(), jp.getContents().getQuotes().get(0).getQuote(), jp.getContents().getQuotes().get(0).getTags()));


                        j[0]++;
                        if (j[0] == 10) {
                            Log.d("Test", "Response0!: " + q.size());
                        } else {
                            Log.d("Test", "Response: " + q.size());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Test", "Error1: " + error.toString());
                    }
                });
        //Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
//                    Log.d("Test", "JsonObjectRequest: " + jsObjRequest.toString());
//                    Log.d("Test", "JsonObjectRequest: " + jsObjRequest.getMethod());
        Log.d("Test", "JsonObjectRequest: " + jsObjRequest.getSequence());


    } while (i < 1000);
    return q;
}

}
