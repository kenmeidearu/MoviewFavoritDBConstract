package com.kenjin.submission4.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.google.gson.annotations.SerializedName;
import com.kenjin.submission4.database.DBContract;

import static com.kenjin.submission4.database.DBContract.getColumnInt;
import static com.kenjin.submission4.database.DBContract.getColumnString;

public class Movie implements Parcelable {
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String moviePoster;
    @SerializedName("overview")
    private String plotSynopsis;
    @SerializedName("vote_average")
    private String userRating;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int id;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }


    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalTitle);
        dest.writeString(this.moviePoster);
        dest.writeString(this.plotSynopsis);
        dest.writeString(this.userRating);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.id);
    }
    public Movie(Parcel in) {
        this.originalTitle = in.readString();
        this.moviePoster = in.readString();
        this.plotSynopsis = in.readString();
        this.userRating = in.readString();
        this.releaseDate = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, MediaStore.Audio.Playlists.Members._ID);
        this.originalTitle = getColumnString(cursor, DBContract.FavoritColumn.TITLE);
        this.plotSynopsis = getColumnString(cursor, (DBContract.FavoritColumn.RATE));
        this.releaseDate = getColumnString(cursor, DBContract.FavoritColumn.DATE);
        this.moviePoster = getColumnString(cursor, DBContract.FavoritColumn.POSTER);
    }
}
