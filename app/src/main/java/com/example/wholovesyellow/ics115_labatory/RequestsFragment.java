package com.example.wholovesyellow.ics115_labatory;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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
public class RequestsFragment extends Fragment {

    ArrayList<String> list = new ArrayList<String>();
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_admin_req, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)vg.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.iron), getResources().getColor(R.color.accepted));
        final ListView listView = (ListView) vg.findViewById(R.id.lv_admin_req);
        final ProgressBar progressSpinner = (ProgressBar) vg.findViewById(R.id.progressBar);
        progressSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        progressSpinner.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        client.get("http://urag.co/labatory_api/api/requests", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list2 = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    if(!response.equalsIgnoreCase("")){
                        JSONArray jArray = new JSONArray(response);

                        for(int i=0; i<jArray.length(); i++){
                            JSONObject json_data = jArray.getJSONObject(i);
                            String request_from = json_data.getString("request_from");
                            String request_item = json_data.getString("request_item");
                            int request_id = json_data.getInt("request_id");
                            int request_status = json_data.getInt("request_status");
                            if(request_status == 0){
                                list2.add("Request #" + request_id);
                            }
                        }
                        ListViewItemsReqAdapter adapter = new ListViewItemsReqAdapter(container.getContext(), R.layout.fragment_admin_req, list2);
                        listView.setAdapter(adapter);



                    }

                    progressSpinner.setVisibility(View.GONE);
                    listView.setEmptyView(vg.findViewById(R.id.nothing_here));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    refreshContent(vg);

            }
        });



        return vg;
    }

    private void refreshContent(final ViewGroup vg){
        final ListView listView = (ListView) vg.findViewById(R.id.lv_admin_req);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());

        client.get("http://urag.co/labatory_api/api/requests", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list2 = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    if(!response.equalsIgnoreCase("")){
                        JSONArray jArray = new JSONArray(response);

                        for(int i=0; i<jArray.length(); i++){
                            JSONObject json_data = jArray.getJSONObject(i);
                            String request_from = json_data.getString("request_from");
                            String request_item = json_data.getString("request_item");
                            int request_id = json_data.getInt("request_id");
                            int request_status = json_data.getInt("request_status");
                            if(request_status == 0){
                                list2.add("Request #" + request_id);
                            }
                        }
                        ListViewItemsReqAdapter adapter = new ListViewItemsReqAdapter(getContext(), R.layout.fragment_admin_req, list2);
                        listView.setAdapter(adapter);
                    }
                    listView.setEmptyView(vg.findViewById(R.id.nothing_here));
                    swipeRefreshLayout.setRefreshing(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
