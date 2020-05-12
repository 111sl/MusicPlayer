package com.example.uncledrew.musicplayer.pojo;

public class Song {
    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
