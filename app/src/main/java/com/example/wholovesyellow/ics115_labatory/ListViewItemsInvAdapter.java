package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Jonas on 11/21/2016.
 */

public class ListViewItemsInvAdapter extends ArrayAdapter<String> {

    public int layout;
    private Context context = getContext();

    public ListViewItemsInvAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mainViewholder = null;

        String item = getItem(position);
        String[] text = item.split("-");
        final int item_id = Integer.parseInt(text[0].trim());
        String item_name = text[1].trim();
        int item_qty = Integer.parseInt(text[2].trim());

        //connection to api
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
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
        listItemText.setText(item_name);

        final TextView listQtyText = (TextView) view.findViewById(R.id.admin_inv_qty);
        listQtyText.setText(String.valueOf(item_qty));

        Button addButton = (Button) view.findViewById(R.id.admin_inv_add);
        Button removeButton = (Button) view.findViewById(R.id.admin_inv_remove);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate alert dialog xml
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialogView = li.inflate(R.layout.add_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // set title
                alertDialogBuilder.setTitle("Add Quantity");
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.editText_inputAdd);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog,
                                                        int id) {
                                        try {
                                            JSONObject jsonParams = new JSONObject();
                                            jsonParams.put("quantity", userInput.getText());

                                            //progress spinner
                                            final ProgressDialog progress = new ProgressDialog(context);
                                            progress.setTitle("Loading");
                                            progress.setMessage("Wait while loading...");
                                            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                            progress.show();

                                            StringEntity entity = new StringEntity(jsonParams.toString());
                                            client.post(null, "http://urag.co/labatory_api/api/items/" + item_id + "/add", entity, "application/json", new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                    try {
                                                        String response = new String(responseBody, "UTF-8");
                                                        JSONObject obj = new JSONObject(response);
                                                        String details = obj.getString("details");
                                                        
                                                        notifyDataSetChanged();
                                                        dialog.dismiss();
                                                        progress.dismiss();
                                                        Toast.makeText(context, details, Toast.LENGTH_LONG).show();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    try {
                                                        String response = new String(responseBody, "UTF-8");
                                                        JSONObject obj = new JSONObject(response);
                                                        String details = obj.getString("details");

                                                        dialog.dismiss();
                                                        notifyDataSetChanged();
                                                        progress.dismiss();
                                                        Toast.makeText(context, details, Toast.LENGTH_LONG).show();

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
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
                alertDialogBuilder.setTitle("Remove Quantity");
                // set custom_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = (EditText) dialogView
                        .findViewById(R.id.editText_inputRemove);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog,
                                                        int id) {
                                        try {
                                            JSONObject jsonParams = new JSONObject();
                                            jsonParams.put("quantity", userInput.getText());

                                            StringEntity entity = new StringEntity(jsonParams.toString());

                                            //progress spinner
                                            final ProgressDialog progress = new ProgressDialog(context);
                                            progress.setTitle("Loading");
                                            progress.setMessage("Wait while loading...");
                                            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                            progress.show();

                                            client.post(null, "http://urag.co/labatory_api/api/items/" + item_id + "/remove", entity, "application/json", new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                    try {
                                                        String response = new String(responseBody, "UTF-8");
                                                        JSONObject obj = new JSONObject(response);
                                                        String details = obj.getString("details");
                                                        dialog.dismiss();

                                                        notifyDataSetChanged();
                                                        progress.dismiss();
                                                        Toast.makeText(context, details, Toast.LENGTH_LONG).show();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    try {
                                                        String response = new String(responseBody, "UTF-8");
                                                        JSONObject obj = new JSONObject(response);
                                                        String details = obj.getString("details");
                                                        dialog.dismiss();
                                                        progress.dismiss();
                                                        Toast.makeText(context, details, Toast.LENGTH_LONG).show();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
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
