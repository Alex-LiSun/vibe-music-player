package com.example.vibemusicplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.adapter.SearchAdapter;
import com.example.vibemusicplayer.ui.model.Song;
import com.example.vibemusicplayer.ui.viewmodel.SearchViewModel;

import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private SearchViewModel mViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        String keyword = getArguments().getString("keyword");
        setupRecyclerView(keyword); // 初始化 RecyclerView

        return view; // 返回视图
    }

    private void setupRecyclerView(String keyword) {
        List<Song> songs = mViewModel.getMusicDataByKeyword(requireContext(), keyword);
        recyclerView.setAdapter(new SearchAdapter(songs));
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
