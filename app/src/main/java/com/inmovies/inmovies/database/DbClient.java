package com.inmovies.inmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.models.NowPlayingMoviesModel;
import com.inmovies.inmovies.models.PopularMoviesModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DbClient {

    private static DbClient instance;
    private NowPlayingMovieDao nowPlayingMovieDao;
    private PopularMovieDao popularMovieDao;
    private MovieDao movieDao;

    private MutableLiveData<List<MovieModel>> nowPlayingMovies, popularMovies;
    private CompositeDisposable disposable = new CompositeDisposable();

    private DbClient(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        nowPlayingMovieDao = db.nowPlayingMovieDao();
        popularMovieDao = db.popularMovieDao();
        nowPlayingMovies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();

        movieDao = db.movieDao();
    }

    public static DbClient getInstance(Context context){
        if(instance == null)
            instance = new DbClient(context);
        return instance;
    }

    public void addToNowPlayingMovieDb(int id){
        nowPlayingMovieDao.insert(new NowPlayingMoviesModel(id))
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v("check", "Added to now playing database");
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.v("check", "Unable to add to now playing database " + e.getMessage());
            }
        });
    }

    public void addToPopularMovieDb(int id){
        popularMovieDao.insert(new PopularMoviesModel(id))
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v("check", "Added to popular database");
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.v("check", "Unable to add to popular database " + e.getMessage());
            }
        });
    }

    public void addToMovieDb(MovieModel movieModel){
        movieDao.insert(movieModel).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v("check", "Added to movie database");
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.v("check", "Unable to add to movie database " + e.getMessage());
            }
        });
    }

    public LiveData<List<MovieModel>> getAllNowPlayingMovies(){
        getAllNowPlayingMoviesFromDb();
        return nowPlayingMovies;
    }

    public LiveData<List<MovieModel>> getAllPopularMovies(){
        getAllPopularMoviesFromDb();
        return popularMovies;
    }
    private void getAllNowPlayingMoviesFromDb(){
        disposable.add(nowPlayingMovieDao.getAllNowPlayingMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleNowPlayingMoviesSuccess, this::handleNowPlayingMoviesError));
    }

    private void handleNowPlayingMoviesSuccess(List<MovieModel> movieModelList) {
        nowPlayingMovies.setValue(movieModelList);
        Log.v("database", "Retrieved Now Playing movies from Db " + movieModelList);
    }

    private void handleNowPlayingMoviesError(Throwable throwable) {
        nowPlayingMovies.setValue(null);
        Log.v("database", "Error getting now playing movies from Db");
    }

    private void getAllPopularMoviesFromDb(){

        disposable.add(popularMovieDao.getAllPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlePopularMoviesSuccess, this::handlePopularMoviesError));
    }

    private void handlePopularMoviesSuccess(List<MovieModel> movieModelList) {
        popularMovies.setValue(movieModelList);
        Log.v("database", "Retrieved Popular Playing movies from Db " + movieModelList);
    }

    private void handlePopularMoviesError(Throwable throwable) {
        popularMovies.setValue(null);
        Log.v("database", "Error getting popular playing movies from Db");
    }

}
