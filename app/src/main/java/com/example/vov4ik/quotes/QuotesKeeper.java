package com.example.vov4ik.quotes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by vov4ik on 5/9/2016.
 */
public class QuotesKeeper{




    public QuotesKeeper (){
    }

    public static List<Quotes> getQuotesList(Context context, String language, int startPosition) {
        List<Quotes> mQuotesList = new ArrayList<Quotes>();
        DbHelper mDbHelper = new DbHelper(context);
        int lengthOfReadingInDatabase = MainActivity.getLengthOfReadingInDatabase();
        int[] arrayOfReadingPositions = new int[lengthOfReadingInDatabase];
        int[] mixer = MainActivity.getMixerForQuotesList();
        if(mixer.length-startPosition<lengthOfReadingInDatabase){
            System.arraycopy(mixer, startPosition, arrayOfReadingPositions, 0, mixer.length-startPosition);
        }else {
            System.arraycopy(mixer, startPosition, arrayOfReadingPositions, 0, lengthOfReadingInDatabase);
        }
        Log.d("Test", "mixerForQuotesList QuotesKeeper: " + Arrays.toString(arrayOfReadingPositions));
        mQuotesList = mDbHelper.properlyDataGetterForTwoLanguages(arrayOfReadingPositions, language);


/*

         //String[] tags = new String[]{"Життя", "Мотивація"};
//1
        //Collections.addAll(tags, "Життя", "Мотивація");
        mQuotesList.add(new Quotes("Уінстон Черчіль", "Хочеш бути великим - приготуйся тримати відповідь за все.", new String[]{"Життя", "Мотивація"}));

//2
       //tags.clear();
       // Collections.addAll(tags, "Життя", "Друзі");
        mQuotesList.add(new Quotes("Оскар Уайлд", "Усі співчувають нещастям своїх друзів, та тільки дехто радіє їх успіхам.",  new String[]{"Життя", "Друзі"}));
//3
        //tags.clear();
       // Collections.addAll(tags, "Сарказм", "Люди", "Суспільство");
        mQuotesList.add(new Quotes("Оскар Уайлд", "Я чув стільки наклепів на Вас, що у мене немає сумнівів: Ви - прекрасна людина!", new String[]{"Сарказм", "Люди", "Суспільство"}));
//4
        //tags.clear();
       // Collections.addAll(tags, "Життя", "Мораль");
        mQuotesList.add(new Quotes("Еріх Марія Ремарк", "Вільний лише той, хто втратив все для чого жив.", new String[]{"Життя", "Мораль"}));
//5
        //ags.clear();
        //Collections.addAll(tags, "Суспільство", "Сарказм", "Люди");
        mQuotesList.add(new Quotes("Уінстон Черчіль", "Кращий аргумент проти димократії - п'ятихвилинна розмова з простим виборцем.", new String[]{"Суспільство", "Сарказм", "Люди"}));
//6
        //tags.clear();
        //Collections.addAll(tags, "Удача", "Мораль");
        mQuotesList.add(new Quotes("Марк Тулій Цицерон", "Фортуна не тільки сама сліпа, а й сліпить кожного, кого обійме.", new String[]{"Удача", "Мораль"}));
//7
        //tags.clear();
        //Collections.addAll(tags, "Мудрість", "Мораль", "Сарказм");
        mQuotesList.add(new Quotes("Фаіна Раневська", "Якщо людина зробила тобі ЗЛО — ти дай їй цукерку, вона тобі ЗЛО — ти їй цукерку… І так до тих пір, поки у цієї тварюки не розвинеться цукровий діабет.", new String[]{"Мудрість", "Мораль", "Сарказм"}));
//8
        //tags.clear();
        //Collections.addAll(tags, "Сарказм", "Люди");
        mQuotesList.add(new Quotes("Фаіна Раневська", "Є такі люди, до яких просто хочеться підійти і поцікавитися, чи складно без мізків жити...", new String[]{"Сарказм", "Люди"}));
//9
        //tags.clear();
       // Collections.addAll(tags, "Доброта", "Мораль", "Життя");
        mQuotesList.add(new Quotes("Фрідріх Ніцше", "Стережiся добрих і праведних! Вони люблять розпинати тих, хто винайде для себе свою власну чесноту.", new String[]{"Доброта", "Мораль", "Життя"}));
//10
        //tags.clear();
       // Collections.addAll(tags, "Суспільство", "Люди", "Сарказм");
        mQuotesList.add(new Quotes("Джордж Бернард Шоу", "Звання і титули придумані для тих, чиї заслуги перед країною безперечні, але народові цієї країни невідомі.", new String[]{"Суспільство", "Люди", "Сарказм"}));
//11
        //tags.clear();
        //Collections.addAll(tags, "Мораль", "Життя");
        mQuotesList.add(new Quotes("Фрідріх Ніцше", "Хто бореться з чудовиськами, тому слід остерігатися, щоб самому при цьому не стати чудовиськом. І якщо ти довго дивишся в безодню, то безодня теж дивиться в тебе.", new String[]{"Мораль", "Життя"}));
        *///mDbHelper.fillData(mQuotesList);
        return mQuotesList;
    }



    public List<Quotes> find( String searchWord, String searchSection, Context context, String language){
        List<Quotes> finedList = new ArrayList<>();
        DbHelper mDbHelper = new DbHelper(context);
//        List<Quotes> list = getQuotesList(context);
//        for (Quotes quotes :list) {
//            if (Objects.equals(searchSection, "author")){
//                if (Objects.equals(quotes.getAuthor(), searchWord)){
//                    finedList.add(quotes);
//                }
//            }else{
//                for (String tag : quotes.getTags()) {
//                    if (Objects.equals(tag, searchWord)){
//                        finedList.add(quotes);
//                    }
//                }
//            }
//
//        }

        return mDbHelper.find(searchWord, searchSection, language);//finedList;
    }

    public static int dataLength(Context context, String language){

        int length = new DbHelper(context).getLengthOfList(language);
        return length;
    }

}
