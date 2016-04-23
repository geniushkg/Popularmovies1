package com.hardikgoswami.popularmovies1.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by geniushkg on 4/23/2016.
 */
public class MoviePosterAdapter extends ArrayAdapter<Result> {
    public MoviePosterAdapter(Context context, int resource, Result[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
