package com.example.brick.thezed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentActivities extends Fragment
{
    View view;
    private Button btnAddActivity;
    private Button btnModifyActivty;

    public FragmentActivities()
    {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activities_fragment, container, false);
        btnAddActivity = (Button) view.findViewById(R.id.addActivity);
        btnModifyActivty = (Button) view.findViewById(R.id.modifyActivity);
        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent add = new Intent(getContext(), addActivityPage.class);
                startActivity(add);
            }
        });

        return view;

    }
}
