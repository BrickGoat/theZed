package com.example.brick.thezed;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Brick
 * Page taking in Schedule entries
 */
public class addActivityPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    DatabaseHelper myUnsortedDb;
    private ScheduleEntry scheduleEntry;
    private LocalDate localDateInitial;
    private LocalDate localDateDue;
    private LocalTime localTimeDue;
    private TextView subTime;
    private TextInputLayout textInputEntryName;
    private TextInputLayout textInputEntryDescription;
    private boolean dateSwitch;
    private String reminderPeriod;
    private EditText reminderView;

    private static final String TAG = "addActivityPage";

    public addActivityPage() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.add_activity_fragment);
        Log.d(TAG, "onCreate: addActivityPage");
        subTime = findViewById(R.id.timeDisplay);
        reminderView = findViewById(R.id.reminderTime);
        //notificationManager = NotificationManagerCompat.from(this);
        myUnsortedDb = new DatabaseHelper(this);
        //subject drop down set up
        final Spinner spinnerActivities = findViewById(R.id.spinner1);
        final Spinner spinnerReminders = findViewById(R.id.spinnerReminder);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activityTypesArray, R.layout.spinner_list_item);
        ArrayAdapter<CharSequence> adapterReminder = ArrayAdapter.createFromResource(this, R.array.reminderTimeIntervals, R.layout.spinner_list_item);
        spinnerActivities.setAdapter(adapter);
        spinnerReminders.setAdapter(adapterReminder);
        spinnerActivities.setOnItemSelectedListener(this);
        spinnerReminders.setOnItemSelectedListener(this);
        //initial calendar date
        final Button initialDateButton = findViewById(R.id.initialDateButton);
        initialDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
                dateSwitch = true;
                validateName();
            }
        });
        //deadline calendar date
        Button deadLine = findViewById(R.id.dueByButton);
        deadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "deadline Picker");
                dateSwitch = false;
            }
        });
        Button timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        //
        textInputEntryName = findViewById(R.id.EntryName);
        textInputEntryDescription = findViewById(R.id.EntryDescription);
        //Attempt to submit entry to database
        Button submit = findViewById(R.id.SubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEntry(spinnerActivities, spinnerReminders);
            }
        });
    }

    public void addData(ScheduleEntry scheduleEntry) {
        if (scheduleEntry.isFull()) {
            long id = myUnsortedDb.addData(scheduleEntry.getTitle(), scheduleEntry.getDescription(), scheduleEntry.getActivityType()
                    , scheduleEntry.getStringInitialDate(), scheduleEntry.getStringFullDueDate());
            if (id > 0) {
                //createNotification(name, dueDate, time);
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            } else {
                Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_LONG).show();
        }
    }


    public void submitEntry(Spinner spinnerActivities, Spinner spinnerReminders) {
        Log.d(TAG, "onClick: Submit");
        String subject = spinnerActivities.getSelectedItem().toString();
        if (subject.equals("Select an item…")) {
            subject = null;
        }
        if (localDateDue != null & localDateInitial != null && localTimeDue != null && validateName() && validateDescription() && subject != null) {
            String nameInput = textInputEntryName.getEditText().getText().toString().trim();
            String descriptionInput = textInputEntryDescription.getEditText().getText().toString().trim();

            LocalDateTime localDateTimeDue = LocalDateTime.of(localDateDue, localTimeDue);

            scheduleEntry = new ScheduleEntry(nameInput, descriptionInput, subject, localDateInitial, localDateTimeDue);
            //subTime.setText(scheduleEntry.getStringFullDueDate());
            addData(scheduleEntry);
            String reminderTime = reminderView.getText().toString();
            int i = 0;
            try {
                i = Integer.parseInt(reminderTime);
            } catch (Exception e) {
                Log.d(TAG, "onItemSelected: " + e);
            }
            long Reminder = setReminder(i, reminderPeriod, localDateTimeDue);
            setAlarm(Reminder);
        } else {
            Toast.makeText(getApplicationContext(), "Data Not Inserted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView initial = findViewById(R.id.textView3);
        TextView dueBy = findViewById(R.id.textView5);
        LocalDate localDate = LocalDate.of(year, month + 1, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCurr = localDate.format(formatter);
        if (dateSwitch) {
            initial.setText(dateCurr);
            localDateInitial = localDate;
        } else {
            dueBy.setText(dateCurr);
            localDateDue = localDate;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime localTime = LocalTime.of(hourOfDay, minute);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        String am_pm;
        if (hourOfDay < 12) {//15 pm 3:52 12:52
            am_pm = " AM";
        } else {
            am_pm = " PM";
        }
        String timeDue = localTime.format(formatter) + am_pm;
        subTime.setText(timeDue);
        localTimeDue = localTime;
        //Toast.makeText(getApplicationContext(), hourOfDay + am_pm, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String reminderTime = reminderView.getText().toString();
        if (adapterView.getItemAtPosition(0).toString().equals("Select a time…")) {
            if (position > 0) {
                reminderPeriod = adapterView.getItemAtPosition(position).toString();
                String reminder = reminderTime + " " + reminderPeriod;
                Toast.makeText(adapterView.getContext(), reminder, Toast.LENGTH_SHORT).show();
            }
        } else {
            String text = adapterView.getItemAtPosition(position).toString();
            if (position > 0)
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public long setReminder(int i, String time, LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String date;
        switch (time) {
            case "min before":
                localDateTime = localDateTime.minusMinutes(i);
                 date= localDateTime.format(formatter);
                Log.d(TAG, "setReminder: " + date);
                break;
            case "hour before":
                localDateTime = localDateTime.minusHours(i);
                date = localDateTime.format(formatter);
                Log.d(TAG, "setReminder: " + date);
                break;
            case "day before":
               localDateTime = localDateTime.minusDays(i);
                date = localDateTime.format(formatter);
                Log.d(TAG, "setReminder: " + date);
                break;
            case "week before":
                localDateTime = localDateTime.minusWeeks(i);
                date = localDateTime.format(formatter);
                Log.d(TAG, "setReminder: " + date);
                break;
            case "No reminder":
                break;
            default:
                Log.d(TAG, "setReminder: default");
        }
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //private boolean
    private boolean validateName() {
        String EntryName = textInputEntryName.getEditText().getText().toString().trim();

        if (EntryName.isEmpty()) {
            textInputEntryName.setError("Field can't be empty");
            return false;
        } else {
            textInputEntryName.setError(null);
            return true;
        }
    }

    private boolean validateDescription() {
        String EntryName = textInputEntryDescription.getEditText().getText().toString().trim();

        if (EntryName.isEmpty()) {
            textInputEntryDescription.setError("Field can't be empty");
            return false;
        } else {
            textInputEntryDescription.setError(null);
            return true;
        }
    }

    private void setAlarm(long reminder) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("entry", scheduleEntry);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.d(TAG, "setAlarm: ");
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder, pendingIntent);
    }
}
