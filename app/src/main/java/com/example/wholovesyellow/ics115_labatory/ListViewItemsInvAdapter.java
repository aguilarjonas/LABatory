package com.example.wholovesyellow.ics115_labatory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jonas on 11/21/2016.
 */

public class ListViewItemsInvAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public ListViewItemsInvAdapter (ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_listview_admin_inv, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.admin_inv_list);
        listItemText.setText(list.get(position));

        Button addButton = (Button) view.findViewById(R.id.admin_inv_add);
        Button removeButton = (Button) view.findViewById(R.id.admin_inv_remove);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add!", Toast.LENGTH_SHORT).show();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Remove!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
