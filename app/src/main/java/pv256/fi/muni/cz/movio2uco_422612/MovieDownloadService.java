package pv256.fi.muni.cz.movio2uco_422612;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.MalformedJsonException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import pv256.fi.muni.cz.movio2uco_422612.entities.Movie;
import pv256.fi.muni.cz.movio2uco_422612.entities.MovieList;

/**
 * Created by pato on 29.11.2017.
 */

public class MovieDownloadService extends IntentService {
    public static final String TAG = MovieDownloadService.class.getSimpleName();
    public static final String ACTION_POPULAR = "download_popular";
    public static final String ACTION_NEW = "download_new";
    public static final String ACTION = "action";
    public static final String ERROR = "error";
    public static final String ERROR_PARSING = "error with parsing";
    public static final String ERROR_CONNECTION = "error with connection";
    public static final String RESPONSE = "response";
    public static final String INTENT = "MovieDonwloadService";

    private MovieApiService service;

    public MovieDownloadService() {
        super(TAG);
    }

    public MovieDownloadService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = DataServiceFactory.getMovieApiService();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        startDownloadNotification();

        try {
            switch (action) {
                case ACTION_POPULAR:
                    broadcastMovies(service.getMostPopularMovies().execute().body(), action);
                    break;
                case ACTION_NEW:
                    broadcastMovies(service.getNewMovies().execute().body(), action);
                    break;
                default:
                    throw new IllegalArgumentException("action not recognized");
            }
        } catch (MalformedJsonException e) {
            broadcastMovieListError(ERROR_PARSING);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            broadcastMovieListError(ERROR_CONNECTION);
            e.printStackTrace();
            return;
        }
        finishedDownloadNotification();
    }

    private void broadcastMovies(MovieList movieList, String action) {
        Intent intent = new Intent(INTENT);
        intent.putExtra(ACTION, action);
        intent.putParcelableArrayListExtra(RESPONSE, new ArrayList<Movie>(Arrays.asList(movieList.getResults())));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void broadcastMovieListError(String error) {
        Intent intent = new Intent(INTENT);
        intent.putExtra(ERROR, error);
        errorNotification(error);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void errorNotification(String error) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getText(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);

        switch (error) {
            case ERROR_CONNECTION:
                n.setContentText(getResources().getText(R.string.no_internet_connection));
                break;
            case ERROR_PARSING:
                n.setContentText("Parsing problems.");
                break;
        }
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }

    private void startDownloadNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Download started")
                .setSmallIcon(R.mipmap.ic_download)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);

        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }

    private void finishedDownloadNotification() {
        Intent intent = new Intent(this,  MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder n  = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Download finished")
                .setSmallIcon(R.mipmap.ic_download)
                .setContentIntent(pIntent) //přesměruje nás do aplikace
                .setAutoCancel(true);

        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build()); //0 udává číslo notifikace. Na některých zařízeních nefunguje jinačí int než 0.
    }

}
