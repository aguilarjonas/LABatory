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
public class InventoryFragment extends Fragment {


    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_admin_inv, container, false);
        ArrayList<String> list = new ArrayList<String>();
        list.add("PROJECTOR");
        list.add("CABLE");

        ListViewItemsInvAdapter adapter = new ListViewItemsInvAdapter(list, container.getContext());

        ListView listView = (ListView) vg.findViewById(R.id.lv_admin_inv);
        listView.setAdapter(adapter);

        return vg;
    }

}
