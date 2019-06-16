package com.example.brick.thezed;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ViewSchedule extends AppCompatActivity {

    private static final String TAG = "ViewSchedule";
    private ArrayList<ScheduleEntry> fullSchedule = new ArrayList<>();
    DatabaseHelper myDb;
    private boolean typeOfList = true;

    public ViewSchedule() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.view_schedule);
        Log.d(TAG, "onCreate: ViewSchedule");
        populateRecyclerView();
        getIncomingIntent();
        createRecyclerView();
    }

    public void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: ");
        if (getIntent().hasExtra("order")) {
            String order = getIntent().getStringExtra("order");
            setScheduleList(order);
        }
        if (getIntent().hasExtra("modify")){
            typeOfList = false;
        }
    }

    public void setScheduleList(String order) {
        switch (order) {
            case "date":
                //for (int i = 0; i < fullSchedule.size(); i++){
                //     ScheduleEntry entry = fullSchedule.get(i);
                Collections.sort(fullSchedule);


                break;
            case "alpha":
                Comparator<ScheduleEntry> alpha = new Comparator<ScheduleEntry>() {
                    public int compare(ScheduleEntry s1, ScheduleEntry s2) {
                        return s1.getTitle().toLowerCase().compareTo(s2.getTitle().toLowerCase());
                    }
                };
                Collections.sort(fullSchedule, alpha);
                break;
            default:
                break;
        }
    }

    private void populateRecyclerView() {
        //get all entries
        //ArrayList<String> activityTypes = new ArrayList<>();
        String activityTypes;
        myDb = new DatabaseHelper(this);
        Cursor data = myDb.getListContents();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        if (data.getCount() == 0) {
            Toast.makeText(ViewSchedule.this, "Empty", Toast.LENGTH_LONG).show();
        } else {
            long i = 1;
            while (data.moveToNext()) {
                ScheduleEntry dateSort = new ScheduleEntry();
                    LocalDate localDateInitial = LocalDate.parse(data.getString(4));
                    LocalDateTime localDateTimeDue = LocalDateTime.parse(data.getString(5), formatter1);
                    dateSort = new ScheduleEntry(data.getString(1), data.getString(2), data.getString(3),
                            localDateInitial, localDateTimeDue);

                    Log.d(TAG, "populateRecyclerView: " + i);

                activityTypes = data.getString(3);
                switch (activityTypes) {
                    case "SLEEP":
                        dateSort.setImageView(R.drawable.sleepinginbed);
                        break;
                    case "MEAL":
                        dateSort.setImageView(R.drawable.meal);
                        break;
                    case "TESTSTUDY":
                        dateSort.setImageView(R.drawable.exam);
                        break;
                    case "HOMEWORK":
                        dateSort.setImageView(R.drawable.books);
                        break;
                    case "SKILLTRAINING":
                        dateSort.setImageView(R.drawable.exercise);
                        break;
                    case "PROJECTBUILDING":
                        dateSort.setImageView(R.drawable.hammer);
                        break;
                    case "ENTERTAINMENT":
                        dateSort.setImageView(R.drawable.tv);
                        break;
                    case "GENERALSTUDY":
                        dateSort.setImageView(R.drawable.books);
                    case "SOCIALTIME":
                        dateSort.setImageView(R.drawable.groupofpeople);
                    default:
                        dateSort.setImageView(R.drawable.error);
                }
                dateSort.setId(i);
                i++;
                fullSchedule.add(dateSort);
            }
        }
    }

    public void createRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, fullSchedule, typeOfList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
