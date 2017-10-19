package pv256.fi.muni.cz.movio2uco_422612;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;
import pv256.fi.muni.cz.movio2uco_422612.fragments.DetailFragment;
import pv256.fi.muni.cz.movio2uco_422612.fragments.MovieListFragment;


public class MainActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectListener {

    private Button mButtonSwitch;
    private static final String THEME = "mainTheme";
    private static final String APP = "movio";
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//          UKOL 2 - Theme switch
//        SharedPreferences preferences = getSharedPreferences(APP, MODE_PRIVATE);
//        final boolean mainTheme = preferences.getBoolean(THEME, false);
//        if(mainTheme){
//            setTheme(R.style.SecondTheme);
//        } else {
//            setTheme(R.style.AppTheme);
//        }
//
//        setContentView(R.layout.activity_main);
//
//        mButtonSwitch = (Button)findViewById(R.id.switch_theme_button);
//        final SharedPreferences.Editor editor = preferences.edit();
//        mButtonSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.putBoolean(THEME, !mainTheme);
//                editor.apply();
//                Intent intent = new Intent(MainActivity.this ,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

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

    }

    @Override
    public void onMovieSelect(Movie movie) {
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
}
