package com.example.wholovesyellow.ics115_labatory;

import android.app.AlertDialog;
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
        final String req_from = req_idString[1].trim();
        final String req_item = req_idString[2].trim();
        final int req_id = Integer.parseInt(req_idString[0].trim());

        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_listview_admin_req, null);
            ViewHolder vh = new ViewHolder();
            vh.listItemText = (TextView) view.findViewById(R.id.admin_user_list);
            vh.viewRequest = (Button) view.findViewById(R.id.admin_view_list);
            vh.viewRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle( "Request #" + req_id )
                            .setMessage( "From: " + req_from + "\nItem: " + req_item)
                            .setPositiveButton( "Accept", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    client.put("http://urag.co/labatory_api/api/requests/" + req_id + "/accept", new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            remove(getItem(position));
                                            notifyDataSetChanged();
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
                                    Log.d( "AlertDialog", "Negative" );
                                }
                            } )
                            .show();
                    Toast.makeText(v.getContext(), "Request #" + req_id, Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(vh);
        } else {
            mainViewholder = (ViewHolder) view.getTag();
            mainViewholder.listItemText.setText(getItem(position));
        }
        TextView listItemText2 = (TextView) view.findViewById(R.id.admin_user_list);
        listItemText2.setText(getItem(position));
        return view;
    }



    public class ViewHolder {
        TextView listItemText;
        Button viewRequest;
    }


}
