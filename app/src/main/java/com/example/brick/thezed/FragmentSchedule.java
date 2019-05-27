package com.example.brick.thezed;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FragmentSchedule extends Fragment
{
    View view;
    private ArrayList<String> eventTitles= new ArrayList<>();
    private ArrayList<String> eventDeadLines= new ArrayList<>();
    private ArrayList mImages = new ArrayList();
    DatabaseHelper myDb;
    Button viewSchedule;
    public FragmentSchedule()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        viewSchedule = view.findViewById(R.id.ViewSchedule);
        viewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent schedule = new Intent(getActivity(), ViewSchedule.class);
                startActivity(schedule);
            }
        });
        return view;
    }

    private void getListEntries(){
        //get all entries
        //ArrayList<String> activityTypes = new ArrayList<>();
        String activityTypes;
        Cursor data = myDb.getListContents();
        while (data.moveToNext()){
          eventTitles.add(data.getString(1));
            eventDeadLines.add(data.getString(5));
            activityTypes = data.getString(3);
            switch (activityTypes){
                case "SLEEP":
                    mImages.add(R.drawable.sleepinginbed);
                    break;
                case "MEAL":
                    mImages.add(R.drawable.meal);
                    break;
                case "TESTSTUDY":
                    mImages.add(R.drawable.exam);
                    break;
                case "HOMEWORK":
                    mImages.add(R.drawable.books);
                    break;
                case "SKILLTRAINING":
                    mImages.add(R.drawable.exercise);
                    break;
                case "PROJECTBUILDING":
                    mImages.add(R.drawable.hammer);
                    break;
                case "ENTERTAINMENT":
                    mImages.add(R.drawable.tv);
                    break;
                case "GENERALSTUDY":
                    mImages.add(R.drawable.books);
                case "SOCIALTIME":
                    mImages.add(R.drawable.groupofpeople);
                default:
                    mImages.add(R.drawable.error);}
            }
        }

    public void createRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), eventTitles, mImages, eventDeadLines);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
