package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wholovesyellow.ics115_labatory.Model.Model;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 11/21/2016.
 */

public class ListViewItemsInvAdapter extends ArrayAdapter<String> {

    public int layout;
    private Context context;

    public ListViewItemsInvAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mainViewholder = null;

        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_listview_admin_inv, null);
            ViewHolder vh = new ViewHolder();
            vh.listItemText = (TextView) view.findViewById(R.id.admin_inv_list);
            vh.addButton = (Button) view.findViewById(R.id.admin_inv_add);
            vh.removeButton = (Button) view.findViewById(R.id.admin_inv_remove);

            view.setTag(vh);
        } else {
            mainViewholder = (ViewHolder) view.getTag();
            mainViewholder.listItemText.setText(getItem(position));
        }

        TextView listItemText = (TextView) view.findViewById(R.id.admin_inv_list);
        listItemText.setText(getItem(position));

        TextView listQtyText = (TextView) view.findViewById(R.id.admin_inv_qty);
        listQtyText.setText(getItem(position));

        Button addButton = (Button) view.findViewById(R.id.admin_inv_add);
        Button removeButton = (Button) view.findViewById(R.id.admin_inv_remove);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate alert dialog xml
                LayoutInflater li = LayoutInflater.from(context);
                View dialogView = li.inflate(R.layout.add_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // set title
                alertDialogBuilder.setTitle("Add Item");
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.editText_inputAdd);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //logic for add
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

                Toast.makeText(v.getContext(), "Add!", Toast.LENGTH_SHORT).show();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate alert dialog xml
                LayoutInflater li = LayoutInflater.from(context);
                View dialogView = li.inflate(R.layout.remove_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // set title
                alertDialogBuilder.setTitle("Remove Item");
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.editText_inputRemove);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //logic for remove
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

                Toast.makeText(v.getContext(), "Remove!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public class ViewHolder {
        TextView listItemText;
        Button addButton;
        Button removeButton;
    }
}
