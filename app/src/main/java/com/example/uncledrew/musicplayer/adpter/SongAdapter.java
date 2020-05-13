package com.example.uncledrew.musicplayer.adpter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uncledrew.musicplayer.R;
import com.example.uncledrew.musicplayer.pojo.Song;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    private int resourceId;

    public SongAdapter(Context context, int resource,List<Song> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView songName = view.findViewById(R.id.songName);
        songName.setText(song.getName());
        return view;
    }
}
