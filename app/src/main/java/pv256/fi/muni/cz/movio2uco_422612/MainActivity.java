package pv256.fi.muni.cz.movio2uco_422612;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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
import pv256.fi.muni.cz.movio2uco_422612.syncadapter.UpdaterSyncAdapter;


public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectListener, MovieListFragment.OnMovieLongClickListener {
    private static final String THEME = "mainTheme";
    private static final String APP = "movio";
    private boolean mTwoPane;
    public static ArrayList<Object> mData = new ArrayList<>();
    private SwitchCompat mSwitchButton;
    private Toolbar toolbar;
    private MovieListFragment fragmentToCreate;


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
        }
        mData = new ArrayList<Object>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UpdaterSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.menuSwitch);
        item.setActionView(R.layout.menu_switch);

        mSwitchButton = item.getActionView().findViewById(R.id.switchBtn);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    compoundButton.setText("Favourites");
                    compoundButton.setChecked(true);
                    fragmentToCreate = MovieListFragment.newInstance(true);
                } else {
                    compoundButton.setText("Discover");
                    compoundButton.setChecked(false);
                    fragmentToCreate = MovieListFragment.newInstance(false);
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_main, fragmentToCreate, MovieListFragment.TAG)
                        .commit();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.synchBtn:
                UpdaterSyncAdapter.syncImmediately(getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

}
