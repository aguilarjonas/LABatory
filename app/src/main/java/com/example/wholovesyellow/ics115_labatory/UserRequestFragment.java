package com.example.wholovesyellow.ics115_labatory;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class UserRequestFragment extends Fragment {


    public UserRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_user_request, container, false);
        final ListView listView = (ListView) vg.findViewById(R.id.lv_user_req);


        final ProgressBar progressSpinner = (ProgressBar) vg.findViewById(R.id.progressBar);
        progressSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progressSpinner.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", Model.getToken());
        client.get("http://urag.co/labatory_api/api/items", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<String> list = new ArrayList<String>();
                    String response = new String(responseBody, "UTF-8");
                    JSONArray jsonArray = new JSONArray(response);

                    for(int ctr = 0; ctr < jsonArray.length(); ctr++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(ctr);
                        String item_name = jsonObject.getString("item_name");

                        list.add(item_name);
                    }

                    ListViewItemsReqUserAdapter adapter = new ListViewItemsReqUserAdapter(container.getContext(), R.layout.fragment_user_request, list);
                    listView.setAdapter(adapter);
                    listView.setEmptyView(vg.findViewById(R.id.nothing_here));
                    progressSpinner.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return vg;
    }

}
