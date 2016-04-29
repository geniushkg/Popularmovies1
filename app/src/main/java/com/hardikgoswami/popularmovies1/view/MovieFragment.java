package com.hardikgoswami.popularmovies1.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hardikgoswami.popularmovies1.MoviesContract;
import com.hardikgoswami.popularmovies1.MyApplication;
import com.hardikgoswami.popularmovies1.R;
import com.hardikgoswami.popularmovies1.model.Movie;
import com.hardikgoswami.popularmovies1.presenter.MoviePresenter;
import com.hardikgoswami.popularmovies1.util.MoviePosterAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment implements MoviesContract.View{

    private MoviesContract.Presenter mPresenter;
    private MoviePosterAdapter mMoviePosterAdapter;
    GridView mGridView;

    public MovieFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MoviePresenter(this, MyApplication.getTheMovieService());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mMoviePosterAdapter = new MoviePosterAdapter(getContext(), new ArrayList<Movie>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gridView_main);
        mGridView.setAdapter(mMoviePosterAdapter);
        mPresenter.loadMovies();

        return rootView;
    }

    @Override
    public void showMovies(ArrayList<Movie> movies) {
        mMoviePosterAdapter.clear();
        mMoviePosterAdapter.addAll(movies);
        mMoviePosterAdapter.notifyDataSetChanged();
    }
}
