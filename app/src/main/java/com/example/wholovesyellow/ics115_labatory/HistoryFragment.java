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
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_admin_hist, container, false);
        ArrayList<String> list = new ArrayList<String>();
        list.add("USER1");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");
        list.add("USER2");

        ListViewItemsHistAdapter adapter = new ListViewItemsHistAdapter(list, container.getContext());

        ListView listView = (ListView) vg.findViewById(R.id.lv_admin_hist);
        listView.setAdapter(adapter);

        return vg;
    }

}
