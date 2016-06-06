package com.nkdroid.tinderswipe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//Writing it for recruiter first..
public class ChatActivityMain extends ActionBarActivity implements View.OnClickListener,
        MessageDataSource.MessagesCallbacks {

    public static final String USER_EXTRA = "USER";

    public static final String TAG = "ChatActivity";

    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private String usrname;
    private ListView mListView;
    private Date mLastMessageDate = new Date();
    private String mConvoId;
    private MessageDataSource.MessagesListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        usrname = intent.getStringExtra("username");
        mRecipient = intent.getStringExtra("recipient");
        System.out.println("usrname="+usrname);
        System.out.println("Recipient=" + mRecipient);

        setContentView(R.layout.activity_chat_activity_main);


//============================================================================================================================================
        //Set Onclick listener for going back to the main activity - Recruiter or seeker
        ImageView backtomain = (ImageView) findViewById(R.id.backtomainxyz);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] xyz = usrname.toCharArray();
                System.out.println(xyz[0]);
                if (xyz[0] == 'S') {
                    //Give intent back to Seeker Activity
                    Intent intenttoseek = new Intent(ChatActivityMain.this, ChatListViewPage.class);
                    intenttoseek.putExtra("username", usrname);
                    finish();
                    startActivity(intenttoseek);
                } else {
                    //Give intent back to Recruiter Activity
                    Intent intenttorec = new Intent(ChatActivityMain.this, ChatListViewPage.class);
                    intenttorec.putExtra("username", usrname);
                    finish();
                    startActivity(intenttorec);
                }


            }
        });


//============================================================================================================================================
        mListView = (ListView)findViewById(R.id.messages_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        setTitle(mRecipient);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        String[] ids = {usrname,"-", mRecipient};
        Arrays.sort(ids);
        mConvoId = ids[0]+ids[1]+ids[2];

        mListener = MessageDataSource.addMessagesListener(usrname,mRecipient, this);





    }

    public void onClick(View v) {
        Intent intent = getIntent();
        final String usrname = intent.getStringExtra("username");
//        final String receiver = intent.getStringExtra("recipient");
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(usrname);

        MessageDataSource.saveMessage(msg,usrname, mRecipient);
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageDataSource.stop(mListener);
    }


    private class MessagesAdapter extends ArrayAdapter<Message> {
        MessagesAdapter(ArrayList<Message> messages){
            super(ChatActivityMain.this, R.layout.activity_message, R.id.message, messages);
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            nameView.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getSender().equals(mRecipient)){
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_right_green));
                } else{
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                }
                layoutParams.gravity = Gravity.RIGHT;
            }
//            else{
//                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
//                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
//                } else{
//                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
//                }
//                layoutParams.gravity = Gravity.LEFT;
//            }

            if (message.getSender().equals(usrname)) {
                if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                    nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
                } else {
                    nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                }
                layoutParams.gravity = Gravity.LEFT;

                nameView.setLayoutParams(layoutParams);
            }


            return convertView;
        }
    }



}
