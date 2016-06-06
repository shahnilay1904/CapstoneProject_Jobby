package com.nkdroid.tinderswipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nkdroid.tinderswipe.tindercard.RoundImage;

public class RecruiterFullprofileForSeeker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_fullprofile_for_seeker);
        Intent intenti = getIntent();
        final String recname = intenti.getStringExtra("extra");
        System.out.println("Recruiter name="+recname);

        //        Setting round image
        ImageView profilePicture = (ImageView)findViewById(R.id.seekerbacktomain);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.jobsgraphic);
        RoundImage r = new RoundImage(bm);
        profilePicture.setImageDrawable(r);



        ImageView seektomain = (ImageView) findViewById(R.id.seekerbacktomain);
        seektomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainpage = new Intent(RecruiterFullprofileForSeeker.this, SeekerActivity.class);
                mainpage.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                finish();
                startActivity(mainpage);



            }
        });

        getandsetData(recname);

    }// on create end

    public void getandsetData(String user){

            final String userN = user;
            Firebase refloc = new Firebase("https://sizzling-torch-8124.firebaseio.com/Company/" + userN);
            System.out.println(refloc);

            refloc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

/*nam */                String rname = (String) dataSnapshot.child("Name").getValue();

/*loc*/                String rplace = (String) dataSnapshot.child("Location").getValue();
/* desc */                String rbio = (String) dataSnapshot.child("Description").getValue();
/* work1 */                String rworkex = (String) dataSnapshot.child("Experience Required").getValue();


/* ro1 */                String rpos1 = (String) dataSnapshot.child("Roles").child("R1").getValue();
/* ro2 */                String rpos2 = (String) dataSnapshot.child("Roles").child("R2").getValue();
/* ro3 */                String rpos3 = (String) dataSnapshot.child("Roles").child("R3").getValue();

/* skill1 */                String rskill1 = (String) dataSnapshot.child("Skills Desired").child("SD1").getValue();
/* skill2 */                String rskill2 = (String) dataSnapshot.child("Skills Desired").child("SD2").getValue();
/* skill3 */                String rskill3 = (String) dataSnapshot.child("Skills Desired").child("SD3").getValue();
/* skill4 */                String rskill4 = (String) dataSnapshot.child("Skills Desired").child("SD4").getValue();
/* skill5 */                String rskill5 = (String) dataSnapshot.child("Skills Desired").child("SD5").getValue();
/* skill6 */                String rskill6 = (String) dataSnapshot.child("Skills Desired").child("SD6").getValue();
/* skill7 */                String rskill7 = (String) dataSnapshot.child("Skills Desired").child("SD7").getValue();

/* power1 */                String rpower1 = (String) dataSnapshot.child("3 Super Powers").child("SP1").getValue();
/* power2 */                String rpower2 = (String) dataSnapshot.child("3 Super Powers").child("SP2").getValue();
/* power3 */                String rpower3 = (String) dataSnapshot.child("3 Super Powers").child("SP3").getValue();

/* des1 */                String rdescribe1 = (String) dataSnapshot.child("Words").child("W1").getValue();
/* des2 */                String rdescribe2 = (String) dataSnapshot.child("Words").child("W2").getValue();
/* des3 */                String rdescribe3 = (String) dataSnapshot.child("Words").child("W3").getValue();
/* des4 */                String rdescribe4 = (String) dataSnapshot.child("Words").child("W4").getValue();
/* des5 */                String rdescribe5 = (String) dataSnapshot.child("Words").child("W5").getValue();

 /* ideal candidate*/     String i1 = (String) dataSnapshot.child("Ideal Candidate").child("I1").getValue();
                    String i2 = (String) dataSnapshot.child("Ideal Candidate").child("I2").getValue();
                    String i3 = (String) dataSnapshot.child("Ideal Candidate").child("I3").getValue();

/*perks */                String rpb = (String) dataSnapshot.child("Perks and Benefits").getValue();

/* sal */                 String rsal = (String) dataSnapshot.child("Salary Offered").getValue();

//================================================================================================================================================

                    TextView nam = (TextView)findViewById(R.id.rname);
                    nam.setText(rname);

                    // TextView age = (TextView)findViewById(R.id.sage);
                    // age.setText(sage);

                    TextView loc = (TextView)findViewById(R.id.rplace);
                    loc.setText(rplace);

                    TextView desc = (TextView)findViewById(R.id.rbio);
                    desc.setText(rbio);

                    TextView work1= (TextView)findViewById(R.id.rworkex);
                    work1.setText(rworkex+"years");

                    TextView rp1 = (TextView)findViewById(R.id.rpos1);
                    rp1.setText(rpos1);

                    TextView rp2 = (TextView)findViewById(R.id.rpos2);
                    rp2.setText(rpos2);
//                TextView rp3 = (TextView)findViewById(R.id.rpos3);
//                rp3.setText(rpos3);

                    TextView skill1 = (TextView)findViewById(R.id.rstack1);
                    skill1.setText(rskill1);
                    TextView skill2 = (TextView)findViewById(R.id.rstack2);
                    skill2.setText(rskill2);
                    TextView skill3 = (TextView)findViewById(R.id.rstack3);
                    skill3.setText(rskill3);
                    TextView skill4 = (TextView)findViewById(R.id.rstack4);
                    skill4.setText(rskill4);
                    TextView skill5 = (TextView)findViewById(R.id.rstack5);
                    skill5.setText(rskill5);
                    TextView skill6 = (TextView)findViewById(R.id.rstack6);
                    skill6.setText(rskill6);
                    TextView skill7 = (TextView)findViewById(R.id.rstack7);
                    skill7.setText(rskill7);

                    TextView power1 = (TextView)findViewById(R.id.rpower1);
                    power1.setText(rpower1);
                    TextView power2 = (TextView)findViewById(R.id.rpower2);
                    power2.setText(rpower2);
                    TextView power3 = (TextView)findViewById(R.id.rpower3);
                    power3.setText(rpower3);

                    TextView des1 = (TextView)findViewById(R.id.rdescribe1);
                    des1.setText(rdescribe1);
                    TextView des2 = (TextView)findViewById(R.id.rdescribe2);
                    des2.setText(rdescribe2);
                    TextView des3 = (TextView)findViewById(R.id.rdescribe3);
                    des3.setText(rdescribe3);
                    TextView des4 = (TextView)findViewById(R.id.rdescribe4);
                    des4.setText(rdescribe4);
                    TextView des5 = (TextView)findViewById(R.id.rdescribe5);
                    des5.setText(rdescribe5);

                    TextView ideal1 = (TextView)findViewById(R.id.rcandidate1);
                    ideal1.setText(i1);
                    TextView ideal2 = (TextView)findViewById(R.id.rcandidate2);
                    ideal2.setText(i2);
                    TextView ideal3 = (TextView)findViewById(R.id.rcandidate3);
                    ideal3.setText(i3);

                    TextView perks = (TextView)findViewById(R.id.rpb);
                    perks.setText(rpb);

                    TextView rsalex = (TextView)findViewById(R.id.rsal);
                    rsalex.setText(rsal);

//===================================================================================================================================================

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

    }//get data ends
}
