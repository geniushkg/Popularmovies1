package com.hardikgoswami.popularmovies1.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardikgoswami.popularmovies1.MoviesContract;
import com.hardikgoswami.popularmovies1.MyApplication;
import com.hardikgoswami.popularmovies1.R;
import com.hardikgoswami.popularmovies1.model.Movie;
import com.hardikgoswami.popularmovies1.presenter.MoviePresenter;
import com.hardikgoswami.popularmovies1.rest.TheMovieDbService;

import java.util.List;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment implements MoviesContract.View{

    private MoviesContract.Presenter mPresenter;
    private TheMovieDbService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mService = MyApplication.getTheMovieService();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = new MoviePresenter(this, mService);
        }
        mPresenter.loadMovies();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        // View declaration
        // Adapter initiate
        // Set Adapter to gridview

        return rootView;
    }

    @Override
    public void showMovies(List<Movie> movies) {

    }
}
