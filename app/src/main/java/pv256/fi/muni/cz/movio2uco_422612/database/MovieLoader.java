package pv256.fi.muni.cz.movio2uco_422612.database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 8.1.2018.
 */

public class MovieLoader extends AbstractDataLoader<List<Movie>> {
    private MovieManager mMovieManager;


    public MovieLoader(Context context, MovieManager movieManager) {
        super(context);
        mMovieManager = movieManager;
    }

    @Override
    protected List<Movie> buildList() {
        List<Movie> movieList = mMovieManager.getSavedMovies();
        return movieList;
    }

    public void create(Movie movie) {
        new InsertTask(this).execute(movie);
    }

    public void delete(Movie movie) {
        new DeleteTask(this).execute(movie);
    }

    private class InsertTask extends ContentChangingTask<Movie, Void, Void> {
        InsertTask(MovieLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Movie... params) {
            mMovieManager.createMovie(params[0]);
            return (null);
        }
    }

    private class DeleteTask extends ContentChangingTask<Movie, Void, Void> {
        DeleteTask(MovieLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Movie... params) {
            mMovieManager.deleteMovie(params[0]);
            return (null);
        }
    }


}
