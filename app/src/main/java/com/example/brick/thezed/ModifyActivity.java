package com.example.brick.thezed;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Brick
 * modify entry from Database
 */
public class ModifyActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private ArrayList<ScheduleEntry> fullSchedule = new ArrayList<>();
    private ScheduleEntry entry;
    private LocalDate localDateDue;
    private LocalTime localTimeDue;
    public static final String TAG = "Modify Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.modify_activity_page);
        //Current Values
        TextView currentHeader = findViewById(R.id.currentHeader);
        final TextView subjectCurr = findViewById(R.id.activityTypeCurrent);
        final TextView descriptionCurrent = findViewById(R.id.descriptionCurrent);
        final TextView dateDue = findViewById(R.id.deadLineCurrent);
        final TextView titleCurr = findViewById(R.id.titleCurrent);
        //New Values
        TextView newHeader = findViewById(R.id.NewHeader);
        final EditText titleNew = findViewById(R.id.titleNew);
        final EditText descriptionNew = findViewById(R.id.descriptionNew);
        final TextView deadLineNew = findViewById(R.id.deadLineNew);
        final TextView deadLineTimeNew = findViewById(R.id.deadLineTimeNew);
        Button dueTimeButton = findViewById(R.id.dueTimeButton4);
        dueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button dueDateButton = findViewById(R.id.dueDateButton3);
        dueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "deadline Picker");
            }
        });
        Button saveButton = findViewById(R.id.saveUpdatedInfo);
        //Spinner setup
        final Spinner activitySpinner = findViewById(R.id.spinnerActivities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activityTypesArray, R.layout.spinner_list_item);
        activitySpinner.setAdapter(adapter);
        activitySpinner.setOnItemSelectedListener(this);
        //Page Setup
        getIncomingIntent();
        populatePage(entry.getTitle(), entry.getDescription(), entry.getStringFullDueDate(), entry.getActivityType());
        currentHeader.setPaintFlags(currentHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        newHeader.setPaintFlags(newHeader.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descriptionNew.getText().toString();
                String subject = activitySpinner.getSelectedItem().toString();
                DatabaseHelper myUnsortedDb = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = myUnsortedDb.getWritableDatabase();
                String title = titleNew.getText().toString();
                String date = null;
                if (localDateDue != null && localTimeDue != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
                    LocalDateTime localDateTime = LocalDateTime.of(localDateDue, localTimeDue);
                    date = localDateTime.format(formatter);
                }
                if (title != null && !title.equals("Enter Title")) {
                    myUnsortedDb.updateTable("NAME", title, entry.getId(), sqLiteDatabase);
                    titleCurr.setText(title);
                    titleNew.setText("Enter Title");
                }
                if (!subject.equals("Select an item…")) {
                    myUnsortedDb.updateTable("SUBJECT", subject, entry.getId(), sqLiteDatabase);
                    subjectCurr.setText(subject);
                    activitySpinner.setSelection(0);
                }
                if (description != null && !description.equals("Enter Description")) {
                    myUnsortedDb.updateTable("DESCRIPTION", description, entry.getId(), sqLiteDatabase);
                    descriptionCurrent.setText(description);
                    descriptionNew.setText("Enter Description");
                }
                if (date != null) {
                    myUnsortedDb.updateTable("DUEDATE", date, entry.getId(), sqLiteDatabase);
                    dateDue.setText(date);
                    deadLineNew.setText("Date");
                    deadLineTimeNew.setText("Time");
                }
               // Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
               // startActivity(intent);
            }
        });
        Button home = findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if (getIntent().hasExtra("entry")) {
            fullSchedule = getIntent().getParcelableArrayListExtra("schedule");
            entry = getIntent().getParcelableExtra("entry");
            if (entry.getTitle() == null) {
                Log.d(TAG, "getIncomingIntent: ERROR NIGGA");
            }
        } else {
            Log.d(TAG, "getIncomingIntent: bad intent");
        }
    }

    public void populatePage(String titleNew, String descriptionNew, String deadLineNew, String activityNew) {
        TextView title = findViewById(R.id.titleCurrent);
        TextView description = findViewById(R.id.descriptionCurrent);
        TextView deadLine = findViewById(R.id.deadLineCurrent);
        TextView activity = findViewById(R.id.activityTypeCurrent);
        title.setText(titleNew);
        description.setText(descriptionNew);
        deadLine.setText(deadLineNew);
        activity.setText(activityNew);
        //reminder.setText(reminderNew);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        if (adapterView.getItemAtPosition(0).toString().equals("Select a time…")) {
            if (position > 0) {
                //reminderPeriod = adapterView.getItemAtPosition(position).toString();
                //String reminder = reminderTime + " " + reminderPeriod;
                //Toast.makeText(adapterView.getContext(), reminder, Toast.LENGTH_SHORT).show();
            }
        } else {
            String text = adapterView.getItemAtPosition(position).toString();
            if (position > 0)
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView dueBy = findViewById(R.id.deadLineNew);
        LocalDate localDate = LocalDate.of(year, month + 1, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCurr = localDate.format(formatter);
        dueBy.setText(dateCurr);
        localDateDue = localDate;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime localTime = LocalTime.of(hourOfDay, minute);
        TextView timeView = findViewById(R.id.deadLineTimeNew);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        String am_pm;
        if (hourOfDay < 12) {//15 pm 3:52 12:52
            am_pm = " AM";
        } else {
            am_pm = " PM";
        }
        String timeDue = localTime.format(formatter) + am_pm;
        timeView.setText(timeDue);
        localTimeDue = localTime;
        //Toast.makeText(getApplicationContext(), hourOfDay + am_pm, Toast.LENGTH_LONG).show();

    }
}
