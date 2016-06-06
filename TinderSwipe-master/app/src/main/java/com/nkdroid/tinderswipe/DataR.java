package com.nkdroid.tinderswipe;

/**
 * Created by nilayshah on 5/12/16.
 */
public class DataR {
    private String snameRec;
    private String descriptionRec;
    private String imagePathRec;
    private String locationRec;
    private String role1Rec;
    private String role2Rec;
    private String role3Rec;

    public DataR(String imagePath,String rname, String description, String location,String role1,String role2,String role3) {
        this.imagePathRec = imagePath;
        this.descriptionRec = description;
        this.locationRec = location;
        this.role1Rec = role1;
        this.role2Rec = role2;
        this.role3Rec = role3;
        this.snameRec = rname;
    }

    public String getNameR(){
        return snameRec;
    }


    public String getDescriptionR() {
        System.out.println("lauda");
        return descriptionRec;
    }
    public String getLocationR(){
        System.out.println("Location:"+locationRec);
        return locationRec;
    }

    public String getImagePathR() {
        return imagePathRec;
    }
    public String getRole1R(){
        return role1Rec;
    }

    public String getRole2R(){
        return role2Rec;
    }

    public String getRole3R(){
        return role3Rec;
    }


}
