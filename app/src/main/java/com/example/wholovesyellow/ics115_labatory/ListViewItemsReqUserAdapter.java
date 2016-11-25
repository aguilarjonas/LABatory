package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wholovesyellow.ics115_labatory.Model.Model;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Jonas on 11/23/2016.
 */

public class ListViewItemsReqUserAdapter extends ArrayAdapter<String> {

    private int layout;
    private Context context;

    public ListViewItemsReqUserAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_listview_user_req, null);
        }

        final TextView listItemText = (TextView) view.findViewById(R.id.user_req_list);
        listItemText.setText(getItem(position));

        Button selectedItem = (Button) view.findViewById(R.id.user_req);

        selectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to request for a " + listItemText.getText().toString() + "?")
                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    client.addHeader("Authorization", Model.getToken());
                                    JSONObject jsonParams = new JSONObject();
                                    jsonParams.put("request_item", listItemText.getText().toString());

                                    StringEntity entity = new StringEntity(jsonParams.toString());
                                    client.post(null, "http://urag.co/labatory_api/api/requests", entity, "application/json", new AsyncHttpResponseHandler() {

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            try {
                                                Toast.makeText(context.getApplicationContext(), "You have successfully requested for: " + listItemText.getText().toString(), Toast.LENGTH_LONG).show();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            Toast.makeText(context.getApplicationContext(), "Failed to request for: " + listItemText.getText().toString(), Toast.LENGTH_LONG).show();
                                            error.printStackTrace();
                                            error.getCause();
                                        }

                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        } )
                        .setCancelable(true)
                        .create()
                        .show();


            }
        });

        return view;
    }
}
