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
import com.example.vibemusicplayer.ui.adapter.ArtistAdapter;
import com.example.vibemusicplayer.ui.model.Artist;
import com.example.vibemusicplayer.ui.viewmodel.ArtistViewModel;

import java.util.ArrayList;

public class ArtistFragment extends Fragment {

    private ArtistViewModel mViewModel;
    private RecyclerView recyclerView;
    private ArtistAdapter adapter;
    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_artist);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        setupRecyclerView();
        setupScrollListener();
        return view;
    }

    private void setupRecyclerView() {
        adapter = new ArtistAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        mViewModel.loadAllData(requireContext());
        observeViewModel();
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && mViewModel.hasMoreData()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            isLoading = true;
                            mViewModel.loadNextPage();
                        }
                    }
                }
            }
        });
    }

    private void observeViewModel() {
        mViewModel.getArtistsLiveData().observe(getViewLifecycleOwner(), artists -> {
            adapter.updateData(artists);
            isLoading = false;
        });
    }
}
