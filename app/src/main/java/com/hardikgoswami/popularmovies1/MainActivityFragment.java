package com.hardikgoswami.popularmovies1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hardikgoswami.popularmovies1.util.MoviePosterAdapter;
import com.hardikgoswami.popularmovies1.util.Popular;
import com.hardikgoswami.popularmovies1.util.Result;
import com.hardikgoswami.popularmovies1.util.TheMovieDbApiInterface;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String API_KEY = BuildConfig.TMDB_API;
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    MoviePosterAdapter mMoviePosterAdapter = null;
    @InjectView(R.id.gridView_main)
    GridView gvMainFrag;
    private Retrofit retrofit;
    private TheMovieDbApiInterface service;
    private Context context;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(TheMovieDbApiInterface.class);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        updateMovieAdapter();
        gvMainFrag.setAdapter(mMoviePosterAdapter);
        gvMainFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"gridview clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void updateMovieAdapter() {
        // fetch data , add to array
        // fetch data from shared prefrence
        String sort_criteria = "popular";
        // static prefrence used for time bieng
        if (sort_criteria.equalsIgnoreCase("popular")) {
            Call<Popular> popularCall = service.getPopularMovies(API_KEY);
            popularCall.enqueue(new Callback<Popular>() {
                @Override
                public void onResponse(Call<Popular> call, Response<Popular> response) {
                    Popular popularMovies = response.body();
                    Log.d("TAG", "popular Movies data is :" + popularMovies.toString());
                    List<Result> resultList = popularMovies.getResults();
                    String[] posterUrl = new String[resultList.size()];
                    int i = 0;
                    Log.d("TAG","result size is : "+resultList.size());
                    for (i = 0; i < resultList.size(); i++) {
                        posterUrl[i] = " http://image.tmdb.org/t/p/w185"+resultList.get(i).getPoster_path();
                        Log.d("TAG","poster url : "+posterUrl[i]);
                    }
                //TODO: modify adapter class 
                    //    mMoviePosterAdapter = new MoviePosterAdapter(posterUrl, context);
                    if(mMoviePosterAdapter.isEmpty()){
                        Log.d("TAG","adapter empty");
                    }
                    mMoviePosterAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<Popular> call, Throwable t) {
                    Log.d("TAG", "Network call failed , Retrofit on failed :" + t.getMessage());
                }
            });
        } else {

            Call<Popular> popularCall = service.getTopRatedMovies(API_KEY);
            popularCall.enqueue(new Callback<Popular>() {
                @Override
                public void onResponse(Call<Popular> call, Response<Popular> response) {
                    Popular popularMovies = response.body();
                    Log.d("TAG", "Top Rated Movies data is :" + popularMovies.toString());
                    List<Result> resultList = popularMovies.getResults();
                    String[] posterUrl = null;
                    int i = 0;
                    for (i = 0; i < resultList.size(); i++) {
                        posterUrl[i] = resultList.get(i).getPoster_path();
                    }
                    mMoviePosterAdapter = new MoviePosterAdapter(posterUrl, context);
                }

                @Override
                public void onFailure(Call<Popular> call, Throwable t) {
                    Log.d("TAG", "Network call failed , Retrofit on failed :" + t.getMessage());
                }
            });

        }
    }
}
