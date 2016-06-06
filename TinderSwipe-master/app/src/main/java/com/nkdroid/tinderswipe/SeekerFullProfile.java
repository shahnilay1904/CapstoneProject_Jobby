package com.nkdroid.tinderswipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.nkdroid.tinderswipe.tindercard.RoundImage;
import com.nkdroid.tinderswipe.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

public class SeekerFullProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_full_profile);

        //        Setting round image
        ImageView profilePicture = (ImageView)findViewById(R.id.seekerbacktomain);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.jobsgraphic);
        RoundImage r = new RoundImage(bm);
        profilePicture.setImageDrawable(r);


        //        Setting round image
        ImageView profilePicture1 = (ImageView)findViewById(R.id.imageView);

        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.doordash123);
        RoundImage r1 = new RoundImage(bm1);
        profilePicture1.setImageDrawable(r1);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

//        System.out.println("Username"+username);
//        sfprofile = (LinearLayout) findViewById(R.id.sscroll);
//        myAppAdapter = new MyAppAdapter(al, SeekerFullProfile.this);
//        sfprofile.setAdapter(myAppAdapter);



        getData(username);

        // globally
        ImageView seektomain = (ImageView) findViewById(R.id.seekerbacktomain);
        seektomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainpage = new Intent(SeekerFullProfile.this, SeekerActivity.class);
                mainpage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                mainpage.putExtra("username", username);
                startActivity(mainpage);
