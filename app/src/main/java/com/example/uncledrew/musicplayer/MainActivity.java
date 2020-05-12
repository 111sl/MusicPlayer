package com.example.uncledrew.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
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

import com.example.uncledrew.musicplayer.pojo.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Song> data = new ArrayList<>();
    private ImageView pause;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private TextView songName;
    private int playNowIndex = -1;//当前播放曲目下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songName = findViewById(R.id.song_name);
        ImageView pre = findViewById(R.id.pre);
        ImageView backward = findViewById(R.id.backward);
        pause = findViewById(R.id.pause);
        ImageView forward = findViewById(R.id.forward);
        ImageView next = findViewById(R.id.next);
        pre.setOnClickListener(this);
        backward.setOnClickListener(this);
        pause.setOnClickListener(this);
        forward.setOnClickListener(this);
        next.setOnClickListener(this);
        seekBar = findViewById(R.id.seekBar);
        ListView songList = findViewById(R.id.song_list);
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            init();
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        SongAdapter adapter = new SongAdapter(this,R.layout.song_item,data);
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
                mediaPlayer.seekTo(progress);
            }
        });

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(playNowIndex == -1 || position != playNowIndex){

                    playNowIndex = position;
                    play(position);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre:
                if(playNowIndex-1>=0){
                    play(playNowIndex-1);
                    playNowIndex--;
                }
                break;
            case R.id.forward:
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    pause.setImageResource(R.drawable.ic_play_normal);
                    mediaPlayer.pause();
                }else{
                    pause.setImageResource(R.drawable.ic_pause_normal);
                    mediaPlayer.start();
                }
                break;
            case R.id.backward:
                break;
            case R.id.next:
                if(playNowIndex+1<data.size()){
                    play(playNowIndex+1);
                    playNowIndex++;
                }
                break;
            default:
        }
    }

    //初始化数据
    private void init(){
        ContentResolver resolver = getContentResolver();
        Uri music = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String name = MediaStore.Video.Media.TITLE;
        String path = MediaStore.Video.Media.DATA;
        Cursor query = resolver.query(music,null,null,null,null);
        while(query.moveToNext()){
            Song song = new Song();
            String songName = query.getString(query.getColumnIndex(name));
            String songPath = query.getString(query.getColumnIndex(path));
            song.setName(songName);
            song.setPath(songPath);
            data.add(song);
        }
    }


    private void play(int position){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(data.get(position).getPath());
            songName.setText(data.get(position).getName());
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int currentPosition = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(currentPosition);
                        }
                    },0,500);
                }
            });

            //监听是否播放完毕
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(playNowIndex+1<data.size()){
                        play(playNowIndex+1);
                        playNowIndex++;
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
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
