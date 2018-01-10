package pv256.fi.muni.cz.movio2uco_422612.database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by pato on 8.1.2018.
 */

public abstract class AbstractDataLoader <E extends List<?>> extends AsyncTaskLoader<E> {
    protected E mLastDataList = null;
    protected abstract E buildList();

    public AbstractDataLoader(Context context) {
        super(context);
    }

    @Override
    public E loadInBackground() {
        return buildList();
    }

    @Override
    public void deliverResult(E dataList) {
        if (isReset()) {
            emptyDataList(dataList);
            return;
        }
        E oldDataList = mLastDataList;
        mLastDataList = dataList;
        if (isStarted()) {
            super.deliverResult(dataList);
        }
        if (oldDataList != null && oldDataList != dataList
                && oldDataList.size() > 0) {
            emptyDataList(oldDataList);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mLastDataList != null) {
            deliverResult(mLastDataList);
        }
        if (takeContentChanged() || mLastDataList == null
                || mLastDataList.size() == 0) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(E dataList) {
        if (dataList != null && dataList.size() > 0) {
            emptyDataList(dataList);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mLastDataList != null && mLastDataList.size() > 0) {
            emptyDataList(mLastDataList);
        }
        mLastDataList = null;
    }
    protected void emptyDataList(E dataList) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.remove(i);
            }
        }
    }
}
