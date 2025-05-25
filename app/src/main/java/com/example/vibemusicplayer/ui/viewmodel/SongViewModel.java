package com.example.vibemusicplayer.ui.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

public class SongViewModel extends ViewModel {
    @SuppressLint("Range")
    public MediaPlayer getLocalMusicByNameAndArtist(Context context, String songName, String songArtist) {
        String filePath = null;
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(
                musicUri,
                new String[]{MediaStore.Audio.Media.DATA}, // 获取文件路径
                MediaStore.Audio.Media.TITLE + " = ? AND " + MediaStore.Audio.Media.ARTIST + " = ?", // 使用歌曲名和歌手作为过滤条件
                new String[]{songName, songArtist},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)); // 获取文件路径
            cursor.close(); // 关闭游标
        }

        if (filePath != null) {
            // 找到了对应的文件路径，创建MediaPlayer并设置数据源为本地文件
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(context, Uri.parse(filePath));
                mediaPlayer.prepare();
                return mediaPlayer;
            } catch (IOException e) {
                Log.e("MusicMetadataHelper", "Failed to get MP3 file", e);
                // 如果发生异常，返回 null
                return null;
            }
        } else {
            // 未找到对应资源，返回 null
            Log.e("MusicMetadataHelper", "Failed to get MP3 file");
            return null;
        }
    }
}
