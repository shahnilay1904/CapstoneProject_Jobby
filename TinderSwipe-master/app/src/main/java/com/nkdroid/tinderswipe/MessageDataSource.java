package com.nkdroid.tinderswipe;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by nilayshah on 5/24/16.
 */
public class MessageDataSource {

    private static final Firebase sRef = new Firebase("https://sizzling-torch-8124.firebaseio.com/Chat");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";


    public static void saveMessage(Message message, String usr, String rcp){
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getText());
        msg.put(COLUMN_SENDER,usr);
        char[] xyz = usr.toCharArray();

        if(xyz[0] == 'S'){
            System.out.println("Seeker S="+xyz[0]);
            System.out.println("Username="+usr);
            System.out.println("Recipient="+rcp);
            sRef.child(rcp).child(usr).child(key).setValue(msg);

        }
        if(xyz[0] == 'C') {
            System.out.println("Company C="+xyz[0]);
            System.out.println("Username="+usr);
            System.out.println("Recipient="+rcp);

            sRef.child(usr).child(rcp).child(key).setValue(msg);
        }

//        sRef.child(usr).child(rcp).child(key).setValue(msg);
    }

    public static MessagesListener addMessagesListener(String usr, String rcp, final MessagesCallbacks callbacks){
        MessagesListener listener = new MessagesListener(callbacks);
        char[] xyz = usr.toCharArray();

        if(xyz[0] == 'S'){
            System.out.println("Seeker S="+xyz[0]);
            System.out.println("Username="+usr);
            System.out.println("Recipient="+rcp);
            sRef.child(rcp).child(usr).addChildEventListener(listener);

        }
        if(xyz[0] == 'C') {
            System.out.println("Company C="+xyz[0]);
            System.out.println("Username="+usr);
            System.out.println("Recipient="+rcp);

            sRef.child(usr).child(rcp).addChildEventListener(listener);
        }
        return listener;

    }

    public static void stop(MessagesListener listener){
        sRef.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener {
        private MessagesCallbacks callbacks;
        MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg = (HashMap)dataSnapshot.getValue();
            Message message = new Message();
            message.setSender(msg.get(COLUMN_SENDER));
            message.setText(msg.get(COLUMN_TEXT));
            try {
                message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            }catch (Exception e){
                Log.d(TAG, "Couldn't parse date" + e);
            }
            if(callbacks != null){
                callbacks.onMessageAdded(message);
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
    }


    public interface MessagesCallbacks{
        public void onMessageAdded(Message message);
    }
}
