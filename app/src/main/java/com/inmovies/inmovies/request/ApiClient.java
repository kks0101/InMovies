package com.inmovies.inmovies.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.utils.Constants;
import com.inmovies.inmovies.utils.MovieApi;

import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

// Singleton Class: Repository requests the data from the client
// Client interacts with the data sources: (making api calls or accessing the data base)
// to get the data; The data retrieval is done through JAVA Executors
public class ApiClient {

    private static ApiClient instance;

    // two lists for two api calls : one for now playing movies, other for popular movies
    private static MutableLiveData<List<MovieModel>> nowPlayingMovieList, popularMovieList;
    private static MutableLiveData<List<MovieModel>> movieListByQuery;
    private static MutableLiveData<MovieModel> movieDetailsByID;

    // disposable
    private CompositeDisposable disposable = new CompositeDisposable();

    //service api : to make API calls
    final private MovieApi movieApi;

    private ApiClient(){
        nowPlayingMovieList = new MutableLiveData<>();
        popularMovieList = new MutableLiveData<>();
        movieListByQuery = new MutableLiveData<>();
        movieDetailsByID = new MutableLiveData<>();
        movieApi = ServiceRequest.getMovieApi();
    }

    public static ApiClient getInstance(){
        if(instance==null)
            instance = new ApiClient();
        return instance;
    }

    public LiveData<List<MovieModel>> getNowPlayingMovies(){
        return nowPlayingMovieList;
    }

    public LiveData<List<MovieModel>> getPopularMovies(){
        return popularMovieList;
    }

    public LiveData<List<MovieModel>> getMoviesByQuery(){
        return movieListByQuery;
    }

    public LiveData<MovieModel> getMovieDetails(){
        return movieDetailsByID;
    }

    public void searchMovieDetails(int movie_id){
        disposable.add(movieApi.getMovieDetails(
                movie_id,
                Constants.API_KEY

        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(movieDetailsResponse -> movieDetailsResponse.getMovie())
        .subscribe(this::handleMovieDetailsSuccess, this::handleMovieDetailsError));
    }

    private void handleMovieDetailsError(Throwable throwable) {
        movieDetailsByID.setValue(null);
        Log.e("response", "Unable to get details: " + throwable.getMessage());
    }

    private void handleMovieDetailsSuccess(MovieModel movieModel) {
        Log.v("details", movieModel.toString());
        movieDetailsByID.setValue(movieModel);
    }

    public void searchNowPlayingMovies(int pageNumber){

        disposable.add(movieApi.nowPlayingMovie(
                Constants.API_KEY,
                pageNumber
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(movieNowPlayingResponse -> movieNowPlayingResponse.getNowPlayingMovies())
        .subscribe(this::handleNowPlayingSuccess, this::handleNowPlayingError));

    }

    private void handleNowPlayingError(Throwable throwable) {
        nowPlayingMovieList.setValue(null);
        Log.v("response", "Error occurred fetching now playing movies" + throwable.getMessage());
    }

    private void handleNowPlayingSuccess(List<MovieModel> movieModels) {
        nowPlayingMovieList.setValue(movieModels);
    }


    public void searchPopularMovies(int pageNumber){

        disposable.add(movieApi.popularMovies(
                Constants.API_KEY,
                pageNumber
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(moviePopularResponse -> moviePopularResponse.getPopularMovieList())
        .subscribe(this::handlePopularMovieSuccess, this::handlePopularMovieError));


    }

    private void handlePopularMovieError(Throwable throwable) {
        popularMovieList.setValue(null);
        Log.v("response", "Error occurred fetching popular movies" + throwable.getMessage());
    }

    private void handlePopularMovieSuccess(List<MovieModel> movieModels) {
        popularMovieList.setValue(movieModels);
    }

    public void searchMoviesByQuery(String query, int pageNumber){

        disposable.add(movieApi.queryMovies(
                Constants.API_KEY,
                query,
                pageNumber
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(moviesByQueryResponse -> moviesByQueryResponse.getMoviesByQuery())
        .subscribe(this::handleQueriedMovieSuccess, this::handleQueriedMovieError));

    }

    private void handleQueriedMovieSuccess(List<MovieModel> movieModels) {
        movieListByQuery.postValue(movieModels);
    }

    private void handleQueriedMovieError(Throwable throwable) {
        movieListByQuery.setValue(null);
        Log.v("response", "Error occurred fetching queried movies" + throwable.getMessage());
    }

}
