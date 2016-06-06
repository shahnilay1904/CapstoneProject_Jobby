package com.nkdroid.tinderswipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatListViewPage extends AppCompatActivity {




    private List<User> chatusers = new ArrayList<>();
    User userobject;
    ListView mylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_view_page);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
//        getChatData();
//=================================================================================================================
//        On click listener to go back to the main page
        ImageView backtomain = (ImageView) findViewById(R.id.backtothemainpage);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] xyz = username.toCharArray();
                System.out.println(xyz[0]);
                if (xyz[0] == 'S') {
                    //Give intent back to Seeker Activity
                    Intent intenttoseek = new Intent(ChatListViewPage.this, SeekerActivity.class);
                    intenttoseek.putExtra("username", username);
                    finish();
                    startActivity(intenttoseek);
                } else {
                    //Give intent back to Recruiter Activity
                    Intent intenttorec = new Intent(ChatListViewPage.this, RecruiterActivity.class);
                    intenttorec.putExtra("username", username);
                    finish();
                    startActivity(intenttorec);
                }


            }
        });


//=================================================================================================================
        mylist = (ListView) findViewById(R.id.Users);

//        trial 1

        System.out.println("Whats the username?"+username);
        char[] xyz = username.toCharArray();
        //For seekers to talk to recruiters
        //If it is S then go to the specific key of S.. ex. S4-> then go to C4 -> S4 and get the key C4
        if (xyz[0] == 'S'){
            System.out.println("It is a seeker!");


            Firebase getid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Chat");


            Query quer = getid.orderByChild("Chat");
            quer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    System.out.println(dataSnapshot);
//                    System.out.println(dataSnapshot.getValue());
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        System.out.println("Key=");
                        System.out.println(data.getKey());
                        final String recid = data.getKey();
                        Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company/" + recid);
                        Query refQuery = seekid.orderByChild("Location");
                        refQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                System.out.println("NestedFirebase");

                                String name = (String) dataSnapshot.child("Name").getValue();
                                String loc = (String) dataSnapshot.child("Location").getValue();
                                String recruid = recid ;
                                System.out.println("Name=" + name);
                                System.out.println("Location=" + loc);
                                userobject = new User(name, R.drawable.user_icon, loc, recruid);
                                chatusers.add(userobject);

                                CustomUsersAdapter adap = new CustomUsersAdapter(getApplicationContext(), R.layout.content_chat_list_view_page, chatusers);
                                mylist.setAdapter(adap);


//                            String name = (String) chatSnapshot.child("Name").getValue();
//                            String loc = (String) chatSnapshot.child("Location").getValue();
                                System.out.println(chatusers);




                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
//                        System.out.println(dataSnapshot.getKey());
//                        System.out.println("Value=");
//                        System.out.println(dataSnapshot.getValue());
//                        System.out.println(data.getValue());

                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String stringText;
                    TextView textview = (TextView) view.findViewById(R.id.tvName);
//                    stringText=textview.getText().toString();
                    TextView idview = (TextView) view.findViewById(R.id.id);
                    stringText = idview.getText().toString();// Gives user id
                    System.out.println("tvName=" + stringText);
                    Intent intent = getIntent();
                    final String username = intent.getStringExtra("username");

                    Intent intented = new Intent(ChatListViewPage.this, ChatActivityMain.class);
                    intented.putExtra("username", username);
                    intented.putExtra("recipient", stringText);
//                    intented.putExtra("username", stringText);
//                    intented.putExtra("recipient", username);
                    finish();
                    startActivity(intented);


                }
            });





//
//
        }




        // For recruiters to talk to seekers
        // If it is C then go to Chat/C.. ex. Chat/C4 and take all childs ex. S1, S2, S3, S4...
        if(xyz[0] == 'C') {
            final ArrayList<String> seekarr = new ArrayList<>();
            final Firebase getid = new Firebase("https://intense-torch-2763.firebaseio.com/Chat/" + username);
            System.out.println("Look at the key=" + getid.child(username).getKey());
//            Query refQuery = getid.orderByChild(username);
            Query refQuery = getid.orderByKey();
            refQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final String sendseekid = (String) dataSnapshot.getKey();
//                    getSeekerandsetAdapter(sendseekid);


                    Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker/" + sendseekid);
                    Query refQuery = seekid.orderByChild("Location");
                    refQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            System.out.println("NestedFirebase");

                            String name = (String) dataSnapshot.child("Name").getValue();
                            String loc = (String) dataSnapshot.child("Location").getValue();
                            String seekeid = sendseekid ;
                            System.out.println("Name=" + name);
                            System.out.println("Location=" + loc);


                            userobject = new User(name, R.drawable.user_icon, loc, seekeid);
                            chatusers.add(userobject);

                            CustomUsersAdapter adap = new CustomUsersAdapter(getApplicationContext(), R.layout.content_chat_list_view_page, chatusers);
                            mylist.setAdapter(adap);


//                            String name = (String) chatSnapshot.child("Name").getValue();
//                            String loc = (String) chatSnapshot.child("Location").getValue();
                            System.out.println(chatusers);

//                            setChatters();


                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

//                    System.out.println(seekarr);
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
            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String stringText;
                    TextView textview=(TextView)view.findViewById(R.id.tvName);
//                    stringText=textview.getText().toString();
                    TextView idview =(TextView)view.findViewById(R.id.id);
                    stringText=idview.getText().toString();// Gives user id
                    System.out.println("tvName="+ stringText);
                    Intent intent = getIntent();
                    final String username = intent.getStringExtra("username");

                    Intent intented = new Intent(ChatListViewPage.this, ChatActivityMain.class);
                    intented.putExtra("username", username);
                    intented.putExtra("recipient",stringText);
                    finish();
                    startActivity(intented);




                }
            });



        }










