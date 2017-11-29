package pv256.fi.muni.cz.movio2uco_422612.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pato on 23.11.2017.
 */

public class MovieList {
    public int page;
    public Movie[] results;
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("total_pages")
    public int totalPages;
}
