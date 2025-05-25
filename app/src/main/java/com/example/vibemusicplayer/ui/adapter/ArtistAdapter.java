package com.example.vibemusicplayer.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private List<Artist> data;

    public ArtistAdapter(List<Artist> data) {
        this.data = data;
    }

    public void updateData(List<Artist> newData) {
        this.data.clear();
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView artist;
        public TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.item_artist);
            count = itemView.findViewById(R.id.item_artist_song_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Artist artist = data.get(position);

        if (artist != null) {
            holder.artist.setText(artist.getName());
            holder.count.setText("歌曲：" + artist.getCount());

            // 设置子项的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext(); // 获取点击 View 的上下文
                    NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
                    Bundle bundle = new Bundle();
                    bundle.putString("artist", data.get(position).getName());
                    navController.navigate(R.id.nav_artist_detail, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
