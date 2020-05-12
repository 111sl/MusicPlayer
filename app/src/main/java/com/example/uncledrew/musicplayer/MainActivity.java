package com.example.uncledrew.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView songName = findViewById(R.id.song_name);
        ImageView pre = findViewById(R.id.pre);
        ImageView backward = findViewById(R.id.backward);
        ImageView pause = findViewById(R.id.pause);
        ImageView forward = findViewById(R.id.forward);
        ImageView next = findViewById(R.id.next);
        ListView songList = findViewById(R.id.song_list);

        //模拟数据
        List<String> data = new ArrayList<>();
        for (int i=0;i<9;i++){
            data.add("last dance");
            data.add("we are young");
            data.add("yellow");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        songList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre:
                break;
            case R.id.forward:
                break;
            case R.id.pause:
                break;
            case R.id.backward:
                break;
            case R.id.next:
                break;
            default:
        }
    }
}
