package pv256.fi.muni.cz.movio2uco_422612.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pv256.fi.muni.cz.movio2uco_422612.BuildConfig;
import pv256.fi.muni.cz.movio2uco_422612.R;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieDbHelper;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieManager;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

import static pv256.fi.muni.cz.movio2uco_422612.Constants.DATE_FORMAT;
import static pv256.fi.muni.cz.movio2uco_422612.Constants.DATE_FORMAT_ORIGIN;

/**
 * Created by pato on 19.10.2017.
 */

public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";

    private Context mContext;
    private Movie mMovie;
    private SQLiteDatabase mDatabase;
    private MovieManager mMovieManager;
    private MovieDbHelper mDbHelper;
    private FloatingActionButton floatingActionButton;

    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, " onCreate method");
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mMovie = args.getParcelable(ARGS_MOVIE);
        }
        mDbHelper = new MovieDbHelper(getActivity());
        mDatabase = mDbHelper.getWritableDatabase();
        mMovieManager = new MovieManager(mDatabase);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " onCreateView method");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        floatingActionButton = view.findViewById(R.id.add);
        if (mMovie != null && mMovieManager.containsId(mMovie.getId())) {
            floatingActionButton.setImageResource(R.drawable.minus);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMovieManager.containsId(mMovie.getId())) {
                    mMovieManager.deleteMovie(mMovie);
                    floatingActionButton.setImageResource(R.drawable.plus);
                }
                else {
                    mMovieManager.createMovie(mMovie);
                    floatingActionButton.setImageResource(R.drawable.minus);
                }
            }
        });

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_low);
        TextView dateTv = (TextView) view.findViewById(R.id.date);

        ImageView coverIv = (ImageView) view.findViewById(R.id.cover_image);
        ImageView backdropIv = (ImageView) view.findViewById(R.id.backdrop_image);

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat formatterOrigin = new SimpleDateFormat(DATE_FORMAT_ORIGIN);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            Date date = new Date();
            try {
                date = formatterOrigin.parse(mMovie.getRealeaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateTv.setText(formatter.format(date));
            titleLowTv.setText(mMovie.getDescription());
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500/" + mMovie.getCoverPath()).into(backdropIv);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500/" + mMovie.getBackdrop()).into(coverIv);
        }

        return view;
    }

    //for debug only
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(BuildConfig.LOGGING){
            Log.d(TAG, " onAttach method");
        }

    }

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
