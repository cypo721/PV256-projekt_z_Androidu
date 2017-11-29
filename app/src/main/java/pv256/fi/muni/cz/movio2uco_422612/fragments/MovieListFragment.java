package pv256.fi.muni.cz.movio2uco_422612.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pv256.fi.muni.cz.movio2uco_422612.BuildConfig;
import pv256.fi.muni.cz.movio2uco_422612.CategoryRecyclerAdapter;
import pv256.fi.muni.cz.movio2uco_422612.MovieDownloadService;
import pv256.fi.muni.cz.movio2uco_422612.R;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 19.10.2017.
 */

public class MovieListFragment extends Fragment {
    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private OnMovieSelectListener mListener;
    private Context mContext;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (OnMovieSelectListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement OnMovieSelectListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null; //Avoid leaking the Activity
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list , container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }


    public interface OnMovieSelectListener {
        void onMovieSelect(int position);
    }

    public interface OnMovieLongClickListener {
        void onMovieLongClick(int position);
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }

    //debug only
    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onStart method");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onResume method");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onPause method");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onStop method");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onDestroy method");
        }
    }   
}
