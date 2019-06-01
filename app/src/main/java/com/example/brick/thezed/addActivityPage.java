package com.example.brick.thezed;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private TextInputLayout textInputEntryName;
    private TextInputLayout textInputEntryDescription;
    private TextView initial;
    private TextView dueBy;
    private TextView subTime;
    private boolean dateSwitch;
    private String nameInput;
    private String descriptionInput;
    private static final String TAG = "addActivityPage";
    private String initialDate;

    public addActivityPage() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_fragment);
        Log.d(TAG, "onCreate: addActivityPage");
        myUnsortedDb = new DatabaseHelper(this);
        initial = findViewById(R.id.textView3);
        dueBy = findViewById(R.id.textView5);
        //subject drop down set up
        final Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activityTypesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        subTime = findViewById(R.id.timeDisplay);
        textInputEntryName = findViewById(R.id.EntryName);
        textInputEntryDescription = findViewById(R.id.EntryDescription);
        //Attempt to submit entry to database
        Button submit = findViewById(R.id.SubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName()) {
                    nameInput = textInputEntryName.getEditText().getText().toString().trim();
                }
                if (validateDescription()) {
                    descriptionInput = textInputEntryDescription.getEditText().getText().toString().trim();
                }
                String subject = spinner.getSelectedItem().toString();
                if (subject.isEmpty())
                    Log.d(TAG, "spinner");
                String initialDate = initial.getText().toString();
                if (initialDate.isEmpty())
                    Log.d(TAG, "initial");
                String dueDate = dueBy.getText().toString();
                if (dueDate.isEmpty())
                    Log.d(TAG, "dueDate");
                String time = subTime.getText().toString();
                if (time.isEmpty())
                    Log.d(TAG, "time");
                addData(nameInput, descriptionInput, subject, initialDate, dueDate, time);
            }
        });
    }

    public void addData(String name, String description, String subject,
                        String initialDate, String dueDate, String time) {
        if (name != null){
        boolean isInserted = myUnsortedDb.addData(name, description, subject, initialDate, dueDate, time);
        if (isInserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
        } else {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_LONG).show();
        }}
        else {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dateCurr = DateFormat.getDateInstance().format(calendar.getTime());

        if (dateSwitch) {
            initial.setText(dateCurr);
        } else {
            dueBy.setText(dateCurr);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        subTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
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
}
