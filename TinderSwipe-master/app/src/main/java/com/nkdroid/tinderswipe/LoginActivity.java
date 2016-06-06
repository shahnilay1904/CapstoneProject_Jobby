package com.nkdroid.tinderswipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    Firebase ref;
    public int i = 0;
    public int zz = 0;
    SpecsCalculation specs = new SpecsCalculation();
    Company c = new Company();
    public String loc;
    public String roles;
    public Integer exp;
    public String salary;
    public String skills;

    ArrayList<String> st;


    public int getzz(){
        return zz;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        b1 = (Button) findViewById(R.id.login);
        ed1=(EditText)findViewById(R.id.username);
        ed2=(EditText)findViewById(R.id.password);

//        b2=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(mylistener);





        ref = new Firebase("https://intense-torch-2763.firebaseio.com/");


    }
    View.OnClickListener mylistener = new View.OnClickListener() {
        public void onClick(View v) {
            final String username = ed1.getText().toString();
            final String password = ed2.getText().toString();
            final ArrayList<String> mylist123 = new ArrayList<String>();
            final HashSet<String> hs = new HashSet<String>();
            final ArrayList<String> algo = new ArrayList<String>();
            final GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};




            Query refQuery = ref.orderByChild(username).equalTo(password);
            refQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    System.out.println(s);
//
                    System.out.println("Login successful");
                    final Intent recruiter = new Intent(LoginActivity.this, RecruiterActivity.class);
                    Intent seeker = new Intent(LoginActivity.this, SeekerActivity.class);
                    if(dataSnapshot.getKey().equalsIgnoreCase("JobPoster")){
                        Toast.makeText(LoginActivity.this,"Welcome Recruiter!",Toast.LENGTH_LONG).show();
                        //Passing the list


                        Firebase ref = new Firebase("https://sizzling-torch-8124.firebaseio.com/");
                        Firebase usersRef = ref.child("Cosine");

                        final HashMap<String, Object> c1Map = new HashMap<String, Object>();


                        final Firebase CompanyRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company");
                        Firebase LikesRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/Likes/"+username);
                       // final Firebase MatchAlgoRef = new Firebase("https://console.firebase.google.com/project/test-app-ds/database/data//AlgoMatch/C1/");


                        LikesRef.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("Count: " + snapshot.getChildrenCount());
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    System.out.println("POSTSNAPSHOT: " + postSnapshot);
                                    final String seekr = postSnapshot.getKey();

                                    CompanyRef.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(DataSnapshot snapshot) {
                                             c.setLocation(snapshot.child(username+"/Location").getValue().toString());
                                             c.setRoles(snapshot.child(username + "/Roles").getValue().toString());
                                             c.setSalaryOffered(snapshot.child(username + "/Salary Offered").getValue().toString());
                                             c.setExp(snapshot.child(username + "/Experience Required").getValue().toString());
                                             c.setSkillsDesired(snapshot.child(username + "/Skills Desired").getValue().toString());

                                             System.out.println("LOCATION: " + username + " " + c.getLocation());
                                             System.out.println("ROLES: "+ username +" "+ c.getRoles());
                                             System.out.println("SALARY: "+ username +" "+c.getSalaryOffered());
                                             System.out.println("EXPERIENCE DESIRED: "+ username +" "+ c.getExp());
                                             System.out.println("SKILLS: "+ username +" "+ c.getSkillsDesired());

                                             loc = c.getLocation();
                                             roles = c.getRoles();
                                             exp = Integer.parseInt(c.getExp());
                                             salary = c.getSalaryOffered();
                                             skills = c.getSkillsDesired();
                                             specs.secondFunc(seekr, loc, roles, skills, salary, exp, username);

                                         }

                                         @Override
                                         public void onCancelled(FirebaseError firebaseError) {
                                             System.out.println("The read failed: " + firebaseError.getMessage());
                                         }
                                     }

                                    );

                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Log.e("The read failed: ", firebaseError.getMessage());
                            }
                        });





                        // Retreiving the list


                        final Firebase MatchAlgoRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/AlgoMatch/"+username);



                        MatchAlgoRef.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("Count Algo Match Children: " + snapshot.getChildrenCount());
                                int z = (int) snapshot.getChildrenCount();
                                mylist123.clear();
                                for (int i = z-1; i >=0 ; i--) {
                                    mylist123.add((String) snapshot.child(String.valueOf(""+i+"")).getValue());
                                    System.out.println(""+i+":"+mylist123);
                                }
                                //System.out.println("LIST1:" + list1);
                                //hs.addAll(algo);
                                //algo.clear();
                                //algo.addAll(hs);
                                //System.out.println("HS: " + hs);
                                System.out.println("LIST1:" + mylist123);

                                recruiter.putExtra("mylist",mylist123);

                                recruiter.putExtra("username", username);
                                startActivity(recruiter);
                                i = 1;



                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });


                    }
                    if(dataSnapshot.getKey().equalsIgnoreCase("JobSeeker")){
                        Toast.makeText(LoginActivity.this,"Welcome Seeker!",Toast.LENGTH_LONG).show();
                        seeker.putExtra("username", username);
                        startActivity(seeker);
                        i = 2;

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        }
    };




}