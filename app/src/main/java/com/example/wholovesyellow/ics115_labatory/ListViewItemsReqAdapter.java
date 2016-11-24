package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
 * Created by Jonas on 11/21/2016.
 */

public class ListViewItemsReqAdapter extends ArrayAdapter<String> {

    public int layout;
    private Context context;

    public ListViewItemsReqAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mainViewholder = null;

        String req = getItem(position);
        String[] text = req.split("#");
        String[] req_idString = text[1].split("-");
        final int req_id = Integer.parseInt(req_idString[0].trim());

        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_listview_admin_req, null);
            ViewHolder vh = new ViewHolder();
            vh.listItemText = (TextView) view.findViewById(R.id.admin_user_list);
            vh.viewRequest = (Button) view.findViewById(R.id.admin_view_list);

            view.setTag(vh);
        } else {
            mainViewholder = (ViewHolder) view.getTag();
            mainViewholder.listItemText.setText(getItem(position));
        }
        TextView listItemText2 = (TextView) view.findViewById(R.id.admin_user_list);
        listItemText2.setText(getItem(position));
        Button viewRequest2 = (Button) view.findViewById(R.id.admin_view_list);
        viewRequest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(getContext());
                progress.setTitle("Loading");
                progress.setMessage("Please wait...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                client.get("http://urag.co/labatory_api/api/requests/" + req_id, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String response = new String(responseBody, "UTF-8");
                            JSONObject obj = new JSONObject(response);
                            String req_from = obj.getString("request_from");
                            String req_item = obj.getString("request_item");
                            String date_req = obj.getString("date_requested");
                            progress.dismiss();
                            new AlertDialog.Builder(getContext())
                                    .setTitle( "Request #" + req_id )
                                    .setMessage("Date Requested: " + date_req + "\n\nFrom: " + req_from + "\nItem: " + req_item)
                                    .setPositiveButton( "Accept", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            final ProgressDialog progress = new ProgressDialog(getContext());
                                            progress.setTitle("Loading");
                                            progress.setMessage("Please wait...");
                                            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                            progress.show();
                                            client.put("http://urag.co/labatory_api/api/requests/" + req_id + "/accept", new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                    remove(getItem(position));
                                                    notifyDataSetChanged();
                                                    progress.dismiss();
                                                    Toast.makeText(getContext(), "Request accepted", Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton( "Decline", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            final ProgressDialog progress = new ProgressDialog(getContext());
                                            progress.setTitle("Loading");
                                            progress.setMessage("Please wait...");
                                            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                            progress.show();
                                            client.put("http://urag.co/labatory_api/api/requests/" + req_id + "/decline", new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                    remove(getItem(position));
                                                    notifyDataSetChanged();
                                                    progress.dismiss();
                                                    Toast.makeText(getContext(), "Request declined", Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    } )
                                    .setCancelable(true)
                                    .create()
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
        return view;
    }



    public class ViewHolder {
        TextView listItemText;
        Button viewRequest;
    }


}
