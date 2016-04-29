package com.hardikgoswami.popularmovies1.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hardikgoswami.popularmovies1.R;
import com.hardikgoswami.popularmovies1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviePosterAdapter extends ArrayAdapter<Movie> {
    public static final String TAG = MoviePosterAdapter.class.getSimpleName();

    public MoviePosterAdapter(Context context, ArrayList<Movie> posterUrls) {
        super(context , 0, posterUrls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Movie movie = getItem(position);

        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent,
                    false);
            vh.ivPoster = (ImageView) convertView.findViewById(R.id.ivGidItemList);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext())
                .load(movie.getPoster_path())
                .into(vh.ivPoster);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivPoster;
    }
}
