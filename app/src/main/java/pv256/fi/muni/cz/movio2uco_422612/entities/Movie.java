package pv256.fi.muni.cz.movio2uco_422612.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pato on 17.10.2017.
 */

public class Movie implements Parcelable{

    private long mRealeaseDate;
    private String mCoverPath;
    private String mTitle;
    private String mBackdrop;
    private float mPopularity;
    private int mCoverId;

    public Movie(long realeaseDate, String coverPath, String backdrop, String title, float popularity, int coverId) {
        mRealeaseDate = realeaseDate;
        mCoverPath = coverPath;
        mTitle = title;
        mBackdrop = backdrop;
        mPopularity = popularity;
        mCoverId = coverId;
    }

    public long getRealeaseDate() {
        return mRealeaseDate;
    }

    public void setRealeaseDate(long realeaseDate) {
        mRealeaseDate = realeaseDate;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        mCoverPath = coverPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        mBackdrop = backdrop;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        mPopularity = popularity;
    }

    public int getCoverId() {
        return mCoverId;
    }

    public void setCoverId(int coverId) {
        mCoverId = coverId;
    }

    protected Movie(Parcel in) {
        mRealeaseDate = in.readLong();
        mCoverPath = in.readString();
        mTitle = in.readString();
        mBackdrop = in.readString();
        mPopularity = in.readFloat();
        mCoverId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mRealeaseDate);
        dest.writeString(mCoverPath);
        dest.writeString(mTitle);
        dest.writeString(mBackdrop);
        dest.writeFloat(mPopularity);
        dest.writeInt(mCoverId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
