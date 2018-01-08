package pv256.fi.muni.cz.movio2uco_422612.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import static pv256.fi.muni.cz.movio2uco_422612.Constants.DATE_FORMAT_ORIGIN;

/**
 * Created by pato on 8.1.2018.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "pv256.fi.muni.cz.movio2uco_422612.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WORK_TIME = "movies";

    /**
     * Converts Date class to a string representation, used for easy comparison and database
     * lookup.
     *
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(DateTime date) {
        return date.toString(DATE_FORMAT_ORIGIN);
    }

    /**
     * Converts a dateText to a long Unix time representation
     *
     * @param dateText the input date string
     * @return the Date object
     */
    public static DateTime getDateFromDb(String dateText) {
        return DateTime.parse(dateText, DateTimeFormat.forPattern(DATE_FORMAT_ORIGIN).withOffsetParsed());
    }

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_TIME).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;


        public static final String TABLE_NAME = "Movies";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_BACKDROP = "backdrop_path";
        public static final String COLUMN_COVER = "cover_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
