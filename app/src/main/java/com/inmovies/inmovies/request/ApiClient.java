package com.inmovies.inmovies.request;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inmovies.inmovies.database.AppDatabase;
import com.inmovies.inmovies.database.BookmarkDao;
import com.inmovies.inmovies.database.DbClient;
import com.inmovies.inmovies.database.MovieDao;
import com.inmovies.inmovies.database.NowPlayingMovieDao;
import com.inmovies.inmovies.models.BookmarkModel;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.models.NowPlayingMoviesModel;
import com.inmovies.inmovies.utils.Constants;
import com.inmovies.inmovies.utils.MovieApi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 *  Singleton Class: Repository requests the data from the client
 *  Client interacts with the data sources: (making api calls or accessing the data base)
 *  to get the data; The data retrieval is done through RxJava
 */
public class ApiClient {

    private static ApiClient instance;

    // two lists for two api calls : one for now playing movies, other for popular movies
    private static MutableLiveData<List<MovieModel>> nowPlayingMovieList, popularMovieList;
    private static MutableLiveData<List<MovieModel>> movieListByQuery;
    private static MutableLiveData<MovieModel> movieDetailsByID;

    private DbClient dbClient;
//    private BookmarkDao bookmarkDao;
//    private NowPlayingMovieDao nowPlayingMovieDao;
//    private MovieDao movieDao;
    // disposable
    private CompositeDisposable disposable = new CompositeDisposable();

    //service api : to make API calls
    final private MovieApi movieApi;

    private ApiClient(Context context){
//        bookmarkDao = AppDatabase.getDatabase(context).bookmarkDao();
//        nowPlayingMovieDao = AppDatabase.getDatabase(context).nowPlayingMovieDao();
//        movieDao = AppDatabase.getDatabase(context).movieDao();
        dbClient = DbClient.getInstance(context);
        nowPlayingMovieList = new MutableLiveData<>();
        popularMovieList = new MutableLiveData<>();
        movieListByQuery = new MutableLiveData<>();
        movieDetailsByID = new MutableLiveData<>();
        movieApi = ServiceRequest.getMovieApi();
    }

    /**
     * facilitating singleton behavior
     * @return ApiClient instance
     */
    public static ApiClient getInstance(Context context){
        if(instance==null)
            instance = new ApiClient(context);
        return instance;
    }

    /**
     * get a list of movies obtained by now playing api call
     * @return LiveData<List<MovieModel>>
     */
    public LiveData<List<MovieModel>> getNowPlayingMovies(){
        return nowPlayingMovieList;
    }

    /**
     * get a list of movies obtained by popular api call
     * @return LiveData<List<MovieModel>>
     */
    public LiveData<List<MovieModel>> getPopularMovies(){
        return popularMovieList;
    }

    /**
     * get a list of movies obtained by search api call on a query string
     * @return LiveData<List<MovieModel>>
     */
    public LiveData<List<MovieModel>> getMoviesByQuery(){
        return movieListByQuery;
    }

    /**
     * get movie object obtained by search movie by id api call
     * @return MovieModel
     */
    public LiveData<MovieModel> getMovieDetails(){
        return movieDetailsByID;
    }

    /**
     * asynchronously observes for movie detail response obtained for api call
     * @param movie_id
     */
    public void searchMovieDetails(int movie_id){
        disposable.add(movieApi.getMovieDetails(
                movie_id,
                Constants.API_KEY

        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(movieDetailsResponse -> movieDetailsResponse.getMovie())
        .subscribe(this::handleMovieDetailsSuccess, this::handleMovieDetailsError));
    }

//    private void updateDatabase(MovieModel movieModel){
//        disposable.add(bookmarkDao.insert(new BookmarkModel(movieModel.getId()))
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe());
//    }

    private void handleMovieDetailsError(Throwable throwable) {
        movieDetailsByID.setValue(null);
        Log.e("response", "Unable to get details: " + throwable.getMessage());
    }

    private void handleMovieDetailsSuccess(MovieModel movieModel) {
        Log.v("details", movieModel.toString());
        movieDetailsByID.setValue(movieModel);
    }


    /**
     * asynchronously observes response obtained on api call to get all now playing movies
     * @param pageNumber
     */
    public void searchNowPlayingMovies(int pageNumber){
        // getting the data from the api and storing it in the database
        disposable.add(movieApi.nowPlayingMovie(
                Constants.API_KEY,
                pageNumber
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(movieNowPlayingResponse -> movieNowPlayingResponse.getNowPlayingMovies())
                .flatMap(movieModelList -> {
                    for(MovieModel movieModel: movieModelList){
                        Log.v("check now playing", movieModel.getTitle());
                        addToMovieDatabase(movieModel);
                        addToNowPlayingMoviesDatabase(movieModel.getId());
                    }
                    return Observable.just(movieModelList);
                })
        .subscribe(this::handleNowPlayingSuccess, this::handleNowPlayingError));

    }

    private void addToMovieDatabase(MovieModel movieModel){
        dbClient.addToMovieDb(movieModel);
    }

    private void addToNowPlayingMoviesDatabase(int id){
        dbClient.addToNowPlayingMovieDb(id);
    }

    private void addToPopularMoviesDatabase(int id){
        dbClient.addToPopularMovieDb(id);
    }

    private void handleNowPlayingError(Throwable throwable) {
        nowPlayingMovieList.setValue(null);
        Log.v("response", "Error occurred fetching now playing movies" + throwable.getMessage());
    }

    private void handleNowPlayingSuccess(List<MovieModel> movieModels) {
        nowPlayingMovieList.setValue(movieModels);
    }

    /**
     * asynchronously observes response obtained on api call to get all popular movies
     * @param pageNumber
     */
    public void searchPopularMovies(int pageNumber){

        // getting the data from the api and storing it in the database
        disposable.add(movieApi.popularMovies(
                Constants.API_KEY,
                pageNumber
        ).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(moviePopularResponse -> moviePopularResponse.getPopularMovieList())
                .flatMap(movieModelList -> {
                    for(MovieModel movieModel: movieModelList){
                        Log.v("check popular", movieModel.getTitle());
                        addToMovieDatabase(movieModel);
                        addToPopularMoviesDatabase(movieModel.getId());
                    }
                    return Observable.just(movieModelList);
                })
        .subscribe(this::handlePopularMovieSuccess, this::handlePopularMovieError));


    }

    private void handlePopularMovieError(Throwable throwable) {
        popularMovieList.setValue(null);
        Log.v("response", "Error occurred fetching popular movies" + throwable.getMessage());
    }

    private void handlePopularMovieSuccess(List<MovieModel> movieModels) {
        popularMovieList.setValue(movieModels);
    }

    /**
     * asynchronously observes response obtained on api call to get all movies by query
     * @param query
     * @param pageNumber
     */
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
