package com.example.brick.thezed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter for schedule list **Not used**
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> eventTitles= new ArrayList<>();
    private ArrayList<String> eventDeadLines= new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView title;
        TextView dueDate;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.eventIcon);
            this.title = itemView.findViewById(R.id.EntryTitle);
            this.dueDate = itemView.findViewById(R.id.deadLine);
            this.parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    public RecyclerViewAdapter(Context mContext,
                               ArrayList<String> eventTitles, ArrayList<String> mImages, ArrayList<String> eventDeadLines)
    {
        this.mContext = mContext;
        this.eventTitles = eventTitles;
        this.eventDeadLines = eventDeadLines;
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(viewHolder.image);
        //viewHolder.dueDate.setText((CharSequence)eventDeadLines.get(position));
        viewHolder.title.setText(eventTitles.get(position));

    }

    @Override
    public int getItemCount() {
        return eventTitles.size();
    }
}

