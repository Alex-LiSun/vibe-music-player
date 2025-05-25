package com.example.vibemusicplayer.ui.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.ViewModel;

import com.example.vibemusicplayer.ui.model.Song;
import com.example.vibemusicplayer.ui.util.MusicMetadataHelper;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailViewModel extends ViewModel {
    public List<Song> getSongsByArtistName(Context context, String artistName) {
        List<Song> songs = new ArrayList<>();

        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(
                musicUri,
                null,
                MediaStore.Audio.Media.IS_MUSIC + " != 0 AND " +
                        MediaStore.Audio.Media.ARTIST + " = ?",
                new String[]{artistName},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String filePath = MusicMetadataHelper.getMusicFilePath(context.getContentResolver(), songId); // 获取文件路径

                long milliseconds = Long.parseLong(duration);
                @SuppressLint("DefaultLocale") String formattedDuration = String.format("%d:%02d",
                        (milliseconds / 60000),
                        (milliseconds % 60000) / 1000);

                // 获取封面
                Uri albumArtUri = MusicMetadataHelper.getAlbumArtFromFile(context, filePath); // 使用文件路径获取封面

                Song song = new Song(name, artist, album, formattedDuration, albumArtUri);
                songs.add(song);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return songs;
    }
}