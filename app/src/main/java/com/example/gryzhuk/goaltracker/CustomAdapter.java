package com.example.gryzhuk.goaltracker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends ArrayAdapter {
    private Context context;


    public CustomAdapter(Context context,  List<Map.Entry<String, Object>> strings) {
        super(context,android.R.layout.simple_list_item_2, strings);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView;
        rowView = convertView != null ?
                convertView :
                inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) this.getItem(position);
       // TextView text1 = (TextView) rowView.findViewById(R.id.textUp);
        //TextView text2 = (TextView) rowView.findViewById(R.id.text2);

        TextView text1 = rowView.findViewById(android.R.id.text1);
        TextView text2 = rowView.findViewById(android.R.id.text2);


        text1.setText(entry.getKey());
        text2.setText(entry.getValue().toString());

        return rowView;
    }

}

