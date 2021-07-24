package com.inmovies.inmovies.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inmovies.inmovies.AppExecutors;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.response.MoviePopularResponse;
import com.inmovies.inmovies.response.MoviesByQueryResponse;
import com.inmovies.inmovies.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

// Singleton Class: Repository requests the data from the client
// Client interacts with the data sources: (making api calls or accessing the data base)
// to get the data; The data retrieval is done through JAVA Executors
public class ApiClient {

    private static ApiClient instance;

    // two lists for two api calls : one for now playing movies, other for popular movies
    private static MutableLiveData<List<MovieModel>> nowPlayingMovieList, popularMovieList;
    private static MutableLiveData<List<MovieModel>> movieListByQuery;

    private RetrieveNowPlayingMoviesRunnable retrieveNowPlayingMoviesRunnable;
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;
    private RetrieveMoviesByQueryRunnable retrieveMoviesByQueryRunnable;

    private ApiClient(){
        nowPlayingMovieList = new MutableLiveData<>();
        popularMovieList = new MutableLiveData<>();
        movieListByQuery = new MutableLiveData<>();
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

    public void searchNowPlayingMovies(int pageNumber){

        if(retrieveNowPlayingMoviesRunnable!= null)
            retrieveNowPlayingMoviesRunnable = null;

        retrieveNowPlayingMoviesRunnable = new RetrieveNowPlayingMoviesRunnable(pageNumber);

        final Future handler = AppExecutors.getInstance().getScheduledExecutorService()
                .submit(retrieveNowPlayingMoviesRunnable);


        AppExecutors.getInstance().getScheduledExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                handler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }

    public void searchPopularMovies(int pageNumber){

        if(retrievePopularMoviesRunnable!= null)
            retrievePopularMoviesRunnable = null;

        retrievePopularMoviesRunnable = new RetrievePopularMoviesRunnable(pageNumber);

        final Future handler = AppExecutors.getInstance().getScheduledExecutorService()
                .submit(retrievePopularMoviesRunnable);


        AppExecutors.getInstance().getScheduledExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                handler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviesByQuery(String query, int pageNumber){
        if(retrieveMoviesByQueryRunnable!=null)
            retrieveMoviesByQueryRunnable = null;
        retrieveMoviesByQueryRunnable = new RetrieveMoviesByQueryRunnable(query, pageNumber);

        final Future handler = AppExecutors.getInstance().getScheduledExecutorService()
                .submit(retrieveMoviesByQueryRunnable);

        AppExecutors.getInstance().getScheduledExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }


    private class RetrieveMoviesByQueryRunnable implements Runnable{

        private int pageNumber;
        private String query;
        private boolean cancelRequest;

        public RetrieveMoviesByQueryRunnable(String query, int pageNumber) {
            this.pageNumber = pageNumber;
            this.query = query;
            cancelRequest = false;
        }


        @Override
        public void run() {

            try {
                Response response = getMoviesByQuery(query, pageNumber).execute();
                if (cancelRequest)
                    return;

                if(response.code() == 200){
                    Log.v("tags", response.body().toString());
                    List<MovieModel> list =
                            new ArrayList<>(((MoviesByQueryResponse)response.body()).getMoviesByQuery());

                    if(pageNumber == 1){
                        // postValue is used for background thread
                        movieListByQuery.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = movieListByQuery.getValue();
                        currentMovies.addAll(list);

                        movieListByQuery.postValue(currentMovies);
                    }
                }
                else{

                    Log.v("tag", "Error: " + response.body().toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
                movieListByQuery.postValue(null);
            }

        }

        // Get Now Playing Movies
        private Call<MoviesByQueryResponse> getMoviesByQuery(String query, int pageNumber){
            return ServiceRequest.getMovieApi().queryMovies(
                    Constants.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("tag", "Cancelling Request");
            cancelRequest = true;
        }
    }


    // Retrieving data from REST API by Runnable Class
    private class RetrieveNowPlayingMoviesRunnable implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveNowPlayingMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }


        @Override
        public void run() {


            // Getting the response objects
            try {
                Response response = getNowPlayingMovies(pageNumber).execute();
                if (cancelRequest)
                    return;

                if(response.code() == 200){
                    List<MovieModel> list =
                            new ArrayList<>(((MovieNowPlayingResponse)response.body()).getNowPlayingMovies());

                    if(pageNumber == 1){
                        // postValue is used for background thread
                        nowPlayingMovieList.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = nowPlayingMovieList.getValue();
                        currentMovies.addAll(list);

                        nowPlayingMovieList.postValue(currentMovies);
                    }
                }
                else{

                    Log.v("tag", "Error: " + response.body().toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
                nowPlayingMovieList.postValue(null);
            }

        }


        // Get Now Playing Movies
        private Call<MovieNowPlayingResponse> getNowPlayingMovies(int pageNumber){
            return ServiceRequest.getMovieApi().nowPlayingMovie(
                    Constants.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("tag", "Cancelling Request");
            cancelRequest = true;
        }
    }



    private class RetrievePopularMoviesRunnable implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetrievePopularMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }


        @Override
        public void run() {


            // Getting the response objects
            try {
                Response response = getPopularMovies(pageNumber).execute();
                if (cancelRequest)
                    return;

                if(response.code() == 200){
                    List<MovieModel> list =
                            new ArrayList<>(((MoviePopularResponse)response.body()).getPopularMovieList());

                    if(pageNumber == 1){
                        // postValue is used for background thread
                        popularMovieList.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = popularMovieList.getValue();
                        currentMovies.addAll(list);

                        popularMovieList.postValue(currentMovies);
                    }
                }
                else{

                    Log.v("tag", "Error: " + response.body().toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
                popularMovieList.postValue(null);
            }

        }

        // Get Popular Movies
        private Call<MoviePopularResponse> getPopularMovies(int pageNumber){
            return ServiceRequest.getMovieApi().popularMovies(
                    Constants.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("tag", "Cancelling Request");
            cancelRequest = true;
        }
    }

}
