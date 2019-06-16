package com.example.brick.thezed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<ScheduleEntry> fullSchedule = new ArrayList<>();
    private Context mContext;
    private boolean typeOfList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView title;
        TextView dueDate;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.eventIcon);
            this.title = itemView.findViewById(R.id.EntryTitle);
            this.dueDate = itemView.findViewById(R.id.deadLine);
            this.parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    public RecyclerViewAdapter(Context mContext,
                               ArrayList<ScheduleEntry> fullSchedule, boolean typeOfList) {
        this.mContext = mContext;
        this.fullSchedule = fullSchedule;
        this.typeOfList = typeOfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        final ScheduleEntry entry = fullSchedule.get(position);
        Glide.with(mContext)
                .asBitmap()
                .load(entry.getImageView())
                .into(viewHolder.image);
        viewHolder.title.setText(entry.getTitle());
        viewHolder.dueDate.setText(entry.getStringFullDueDate());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeOfList){
                Intent details = new Intent(mContext, DetailedScheduleView.class);
                details.putParcelableArrayListExtra("schedule", fullSchedule);
                details.putExtra("entry", fullSchedule.get(position));
                if (entry.getTitle() == null){
                    Log.d(TAG, "getIncomingIntent: ERROR NIGGA");
                }
                mContext.startActivity(details);}else {
                    Intent modify = new Intent(mContext, ModifyActivity.class);
                    modify.putParcelableArrayListExtra("schedule", fullSchedule);
                    modify.putExtra("entry", fullSchedule.get(position));
                    if (entry.getTitle() == null){
                        Log.d(TAG, "getIncomingIntent: ERROR NIGGA");
                    }
                    mContext.startActivity(modify);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullSchedule.size();
    }
}

