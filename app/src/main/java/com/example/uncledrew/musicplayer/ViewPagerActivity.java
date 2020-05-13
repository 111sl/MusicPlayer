package com.example.uncledrew.musicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    public static final String TAG = "FirstFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Activity:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        image1 = findViewById(R.id.img_1);
        image2 = findViewById(R.id.img_2);
        image3 = findViewById(R.id.img_3);
        viewPager = findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        fragmentList.add(new ThirdFragment());
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager,fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i==0){
                    image1.setImageResource(R.drawable.ic_play_normal);
                    image2.setImageResource(R.drawable.seek_thumb_pic);
                    image3.setImageResource(R.drawable.seek_thumb_pic);
                }else if(i==1){
                    image2.setImageResource(R.drawable.ic_play_normal);
                    image1.setImageResource(R.drawable.seek_thumb_pic);
                    image3.setImageResource(R.drawable.seek_thumb_pic);
                }else{
                    image3.setImageResource(R.drawable.ic_play_normal);
                    image2.setImageResource(R.drawable.seek_thumb_pic);
                    image1.setImageResource(R.drawable.seek_thumb_pic);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
