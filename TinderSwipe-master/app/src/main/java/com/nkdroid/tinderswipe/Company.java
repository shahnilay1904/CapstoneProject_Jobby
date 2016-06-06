package com.nkdroid.tinderswipe;

/**
 * Created by nilayshah on 5/28/16.
 */
public class Company {


    private static String location;
    private static String roles;
    private static String exp;
    private static String skillsDesired;
    private static String salaryOffered;

    public Company() {
    }

    public Company(String location, String roles, String exp, String skillsDesired, String salaryOffered,String compRoles) {
        this.location = location;
        this.roles = roles;
        this.exp = exp;
        this.skillsDesired = skillsDesired;
        this.salaryOffered = salaryOffered;
    }

    public static String getLocation() { return location; }
    public static String getRoles() { return roles; }
    public static String getExp() { return exp; }
    public static String getSkillsDesired() { return skillsDesired; }
    public static String getSalaryOffered() { return salaryOffered; }

    public static void setLocation(String str) { location = str; }
    public static void setRoles(String str) { roles = str; }
    public static void setExp(String str) { exp = str; }
    public static void setSkillsDesired(String str) { skillsDesired = str; }
    public static void setSalaryOffered(String str) { salaryOffered = str; }
}
