package com.example.wholovesyellow.ics115_labatory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.wholovesyellow.ics115_labatory.Model.Model;
import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

/**
 * Created by Jonas on 11/25/2016.
 */

public class ListViewActiveReqAdapter extends ArrayAdapter<String> {

    public int layout;
    private Context context = getContext();

    public ListViewActiveReqAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder mainViewholder = null;

        String item = getItem(position);
        String[] text = item.split("-");
        final int req_id = Integer.parseInt(text[0].trim());
        String req_item = text[1].trim();
        String date = text[2].trim();
        int status = Integer.parseInt(text[3].trim());

        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.layout_listview_user_activereq, null);
            ViewHolder vh = new ViewHolder();
            vh.listItemText = (TextView) view.findViewById(R.id.user_active_req_list);
            vh.date = (TextView) view.findViewById(R.id.user_active_req_date);
            vh.status = (TextView) view.findViewById(R.id.user_active_req_status);

            view.setTag(vh);
        } else {
            mainViewholder = (ViewHolder) view.getTag();
            mainViewholder.listItemText.setText(getItem(position));
        }

        TextView listItemText = (TextView) view.findViewById(R.id.user_active_req_list);
        listItemText.setText("Request # " + req_id + "-" + req_item);

        TextView listItemDate = (TextView) view.findViewById(R.id.user_active_req_date);
        listItemDate.setText(date);


        if(status == 0) { //pending
            TextView listItemStatus = (TextView) view.findViewById(R.id.user_active_req_status);
            listItemStatus.setTextColor(view.getResources().getColor(R.color.pending));
            listItemStatus.setText(view.getResources().getString(R.string.pending));
        } else if(status == 1) { //accepted
            TextView listItemStatus = (TextView) view.findViewById(R.id.user_active_req_status);
            listItemStatus.setTextColor(view.getResources().getColor(R.color.accepted));
            listItemStatus.setText(view.getResources().getString(R.string.accepted));
        } else if(status == 2){ //declined
            TextView listItemStatus = (TextView) view.findViewById(R.id.user_active_req_status);
            listItemStatus.setTextColor(view.getResources().getColor(R.color.decllined));
            listItemStatus.setText(view.getResources().getString(R.string.declined));
        }

        return view;
    }

    public class ViewHolder {
        TextView listItemText;
        TextView date;
        TextView status;
    }
}
