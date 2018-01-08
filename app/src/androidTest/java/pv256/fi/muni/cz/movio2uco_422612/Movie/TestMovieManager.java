package pv256.fi.muni.cz.movio2uco_422612.Movie;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;

import pv256.fi.muni.cz.movio2uco_422612.database.MovieContract;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieDbHelper;
import pv256.fi.muni.cz.movio2uco_422612.database.MovieManager;
import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;

/**
 * Created by pato on 8.1.2018.
 */

public class TestMovieManager extends AndroidTestCase {
    private static final String TAG = TestMovieManager.class.getSimpleName();

    private MovieManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovieManager((new MovieDbHelper(mContext)).getWritableDatabase());
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }


    public void testAddMovie() throws Exception {
        Movie expected = createMovie("test", "1995-02-01", "dsadsad", "Dsaasdas", 6.5f , "this is test");
        mManager.createMovie(expected);
        ArrayList<Movie> saved = (ArrayList)mManager.getSavedMovies();

        Log.d(TAG, saved.get(0).toString());
        assertTrue(saved.size() == 1);
        assertEquals(expected, saved.get(0));
    }

    private Movie createMovie(String title, String release, String cover, String backdrop, float rating, String description) {
        Movie m = new Movie();
        m.setDescription(description);
        m.setTitle(title);
        m.setBackdrop(backdrop);
        m.setCoverPath(cover);
        m.setRealeaseDate(release);
        m.setPopularity(rating);
        return m;
    }
}
