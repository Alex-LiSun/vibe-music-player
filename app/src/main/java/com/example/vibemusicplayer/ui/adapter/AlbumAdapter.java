package com.example.vibemusicplayer.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> data;

    public AlbumAdapter(List<Album> data) {
        this.data = data;
    }

    public void updateData(List<Album> newData) {
        this.data.clear();
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public TextView album;
        public TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.item_album_imageView);
            album = itemView.findViewById(R.id.item_album);
            count = itemView.findViewById(R.id.item_album_song_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Album album = data.get(position);

        if (album != null) {
            if (album.getAlbumArtUri() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(album.getAlbumArtUri())
                        .error(R.drawable.nav_logo) // 错误时的占位符
                        .into(holder.logo); // 将图片加载到 ImageView
            } else {
                holder.logo.setImageResource(R.drawable.nav_logo);
            }

            holder.album.setText(album.getName());
            holder.count.setText("歌曲：" + album.getCount());
        }

        // 设置子项的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext(); // 获取点击 View 的上下文
                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
                Bundle bundle = new Bundle();
                bundle.putString("logo", data.get(position).getAlbumArtUri().toString());
                bundle.putString("album", data.get(position).getName());
                navController.navigate(R.id.nav_album_detail, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
