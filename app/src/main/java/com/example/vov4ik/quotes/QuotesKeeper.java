package com.example.vov4ik.quotes;

import android.content.Context;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by vov4ik on 5/9/2016.
 */
public class QuotesKeeper  {




    public QuotesKeeper (){

    }

    public List<Quotes> getQuotesList() {
        List<Quotes> mQuotesList = new ArrayList<Quotes>();
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

        return mQuotesList;
    }

    public List<Quotes> find(String searchWord, String searchSection){
        List<Quotes> finedList = new ArrayList<>();
        List<Quotes> list = getQuotesList();
        for (Quotes quotes :list) {
            if (Objects.equals(searchSection, "author")){
                if (Objects.equals(quotes.getAuthor(), searchWord)){
                    finedList.add(quotes);
                }
            }else{
                for (String tag : quotes.getTags()) {
                    if (Objects.equals(tag, searchWord)){
                        finedList.add(quotes);
                    }
                }
            }

        }
        return finedList;
    }

}
