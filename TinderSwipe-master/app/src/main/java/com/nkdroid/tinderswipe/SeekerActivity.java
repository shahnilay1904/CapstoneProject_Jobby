package com.nkdroid.tinderswipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.nkdroid.tinderswipe.tindercard.FlingCardListener;
import com.nkdroid.tinderswipe.tindercard.RoundImage;
import com.nkdroid.tinderswipe.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class SeekerActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;
    Firebase ref;
    public int i = 1;
    ImageView imgs;

    public static void removeBackground() {
//        System.out.println("Seeke22");


        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seeker);
        Firebase.setAndroidContext(this);
        al = new ArrayList<>(10);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

//Getting username from login intent
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        char[] xyz = username.toCharArray();
        System.out.println("Character="+xyz[0]);
//        System.out.println(username);


//        al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.","Hi"));
//                al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.","ey"));
//                al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.","heyyy"));
//                al.add(new Data("http://switchboard.nrdc.org/blogs/dlashof/mission_impossible_4-1.jpg", "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.","lul"));
//                al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", "explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.","yuuy"));









        myAppAdapter = new MyAppAdapter(al, SeekerActivity.this);
        flingContainer.setAdapter(myAppAdapter);

//        TextView view = (TextView) findViewById(R.id.rname);
//        String a = view.getText().toString();
//        System.out.println("Heheh"+a);

//        Setting round image
        ImageView profilePicture = (ImageView)findViewById(R.id.seekownprofile);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.doordash123);
        RoundImage r = new RoundImage(bm);
        profilePicture.setImageDrawable(r);



        getData();

// A listener for viewing ones own profile
        ImageView seek = (ImageView) findViewById(R.id.seekownprofile);
        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seek = new Intent(SeekerActivity.this, SeekerFullProfile.class);
                seek.putExtra("username", username);
                seek.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                finish();
                startActivity(seek);
            }
        });

//        Listener for chatviewlistclass


        final ImageView chat = (ImageView) findViewById(R.id.chatseek);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatintent = new Intent(SeekerActivity.this,ChatListViewPage.class);
                chatintent.putExtra("username",username);
                chatintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                finish();
                startActivity(chatintent);
            }
        });



//    ===================================================================================
        // Setting timer to get the view

//                View view = flingContainer.getSelectedView();
//                TextView textView = (TextView) view.findViewById(R.id.rname);
//                String a = textView.getText().toString();
//                System.out.println("Hellotext"+a);





//    ===================================================================================
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                System.out.println(al.get(0).getName());
                String a = (String) al.get(0).getName();
                Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company");
                Query getid = seekid.orderByChild("Name").equalTo(a);
                getid.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String x = (String) dataSnapshot.getKey();
                        System.out.println("Companyname="+x);
                        Firebase updatelike = new Firebase("https://sizzling-torch-8124.firebaseio.com/DisLikes/"+x);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(username,x);
                        updatelike.updateChildren(map);

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


                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject


            }

            @Override
            public void onRightCardExit(final Object dataObject) {
                al.remove(0);
                if (al.size() != 0){
                System.out.println(al);

                Toast.makeText(SeekerActivity.this,"Interest Sent!",Toast.LENGTH_LONG).show();
                myAppAdapter.notifyDataSetChanged();


//                System.out.println(al.get(0).getName());
                String a = (String) al.get(0).getName();
                Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company");
                Query getid = seekid.orderByChild("Name").equalTo(a);
                getid.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String x = (String) dataSnapshot.getKey();
                        System.out.println("Companyname="+x);
                        Firebase updatelike = new Firebase("https://sizzling-torch-8124.firebaseio.com/Likes/"+x);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(username,x);
                        updatelike.updateChildren(map);
                        // For Chat
//                        Intent intent = new Intent(SeekerActivity.this, ChatActivityMain.class);
//                        intent.putExtra("recipient", x);
//                        intent.putExtra("username", username);
//                        startActivity(intent);


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
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

//                Toast.makeText(SeekerActivity.this,"There are no more jobs for you!",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

// To view the opposite persons full profile
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            String x;
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                TextView textView = (TextView) view.findViewById(R.id.rname);
                String a = textView.getText().toString();
                System.out.println("A=" + a);
                Log.d("Viewname", String.valueOf(view.findViewById(R.id.rname)));


//              Query to Get the current key of the seeker
                Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company");
                Query refQuery = seekid.orderByChild("Name").equalTo(a);
                refQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        x = (String) dataSnapshot.getKey();
                        Intent viewseek = new Intent(SeekerActivity.this,RecruiterFullprofileForSeeker.class);

                        viewseek.putExtra("extra", x);
                        System.out.println("Key of name="+x);
                        viewseek.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(viewseek);
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





//                Intent viewrec = new Intent(SeekerActivity.this, RecruiterFullprofileForSeeker.class);
//                viewrec.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(viewrec);

//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }//On Create End



    public void getData(){
        Firebase refloc = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company");
        Query queryloc = refloc.orderByChild("Location");
        queryloc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String loc = (String) dataSnapshot.child("Location").getValue();
                String desc = (String) dataSnapshot.child("Description").getValue();
                String name = (String) dataSnapshot.child("Name").getValue();
                String r1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
                String r2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
                String r3 = (String) dataSnapshot.child("Roles").child("R3").getValue();
                Log.d("Role", "yes");
                System.out.println("y:" + name);
                System.out.println("x:" + loc);
                al.add(new Data("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", name, desc, loc, r1, r2, r3));
                myAppAdapter.notifyDataSetChanged();

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

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public TextView DataText1;
        public ImageView cardImage;
        public TextView role1;
        public TextView role2;
        public TextView role3;
        public TextView description;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
//            System.out.println("Data size:"+parkingList.size());
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = LayoutInflater.from(context);
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.rname);
                viewHolder.DataText1 = (TextView) rowView.findViewById(R.id.r_loc);
                viewHolder.role1 = (TextView) rowView.findViewById(R.id.r_role1);
                viewHolder.role2 = (TextView) rowView.findViewById(R.id.r_role2);
                viewHolder.role3 = (TextView) rowView.findViewById(R.id.r_role3);
                viewHolder.description = (TextView) rowView.findViewById(R.id.r_bio);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardimg);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getName());
            viewHolder.DataText1.setText(parkingList.get(position).getLocation());
            viewHolder.role1.setText(parkingList.get(position).getRole1());
            viewHolder.role2.setText(parkingList.get(position).getRole2());
            viewHolder.role3.setText(parkingList.get(position).getRole3());
            viewHolder.description.setText(parkingList.get(position).getDescription());
            Glide.with(SeekerActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}