//                finish();


            }
        });





    }// On Create End


    public void getData(String user){
        final String userN = user;
        Firebase refloc = new Firebase("https://sizzling-torch-8124.firebaseio.com/Seeker/"+userN);
        System.out.println(refloc);
        refloc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
// Text View names         Get Texts
/*nam */                String sname = (String) dataSnapshot.child("Name").getValue();
/*age */                //int sage = (int) dataSnapshot.child("Age").getValue();
/*loc*/                String splace = (String) dataSnapshot.child("Location").getValue();
/* desc */                String sbio = (String) dataSnapshot.child("Description").getValue();
/* work1 */                String sworkex = (String) dataSnapshot.child("Work Experience").child("WE1").child("We1").getValue();
                System.out.println("Work ex = "+sworkex);
/* work2 */                String sworkexcont = " "+String.valueOf(dataSnapshot.child("Work Experience").child("WE1").child("We2").getValue())+" years";
/* ed1 */                String seducation = (String) dataSnapshot.child("Education").child("ED1").child("Ed1").getValue();
/* ed2 */                String seducationcont = (String) dataSnapshot.child("Education").child("ED1").child("Ed2").getValue();
/* ro1 */                String srole1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
/* ro2 */                String srole2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
/* ro3 */                String srole3 = (String) dataSnapshot.child("Roles").child("R3").getValue();
/* goal */                String sgoal = (String) dataSnapshot.child("My goals").getValue();
/* skill1 */                String sskill1 = (String) dataSnapshot.child("Skillset").child("Sk1").getValue();
                System.out.println("Skill1"+sskill1);
/* skill2 */                String sskill2 = (String) dataSnapshot.child("Skillset").child("Sk2").getValue();
/* skill3 */                String sskill3 = (String) dataSnapshot.child("Skillset").child("Sk3").getValue();
/* skill4 */                String sskill4 = (String) dataSnapshot.child("Skillset").child("Sk4").getValue();
/* skill5 */                String sskill5 = (String) dataSnapshot.child("Skillset").child("Sk5").getValue();
/* skill6 */                String sskill6 = (String) dataSnapshot.child("Skillset").child("Sk6").getValue();
/* skill7 */                String sskill7 = (String) dataSnapshot.child("Skillset").child("Sk7").getValue();
/* lang1 */                String slang1 = (String) dataSnapshot.child("Language").child("Lg1").getValue();
/* lang2 */                String slang2 = (String) dataSnapshot.child("Language").child("Lg2").getValue();
/* lang3 */                String slang3 = (String) dataSnapshot.child("Language").child("Lg3").getValue();
/* power1 */                String spower1 = (String) dataSnapshot.child("3 superpowers").child("Sp1").getValue();
/* power2 */                String spower2 = (String) dataSnapshot.child("3 superpowers").child("Sp2").getValue();
/* power3 */                String spower3 = (String) dataSnapshot.child("3 superpowers").child("Sp3").getValue();
/* des1 */                String sdescribe1 = (String) dataSnapshot.child("5 words that describe me").child("W1").getValue();
/* des2 */                String sdescribe2 = (String) dataSnapshot.child("5 words that describe me").child("W2").getValue();
/* des3 */                String sdescribe3 = (String) dataSnapshot.child("5 words that describe me").child("W3").getValue();
/* des4 */                String sdescribe4 = (String) dataSnapshot.child("5 words that describe me").child("W4").getValue();
/* des5 */                String sdescribe5 = (String) dataSnapshot.child("5 words that describe me").child("W5").getValue();
                String iw1 = (String) dataSnapshot.child("Ideal Workplace").child("IW1").getValue();
                String iw2 = (String) dataSnapshot.child("Ideal Workplace").child("IW2").getValue();
                String iw3 = (String) dataSnapshot.child("Ideal Workplace").child("IW3").getValue();
/* salc */                String spsal = (String) dataSnapshot.child("Previous Salary").getValue();
/* salex */                String sesal = (String) dataSnapshot.child("Expected Salary").getValue();

//============================================================================================================================
// SetTexts:

                TextView nam = (TextView)findViewById(R.id.sname);
                nam.setText(sname);

               // TextView age = (TextView)findViewById(R.id.sage);
               // age.setText(sage);

                TextView loc = (TextView)findViewById(R.id.splace);
                loc.setText(splace);

                TextView desc = (TextView)findViewById(R.id.sbio);
                desc.setText(sbio);

                TextView work1= (TextView)findViewById(R.id.sworkex);
                work1.setText(sworkex);
                TextView work2 = (TextView)findViewById(R.id.sworkexcontinue);
                work2.setText(sworkexcont);

                TextView ed1 = (TextView)findViewById(R.id.seducation);
                ed1.setText(seducation);
                TextView ed2 = (TextView)findViewById(R.id.seducationcontinue);
                ed2.setText(seducationcont);

                TextView ro1 = (TextView)findViewById(R.id.srole1);
                ro1.setText(srole1);
                TextView ro2 = (TextView)findViewById(R.id.srole2);
                ro2.setText(srole2);
//                TextView ro3 = (TextView)findViewById(R.id.srole3);
//                ro3.setText(srole3);

                TextView goal = (TextView)findViewById(R.id.sgoal);
                goal.setText(sgoal);

                TextView skill1 = (TextView)findViewById(R.id.sskill1);
                skill1.setText(sskill1);
                TextView skill2 = (TextView)findViewById(R.id.sskill2);
                skill2.setText(sskill2);
                TextView skill3 = (TextView)findViewById(R.id.sskill3);
                skill3.setText(sskill3);
                TextView skill4 = (TextView)findViewById(R.id.sskill4);
                skill4.setText(sskill4);
                TextView skill5 = (TextView)findViewById(R.id.sskill5);
                skill5.setText(sskill5);
                TextView skill6 = (TextView)findViewById(R.id.sskill6);
                skill6.setText(sskill6);
                TextView skill7 = (TextView)findViewById(R.id.sskill7);
                skill7.setText(sskill7);

                TextView lang1 = (TextView)findViewById(R.id.slang1);
                lang1.setText(slang1);
                TextView lang2 = (TextView)findViewById(R.id.slang2);
                lang2.setText(slang2);
                TextView lang3 = (TextView)findViewById(R.id.slang3);
                lang3.setText(slang3);

                TextView power1 = (TextView)findViewById(R.id.spower1);
                power1.setText(spower1);
                TextView power2 = (TextView)findViewById(R.id.spower2);
                power2.setText(spower2);
                TextView power3 = (TextView)findViewById(R.id.spower3);
                power3.setText(spower3);

                TextView des1 = (TextView)findViewById(R.id.sdescribe1);
                des1.setText(sdescribe1);
                TextView des2 = (TextView)findViewById(R.id.sdescribe2);
                des2.setText(sdescribe2);
                TextView des3 = (TextView)findViewById(R.id.sdescribe3);
                des3.setText(sdescribe3);
                TextView des4 = (TextView)findViewById(R.id.sdescribe4);
                des4.setText(sdescribe4);
                TextView des5 = (TextView)findViewById(R.id.sdescribe5);
                des5.setText(sdescribe5);

                TextView salc = (TextView)findViewById(R.id.spsal);
                salc.setText(spsal);
                TextView salex = (TextView)findViewById(R.id.sesal);
                salex.setText(sesal);

                //
                TextView ideal1 = (TextView)findViewById(R.id.sworkplace1);
                ideal1.setText(iw1);
                TextView ideal2 = (TextView)findViewById(R.id.sworkplace2);
                ideal2.setText(iw2);
                TextView ideal3 = (TextView)findViewById(R.id.sworkplace3);
                ideal3.setText(iw3);




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });
//                System.out.println(x.Location);
//                System.out.println(dataSnapshot.child("Location").getValue());
//                System.out.println(dataSnapshot.child("Location").getKey());
//                String loc = (String) dataSnapshot.child("Location").getValue();
//                String desc = (String) dataSnapshot.child("Description").getValue();
//                String name = (String) dataSnapshot.child("Name").getValue();
//                String r1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
//                String r2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
//                String r3 = (String) dataSnapshot.child("Roles").child("R3").getValue();
////                Log.d("Role", "yes");
//                System.out.println("y:" + name);
//                System.out.println("x:" + loc);
//                System.out.println("Desc:" + desc);





    }

}