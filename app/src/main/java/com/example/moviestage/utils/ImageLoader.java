package com.example.moviestage.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.moviestage.app.Endpoint;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class ImageLoader {

    public static void loadPoster(ImageView imageView, String path){
        Picasso.with(imageView.getContext())
                .load(Endpoint.TMDB_POSTER_BASE_URL + path)
                .into(imageView);
    }
}
