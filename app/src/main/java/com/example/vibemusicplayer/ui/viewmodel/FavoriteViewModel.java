package com.example.vibemusicplayer.ui.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vibemusicplayer.ui.database.SongDatabaseHelper;
import com.example.vibemusicplayer.ui.model.Song;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<Song>> favoriteLiveData = new MutableLiveData<>();
    private final List<Song> allFavorites = new ArrayList<>();
    private int currentPage = 0;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    public LiveData<List<Song>> getFavoriteLiveData() {
        return favoriteLiveData;
    }

    // 异步加载所有收藏
    public void loadAllData(Context context) {
        if (isLoading) return;
        isLoading = true;
        currentPage = 0;
        allFavorites.clear();

        new Thread(() -> {
            SongDatabaseHelper dbHelper = new SongDatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM favorite_song", null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String artist = cursor.getString(cursor.getColumnIndex("artist"));
                    String album = cursor.getString(cursor.getColumnIndex("album"));
                    String duration = cursor.getString(cursor.getColumnIndex("duration"));
                    String uri = cursor.getString(cursor.getColumnIndex("uri"));
                    Uri albumArtUri = Uri.parse(uri);
                    Song song = new Song(name, artist, album, duration, albumArtUri);
                    allFavorites.add(song);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            isLoading = false;
            hasMoreData = allFavorites.size() > 0;
            new Handler(Looper.getMainLooper()).post(this::loadNextPage);
        }).start();
    }

    // 内存分页
    public void loadNextPage() {
        int fromIndex = currentPage * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allFavorites.size());
        if (fromIndex < toIndex) {
            List<Song> currentList = new ArrayList<>(allFavorites.subList(0, toIndex));
            favoriteLiveData.postValue(currentList);
            currentPage++;
            hasMoreData = toIndex < allFavorites.size();
        } else {
            hasMoreData = false;
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }
}