package com.example.vibemusicplayer.ui.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vibemusicplayer.ui.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<Artist>> artistsLiveData = new MutableLiveData<>();
    private final List<Artist> allArtists = new ArrayList<>();
    private int currentPage = 0;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    public LiveData<List<Artist>> getArtistsLiveData() {
        return artistsLiveData;
    }

    // 异步加载所有歌手
    public void loadAllData(Context context) {
        if (isLoading) return;
        isLoading = true;
        currentPage = 0;
        allArtists.clear();

        new Thread(() -> {
            Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(
                    artistUri,
                    new String[]{MediaStore.Audio.Artists.ARTIST},
                    null,
                    null,
                    MediaStore.Audio.Artists.ARTIST + " ASC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String count = String.valueOf(countSongsInArtist(context, name));
                    Artist artist = new Artist(name, count);
                    allArtists.add(artist);
                } while (cursor.moveToNext());
                cursor.close();
            }
            isLoading = false;
            hasMoreData = allArtists.size() > 0;
            new Handler(Looper.getMainLooper()).post(this::loadNextPage);
        }).start();
    }

    // 内存分页
    public void loadNextPage() {
        int fromIndex = currentPage * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allArtists.size());
        if (fromIndex < toIndex) {
            List<Artist> currentList = new ArrayList<>(allArtists.subList(0, toIndex));
            artistsLiveData.postValue(currentList);
            currentPage++;
            hasMoreData = toIndex < allArtists.size();
        } else {
            hasMoreData = false;
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public static int countSongsInArtist(Context context, String artistName) {
        int songCount = 0;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.ARTIST + " = ?",
                new String[]{artistName},
                null
        );
        if (cursor != null) {
            songCount = cursor.getCount();
            cursor.close();
        }
        return songCount;
    }
}