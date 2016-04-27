package com.hardikgoswami.popularmovies1.presenter;

import com.hardikgoswami.popularmovies1.MoviesContract;
import com.hardikgoswami.popularmovies1.MoviesContract.View;
import com.hardikgoswami.popularmovies1.MyApplication;
import com.hardikgoswami.popularmovies1.model.Popular;
import com.hardikgoswami.popularmovies1.model.Movie;
import com.hardikgoswami.popularmovies1.rest.TheMovieDbService;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviePresenter implements MoviesContract.Presenter {
    MoviesContract.View mView;
    TheMovieDbService mService;

    public MoviePresenter(View mView, TheMovieDbService service) {
        this.mView = mView;
        this.mService = service;
    }

    @Override
    public void loadMovies() {
        Call<Popular> call = mService.getPopularMovies(MyApplication.API_KEY);
        call.enqueue(new Callback<Popular>() {
            @Override
            public void onResponse(Call<Popular> call, Response<Popular> response) {
                if(response.isSuccessful()){
                    Popular popularMovies = response.body();
                    List<Movie> movieList = popularMovies.getResults();
                    mView.showMovies(movieList);
                }
            }

            @Override
            public void onFailure(Call<Popular> call, Throwable t) {
                if (t instanceof IOException) {
                    // mView.showNotInternetConnected();
                }
            }
        });
        // Call rest
            // On successful respose call mView.showMovies();
    }
}
