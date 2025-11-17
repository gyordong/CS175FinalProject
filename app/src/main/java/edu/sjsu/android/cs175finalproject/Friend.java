package edu.sjsu.android.cs175finalproject;

public class Friend {
    private final String name;
    private final String username;

    public Friend(String name, String username){
        this.name = name;
        this.username = username;
    }

    public String getName(){ return name;}
    public String getUsername(){ return username;}

}
