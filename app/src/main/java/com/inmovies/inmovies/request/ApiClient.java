package com.inmovies.inmovies.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inmovies.inmovies.AppExecutors;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.response.MovieNowPlayingResponse;
import com.inmovies.inmovies.response.MoviePopularResponse;
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

    private static MutableLiveData<List<MovieModel>> movieList;

    private RetrieveNowPlayingMoviesRunnable retrieveNowPlayingMoviesRunnable;
    private ApiClient(){
        movieList = new MutableLiveData<>();
    }

    public static ApiClient getInstance(){
        if(instance==null)
            instance = new ApiClient();
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieList;
    }


    public void searchPopularMoviesApi(int pageNumber){

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
                        movieList.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = movieList.getValue();
                        currentMovies.addAll(list);

                        movieList.postValue(currentMovies);
                    }
                }
                else{

                    Log.v("tag", "Error: " + response.body().toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
                movieList.postValue(null);
            }


        }

//        // Get Popular Movies
//        private Call<MoviePopularResponse> getPopularMovies(int pageNumber){
//            return ServiceRequest.getMovieApi().popularMovies(
//                    Constants.API_KEY,
//                    pageNumber
//            );
//        }

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

}
