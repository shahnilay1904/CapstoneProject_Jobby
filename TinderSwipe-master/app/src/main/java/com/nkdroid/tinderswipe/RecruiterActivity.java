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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruiterActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapterRec myAppAdapterRec;
    public static ViewHolder viewHolderRec;
    private ArrayList<DataR> alRec;
    private SwipeFlingAdapterView flingContainer;
    Firebase ref;
    ImageView img;
    public int zzz;
    final ArrayList names = new ArrayList();// To get seeker names
    public List<String> sortedNames = new ArrayList<>();
    String x;

    //For the algorithm
    public String loc;
    public String roles;
    public Integer exp;
    public String salary;
    public String skills;
    //public String compRoles;
    SpecsCalculation specs = new SpecsCalculation();
    Company c = new Company();
    List<String> algo = new ArrayList<>();
    ArrayList<String> st;





    public static void removeBackground() {



        viewHolderRec.background.setVisibility(View.GONE);
        myAppAdapterRec.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_recruiter);

//        Setting round image
        ImageView profilePicture = (ImageView)findViewById(R.id.recownprofile);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.doordash123);
        RoundImage r = new RoundImage(bm);
        profilePicture.setImageDrawable(r);



        //

//        sortedNames.add("S1");
//        sortedNames.add("S2");
//        sortedNames.add("S3");
//        sortedNames.add("S4");
// ==================================================================================================================================









// ==================================================================================================================================
//        System.out.println("ST List:"+st);
//        System.out.println(sortedNames.get(0).toString());


//        final int size = sortedNames.size();
//        for (int i =0; i<size;i++){

        alRec = new ArrayList<>(10);


//        }

        System.out.println(Arrays.toString(sortedNames.toArray()));

        Firebase.setAndroidContext(this);


        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final ArrayList<String> mylist123 = (ArrayList<String>) intent.getSerializableExtra("mylist");
        System.out.println("mylist123="+mylist123);
//        alRec.remove(0);

        for (int i = 0; i<mylist123.size(); i++) {
            getsomeData(mylist123.get(i));
        }


        myAppAdapterRec = new MyAppAdapterRec(alRec, RecruiterActivity.this);


        flingContainer.setAdapter(myAppAdapterRec);


//        getCandidates(username);
//        getData();


        // A listener for viewing ones own profile
        ImageView rec = (ImageView) findViewById(R.id.recownprofile);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rec = new Intent(RecruiterActivity.this, RecruiterFullProfile.class);
                rec.putExtra("username", username);
                LoginActivity log = new LoginActivity();
                zzz = log.getzz();
                System.out.println(zzz);
                zzz = zzz + 1;
                rec.putExtra("zzz", zzz);

                rec.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                Log.d("Sexy", "bitch!");
//                rec.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(rec);

            }
        });


        //        Listener for chatviewlistclass


        final ImageView chat = (ImageView) findViewById(R.id.chatrec);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatintent = new Intent(RecruiterActivity.this,ChatListViewPage.class);
                chatintent.putExtra("username", username);
                chatintent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(chatintent);
            }
        });



//    ===================================================================================



        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {



            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                alRec.remove(0);
                myAppAdapterRec.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {


                System.out.println(alRec.get(0).getNameR());
                String a = (String) alRec.get(0).getNameR();
                alRec.remove(0);
                Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker");
                Query getid = seekid.orderByChild("Name").equalTo(a);
                getid.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String x = (String) dataSnapshot.getKey();
                        System.out.println("Companyname=" + x);
                        Firebase match = new Firebase("https://sizzling-torch-8124.firebaseio.com/Matches/" + username);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(x, "Match");
                        match.updateChildren(map);
                        Intent intent = new Intent(RecruiterActivity.this, ChatActivityMain.class);
                        intent.putExtra("username", username);
                        intent.putExtra("recipient",x);
                        startActivity(intent);


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








                myAppAdapterRec.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {


            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });



        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            String x;

            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                TextView textView = (TextView) view.findViewById(R.id.sname);
                String a = textView.getText().toString();
                System.out.println("A=" + a);
                Log.d("Viewname", String.valueOf(view.findViewById(R.id.sname)));


//              Query to Get the current key of the seeker
                Firebase seekid = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker");
                Query refQuery = seekid.orderByChild("Name").equalTo(a);
                refQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        x = (String) dataSnapshot.getKey();
                        Intent viewseek = new Intent(RecruiterActivity.this,SeekerFullProfileForRecruiter.class);

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


//                Bundle extra = new Bundle();
//                extra.putSerializable("names", names);

//


//
                myAppAdapterRec.notifyDataSetChanged();
            }
        });




    }