//=================================================================================================================






    }// on Create end




//    public void setChatters(){
//
//
//
//        mylist = (ListView) findViewById(R.id.Users);
//        CustomUsersAdapter adap = new CustomUsersAdapter(getApplicationContext(), R.layout.content_chat_list_view_page,chatusers);
//        mylist.setAdapter(adap);
//        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent inten = new Intent(ChatListViewPage.this, ChatActivityMain.class);
//                startActivity(inten);
//
//            }
//        });
//
//    }

//    public void setAdapterValues(String name, String loc){
//        Log.d("Helo","setAdapterValues");
////        chatusers = new ArrayList<>();
//        chatusers.add(new User( name, R.drawable.user_icon, loc));
//        ListView lv = (ListView) findViewById(R.id.Users);
//        lv.setAdapter(new CustomUsersAdapter(this, R.layout.content_chat_list_view_page, chatusers));
//        lv.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//
//    }
//
//    public void getChatData(){
//
//        Intent intent = getIntent();
//        final String username = intent.getStringExtra("username");
//        System.out.println("Whats the username?"+username);
//        char[] xyz = username.toCharArray();
//        //For seekers to talk to recruiters
//        //If it is S then go to the specific key of S.. ex. S4-> then go to C4 -> S4 and get the key C4
//        if (xyz[0] == 'S'){
//
//
//            Firebase getid = new Firebase("https://intense-torch-2763.firebaseio.com/Chat/"+username);
//
//
//
//
//
////
////            ListView lv = (ListView) findViewById(R.id.Users);
////            lv.setAdapter(new CustomUsersAdapter(this, R.layout.content_chat_list_view_page, chatusers));
//        }
//
//
//
//
//        // For recruiters to talk to seekers
//        // If it is C then go to Chat/C.. ex. Chat/C4 and take all childs ex. S1, S2, S3, S4...
//        if(xyz[0] == 'C'){
//            final ArrayList<String> seekarr = new ArrayList<>();
//            Firebase getid = new Firebase("https://intense-torch-2763.firebaseio.com/Chat/"+username);
//            System.out.println("Look at the key="+getid.child(username).getKey());
////            Query refQuery = getid.orderByChild(username);
//            Query refQuery = getid.orderByKey();
//            refQuery.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    String sendseekid = (String)dataSnapshot.getKey();
//                    getSeekerandsetAdapter(sendseekid);
////                    System.out.println(seekarr);
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//
//
//
////
////            ListView lv = (ListView) findViewById(R.id.Users);
////            lv.setAdapter(new CustomUsersAdapter(this, R.layout.content_chat_list_view_page, chatusers));
//
//
//        }
//
//
//
//    }
//    //getandsetdata
//    public void getSeekerandsetAdapter(String seekerid){
//
//        Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker"+seekerid);
//        Query refQuery = seekid.orderByChild("Location");
//        refQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String name = (String) dataSnapshot.child("Name").getValue();
//                String loc = (String) dataSnapshot.child("Location").getValue();
//                setAdapterValues(name,loc);
////                chatusers.add(new User(name, R.drawable.user_icon, loc));
////                ListView lv = (ListView) findViewById(R.id.Users);
////                lv.setAdapter(new CustomUsersAdapter(this, R.layout.content_chat_list_view_page, chatusers));
//
//
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//
//
//    }


//    private void populateUsersList() {
//        // Construct the data source
//        ArrayList<User> arrayOfUsers = User.getUsers();
//        // Create the adapter to convert the array to views
//        CustomUsersAdapter adapter = new CustomUsersAdapter(this, arrayOfUsers);
//        // Attach the adapter to a ListView
//        ListView listView = (ListView) findViewById(R.id.lvUsers);
//        listView.setAdapter(adapter);
//    }



}
