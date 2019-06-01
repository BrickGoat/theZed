package com.example.brick.thezed;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailedScheduleView extends AppCompatActivity {
    private static final String TAG = "DetailedScheduleView";
    private CircleImageView imageView;
    private TextView titleView;
    private TextView activityView;
    private TextView initialDateView;
    private TextView dueDateView;
    private TextView descriptionView;
    private ArrayList<scheduleEntry> fullSchedule = new ArrayList<>();
    private scheduleEntry entry;

    public DetailedScheduleView() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_shedule_view);
        Log.d(TAG, "onCreate: starting");
        titleView = findViewById(R.id.titleFull);
        descriptionView = findViewById(R.id.descriptionText);
        initialDateView = findViewById(R.id.initialDateFull);
        dueDateView = findViewById(R.id.dueDateFull);
        activityView = findViewById(R.id.activityFull);
        imageView = findViewById(R.id.activityImage);
        titleView.setPaintFlags(titleView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getIncomingIntent();
        populatePage();
        Button home = findViewById(R.id.home);
        Button previous = findViewById(R.id.previous);
        Button next = findViewById(R.id.next);
        Button delete = findViewById(R.id.deleteButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainPage);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(getApplicationContext(), DetailedScheduleView.class);
                details.putParcelableArrayListExtra("schedule", fullSchedule);
                details.putExtra("entry", getPrevious());
                startActivity(details);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(getApplicationContext(), DetailedScheduleView.class);
                details.putParcelableArrayListExtra("schedule", fullSchedule);
                details.putExtra("entry", getNext());
                startActivity(details);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteContact(view);
                Intent mainPage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainPage);
            }
        });
    }

    private scheduleEntry getPrevious() {
        int last = fullSchedule.size() - 1;
        for (int i = 0; i < fullSchedule.size(); i++) {
            scheduleEntry tmp = fullSchedule.get(i);
            if (tmp.equals(entry)) {
                if (i == 0) {
                    return fullSchedule.get(last);
                }
                return fullSchedule.get(i - 1);
            }
        }
        return fullSchedule.get(0);
    }

    private scheduleEntry getNext() {
        int last = fullSchedule.size() - 1;
        for (int i = 0; i < fullSchedule.size(); i++) {
            scheduleEntry tmp = fullSchedule.get(i);
            if (tmp.equals(entry)) {
                if (i == last) {
                    return fullSchedule.get(0);
                }
                return fullSchedule.get(i + 1);
            }
        }
        return fullSchedule.get(0);
    }

    public void DeleteContact(View view){
        DatabaseHelper myDB =new DatabaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
        myDB.deleteinformation(entry.getTitle(),sqLiteDatabase);
        Toast.makeText(getApplication(),"  Deleted content",Toast.LENGTH_LONG).show();

    }
    //sets all the fields to entry properties
    private void populatePage() {
        Log.d(TAG, "populatePage");
        titleView.setText(entry.getTitle());
        descriptionView.setText(entry.getDescription());
        initialDateView.setText(entry.getStringInitialDate());
        dueDateView.setText(entry.getStringDueDate());
        String activityType = entry.getActivityType();
        Integer image;
        activityView.setText(activityType);
        switch (activityType) {
            case "SLEEP":
                image = (R.drawable.sleepinginbed);
                break;
            case "MEAL":
                image = (R.drawable.meal);
                break;
            case "TESTSTUDY":
                image = (R.drawable.exam);
                break;
            case "HOMEWORK":
                image = (R.drawable.books);
                break;
            case "SKILLTRAINING":
                image = (R.drawable.exercise);
                break;
            case "PROJECTBUILDING":
                image = (R.drawable.hammer);
                break;
            case "ENTERTAINMENT":
                image = (R.drawable.tv);
                break;
            case "GENERALSTUDY":
                image = (R.drawable.books);
            case "SOCIALTIME":
                image = (R.drawable.groupofpeople);
            default:
                image = (R.drawable.error);
        }

        Glide.with(this)
                .asBitmap()
                .load(image)
                .into(imageView);
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("entry")) {
            fullSchedule = getIntent().getParcelableArrayListExtra("schedule");
            entry = (scheduleEntry) getIntent().getParcelableExtra("entry");
            if (entry.getTitle() == null) {
                Log.d(TAG, "getIncomingIntent: ERROR NIGGA");
            }
        } else {
            Log.d(TAG, "getIncomingIntent: bad intent");
        }
    }

}

