package com.example.vibemusicplayer.ui.model;

import android.net.Uri;

import java.io.Serializable;

public class Song implements Serializable {
    private String name;
    private String artist;
    private String album;
    private String duration;
    private Uri albumArtUri; // 封面图片的 URI

    public Song(String name, String artist, String album, String duration, Uri albumArtUri) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.albumArtUri = albumArtUri;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDuration() {
        return duration;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }
}
