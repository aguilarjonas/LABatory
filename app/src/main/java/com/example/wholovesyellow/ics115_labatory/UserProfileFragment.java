package com.example.wholovesyellow.ics115_labatory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wholovesyellow.ics115_labatory.Model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {


    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_user_profile, container, false);

        TextView name = (TextView) vg.findViewById(R.id.textView_name2);
        TextView position = (TextView) vg.findViewById(R.id.textView_position2);
        TextView username = (TextView) vg.findViewById(R.id.textView_username2);

        String fullname = Model.getFullname();
        String user_pos = Model.getPosition();
        String user_username = Model.getUsername();
        name.setText("Name: " + fullname);
        position.setText("Position: " + user_pos);
        username.setText("Username: " + user_username);
        // Inflate the layout for this fragment
        return vg;
    }

}
