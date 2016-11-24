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
public class InventoryFragment extends Fragment {


    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_admin_inv, container, false);
        final ListView listView = (ListView) vg.findViewById(R.id.lv_admin_inv);
        listView.setEmptyView(vg.findViewById(R.id.nothing_here));

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
                        int quantity = jsonObject.getInt("item_quantity");

                        list.add(item_name);
                        list.add(String.valueOf(quantity));
                    }

                    ListViewItemsInvAdapter adapter = new ListViewItemsInvAdapter(container.getContext(), R.layout.fragment_admin_inv, list);
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
