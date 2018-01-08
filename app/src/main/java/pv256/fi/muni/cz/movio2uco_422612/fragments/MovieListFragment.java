package pv256.fi.muni.cz.movio2uco_422612.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pv256.fi.muni.cz.movio2uco_422612.BuildConfig;
import pv256.fi.muni.cz.movio2uco_422612.CategoryRecyclerAdapter;
import pv256.fi.muni.cz.movio2uco_422612.MovieDownloadService;
import pv256.fi.muni.cz.movio2uco_422612.R;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieDbHelper;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieLoader;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieManager;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

import static pv256.fi.muni.cz.movio2uco_422612.MainActivity.mData;

/**
 * Created by pato on 19.10.2017.
 */

public class MovieListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = ListView.INVALID_POSITION;
    private OnMovieSelectListener mListener;
    private Context mContext;
    //private ArrayList<Object> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private TextView mNoInternetView;
    private CategoryRecyclerAdapter mAdapter;
    private MovieListReceiver mReceiver;

    private static final int LOADER_ID = 1;
    private SQLiteDatabase mDatabase;
    private MovieManager mMovieManager;
    private MovieDbHelper mDbHelper;
    private boolean isFavourite;

    public MovieListFragment() {}

    public static MovieListFragment newInstance(Boolean isChecked) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putBoolean("isFavourite", isChecked);
        fragment.setArguments(args);
        return fragment;
    }

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
        mData.clear();
        mContext = getActivity().getApplicationContext();
        if (getArguments() != null) {
            isFavourite = getArguments().getBoolean("isFavourite");
        }else {
            isFavourite = false;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list , container, false);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mNoInternetView = (TextView) view.findViewById(R.id.no_internet);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new CategoryRecyclerAdapter(getContext(), new ArrayList<Object>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (isFavourite) {
            mDbHelper = new MovieDbHelper(getActivity());
            mDatabase = mDbHelper.getWritableDatabase();
            mMovieManager = new MovieManager(mDatabase);
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            if(!haveInternetConnection(this.getActivity())) {
                Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(getActivity(), MovieDownloadService.class);
                intent.setAction(MovieDownloadService.ACTION_POPULAR);
                getActivity().startService(intent);
                intent = new Intent(getActivity(), MovieDownloadService.class);
                intent.setAction(MovieDownloadService.ACTION_NEW);
                getActivity().startService(intent);

                IntentFilter intentFilter = new IntentFilter(MovieDownloadService.INTENT);
                mReceiver = new MovieListReceiver();
                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, intentFilter);
            }
        }
        return view;
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        MovieLoader loader = new MovieLoader(this.getActivity(), mMovieManager);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mData.clear();
        for (Movie movie : data) {
            mData.add(movie);
        }
        mAdapter.setItems(mData);

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    public class MovieListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String error = intent.getStringExtra(MovieDownloadService.ERROR);
            if (error != null) {
                switch (error) {
                    case MovieDownloadService.ERROR_CONNECTION:
                        mRecyclerView.setVisibility(View.GONE);
                        mNoInternetView.setVisibility(View.VISIBLE);
                        break;
                    case MovieDownloadService.ERROR_PARSING:
                        mRecyclerView.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
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
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mAdapter.setItems(mData);
            }
        }
    };

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
        IntentFilter intentFilter = new IntentFilter("MovieDownloadService");
        mReceiver = new MovieListReceiver();
        getActivity().registerReceiver(mReceiver, intentFilter);
        if(BuildConfig.LOGGING) {
            Log.d(TAG, " onResume method");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        getActivity().unregisterReceiver(mReceiver);
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

    public boolean haveInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
