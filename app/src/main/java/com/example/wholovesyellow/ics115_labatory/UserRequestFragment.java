package com.example.wholovesyellow.ics115_labatory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserRequestFragment extends Fragment {


    public UserRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_user_request, container, false);
        ArrayList<String> list = new ArrayList<>();
        list.add("Projector");
        list.add("VGA Cable");

        ListViewItemsReqUserAdapter adapter = new ListViewItemsReqUserAdapter(list, container.getContext());

        ListView listView = (ListView) vg.findViewById(R.id.lv_user_req);
        listView.setAdapter(adapter);

        return vg;
    }

}
