package com.solodroid.ads.sdkdemo.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.solodroid.ads.sdkdemo.R;
import com.solodroid.ads.sdkdemo.adapter.AdapterPost;
import com.solodroid.ads.sdkdemo.database.SharedPref;
import com.solodroid.ads.sdkdemo.helper.AdsManager;
import com.solodroid.ads.sdkdemo.model.Post;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    SharedPref sharedPref;
    RecyclerView recyclerView;
    AdapterPost adapterPost;
    Toolbar toolbar;
    AdsManager adsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        getAppTheme();
        setContentView(R.layout.activity_second);
        initView();
        initAds();
        initToolbar();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterPost = new AdapterPost(this, new ArrayList<>());
        recyclerView.setAdapter(adapterPost);
        displayData(sharedPref.getPostList());
    }

    private void initAds() {
        adsManager = new AdsManager(this);
        adsManager.loadBannerAd();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Second Activity");
        }
    }

    private void displayData(List<Post> posts) {
        if (posts != null && posts.size() > 0) {
            adapterPost.setListData(posts, posts.size());
            adapterPost.setOnItemClickListener((view, obj, position) -> {
                Toast.makeText(getApplicationContext(), "" + obj.name, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adsManager.destroyBannerAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void getAppTheme() {
        if (sharedPref.getIsDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

}
