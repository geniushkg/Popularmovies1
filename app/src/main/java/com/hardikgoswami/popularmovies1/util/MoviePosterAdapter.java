package com.hardikgoswami.popularmovies1.util;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hardikgoswami.popularmovies1.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;


public class MoviePosterAdapter extends ArrayAdapter<String> {
    public static final String TAG = MoviePosterAdapter.class.getSimpleName();
    List<String> mPosterUrls;

    public MoviePosterAdapter(Context context, List<String> posterUrls) {
        super(context, R.layout.grid_item, posterUrls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String posterUrl = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent,
                    false);
        }
        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivGidItemList);
        Log.d(TAG, posterUrl);
        Picasso.with(getContext())
                .load(posterUrl)
                .into(ivPoster);

        return convertView;
    }
}
