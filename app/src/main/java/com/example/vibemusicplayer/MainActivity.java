package com.example.vibemusicplayer;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vibemusicplayer.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_library, R.id.nav_album, R.id.nav_artist, R.id.nav_favorite, R.id.nav_settings, R.id.nav_about, R.id.nav_feedback, R.id.nav_contact, R.id.nav_song, R.id.nav_search)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.action_search);

        // 设置搜索配置信息
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("搜索歌曲");

        // 设置搜索视图点击事件
        searchView.setOnSearchClickListener(v -> {
            String keyword = searchView.getQuery().toString();
            navigateToSearchFragment(keyword);
        });

        // 设置搜索文本变化监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String keyword = searchView.getQuery().toString();
                navigateToSearchFragment(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String keyword = searchView.getQuery().toString();
                navigateToSearchFragment(keyword);
                return false;
            }
        });

        // 设置搜索视图关闭监听器
        searchView.setOnCloseListener(() -> {
            getSupportFragmentManager().popBackStack();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void navigateToSearchFragment(String keyword) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        navController.navigate(R.id.nav_search, bundle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}