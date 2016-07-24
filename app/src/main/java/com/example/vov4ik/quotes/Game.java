package com.example.vov4ik.quotes;

/**
 * Created by vov4ik on 7/11/2016.
 */
public class Game {
    private String name;
    private String password;
    private String rank;

    public Game(String name, String password,  String rank){
        this.name = name;
        this.password = password;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
