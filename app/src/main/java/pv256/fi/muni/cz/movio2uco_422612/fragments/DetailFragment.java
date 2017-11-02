package pv256.fi.muni.cz.movio2uco_422612.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pv256.fi.muni.cz.movio2uco_422612.R;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 19.10.2017.
 */

public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";

    private Context mContext;
    private Movie mMovie;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, " onCreateView method");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_low);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            titleLowTv.setText(mMovie.getCoverPath());
        }

        return view;
    }


    //for debug only
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, " onAttach method");
    }

    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Log.d(TAG, " onStart method");
    }

    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        Log.d(TAG, " onResume method");
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        Log.d(TAG, " onPause method");
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        Log.d(TAG, " onStop method");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        Log.d(TAG, " onDestroy method");
    }
}
