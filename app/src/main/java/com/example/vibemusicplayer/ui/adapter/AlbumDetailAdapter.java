package com.example.vibemusicplayer.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.model.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder> {

    private final String albumName;
    private final String albumArt;
    private final List<Song> data;
    private Uri albumArtUri;

    public AlbumDetailAdapter(String albumName, String albumArt, List<Song> data) {
        this.albumName = albumName; // 传入数据源
        this.albumArt = albumArt;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView banner;
        public ImageView logo;
        public TextView name;
        public TextView artist;
        public TextView album;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            logo = itemView.findViewById(R.id.item_song_imageView);
            album = itemView.findViewById(R.id.album_title);
            name = itemView.findViewById(R.id.item_song_name);
            artist = itemView.findViewById(R.id.item_song_artist);
            album = itemView.findViewById(R.id.item_song_album);
            time = itemView.findViewById(R.id.item_song_time);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (albumName != null) {
            if (albumArt != null) {
                // 使用 Glide 加载图片
                albumArtUri = Uri.parse(albumArt);
                Glide.with(holder.itemView.getContext())
                        .load(albumArtUri)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop())) // 应用圆形裁剪
                        .error(R.drawable.nav_logo) // 错误时的占位符
                        .into(holder.logo); // 将图片加载到 songImageView
            } else {
                // 如果 logo 为 null，则使用默认的占位符图像
                holder.logo.setImageResource(R.drawable.nav_logo);
            }

            Song song = data.get(position);
            holder.name.setText(song.getName());
            holder.artist.setText(song.getArtist());
            holder.album.setText(song.getAlbum());
            holder.time.setText(song.getDuration());

            // 设置子项的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext(); // 获取点击 View 的上下文
                    NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", new ArrayList<>(data));
                    bundle.putInt("position", position);
                    navController.navigate(R.id.nav_song, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
