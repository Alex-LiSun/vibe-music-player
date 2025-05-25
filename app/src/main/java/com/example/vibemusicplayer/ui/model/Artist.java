package com.example.vibemusicplayer.ui.model;

public class Artist {
    private String name;
    private String count;

    public Artist(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }
}
