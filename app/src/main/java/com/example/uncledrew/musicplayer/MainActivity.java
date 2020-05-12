package com.example.uncledrew.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<String> data = new ArrayList<>();

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
        SeekBar seekBar = findViewById(R.id.seekBar);
        ListView songList = findViewById(R.id.song_list);
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            init();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        songList.setAdapter(adapter);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取进度
                int progress = seekBar.getProgress();
            }
        });

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play(position);
            }
        });
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

    //初始化数据
    private void init(){
        ContentResolver resolver = getContentResolver();
        Uri music = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String song = MediaStore.Video.Media.TITLE;
        Cursor query = resolver.query(music,null,null,null,null);
        while(query.moveToNext()){
            data.add(query.getString(query.getColumnIndex(song)));
        }
    }


    private void play(int positiom){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    init();
                }else {

                }
                break;
            default:
        }
    }
}
