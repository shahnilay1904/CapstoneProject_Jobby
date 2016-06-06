package com.nkdroid.tinderswipe;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by nilayshah on 5/28/16.
 */
public class SpecsCalculation {
    static Seeker s = new Seeker();
    static GenSimilarity cs = new GenSimilarity();
    public static List<String> myList = new ArrayList<>();
    public static List<String> lt ;
    static Seeker.WorkEx we = new Seeker.WorkEx();
    public static Integer temp = 0;
    static SortedMap<String, Double> sList = new TreeMap<String, Double>();


    public static void secondFunc(final String seekr, final String loc, final String roles, final String skills, final String salary, final Integer exp, final String username) {
        Firebase SeekerRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker");
        SeekerRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                temp = 0;
                s.setLocation(snapshot.child(seekr + "/Location").getValue().toString());
                System.out.println("LOCATION: " + seekr + " " + snapshot.child(seekr + "/Location").getValue());
                s.setRoles(snapshot.child(seekr + "/Roles").getValue().toString());
                System.out.println("ROLES:" + seekr + snapshot.child(seekr + "/Roles").getValue());
                s.setSalary(snapshot.child(seekr + "/Expected Salary").getValue().toString());
                s.setSkills(snapshot.child(seekr + "/Skillset").getValue().toString());
                System.out.println("Details of company: " + loc + " , " + roles + " , " + salary + " , " + skills + "," + exp);
                System.out.println("Details of " + seekr + ": " + s.getLocation() + " , " + s.getRoles() + " , " + s.getSalary() + " , " + s.getSkills());
                System.out.println("Location similarity between c1," + seekr + " : " + cs.cosineSimilarity(s.getLocation(), loc));
                System.out.println("Roles similarity between c1, " + seekr + " : " + cs.cosineSimilarity(s.getRoles(), roles));
                System.out.println("Salary similarity between c1, " + seekr + " : " + cs.cosineSimilarity(s.getSalary(), salary));
                System.out.println("Skills similarity between c1, " + seekr + " : " + cs.cosineSimilarity(s.getSkills(), skills));
                final double cs_loc = cs.cosineSimilarity(s.getLocation(), loc);
                final double cs_roles = cs.cosineSimilarity(s.getRoles(), roles);
                final double cs_salary = cs.cosineSimilarity(s.getSalary(), salary);
                final double cs_skills = cs.cosineSimilarity(s.getSkills(), skills);
                Firebase WorkExpRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker/" + seekr + "/Work Experience");
                WorkExpRef.addValueEventListener(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println("Count of children (Work Exp): " + snapshot.getChildrenCount());
                        if (snapshot.getChildrenCount() == 0) {
                            we.setYears(0);
                        }
                        temp = 0;
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            System.out.println("Work Exp:POSTSNAPSHOT: " + postSnapshot.child("We2").getValue());
                            Integer exp2 = (int) (long) (postSnapshot.child("We2").getValue());
                            System.out.println("Exp: " + exp2);
                            we.setYears(temp + exp2);
                            temp = we.getYears();
                            System.out.println("TEMP: " + temp);

                        }
                        System.out.println("Total EXP from in: " + we.getYears());
                        Integer seekerExp = we.getYears();
                        lt = calcSimilarity(seekr, exp, seekerExp, cs_loc, cs_roles, cs_salary, cs_skills);

                        Firebase r = new Firebase("https://sizzling-torch-8124.firebaseio.com/");
                        Firebase algoRef = r.child("AlgoMatch");

                        final HashMap<String, Object> compMap = new HashMap<String, Object>();
                        compMap.put(username, lt);
                        algoRef.setValue(compMap);


                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public static List calcSimilarity(final String seekr, final Integer exp, final Integer seekerExp, final double cs_loc, final double cs_roles, final double cs_salary, final double cs_skills) {
        double cs_exp;
        System.out.println("COUNT OF WORK EX: " + seekerExp);
        if (seekerExp >= exp) {
            cs_exp = 1.0;
        } else {
            cs_exp = 0.0;
        }
        double avg = cs_loc + cs_roles + cs_salary + cs_skills + cs_exp;
        System.out.println("AVERAGE OF " + seekr + " is: " + avg / 5.0);
        sList.put(seekr, avg / 5.0);
        System.out.println("THE LIST: " + sList);

        SortedSet<Map.Entry<String, Double>> sortedset = new TreeSet<>(
                new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> e1,
                                       Map.Entry<String, Double> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });

        sortedset.addAll(sList.entrySet());
        System.out.println(sortedset);
        List<String> finalList = new ArrayList<>();
        Iterator<Map.Entry<String, Double>> itr = sortedset.iterator();
        //int leng = sortedset.size();
        while (itr.hasNext()) {
            finalList.add(itr.next().getKey());
            myList = finalList;
            System.out.println("list: "+myList);
        }
        System.out.println("List from out=" + myList);
        return myList;

    }
}
