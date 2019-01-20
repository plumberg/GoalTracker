package com.example.gryzhuk.goaltracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String>
{
    private Context context;
    private List<String> strings;

    public CustomAdapter(Context context, List<String> strings)
    {
        super(context, R.layout.content_main,strings);
        this.context = context;
        this.strings = new ArrayList<String>();
        this.strings = strings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.content_main, parent, true);

        TextView text1 = (TextView) rowView.findViewById(R.id.textUp);
        TextView text2 = (TextView) rowView.findViewById(R.id.text2);

        text1.setText(strings.get(position));
        text2.setText(strings.get(position+1));

        return rowView;
    }
}
