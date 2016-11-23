package com.example.wholovesyellow.ics115_labatory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wholovesyellow.ics115_labatory.Model.Model;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_admin_hist, container, false);

        final ListView listView = (ListView) vg.findViewById(R.id.lv_admin_hist);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());
        client.get("http://urag.co/labatory_api/api/requests", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list2 = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    JSONArray jArray = new JSONArray(response);

                    for(int i=0; i<jArray.length(); i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        String request_from = json_data.getString("request_from");
                        String request_item = json_data.getString("request_item");
                        int request_id = json_data.getInt("request_id");
                        int request_status = json_data.getInt("request_status");
                        if(request_status != 0){
                            list2.add("#" + request_id + " - " + request_from + " - " + request_item);
                        }


                    }
                    ListViewItemsHistAdapter adapter = new ListViewItemsHistAdapter(container.getContext(), R.layout.fragment_admin_hist, list2);
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });



        return vg;
    }

}