//    public void getCandidates(String recid){
//        final ArrayList candidatelist = new ArrayList();
//        Firebase cand = new Firebase("https://sizzling-torch-8124.firebaseio.com/Likes/"+recid);
//        Query getC = cand.orderByChild(recid);
//        getC.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                System.out.println(dataSnapshot.getKey());
////                System.out.println("Bwahahah");
////                System.out.println(dataSnapshot.getValue());
//                candidatelist.add(dataSnapshot.getKey());
//                System.out.println(candidatelist);
//                getData(candidatelist);
//            }
//
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//        });
//
//    }


//    public void getData(final ArrayList cand){
//
//        Firebase reflocRec = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker");
//        String id = (String) cand.get(0);
//        Query querylocRec = reflocRec.orderByChild((String) cand.get(0));
//        cand.remove(0);
//
//        querylocRec.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
////                String applicant = (String) cand.get(0);
//                String loc = (String) dataSnapshot.child("Location").getValue();
//                System.out.println("Loc="+loc);
//
////                String desc = (String) dataSnapshot.child("Description").getValue();
////                String name = (String) dataSnapshot.child("Name").getValue();
////                String r1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
////                String r2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
////                String r3 = (String) dataSnapshot.child("Roles").child("R3").getValue();
////                final String key = dataSnapshot.getKey();
////                names.add(key);
////
////                System.out.println("Key= " + key);
////                System.out.println("Contants: "+names );
//////                Log.d("Role", "yes");
//////                System.out.println("y:" + name);
//////                System.out.println("x:" + loc);
////                alRec.add(new DataR("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", name, desc, loc, r1, r2, r3));
//                myAppAdapterRec.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }


//    public void getData(String arg){
//        Firebase reflocRec = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker/"+arg);
//        Query querylocRec = reflocRec.orderByKey();
//querylocRec.addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(DataSnapshot dataSnapshot) {
//
//    }
//
//    @Override
//    public void onCancelled(FirebaseError firebaseError) {
//
//
//    }
//})

//    }

//    public void getData(){
//
//
//        final List<String> xyz = new ArrayList();
//        final Firebase MatchAlgoRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/AlgoMatch/C1");
//
////        System.out.println("Count Algo Match Children: " + snapshot.getChildrenCount());
//        MatchAlgoRef.addValueEventListener(new ValueEventListener() {
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println("Count Algo Match Children: " + snapshot.getChildrenCount());
//                int z = (int) snapshot.getChildrenCount();
//                int x = 0;
//                xyz.clear();
////                for (int i = 0; i<z; i++){
//                String value = (String) snapshot.child(String.valueOf("0")).getValue();
//                System.out.println("0:" + value);
//
//                xyz.add(value);
//
//
////                }
//                System.out.println("XYZ123=" + xyz);
//
//                getsomeData(value);
//
//
//
////=========
//
//
////                ========
//
//
//
//
////                 Object st = snapshot.getChildren();
////                xyz.add((List) snapshot.getValue());
////                System.out.println("ST: " + st);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//
//        });
//        for (int i = 0; i<xyz.size();i++){
//            System.out.println("Arraylist: "+xyz.get(i));
//        }
//        System.out.println("XYZ123456="+ xyz);




//    }
    public void getsomeData(final String seekerID){


//        final int[] flag = {0};
//        int y =1;
        Firebase reflocRec = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker/");
//            Query querylocRec = reflocRec.orderByChild();
        reflocRec.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("Size= "+(String.valueOf(size)));
//                for (int i = size; i>0;i--) {
//                    final String seekerID = (String) xyz.get(i-1);

                System.out.println("SeekeerssssIDDD=" + seekerID);
//                    System.out.println("Flag="+flag);


                String loc = (String) dataSnapshot.child(seekerID).child("Location").getValue();
                String desc = (String) dataSnapshot.child(seekerID).child("Description").getValue();
                String name = (String) dataSnapshot.child(seekerID).child("Name").getValue();
                String r1 = (String) dataSnapshot.child(seekerID).child("Roles").child("R1").getValue();
                String r2 = (String) dataSnapshot.child(seekerID).child("Roles").child("R2").getValue();
                String r3 = (String) dataSnapshot.child(seekerID).child("Roles").child("R3").getValue();
                final String key = dataSnapshot.child(seekerID).getKey();
                names.add(key);
                System.out.println("Key= " + loc);
                System.out.println("desc= " + desc);
                System.out.println("name= " + name);
                System.out.println("Key= " + key);
                System.out.println("Contants: " + names);
//                Log.d("Role", "yes");
//                System.out.println("y:" + name);
//                System.out.println("x:" + loc);

                alRec.add(new DataR("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", name, desc, loc, r1, r2, r3));

                System.out.println("Adaptercheck");
                myAppAdapterRec.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });




    }




