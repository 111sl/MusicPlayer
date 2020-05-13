package com.example.uncledrew.musicplayer.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.uncledrew.musicplayer.R;
import com.example.uncledrew.musicplayer.pojo.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerService extends Service {

    private List<Song> data = new ArrayList<>();
    private PlayerBinder playerBinder = new PlayerBinder();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int playNowIndex = -1;//当前播放曲目下标


    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return playerBinder;
    }

    public class PlayerBinder extends Binder{
        public void play(final int position){
            if(position != playNowIndex){
                playNowIndex = position;
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(data.get(position).getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            Intent intent = new Intent("com.uncle.CHANGESONG");
                            intent.putExtra("position",position);
                            sendBroadcast(intent);
                            mediaPlayer.start();
                        }
                    });

                    //监听是否播放完毕
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next();
                        }
                    });

                    /**
                     * setOnErrorListener方法如果不写，会由于在reset后调用prepare等方法
                     * 后直接跳到onCompletion导致上一曲下一曲时跳过一些歌曲的bug出现                     *
                     */
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            return true;
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //初始化数据
        public List<Song> init(){
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
            return data;
        }

        public void pause(){
            if(mediaPlayer.isPlaying()){
//                pause.setImageResource(R.drawable.ic_play_normal);
                mediaPlayer.pause();
            }else{
//                pause.setImageResource(R.drawable.ic_pause_normal);
                mediaPlayer.start();
            }
        }

        public void next(){
            if(playNowIndex+1<data.size()){
                play(playNowIndex+1);
            }
        }

        public void pre(){
            if(playNowIndex-1>=0){
                play(playNowIndex-1);
            }
        }

        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }

        public void seekTo(int progress){
            mediaPlayer.seekTo(progress);
        }

        public int getMax(int position){
            return mediaPlayer.getDuration();
        }

        public  int getCurrentPosition(){
           return  mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


}
