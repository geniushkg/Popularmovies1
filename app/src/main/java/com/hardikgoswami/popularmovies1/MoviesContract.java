package com.hardikgoswami.popularmovies1;


import com.hardikgoswami.popularmovies1.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geniushkg on 4/26/2016.
 */
public interface MoviesContract {
    interface View {
        void showMovies(ArrayList<Movie> movies);
    }

    interface Presenter {
        void loadMovies();
    }
}

