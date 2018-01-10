package pv256.fi.muni.cz.movio2uco_422612;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pato on 23.11.2017.
 */

public class DataServiceFactory {
    public static final String API_SCHEME = "https";
    public static final String API_HOST = "api.themoviedb.org";
    public static final String API_VERSION = "3";

    private static MovieApiService sMovieApiService;

    public static MovieApiService getMovieApiService() {
        if(DataServiceFactory.sMovieApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_SCHEME + "://" + API_HOST + "/" + API_VERSION + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sMovieApiService = retrofit.create(MovieApiService.class);
        }
        return  sMovieApiService;
    }

}
