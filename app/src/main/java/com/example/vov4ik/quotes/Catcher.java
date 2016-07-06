package com.example.vov4ik.quotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


/**
 * Created by vov4ik on 6/10/2016.
 */
public class Catcher {

    public static List<Quotes> purser(String html) {

        List<Quotes> mQuotesList = new ArrayList<>();
        Quotes quotes;

        String[] myTags = { "удача","успех","наказание","история", "короли","народ", "саркастичные цитаты","друзья","грустные","смерть", "красивые","жизнь",
                "мозг","мышление","стереотипы","хорошо","люди","человек","жизненные цитаты", "путь", "ошибки", "мотивирующие цитаты", "мудрые цитаты",
                "мысли", "счастье", "управление", "отношения","честь","обман","деньги",

                "Удача","Успех","Наказание","История", "Короли","Народ", "Саркастичные цитаты","Друзья","Грустные","Смерть", "Красивые","Жизнь",
                "Мозг","Мышление","Стереотипы","Хорошо","Человек","Жизненные цитаты", "Люди", "Путь", "Ошибки", "Мотивирующие цитаты", "Мудрые цитаты",
                "Мысли", "Счастье", "Управление", "Отношения","Честь","Обман","Деньги"};
        Document doc = Jsoup.parse(html);
        Elements article = doc.select("div#content");
//        Log.d("Test", "article "+article.text());
//        Log.d("Test", "article size "+article.size());
        if(article.size()<1) {
            article = doc.select("font[class=\"statusy\"]");
//            Log.d("Test", "Test "+article.size());
        }
        //Log.d("Test", "Test");
        //Log.d("Test", "Text article "+article.text());

        for (Element i : article) {
            Elements p = i.select("p");
            //Log.d("Test", "Text p "+p.text());
            for (Element j : p) {
                Log.d("Test", "J "+j.text());
                if (j.select("p[style]").text().length()>0){
//                    Log.d("Test", "BREAK "+j.text());
                    break;
                }
                String quote, author = "";

                    String allTextQuoteAndAuthor = j.text();
//                    Log.d("Test", "Text allTextQuoteAndAuthor "+allTextQuoteAndAuthor);
                if (allTextQuoteAndAuthor.contains(".")&&(allTextQuoteAndAuthor.lastIndexOf(".")<allTextQuoteAndAuthor.length()-2)
                        &&(allTextQuoteAndAuthor.lastIndexOf(".")+20>allTextQuoteAndAuthor.length())&&(!allTextQuoteAndAuthor.equals("Ще цитати і вислови про кохання:"))) {
                    author = allTextQuoteAndAuthor.substring(allTextQuoteAndAuthor.lastIndexOf(".") + 1).replaceAll("[^а-яА-Яі ]", "");
                    quote = allTextQuoteAndAuthor.substring(0, allTextQuoteAndAuthor.lastIndexOf(".") + 1);
                }else{
                    quote = allTextQuoteAndAuthor;
                }
           /*  Elements author = i.select("a[title=\"Автор цитаты\"]");
            //Log.d("Test", "Author: "+ author.text());
            //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));

            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из фильма\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из фильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }

            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из книги\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из книги");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }

            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из сериала\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из сериала");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из мультфильма\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Самиздат\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из аниме\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Исполнитель\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из игры\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из комикса\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }
            if (author.text() == "") {
                author = i.select("a[title=\"Цитата из телешоу\"]");
                //Log.d("Test", "AuthorTrue: Checked Цитата из мультфильма");
                //Log.d("Test", "Author: "+ author.text());
                //Log.d("Test", "AuthorTrue: "+String.valueOf(author.text() == ""));
            }

            Elements tags = i.select("a[href]");
            String tag = tags.text();
            String stringTagsMode = tag.replaceFirst(author.text(), "");
             String stringTags = stringTagsMode.replaceFirst("Сообщить об ошибке.+", "");
            */

            //String author = "Уінстон Черчіль";
            if ((quote.length() > 2)&&(quote.length() < 200)) {
                Log.d("Test", "Quote: " + quote);
                Log.d("Test", "Author: " + author);
                //Log.d("Test", "Author: " + stringTags);
                //Log.d("Test", "String Tags: " + stringTags);
                //Log.d("Test", "next page: "+catcherForNextPage(html));
/*
                int counter = 0, j=0;
                for (String singleTag: myTags){
                    if (stringTags.contains(singleTag)){
                        counter++;

                    }
                }
                String[] tagsForQuote = new String[counter];
                for (String singleTag: myTags){
                    if (stringTags.contains(singleTag)){
                        tagsForQuote[j]=singleTag;
                        j++;
                    }
                }*/
            String[] tagsForQuote = {""};
                mQuotesList.add(new Quotes(author, quote, tagsForQuote));

            }
        }

        }
        return mQuotesList;
    }
    public static String catcherForNextPage(String html){
        Document doc = Jsoup.parse(html);
        String fullPath="";
        Elements tagName = doc.select("tbody");
        Elements path = tagName.select("a[class=\"rolloverdalee\"]");
        for (Element i: path){
            fullPath = i.attr("href");//("http://citaty.info").concat(path.attr("href"));
//            Log.d("Test", "next page: " + fullPath);

        }

        return fullPath;

    }

