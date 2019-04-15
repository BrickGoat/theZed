package com.example.brick.thezed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentSchedule extends Fragment
{
    View view;
    ListView scheduleList;
    String nameList[];
    int dueDates[];

    public FragmentSchedule()
    {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        scheduleList = (ListView) view.findViewById(R.id.scheduleListView);
        //scheduleList.setAdapter(new CustomListAdapter(getActivity(), nameList, dueDates));
        return view;
    }

    public static class FragmentAddActivity {
    }
}
