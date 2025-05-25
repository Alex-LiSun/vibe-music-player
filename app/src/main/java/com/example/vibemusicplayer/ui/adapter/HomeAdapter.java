package com.example.vibemusicplayer.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vibemusicplayer.ui.fragment.AlbumFragment;
import com.example.vibemusicplayer.ui.fragment.ArtistFragment;
import com.example.vibemusicplayer.ui.fragment.FavoriteFragment;
import com.example.vibemusicplayer.ui.fragment.LibraryFragment;


public class HomeAdapter extends FragmentStateAdapter {

    public HomeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LibraryFragment(); // 返回曲库Fragment
            case 1:
                return new AlbumFragment(); // 返回专辑Fragment
            case 2:
                return new ArtistFragment(); // 返回歌手Fragment
            case 3:
                return new FavoriteFragment(); // 返回喜欢Fragment
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 4; // 四个选项卡
    }
}