    public static String html(String url) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);


        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "Windows-1251"));//change cherset
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {//Log.d("Test", line);
            str.append(line);
            //Log.d("Test", String.valueOf(str.length()));

        }
        in.close();
        String html = new String(str);
        // Log.d("Test", "HTML");
        //Log.d("Test", html);

        return html;
    }

    public static String[] pageStealer(String html){
        Document doc = Jsoup.parse(html);
        String[] path=new String[972];
        Elements tagName = doc.select("table");
        int i = 0;
        for(Element tagLi: tagName) {
            Elements selectedLI = tagLi.select("li");
            for (Element tagA: selectedLI) {
                Elements selectedPath = tagA.select("a[href]");
//                Log.d("Test", selectedPath.attr("href") + " "+ selectedPath.text());
                path[i] = selectedPath.attr("href");
                path[i+1] = selectedPath.text();
                i=i+2;
            }
        }
//        Log.d("Test","I="+i);
        return path;
    }










       /* Pattern pattern = Pattern.compile("(<p>).+(</p>)");
        Matcher m = pattern.matcher(html);
        if (m.find()){
        Log.d("Test", String.valueOf(m.start()+" finished "+m.end()));}
        Log.d("Test", String.valueOf(m.find()));


    }
    /*public byte[] getUrlBytes(String urlSpec) throws IOException {
        URLConnection connection = null;
        try {
            URL url = new URL(urlSpec);
            connection =  url.openConnection();
           // connection.setConnectTimeout(3000);
            connection.connect();



            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            InputStream in = new BufferedInputStream(url.openStream(),8192);


            //connection.getInputStream();
           // if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
             //   throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            //} else {
                int bytesRead;
                byte buffer[] = new byte[1024];

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);

                }
                Log.d("Test", "VYUGBJIK1");
            Log.d("Test", "2"+String.valueOf(out.toByteArray().length));


            in.close();
                return out.toByteArray();
           // }
        } finally {
            Log.d("Test", "FINISH CONNECTING");

        }
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        String html = "";
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();
        html = str.toString();
        return html;*/

   /* }

    public String getUrlString(String urlSpec) throws IOException {
         /*   // Get The Site and Parse it
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        InputStream in = connection.getInputStream();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
        }
       /* Document doc = Jsoup.connect(urlSpec).get();
            // Select Table
            Element table = doc.select(".mon_list").first();
     //   Log.d("Test", table.text());
String g = null;
            Iterator<Element> lines = doc.select("p").iterator();

            while (lines.hasNext()) {
                Element line = lines.next();
                Log.d("Test", "TD text : "+lines.next().text());
                g= g+doc.select("p").text();
                Log.d("Test", "12134355412312.134.121231"+String.valueOf(g.length()));
            }

        return new String(getUrlBytes(urlSpec));
       // return g;
    }

    public void webViewTestMethod(String url, View rootView) {
        WebView myWebView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(url);
    }

public String web(String urlGiven) throws IOException {
    URL url = new URL(urlGiven);
    URLConnection con = url.openConnection();
    //Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
   // Matcher m = p.matcher(con.getContentType());
    /* If Content-Type doesn't match this pre-conception, choose default and
     * hope for the best.
   //String charset = m.matches() ? m.group(1) : "ISO-8859-1";
    Reader r = new InputStreamReader(con.getInputStream());
    StringBuilder buf = new StringBuilder();
    while (true) {
        int ch = r.read();
        if (ch < 0)
            break;
        buf.append((char) ch);
    }
    String str = buf.toString();
    return  str;
}
}*/


}