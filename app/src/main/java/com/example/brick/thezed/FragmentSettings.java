package com.example.brick.thezed;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class FragmentSettings extends Fragment {
    View view;
    Switch aSwitch;
    public FragmentSettings() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);
        aSwitch = view.findViewById(R.id.switch1);
        Button clearSchedule = view.findViewById(R.id.clearSchedule);
        clearSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB =new DatabaseHelper(view.getContext());
                SQLiteDatabase sqLiteDatabase = myDB.getReadableDatabase();
                myDB.deleteinformation(0,sqLiteDatabase);
                Toast.makeText(view.getContext(), "Schedule Deleted", Toast.LENGTH_LONG).show();
            }
        });
        aSwitch.setChecked(Utils.onActivityCreateSetTheme(getActivity()));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    Utils.changeToTheme(getActivity(), true);
                }else {
                    Utils.changeToTheme(getActivity(), false);
                }
            }
        });
        Button deleteReminders = view.findViewById(R.id.clearReminders);
        deleteReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AlertReceiver.class);
                PendingIntent.getBroadcast(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                Toast.makeText(getContext(), "Reminders Deleted", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
