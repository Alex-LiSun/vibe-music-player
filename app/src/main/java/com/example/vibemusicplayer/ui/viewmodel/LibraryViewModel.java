package com.example.vibemusicplayer.ui.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vibemusicplayer.ui.model.Song;
import com.example.vibemusicplayer.ui.util.MusicMetadataHelper;

import java.util.ArrayList;
import java.util.List;

public class LibraryViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<Song>> songsLiveData = new MutableLiveData<>();
    private final List<Song> allSongs = new ArrayList<>();
    private int currentPage = 0;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    public LiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }

    // 异步加载所有数据
    public void loadAllData(Context context) {
        if (isLoading) return;
        isLoading = true;
        currentPage = 0;
        allSongs.clear();

        new Thread(() -> {
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(
                    musicUri,
                    null,
                    MediaStore.Audio.Media.IS_MUSIC + " != 0",
                    null,
                    MediaStore.Audio.Media.TITLE + " ASC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    long songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String filePath = MusicMetadataHelper.getMusicFilePath(context.getContentResolver(), songId);

                    long milliseconds = Long.parseLong(duration);
                    @SuppressLint("DefaultLocale") String formattedDuration = String.format("%d:%02d",
                            (milliseconds / 60000),
                            (milliseconds % 60000) / 1000);

                    Uri albumArtUri = MusicMetadataHelper.getAlbumArtFromFile(context, filePath);
                    Song song = new Song(name, artist, album, formattedDuration, albumArtUri);
                    allSongs.add(song);
                } while (cursor.moveToNext());
                cursor.close();
            }
            isLoading = false;
            hasMoreData = allSongs.size() > 0;
            // 回到主线程执行分页和LiveData更新
            new Handler(Looper.getMainLooper()).post(this::loadNextPage);
        }).start();
    }

    // 内存分页
    public void loadNextPage() {
        int fromIndex = currentPage * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allSongs.size());
        if (fromIndex < toIndex) {
            List<Song> currentList = new ArrayList<>(allSongs.subList(0, toIndex));
            songsLiveData.postValue(currentList);
            currentPage++;
            hasMoreData = toIndex < allSongs.size();
        } else {
            hasMoreData = false;
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
