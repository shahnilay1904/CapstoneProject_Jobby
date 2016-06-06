package com.nkdroid.tinderswipe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilayshah on 5/25/16.
 */
public class CustomUsersAdapter extends ArrayAdapter<User> {

    private List<User> users;
    public CustomUsersAdapter(Context context, int resource, List<User> user) {
        super(context, resource, user);
        this.users = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        if (convertView == null) {
            row = inf.inflate(R.layout.content_chat_list_view_page, parent, false);
        }
        TextView textView = (TextView) row.findViewById(R.id.tvName);
        textView.setText(users.get(position).getName());
        TextView textViewloc = (TextView) row.findViewById(R.id.tvHometown);
        textViewloc.setText(users.get(position).getDescription());
        TextView textView2 = (TextView) row.findViewById(R.id.id);
        textView2.setText(users.get(position).getidentity());

        ImageView imageView = (ImageView) row.findViewById(R.id.ivUserIcon);
        try {
            int imageID = users.get(position).getImageID();
//            InputStream inputStream = getContext().getAssets().open(imageID);
//            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageResource(imageID);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error in Loading image", "Image error");
        }
        return row;
    }







//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        User user = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_chat_list_view_page, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHometown);
//        // Populate the data into the template view using the data object
//        tvName.setText(user.name);
//        tvHome.setText(user.hometown);
//        // Return the completed view to render on screen
//        return convertView;
//    }
}
