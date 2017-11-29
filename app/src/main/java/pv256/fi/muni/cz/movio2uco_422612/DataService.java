package pv256.fi.muni.cz.movio2uco_422612;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;
import pv256.fi.muni.cz.movio2uco_422612.entities.MovieList;

/**
 * Created by pato on 23.11.2017.
 */

public class DataService {
    public static final String API_SCHEME = "https";
    public static final String API_HOST = "api.themoviedb.org";
    public static final String API_VERSION = "3";

    private OkHttpClient client = new OkHttpClient();


    private static DataService instance;

    public static DataService getInstance() {
        if(DataService.instance == null) {
            DataService.instance = new DataService();
        }
        return  DataService.instance;
    }


    public MovieList getMostPopularMovies() throws IOException {
        Request request = new Request.Builder()
                .url(getUrlBuilder()
                        .addPathSegment("discover")
                        .addPathSegment("movie")
                        .addQueryParameter("sort_by", "popularity.desc")
                        .build()
                )
                .build();

        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        MovieList movieList =  gson.fromJson(response.body().string(), MovieList.class);
        return movieList;
    }

    public MovieList getNewMovies() throws IOException {
        Request request = new Request.Builder()
                .url(getUrlBuilder()
                        .addPathSegment("discover")
                        .addPathSegment("movie")
                        .addQueryParameter("sort_by", "release_date.desc")
                        .build()
                )
                .build();

        Response response = client.newCall(request).execute();
        Gson gson = new Gson();
        MovieList movieList =  gson.fromJson(response.body().string(), MovieList.class);
        return movieList;
    }


    private HttpUrl.Builder getUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(DataService.API_SCHEME)
                .host(DataService.API_HOST)
                .addQueryParameter("api_key", Constants.APIKEYV3)
                .addPathSegment(DataService.API_VERSION);
    }
}
