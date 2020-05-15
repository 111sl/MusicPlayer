package com.example.uncledrew.musicplayer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uncledrew.musicplayer.AsynTask.AsynTest;

public class Download extends AppCompatActivity {

    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Button button = findViewById(R.id.download);
        time = findViewById(R.id.time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynTest asynTest = new AsynTest(Download.this);
                asynTest.execute();
            }
        });


        //新启动一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        mHandler.sendEmptyMessage(1);
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(2);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Long sysTime;
            CharSequence sysTimeStr;
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    sysTime = System.currentTimeMillis();
                    sysTimeStr = DateFormat.format("hh : mm", sysTime);
                    time.setText(sysTimeStr);
                    break;
                case 2:
                    sysTime = System.currentTimeMillis();
                    sysTimeStr = DateFormat.format("hh   mm", sysTime);
                    time.setText(sysTimeStr);
                    break;
            }
        }
    };
}
