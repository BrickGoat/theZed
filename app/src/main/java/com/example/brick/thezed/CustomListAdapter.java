package com.example.brick.thezed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter for schedule list
 */
public class CustomListAdapter extends BaseAdapter {

    private String scheduleNames[];
    private int dueDates[];
    private LayoutInflater inflter;

    public CustomListAdapter(Context scheduleFragment,
                             String scheduleNames[], int dueDates[])
    {
        inflter = (LayoutInflater.from(scheduleFragment));
        this.scheduleNames = scheduleNames;
        this.dueDates = dueDates;
    }

    @Override
    public int getCount()
    {
        return scheduleNames.length;
    }

    @Override
    public Object getItem(int i){return null;}

    @Override
    public long getItemId(int i){return 0;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;

        if(view == null)
        {
            view = inflter.inflate(R.layout.listview, viewGroup, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.textView);
            holder.dueDate = view.findViewById(R.id.textView2);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(scheduleNames[i]);
        holder.dueDate.setText(dueDates[i]);
        return view;
    }

    static class ViewHolder{
    TextView name;
    TextView dueDate;
    }
}