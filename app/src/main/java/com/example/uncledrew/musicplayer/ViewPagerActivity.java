package com.example.uncledrew.musicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.uncledrew.musicplayer.adpter.ViewPagerAdapter;
import com.example.uncledrew.musicplayer.fragment.FirstFragment;
import com.example.uncledrew.musicplayer.fragment.SecondFragment;
import com.example.uncledrew.musicplayer.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    public static final String TAG = "FirstFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Activity:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager,fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity:onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity:onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity:onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity:onResume");
    }
}
