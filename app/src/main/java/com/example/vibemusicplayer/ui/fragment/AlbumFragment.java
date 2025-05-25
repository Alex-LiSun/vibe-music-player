package com.example.vibemusicplayer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vibemusicplayer.R;
import com.example.vibemusicplayer.ui.adapter.AlbumAdapter;
import com.example.vibemusicplayer.ui.model.Album;
import com.example.vibemusicplayer.ui.viewmodel.AlbumViewModel;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    private AlbumViewModel mViewModel;
    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_album);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        mViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);
        setupRecyclerView();
        setupScrollListener();

        return view;
    }

    private void setupRecyclerView() {
        adapter = new AlbumAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        mViewModel.loadAllData(requireContext());
        observeViewModel();
    }

    private void setupScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
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
        mViewModel.getAlbumsLiveData().observe(getViewLifecycleOwner(), albums -> {
            adapter.updateData(albums);
            isLoading = false;
        });
    }
}
