package com.github.msbaek.rxessentials;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SoAdapter.ViewHolder.OpenProfileListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipe;
    private SoAdapter mAdapter;
    private SeApiManager mSeApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeApiManager = new SeApiManager();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        mAdapter = new SoAdapter(new ArrayList<User>());
        mAdapter.setOpenProfileListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearOnScrollListeners();
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                refreshList(current_page);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSwipe.setOnRefreshListener(this::onRefresh);

        if(savedInstanceState == null)
            refreshList(1);
    }

    private void refreshList(int pageNo) {
        App.L.error("refreshList(" + pageNo + ")");
        showRefresh(true);
        List<User> users = mSeApiManager.getMostPopularSOusers(pageNo);
        showRefresh(false);
        mAdapter.updateUsers(users);
    }

    private void showRefresh(boolean show) {
        mSwipe.setRefreshing(show);
        int visibility = show ? View.GONE : View.VISIBLE;
        mRecyclerView.setVisibility(visibility);
    }

    @Override
    public void open(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void onRefresh() {
        refreshList(1);
    }
}
