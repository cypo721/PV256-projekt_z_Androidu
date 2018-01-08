package pv256.fi.muni.cz.movio2uco_422612;

import pv256.fi.muni.cz.movio2uco_422612.entities.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pato on 29.11.2017.
 */

public interface MovieApiService {
    @GET("discover/movie?sort_by=popularity.desc&api_key=" + Constants.APIKEYV3)
    public Call<MovieList> getMostPopularMovies();

    @GET("discover/movie?sort_by=release_date.desc&api_key=" + Constants.APIKEYV3)
    public Call<MovieList> getNewMovies();
}
