package com.example.vibemusicplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.adapter.AlbumDetailAdapter;
import com.example.vibemusicplayer.ui.model.Song;
import com.example.vibemusicplayer.ui.viewmodel.AlbumDetailViewModel;

import java.util.List;

public class AlbumDetailFragment extends Fragment {

    private AlbumDetailViewModel mViewModel;
    private RecyclerView recyclerView;

    public static AlbumDetailFragment newInstance() {
        return new AlbumDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_detail, container, false);

        String albumArt = getArguments().getString("logo");
        ImageView bannerImageView = view.findViewById(R.id.banner);

        if (albumArt != null) {
            // 使用 Glide 加载图片
            Glide.with(view.getContext())
                    .load(albumArt)
                    .error(R.drawable.nav_logo) // 错误时的占位符
                    .into(bannerImageView); // 将图片加载到 bannerImageView
        } else {
            // 如果 logo 为 null，则使用默认的占位符图像
            bannerImageView.setImageResource(R.drawable.nav_logo);
        }

        String albumName = getArguments().getString("album");
        TextView txtTitle = view.findViewById(R.id.album_title);
        txtTitle.setText(albumName);

        recyclerView = view.findViewById(R.id.recyclerView_library);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mViewModel = new ViewModelProvider(this).get(AlbumDetailViewModel.class);
        setupRecyclerView(albumName, albumArt); // 初始化 RecyclerView

        return view;
    }

    private void setupRecyclerView(String albumName, String albumArt) {
        List<Song> songs = mViewModel.getSongsByAlbumName(requireContext(), albumName);
        recyclerView.setAdapter(new AlbumDetailAdapter(albumName, albumArt, songs));
    }

}