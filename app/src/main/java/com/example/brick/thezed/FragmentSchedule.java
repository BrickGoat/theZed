package com.example.brick.thezed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FragmentSchedule extends Fragment {
    View view;
    private ArrayList<String> eventTitles = new ArrayList<>();
    private ArrayList<String> eventDeadLines = new ArrayList<>();
    private ArrayList mImages = new ArrayList();
    DatabaseHelper myDb;
    Button viewScheduleByDate;
    Button viewScheduleByTitle;
    public FragmentSchedule() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        viewScheduleByDate = view.findViewById(R.id.ViewSchedule);
        viewScheduleByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(getActivity(), ViewSchedule.class);
                schedule.putExtra("order", "date");
                startActivity(schedule);
            }
        });
        viewScheduleByTitle = view.findViewById(R.id.viewScheduleAZ);
        viewScheduleByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(getActivity(), ViewSchedule.class);
                schedule.putExtra("order", "alpha");
                startActivity(schedule);
            }
        });
        return view;
    }
}
