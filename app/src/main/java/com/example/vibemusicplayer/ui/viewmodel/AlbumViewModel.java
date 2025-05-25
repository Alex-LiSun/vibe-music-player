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

import com.example.vibemusicplayer.ui.model.Album;
import com.example.vibemusicplayer.ui.util.MusicMetadataHelper;

import java.util.ArrayList;
import java.util.List;

public class AlbumViewModel extends ViewModel {
    private static final int PAGE_SIZE = 20;
    private final MutableLiveData<List<Album>> albumsLiveData = new MutableLiveData<>();
    private final List<Album> allAlbums = new ArrayList<>();
    private int currentPage = 0;
    private boolean isLoading = false;
    private boolean hasMoreData = true;

    public LiveData<List<Album>> getAlbumsLiveData() {
        return albumsLiveData;
    }

    // 异步加载所有专辑
    public void loadAllData(Context context) {
        if (isLoading) return;
        isLoading = true;
        currentPage = 0;
        allAlbums.clear();

        new Thread(() -> {
            Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(
                    albumUri,
                    new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART},
                    null,
                    null,
                    MediaStore.Audio.Albums.ALBUM + " ASC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    long songId = getFirstSongIdInAlbum(context, name);
                    String filePath = MusicMetadataHelper.getMusicFilePath(context.getContentResolver(), songId);
                    String count = String.valueOf(countSongsInAlbum(context, name));
                    Uri albumArtUri = MusicMetadataHelper.getAlbumArtFromFile(context, filePath);
                    Album album = new Album(name, count, albumArtUri);
                    allAlbums.add(album);
                } while (cursor.moveToNext());
                cursor.close();
            }
            isLoading = false;
            hasMoreData = allAlbums.size() > 0;
            new Handler(Looper.getMainLooper()).post(this::loadNextPage);
        }).start();
    }

    // 内存分页
    public void loadNextPage() {
        int fromIndex = currentPage * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allAlbums.size());
        if (fromIndex < toIndex) {
            List<Album> currentList = new ArrayList<>(allAlbums.subList(0, toIndex));
            albumsLiveData.postValue(currentList);
            currentPage++;
            hasMoreData = toIndex < allAlbums.size();
        } else {
            hasMoreData = false;
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public static int countSongsInAlbum(Context context, String albumName) {
        int songCount = 0;
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.ALBUM + " = ?",
                new String[]{albumName},
                null
        );
        if (cursor != null) {
            songCount = cursor.getCount();
            cursor.close();
        }
        return songCount;
    }

    public static long getFirstSongIdInAlbum(Context context, String albumName) {
        long songId = -1;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media._ID},
                    MediaStore.Audio.Media.ALBUM + " = ?",
                    new String[]{albumName},
                    MediaStore.Audio.Media.TRACK + " ASC"
            );
            if (cursor != null && cursor.moveToFirst()) {
                songId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return songId;
    }
}
