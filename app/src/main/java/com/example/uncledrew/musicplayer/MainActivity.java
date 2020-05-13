package com.example.uncledrew.musicplayer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import com.example.uncledrew.musicplayer.service.PlayerService;

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
    private ListView songList;
    private PlayerService.PlayerBinder playBinder;
    private TextView playTime;
    private TextView totalTime;
    public static final int UPDATE_TEXT = 1;
    RefreshReceiver refreshReceiver = new RefreshReceiver();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playBinder = (PlayerService.PlayerBinder)service;
            data = playBinder.init();
            SongAdapter adapter = new SongAdapter(MainActivity.this,R.layout.song_item,data);
            songList.setAdapter(adapter);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
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
        songList = findViewById(R.id.song_list);
        playTime = findViewById(R.id.play_time);
        totalTime = findViewById(R.id.total_time);

        IntentFilter intentFilter = new IntentFilter("com.uncle.CHANGESONG");

        registerReceiver(refreshReceiver,intentFilter);

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
                playBinder.seekTo(progress);
            }
        });

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playBinder.play(position);
                refresh(position);
            }
        });
        Intent intent = new Intent(this,PlayerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre:
                playBinder.pre();
                break;
            case R.id.forward:
                break;
            case R.id.pause:
                if(playBinder.isPlaying()){
                    pause.setImageResource(R.drawable.ic_play_normal);
                }else{
                    pause.setImageResource(R.drawable.ic_pause_normal);
                }
                playBinder.pause();
                break;
            case R.id.backward:
                break;
            case R.id.next:
                playBinder.next();
                break;
            default:
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    data = playBinder.init();
                }else {

                }
                break;
            default:
        }
    }

    private void refresh(int position){
        songName.setText(data.get(position).getName());
        int totalTimeNum = playBinder.getMax(position);
        String timeNum;
        if(totalTimeNum/1000/60>0){
            if(totalTimeNum/1000%60<10){
                timeNum = totalTimeNum/1000/60 + ":0"+ totalTimeNum/1000%60;
            }else{
                timeNum = totalTimeNum/1000/60 + ":"+ totalTimeNum/1000%60;
            }
        }else if(totalTimeNum/1000<10){
            timeNum = "0:0" + totalTimeNum/1000;
        }else{
            timeNum = "0:" + totalTimeNum/1000;
        }
        totalTime.setText(timeNum);
        seekBar.setMax(playBinder.getMax(position));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int currentPosition = playBinder.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);
            }
        },0,500);

    }


    class RefreshReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position",0);
            refresh(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshReceiver);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    int totalTimeNum = playBinder.getCurrentPosition();
                    String timeNum;
                    if(totalTimeNum/1000/60>0){
                        if(totalTimeNum/1000%60<10){
                            timeNum = totalTimeNum/1000/60 + ":0"+ totalTimeNum/1000%60;
                        }else{
                            timeNum = totalTimeNum/1000/60 + ":"+ totalTimeNum/1000%60;
                        }
                    }else if(totalTimeNum/1000<10){
                        timeNum = "0:0" + totalTimeNum/1000;
                    }else{
                        timeNum = "0:" + totalTimeNum/1000;
                    }
                    playTime.setText(timeNum);
                    break;
                default:
                    break;
            }
        }
    };
}
