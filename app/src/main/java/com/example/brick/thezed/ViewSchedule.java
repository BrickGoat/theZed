package com.example.brick.thezed;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewSchedule extends AppCompatActivity {

    private ArrayList<String> eventTitles= new ArrayList<>();
    private ArrayList<String> eventDeadLines= new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList();
    DatabaseHelper myDb;
    private static final String TAG = "ViewSchedule";

    public ViewSchedule() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        Log.d(TAG, "onCreate: ViewSchedule");
        populateRecyclerView();
    }

    private void populateRecyclerView(){
        //get all entries
        //ArrayList<String> activityTypes = new ArrayList<>();
        String activityTypes;
        myDb = new DatabaseHelper(this);
        Cursor data = myDb.getListContents();
        if (data.getCount() == 0){
            Toast.makeText(ViewSchedule.this, "Empty", Toast.LENGTH_LONG).show();
        }else {
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
        createRecyclerView();}
    }

    public void createRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, eventTitles, mImages, eventDeadLines);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
