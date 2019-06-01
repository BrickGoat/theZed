package com.example.brick.thezed;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Brick
 */
public class FragmentAddActivity extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Context addFragment;
    TextView header;

    public FragmentAddActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_activity_fragment, container, false);
        addFragment = getContext();
        Spinner spinner = view.findViewById(R.id.spinner1);
        header = view.findViewById(R.id.addActivityPageHeader);
        header.setPaintFlags(header.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(addFragment, R.array.activityTypesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        ViewPagerAdapter fragmentAdapter = new ViewPagerAdapter(getFragmentManager());
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
