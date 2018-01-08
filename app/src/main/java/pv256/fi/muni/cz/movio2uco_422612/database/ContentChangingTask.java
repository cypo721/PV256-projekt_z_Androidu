package pv256.fi.muni.cz.movio2uco_422612.database;

import android.os.AsyncTask;
import android.support.v4.content.Loader;

/**
 * Created by pato on 8.1.2018.
 */

public abstract class ContentChangingTask<T1, T2, T3> extends AsyncTask<T1, T2, T3> {
    private Loader<?> loader=null;

    ContentChangingTask(Loader<?> loader) {
        this.loader=loader;
    }

    @Override
    protected void onPostExecute(T3 param) {
        loader.onContentChanged();
    }
}
