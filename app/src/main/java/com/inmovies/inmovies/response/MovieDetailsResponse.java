package com.inmovies.inmovies.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inmovies.inmovies.models.MovieModel;


/**
 *  GSON JSON Adapter class to serialize JSON response:
 *  convert json to MovieModel object
 */
public class MovieDetailsResponse {

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("poster_path")
    @Expose()
    private String poster_path;

    @SerializedName("overview")
    @Expose()
    private String overview;

    @SerializedName("backdrop_path")
    @Expose()
    private String backdrop_path;

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("vote_average")
    @Expose()
    private float vote_average;

    @SerializedName("vote_count")
    @Expose()
    private int vote_count;

    @SerializedName("release_date")
    @Expose()
    private String release_date;

    /**
     *  get MovieModel object once the json response is parsed and serialized
     * @return MovieModel
     */
    public MovieModel getMovie(){
        return new MovieModel(this.title, this.poster_path, this.overview,
                this.backdrop_path, this.id, this.vote_average,
        this.vote_count, this.release_date);
    }
    @Override
    public String toString() {
        return "Movie detail for " + this.id + " " + this.title;
    }
}
