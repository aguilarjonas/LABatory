package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jonas on 11/22/2016.
 */

public class ListViewItemsHistAdapter extends ArrayAdapter<String> {

    public int layout;
    private Context context;

    public ListViewItemsHistAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ListViewItemsHistAdapter.ViewHolder mainViewholder = null;

        String req = getItem(position);
        String[] text = req.split("#");
        String[] req_idString = text[1].split("-");
        final int req_id = Integer.parseInt(req_idString[0].trim());

        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_listview_admin_hist, null);
            ListViewItemsHistAdapter.ViewHolder vh = new ListViewItemsHistAdapter.ViewHolder();
            vh.listItemText = (TextView) view.findViewById(R.id.admin_hist_list);
            vh.viewRequest = (Button) view.findViewById(R.id.admin_hist_view);
            vh.viewRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    client.get("http://urag.co/labatory_api/api/requests/" + req_id, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String response = new String(responseBody, "UTF-8");
                                JSONObject obj = new JSONObject(response);
                                String req_from = obj.getString("request_from");
                                String req_item = obj.getString("request_item");
                                int req_status = obj.getInt("request_status");
                                String date_req = obj.getString("date_requested");
                                String date_modified = obj.getString("date_statusChanged");
                                String req_careof = obj.getString("request_careof");
                                String statusText = "";
                                if(req_status == 1) { statusText = "Accepted"; }
                                else if(req_status == 2) {statusText = "Declined"; }

                                new AlertDialog.Builder(getContext())
                                        .setTitle( "Request #" + req_id )
                                        .setMessage( "From: " + req_from + "\nItem: " + req_item + "\nDate Requested: " + date_req + "\n\nStatus: " + statusText + "\nDate " + statusText + ": " + date_modified
                                                    + "\n" + statusText + " By: " + req_careof)
                                        .setPositiveButton( "Close", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                }
            });
            view.setTag(vh);
        } else {
            mainViewholder = (ListViewItemsHistAdapter.ViewHolder) view.getTag();
            mainViewholder.listItemText.setText(getItem(position));
        }
        TextView listItemText2 = (TextView) view.findViewById(R.id.admin_hist_list);
        listItemText2.setText(getItem(position));
        return view;
    }



    public class ViewHolder {
        TextView listItemText;
        Button viewRequest;
    }
}
