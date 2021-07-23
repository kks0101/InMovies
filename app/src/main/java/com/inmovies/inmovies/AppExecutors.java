package com.inmovies.inmovies;

import com.inmovies.inmovies.utils.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    // To provide JAVA Executor service for off main thread api calls/data source interaction
    // Singleton Class
    private static AppExecutors instance;

    private AppExecutors(){

    }

    public static AppExecutors getInstance(){
        if(instance == null)
            instance = new AppExecutors();
        return instance;
    }

    private final ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(Constants.THREAD_POOL);

    public ScheduledExecutorService getScheduledExecutorService(){
        return scheduledExecutorService;
    }

}
