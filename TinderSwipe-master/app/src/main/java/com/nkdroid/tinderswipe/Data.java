package com.nkdroid.tinderswipe;

// class of seeker activity for retrieving recruiters profiles.
public class Data {

    private String rname;
    private String description;
    private String imagePath;
    private String location;
    private String role1;
    private String role2;
    private String role3;

    public Data(String rname){
        this.rname = rname;
    }

    public Data(String imagePath,String rname, String description, String location,String role1,String role2,String role3) {
        this.imagePath = imagePath;
        this.description = description;
        this.location = location;
        this.role1 = role1;
        this.role2 = role2;
        this.role3 = role3;
        this.rname = rname;
    }

    public String getName(){
        return rname;
    }


    public String getDescription() {
        System.out.println("lauda");
        return description;
    }
    public String getLocation(){
        System.out.println("Location:"+location);
        return location;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getRole1(){
        return role1;
    }

    public String getRole2(){
        return role2;
    }

    public String getRole3(){
        return role3;
    }


}
