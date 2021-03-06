package com.ysdc.movie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class that represent a movie in our application.
 */
public final class Movie implements Parcelable {

    private final int id;
    private final String title;
    private final String originalTitle;
    private final String tagline;
    private final String overview;
    private final Float voteAverage;
    private final Integer voteCount;
    private final String poster;
    private final Date releaseDate;
    private final List<String> genres;

    private Movie(int id, String title, String originalTitle, String tagline, String overview, Float voteAverage, Integer voteCount, String poster, Date releaseDate, List<String>
            genres) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.tagline = tagline;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTagline() {
        return tagline;
    }

    public String getOverview() {
        return overview;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }


    public String getPoster() {
        return poster;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public static class Builder {
        private int id;
        private String title;
        private String originalTitle;
        private String tagline;
        private String overview;
        private Float voteAverage;
        private Integer voteCount;
        private String poster;
        private Date releaseDate;
        private List<String> genres;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder withTagline(String tagline) {
            this.tagline = tagline;
            return this;
        }

        public Builder withOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder withVoteAverage(Float vote) {
            this.voteAverage = vote;
            return this;
        }

        public Builder withVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Builder withPoster(String poster) {
            this.poster = poster;
            return this;
        }

        public Builder withReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder withGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public Movie build() {
            return new Movie(id, title, originalTitle, tagline, overview, voteAverage, voteCount, poster, releaseDate, genres);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.tagline);
        dest.writeString(this.overview);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
        dest.writeString(this.poster);
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : -1);
        dest.writeStringList(this.genres);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.tagline = in.readString();
        this.overview = in.readString();
        this.voteAverage = (Float) in.readValue(Float.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.poster = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.genres = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
