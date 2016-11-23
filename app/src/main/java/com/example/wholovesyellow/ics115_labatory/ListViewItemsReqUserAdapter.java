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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Jonas on 11/23/2016.
 */

public class ListViewItemsReqUserAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public ListViewItemsReqUserAdapter(ArrayList<String> list, Context context) {
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
            view = inflater.inflate(R.layout.layout_listview_user_req, null);
        }

        final TextView listItemText = (TextView) view.findViewById(R.id.user_req_list);
        listItemText.setText(list.get(position));

        Button selectedItem = (Button) view.findViewById(R.id.user_req);

        selectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("Authorization", "Bearer eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJjb25zdW1lcktleSI6IkJDSSIsInVzZXJJZCI6IjEiLCJpc3N1ZWRBdCI6IjExXC8yM1wvMjAxNiAxNToyMTo1MiArMDg6MDBOb3YiLCJkYXRhIjp7InVzZXJuYW1lIjoiMjAxMzA1NjA5OSIsInVzZXJfdHlwZSI6IjIifX0.vx4CE1xto6l0XiRoUWkEpn9W4sVdeldswHUwg1DtxcM");
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
        });

        return view;
    }
}
