package com.nkdroid.tinderswipe;

import java.util.ArrayList;

/**
 * Created by nilayshah on 5/25/16.
 */
public class User {
    private String name;
    private int imageID;
    private String location;
    private String id;

    public User(String name, int i, String location, String id) {
        imageID = i;
        this.name = name;
        this.location = location;
        this.id = id;


    }

    public String getidentity(){return id;}

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }

    public String getDescription() {
        return location;
    }

//    public static ArrayList<User> getUsers() {
//        ArrayList<User> users = new ArrayList<User>();
//        users.add(new User("Harry", "San Diego"));
//        users.add(new User("Marla", "San Francisco"));
//        users.add(new User("Sarah", "San Marco"));
//        return users;
//
//
//    }
}
