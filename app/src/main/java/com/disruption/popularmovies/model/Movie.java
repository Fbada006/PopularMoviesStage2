package com.disruption.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.disruption.popularmovies.utils.MovieConstants;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = MovieConstants.TABLE_NAME)
public class Movie implements Parcelable {
    @SerializedName("id")
    @PrimaryKey
    private int movieId;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("title")
    @ColumnInfo(name = MovieConstants.COLUMN_TITLE)
    private String title;
    @SerializedName("vote_average")
    @ColumnInfo(name = MovieConstants.COLUMN_VOTE)
    private String vote;

    public Movie(int movieId, String posterPath, String overview, String releaseDate, String title, String vote) {
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.vote = vote;
    }

    @Ignore
    public Movie() {
    }

    protected Movie(Parcel in) {
        movieId = in.readInt();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        vote = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getVote() {
        return vote;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(title);
        parcel.writeString(vote);
    }
}
