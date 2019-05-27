package com.example.brick.thezed;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager activityButtons;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MainActivity");
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        appBarLayout = findViewById(R.id.appBarId);
        createMainViewPager();
        tabLayout.setupWithViewPager(viewPager);
 //       createActivitiesPager();

    }
    //horizontal swipe fragments
    private void createMainViewPager(){
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentActivities(), "activities");
        adapter.AddFragment(new FragmentSchedule(), "schedule");
        adapter.AddFragment(new FragmentSettings(), "settings");
        viewPager.setAdapter(adapter);
    }
    //Activity Buttons
   // private void createActivitiesPager(){
  //      activityButtons = findViewById(R.id.viewPagerActivities);
  //      ViewPagerAdapter adapterButtons = new ViewPagerAdapter(getSupportFragmentManager());
 //       adapterButtons.AddFragment(new FragmentAddActivity(), "add");
 //       adapterButtons.AddFragment(new FragmentModifyActivity(), "change");
 //       activityButtons.setAdapter(adapterButtons);
 //   }


}
