package com.hardikgoswami.popularmovies1;

import android.app.Application;

import com.hardikgoswami.popularmovies1.rest.TheMovieDbService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geniushkg on 4/26/2016.
 */
public class MyApplication extends Application {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.TMDB_API;
    private static TheMovieDbService sService;


    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        sService = retrofit.create(TheMovieDbService.class);
    }

    public static TheMovieDbService getTheMovieService() {
        if (sService == null)
            throw new NullPointerException("TheMovieDbService is null!");
        return sService;
    }
}
