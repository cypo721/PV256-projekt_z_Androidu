package pv256.fi.muni.cz.movio2uco_422612.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pato on 17.10.2017.
 */

public class Movie implements Parcelable{

    @SerializedName("id")
    private long mId;
    @SerializedName("release_date")
    private String mRealeaseDate;
    @SerializedName("poster_path")
    private String mCoverPath;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("backdrop_path")
    private String mBackdrop;
    @SerializedName("vote_average")
    private float mPopularity;
    @SerializedName("overview")
    private String mDescription;
    private int mCoverId;

    public Movie() {
    }

    public Movie(String realeaseDate, String coverPath, String backdrop, String title, float popularity, int coverId) {
        mRealeaseDate = realeaseDate;
        mCoverPath = coverPath;
        mTitle = title;
        mBackdrop = backdrop;
        mPopularity = popularity;
        mCoverId = coverId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getRealeaseDate() {
        return mRealeaseDate;
    }

    public void setRealeaseDate(String realeaseDate) {
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    protected Movie(Parcel in) {
        mId = in.readLong();
        mRealeaseDate = in.readString();
        mCoverPath = in.readString();
        mTitle = in.readString();
        mBackdrop = in.readString();
        mPopularity = in.readFloat();
        mDescription = in.readString();
        mCoverId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mRealeaseDate);
        dest.writeString(mCoverPath);
        dest.writeString(mTitle);
        dest.writeString(mBackdrop);
        dest.writeFloat(mPopularity);
        dest.writeString(mDescription);
        dest.writeInt(mCoverId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (mId != movie.mId) return false;
        if (Float.compare(movie.mPopularity, mPopularity) != 0) return false;
        if (mCoverId != movie.mCoverId) return false;
        if (mRealeaseDate != null ? !mRealeaseDate.equals(movie.mRealeaseDate) : movie.mRealeaseDate != null)
            return false;
        if (mCoverPath != null ? !mCoverPath.equals(movie.mCoverPath) : movie.mCoverPath != null)
            return false;
        if (!mTitle.equals(movie.mTitle)) return false;
        if (mBackdrop != null ? !mBackdrop.equals(movie.mBackdrop) : movie.mBackdrop != null)
            return false;
        return mDescription != null ? mDescription.equals(movie.mDescription) : movie.mDescription == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + (mRealeaseDate != null ? mRealeaseDate.hashCode() : 0);
        result = 31 * result + (mCoverPath != null ? mCoverPath.hashCode() : 0);
        result = 31 * result + mTitle.hashCode();
        result = 31 * result + (mBackdrop != null ? mBackdrop.hashCode() : 0);
        result = 31 * result + (mPopularity != +0.0f ? Float.floatToIntBits(mPopularity) : 0);
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        result = 31 * result + mCoverId;
        return result;
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
