package com.nkdroid.tinderswipe;

import java.util.List;

/**
 * Created by nilayshah on 5/28/16.
 */
public class Seeker {

    private static String location;
    private static String roles;
    private static String exp;
    private static String salary;
    private static String skills;
    private static List<String> classList;
    private static List<String> classList2;


    public Seeker() {
    }

    public Seeker(String location, String roles, String exp, String salary, String skills, List classList,List classList2) {
        this.roles = roles;
        this.location = location;
        this.exp = exp;
        this.salary = salary;
        this.skills = skills;

    }

    public static String getRoles() { return roles; }
    public static String getLocation() { return location; }
    public static String getSalary() { return salary; }
    public static String getSkills() { return skills; }



    public static void setLocation(String str) {location = str; }
    public static void setRoles(String str) { roles = str; }
    public static void setSalary(String str) { salary = str; }
    public static void setSkills(String str) { skills = str; }


    /**
     * Created by devina on 5/24/16.
     */
    public static class WorkEx {
        private static Integer years;


        public WorkEx() {
        }

        public WorkEx(Integer years) {
            this.years = years;

        }

        public static Integer getYears() { return years; }

        public static void setYears(Integer intg) { years = intg; }

    }


}
