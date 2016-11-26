package com.example.wholovesyellow.ics115_labatory;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import java.util.Collection;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveReqFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;

    public ActiveReqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_active_req, container, false);
        final ListView listView = (ListView) vg.findViewById(R.id.lv_user_activereq);
        swipeRefreshLayout = (SwipeRefreshLayout)vg.findViewById(R.id.swipe_active_req);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.iron), getResources().getColor(R.color.accepted));

        final ProgressBar progressSpinner = (ProgressBar) vg.findViewById(R.id.progressBar);
        progressSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progressSpinner.setVisibility(View.VISIBLE);

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
                    Collections.reverse(list);
                    ListViewActiveReqAdapter adapter = new ListViewActiveReqAdapter(container.getContext(), R.layout.fragment_active_req, list);
                    listView.setAdapter(adapter);
                    listView.setEmptyView(vg.findViewById(R.id.nothing_here));
                    progressSpinner.setVisibility(View.GONE);
                } catch (Exception e) {
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
        final ListView listView = (ListView) vg.findViewById(R.id.lv_user_activereq);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());
        client.get("http://urag.co/labatory_api/api/requests", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    if(!response.equalsIgnoreCase("")) {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(ctr);
                            int request_from = jsonObject.getInt("request_from");

                            if (request_from == Integer.parseInt(Model.getUsername())) {
                                int req_id = jsonObject.getInt("request_id");
                                String req_item = jsonObject.getString("request_item");
                                String date = jsonObject.getString("date_requested");
                                int status = jsonObject.getInt("request_status");
                                Log.d("LIST: ", req_id + "-" + req_item + "-" + date + "-" + status);
                                list.add(req_id + "," + req_item + "," + date + "," + status);

                            }
                        }
                    }
                    Collections.reverse(list);
                    ListViewActiveReqAdapter adapter = new ListViewActiveReqAdapter(getContext(), R.layout.fragment_active_req, list);
                    listView.setAdapter(adapter);
                    listView.setEmptyView(vg.findViewById(R.id.nothing_here));
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                swipeRefreshLayout.setRefreshing(false);
                error.printStackTrace();
            }
        });
    }

}
