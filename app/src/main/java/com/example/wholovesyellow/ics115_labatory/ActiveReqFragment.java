package com.example.wholovesyellow.ics115_labatory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class ActiveReqFragment extends Fragment {


    public ActiveReqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_active_req, container, false);
        final ListView listView = (ListView) vg.findViewById(R.id.lv_user_activereq);
        listView.setEmptyView(vg.findViewById(R.id.nothing_here));

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        client.get("http://urag.co/labatory_api/api/requests", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    JSONArray jsonArray = new JSONArray(response);

                    for(int ctr = 0; ctr < jsonArray.length(); ctr++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(ctr);
                        int request_from = jsonObject.getInt("request_from");

                        if(request_from == Integer.parseInt(Model.getUsername())) {
                            int req_id = jsonObject.getInt("request_id");
                            String req_item = jsonObject.getString("request_item");
                            String date = jsonObject.getString("date_requested");
                            int status = jsonObject.getInt("request_status");
                            Log.d("LIST: ",req_id + "-" + req_item + "-" + date + "-" + status);
                            list.add(req_id + "," + req_item + "," + date + "," + status);
                        }
                    }

                    ListViewActiveReqAdapter adapter = new ListViewActiveReqAdapter(container.getContext(), R.layout.fragment_active_req, list);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
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
