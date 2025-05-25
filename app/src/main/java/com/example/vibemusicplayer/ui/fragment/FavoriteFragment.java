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
import com.example.vibemusicplayer.ui.adapter.FavoriteAdapter;
import com.example.vibemusicplayer.ui.viewmodel.FavoriteViewModel;
import com.example.vibemusicplayer.ui.model.Song;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel mViewModel;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private boolean isLoading = false;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        setupRecyclerView();
        setupScrollListener();
        return view;
    }

    private void setupRecyclerView() {
        adapter = new FavoriteAdapter(new ArrayList<>());
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
        mViewModel.getFavoriteLiveData().observe(getViewLifecycleOwner(), songs -> {
            adapter.updateData(songs);
            isLoading = false;
        });
    }
}
