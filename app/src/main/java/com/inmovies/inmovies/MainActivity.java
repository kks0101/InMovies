package com.inmovies.inmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.inmovies.inmovies.database.AppDatabase;
import com.inmovies.inmovies.databinding.ActivityMainBinding;
import com.inmovies.inmovies.models.MovieModel;
import com.inmovies.inmovies.ui.home.HomeViewModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Pattern;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);


        new CompositeDisposable(hasInternetConnection().subscribe((hasInternet)->{
            if(!hasInternet){

                Snackbar.make(binding.getRoot(), "No internet connection.", Snackbar.LENGTH_LONG).show();
            }
            else{
                Log.v("connection", "Device has active internet connection ");
            }
        }));


        AppDatabase.getDatabase(this);
        handleDeeplinkIntent(getIntent());


    }

    public static Single<Boolean> hasInternetConnection(){
        return Single.fromCallable(()->{
            try{
                int timeoutMS= 1500;
                Socket socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
                socket.connect(socketAddress, timeoutMS);
                socket.close();

                return true;
            }catch (IOException e){
                return false;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleDeeplinkIntent(intent);
    }

    private void handleDeeplinkIntent(Intent intent){
        String action = intent.getAction();
        Uri data = intent.getData();
        if(Intent.ACTION_VIEW.equals(action) && data!=null){
            LoadingDialog loadingDialog = new LoadingDialog(this);
            loadingDialog.showDialog();
            // check if passed movie id is integer
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Activity curr = this;

            String movieId = data.getQueryParameter("movie_id");

            if(movieId!=null && !movieId.isEmpty() && pattern.matcher(movieId).matches()){
                Log.v("deeplink", movieId);

                int id = Integer.parseInt(movieId);
                homeViewModel.searchMovieDetails(id);
                homeViewModel.getMovieDetailsById().observe(this, new Observer<MovieModel>() {
                    @Override
                    public void onChanged(MovieModel movieModel) {
                        if(movieModel!=null){
                            MobileNavigationDirections.ActionGlobalNavigationMovieDetails action =
                                    MobileNavigationDirections.actionGlobalNavigationMovieDetails(movieModel);

                            Navigation.findNavController(curr, R.id.nav_host_fragment_activity_main).navigate(action);
                            loadingDialog.hideDialog();
                        }
                    }
                });
            }
            else{
                Navigation.findNavController(curr, R.id.nav_host_fragment_activity_main).navigate(R.id.errorFragment);
                loadingDialog.hideDialog();
            }

        }
    }
}