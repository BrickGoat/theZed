package com.example.brick.thezed;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class addActivityPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener{

    private TextInputLayout textInputEntryName;
    private TextInputLayout textInputEntryDescription;
    private String dateCurr;
    private TextView initial;
    private TextView dueBy;
    private TextView time;
    private boolean dateSwitch;
    public addActivityPage() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_fragment);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activityTypesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button initialDate = findViewById(R.id.initialDateButton);
        Button deadLine = findViewById(R.id.dueByButton);

        initial = findViewById(R.id.textView3);
        dueBy = findViewById(R.id.textView5);
        initialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
                dateSwitch = true;
            }
        });
        deadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "deadline Picker");
                dateSwitch = false;
            }
        });
        textInputEntryName = findViewById(R.id.EntryName);
        textInputEntryDescription = findViewById(R.id.EntryDescription);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        dateCurr = DateFormat.getDateInstance().format(calendar.getTime());
        if (dateSwitch){
            initial.setText(dateCurr);
        }
        else {
            dueBy.setText(dateCurr);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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

    private boolean validateDescriptoion() {
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
