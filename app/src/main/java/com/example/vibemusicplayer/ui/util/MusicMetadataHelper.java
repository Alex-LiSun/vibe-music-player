package com.example.vibemusicplayer.ui.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import java.io.File;
import java.io.FileOutputStream;

public class MusicMetadataHelper {
    @SuppressLint("Range")
    public static String getMusicFilePath(ContentResolver contentResolver, long songId) {
        String filePath = null;
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(
                musicUri,
                new String[]{MediaStore.Audio.Media.DATA}, // 获取文件路径
                MediaStore.Audio.Media._ID + " = ?", // 使用 songId 作为过滤条件
                new String[]{String.valueOf(songId)},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)); // 获取文件路径
            cursor.close(); // 关闭游标
        }

        return filePath; // 返回文件路径
    }

    public static Uri getAlbumArtFromFile(Context context, String filePath) {
        Uri albumArtUri = null;

        try {
            Mp3File mp3File = new Mp3File(filePath);

            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3File.getId3v2Tag();

                if (id3v2Tag != null && id3v2Tag.getAlbumImage() != null) {
                    byte[] albumImage = id3v2Tag.getAlbumImage(); // 获取封面数据

                    // 创建临时文件保存封面
                    File tempFile = File.createTempFile("album_art_", ".jpg", context.getCacheDir());
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    fos.write(albumImage); // 将封面数据写入临时文件
                    fos.close();

                    albumArtUri = Uri.fromFile(tempFile); // 返回临时文件的 URI
                }
            }

        } catch (Exception e) {
            Log.e("MusicMetadataHelper", "Failed to get album art from MP3 file", e);
        }

        return albumArtUri;
    }
}