//    public void getData(){
//        Firebase reflocRec = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker");
//        Query querylocRec = reflocRec.orderByKey();
//
//        querylocRec.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String loc = (String) dataSnapshot.child("Location").getValue();
//                String desc = (String) dataSnapshot.child("Description").getValue();
//                String name = (String) dataSnapshot.child("Name").getValue();
//                String r1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
//                String r2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
//                String r3 = (String) dataSnapshot.child("Roles").child("R3").getValue();
//                final String key = dataSnapshot.getKey();
//                names.add(key);
//                System.out.println("Key= " + loc);
//                System.out.println("desc= " + desc);
//                System.out.println("name= " + name);
//                System.out.println("Key= " + key);
//                System.out.println("Contants: "+names );
////                Log.d("Role", "yes");
////                System.out.println("y:" + name);
////                System.out.println("x:" + loc);
//                alRec.add(new DataR("http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg", name, desc, loc, r1, r2, r3));
//                myAppAdapterRec.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }
//    @Override
//    public void onActionDownPerform() {
//        Log.e("action", "bingo");
//    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataTextRec;
        public TextView DataText1Rec;
        public ImageView cardImageRec;
        public TextView role1Rec;
        public TextView role2Rec;
        public TextView role3Rec;
        public TextView descriptionRec;

    }

    public class MyAppAdapterRec extends BaseAdapter {


        public List<DataR> parkingListRec;
        public Context contextRec;
        public String namexyz;


        private MyAppAdapterRec(List<DataR> appsR, Context contextRec) {
            this.parkingListRec = appsR;
            this.contextRec = contextRec;
        }

//        public List remove(List al){
//            al.remove(0);
//            return al;
//        }


        @Override
        public int getCount() {
            return parkingListRec.size();

        }

        @Override
        public Object getItem(int positionR) {
            return positionR;
        }

        @Override
        public long getItemId(int positionR) {
            return positionR;
        }

        @Override
        public View getView(final int positionR, View convertViewR, ViewGroup parentR) {

            View rowViewR = convertViewR;


            if (rowViewR == null) {

                LayoutInflater inflater = LayoutInflater.from(contextRec);
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowViewR = inflater.inflate(R.layout.content_recruiter, parentR, false);
                // configure view holder
                viewHolderRec = new ViewHolder();
                viewHolderRec.DataTextRec = (TextView) rowViewR.findViewById(R.id.sname);
                viewHolderRec.DataText1Rec = (TextView) rowViewR.findViewById(R.id.s_loc);
                viewHolderRec.role1Rec = (TextView) rowViewR.findViewById(R.id.s_role1);
                viewHolderRec.role2Rec = (TextView) rowViewR.findViewById(R.id.s_role2);
                viewHolderRec.role3Rec = (TextView) rowViewR.findViewById(R.id.s_role3);
                viewHolderRec.descriptionRec = (TextView) rowViewR.findViewById(R.id.s_bio);
                viewHolderRec.background = (FrameLayout) rowViewR.findViewById(R.id.background);
                viewHolderRec.cardImageRec = (ImageView) rowViewR.findViewById(R.id.seekercardimg);
                rowViewR.setTag(viewHolderRec);
                namexyz = (String) parkingListRec.get(positionR).getNameR();

            } else {
                viewHolderRec = (ViewHolder) convertViewR.getTag();
            }
            viewHolderRec.DataTextRec.setText(parkingListRec.get(positionR).getNameR());
            viewHolderRec.DataText1Rec.setText(parkingListRec.get(positionR).getLocationR());
            viewHolderRec.role1Rec.setText(parkingListRec.get(positionR).getRole1R());
            viewHolderRec.role2Rec.setText(parkingListRec.get(positionR).getRole2R());
            viewHolderRec.role3Rec.setText(parkingListRec.get(positionR).getRole3R());
            viewHolderRec.descriptionRec.setText(parkingListRec.get(positionR).getDescriptionR());
            Glide.with(RecruiterActivity.this).load(parkingListRec.get(positionR).getImagePathR()).into(viewHolderRec.cardImageRec);

            return rowViewR;
        }
    }


}