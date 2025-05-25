package com.example.vibemusicplayer.ui.model;

import android.net.Uri;

public class Album {
    private String name;
    private String count;
    private Uri albumArtUri; // 封面图片的 URI

    public Album(String name, String count, Uri albumArtUri) {
        this.name = name;
        this.count = count;
        this.albumArtUri = albumArtUri;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }
}
