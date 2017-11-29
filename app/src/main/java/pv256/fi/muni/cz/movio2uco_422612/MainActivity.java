package pv256.fi.muni.cz.movio2uco_422612;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;
import pv256.fi.muni.cz.movio2uco_422612.entities.MovieList;
import pv256.fi.muni.cz.movio2uco_422612.fragments.DetailFragment;
import pv256.fi.muni.cz.movio2uco_422612.fragments.MovieListFragment;


public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectListener, MovieListFragment.OnMovieLongClickListener {
    private static final String THEME = "mainTheme";
    private static final String APP = "movio";
    private boolean mTwoPane;
    private ArrayList<Object> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CategoryRecyclerAdapter mAdapter;
    private AsyncTask loaderTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DetailFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setRecyclerViewLayoutManager();

        mAdapter = new CategoryRecyclerAdapter(this, new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        final ArrayList<Object> items = new ArrayList<>(14);

        Intent intent = new Intent(this, MovieDownloadService.class);
        intent.setAction(MovieDownloadService.ACTION_POPULAR);
        startService(intent);
        intent = new Intent(this, MovieDownloadService.class);
        intent.setAction(MovieDownloadService.ACTION_NEW);
        startService(intent);

        IntentFilter intentFilter = new IntentFilter(MovieDownloadService.INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMovieListReceiver, intentFilter);
    }

    private BroadcastReceiver mMovieListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String error = intent.getStringExtra(MovieDownloadService.RESPONSE);
            if (error != null) {
                switch (error) {
                    case MovieDownloadService.ERROR_CONNECTION:
                        mRecyclerView.setVisibility(View.GONE);
                        findViewById(R.id.no_internet).setVisibility(View.VISIBLE);
                        break;
                    case MovieDownloadService.ERROR_PARSING:
                        mRecyclerView.setVisibility(View.GONE);
                        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                        break;
                }
                return;
            }
            String action = intent.getStringExtra(MovieDownloadService.ACTION);
            ArrayList<Movie> movies = intent.getExtras().getParcelableArrayList(MovieDownloadService.RESPONSE);
            switch (action) {
                case MovieDownloadService.ACTION_POPULAR:
                    mData.add("Popular");
                    mData.addAll(movies);
                    break;
                case MovieDownloadService.ACTION_NEW:
                    mData.add("New");
                    mData.addAll(movies);
                    break;
            }
            if (mData.size() <= 2) {
                mRecyclerView.setVisibility(View.GONE);
                findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            } else {
                mAdapter.setItems(mData);
            }
        }
    };

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MovieDownloadService.class);
        this.stopService(intent);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMovieListReceiver);
    }

    @Override
    public void onMovieSelect(int position) {
        Movie movie = (Movie) mData.get(position);
        if (mTwoPane) {
            FragmentManager fm = getSupportFragmentManager();

            DetailFragment fragment = DetailFragment.newInstance(movie);
            fm.beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailFragment.TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }

    @Override
    public void onMovieLongClick(int position) {
        Toast.makeText(this, ((Movie) mData.get(position)).getTitle(), Toast.LENGTH_SHORT).show();
    }


    public boolean haveInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }


}
