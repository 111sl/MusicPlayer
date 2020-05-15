package com.example.uncledrew.musicplayer.AsynTask;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsynTest extends AsyncTask <Void,Integer,Boolean>{
    private Context context;

    public AsynTest(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        URL url = null;
        try {
            url = new URL("http://music.163.com/song/media/outer/url?id=1427051960.mp3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        OutputStream output=null;
        String path="file";
        String fileName="2.mp3";
        String SDCard = Environment.getExternalStorageDirectory()+"";
        String pathName = SDCard+"/"+path+"/"+fileName;//文件存储路径
        File file=new File(pathName);
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(6000);
            connection.setReadTimeout(6000);
            InputStream input = connection.getInputStream();
            Log.d("000000000000", "0");

            if(file.exists()){
                return false;
            }else{
                String dir=SDCard+"/"+path;
                new File(dir).mkdir();//新建文件夹
                file.createNewFile();//新建文件
                output=new FileOutputStream(file);
                //读取文件
                byte[] buffer = new byte[4 * 1024];
                int len;
                while ((len=input.read(buffer))!= -1) {
                    output.write(buffer,0,len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            Toast.makeText(context,"完成",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"失败或已存在",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
