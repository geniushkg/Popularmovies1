package com.hardikgoswami.popularmovies1.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hardikgoswami.popularmovies1.R;
import com.squareup.picasso.Picasso;


import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by geniushkg on 4/21/2016.
 */
public class MoviePosterAdapter extends BaseAdapter {
    String[] moviesUrl = null;
    LayoutInflater inflate = null;

    public MoviePosterAdapter(String[] posterUrl  , LayoutInflater layoutInflater) {
        this.moviesUrl = posterUrl;
        this.inflate = layoutInflater;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflate.inflate(R.layout.grid_item, parent, false);
        holder = new ViewHolder(view);

        Picasso.with(inflate.getContext())
                .load(moviesUrl[position])
                .into(holder.imageView);
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.ivGidItemList)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
