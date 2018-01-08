package pv256.fi.muni.cz.movio2uco_422612.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 8.1.2018.
 */

public class MovieManager {
    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_TITLE = 1;
    public static final int COL_MOVIE_OVERVIEW = 2;
    public static final int COL_MOVIE_COVER = 3;
    public static final int COL_MOVIE_BACKDROP = 4;
    public static final int COL_MOVIE_RATING = 5;
    public static final int COL_MOVIE_RELEASE_DATE = 6;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_COVER,
            MovieContract.MovieEntry.COLUMN_BACKDROP,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
    };

    private SQLiteDatabase mDatabase;

    public MovieManager(SQLiteDatabase db) {
        mDatabase = db;
    }

    public boolean createMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        if (movie.getTitle() == null) {
            throw new IllegalStateException("movie title cannot be null");
        }

        long result = mDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, prepareMovieValues(movie));
        return result != -1;
    }

    public List<Movie> getSavedMovies() {
        Cursor cursor = mDatabase.query(MovieContract.MovieEntry.TABLE_NAME, MOVIE_COLUMNS, null,
                null, null, null, null);
        List<Movie> movies = new ArrayList();
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                movies.add(getMovie(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return movies;
    }

    public boolean deleteMovie(Movie movie) {
        if (movie == null) {
            return false;
        }

        int result = mDatabase.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID + " = " + movie.getId(), null);
        return result != 0;
    }

    public boolean containsId(Long id) {
        String Query = "Select * from " + MovieContract.MovieEntry.TABLE_NAME + " where " + MovieContract.MovieEntry._ID + " = " + id;
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    private ContentValues prepareMovieValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRealeaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_COVER, movie.getCoverPath());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getDescription());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, movie.getBackdrop());

        return values;
    }

    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getLong(COL_MOVIE_ID));
        movie.setTitle(cursor.getString(COL_MOVIE_TITLE));
        movie.setRealeaseDate(cursor.getString(COL_MOVIE_RELEASE_DATE));
        movie.setCoverPath(cursor.getString(COL_MOVIE_COVER));
        movie.setPopularity(cursor.getFloat(COL_MOVIE_RATING));
        movie.setDescription(cursor.getString(COL_MOVIE_OVERVIEW));
        movie.setBackdrop(cursor.getString(COL_MOVIE_BACKDROP));

        return movie;
    }
}
