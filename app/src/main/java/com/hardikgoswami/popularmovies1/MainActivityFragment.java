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

import java.util.ArrayList;
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
    ArrayList<String> posterUrlList = new ArrayList<>();

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
        mMoviePosterAdapter = new MoviePosterAdapter(getContext(), posterUrlList);
        gvMainFrag.setAdapter(mMoviePosterAdapter);
        gvMainFrag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"gridview clicked",Toast.LENGTH_SHORT).show();
            }
        });
        updateMovieAdapter();
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
                    List<String> posterList = new ArrayList<String>();

                    Log.d("TAG","result size is : "+resultList.size());
                    for (int i = 0; i < resultList.size(); i++) {
                        posterList.add(" http://image.tmdb.org/t/p/w185"+resultList.get(i).getPoster_path());
                        Log.d("TAG","poster url : "+ posterList.size());
                    }
                    mMoviePosterAdapter.clear();
                    mMoviePosterAdapter.addAll(posterList);
                    if(mMoviePosterAdapter.isEmpty()){
                        Log.d("TAG","adapter empty");
                    }else {
                        Log.d("TAG","adapter not null:"+mMoviePosterAdapter.getCount());
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
                    List<String> posterList = new ArrayList<String>();
                    String[] posterUrl = new String[resultList.size()];
                    int i = 0;
                    for (i = 0; i < resultList.size(); i++) {
                        posterList.add(resultList.get(i).getPoster_path());
                    }
                    mMoviePosterAdapter.clear();
                    mMoviePosterAdapter.addAll(posterList);
                    mMoviePosterAdapter.notifyDataSetInvalidated();
                }
                @Override
                public void onFailure(Call<Popular> call, Throwable t) {
                    Log.d("TAG", "Network call failed , Retrofit on failed :" + t.getMessage());
                }
            });

        }
    }
}
