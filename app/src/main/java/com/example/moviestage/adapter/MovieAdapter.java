package com.example.moviestage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviestage.R;
import com.example.moviestage.listener.MovieListener;
import com.example.moviestage.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<Movie> movies;

    private MovieListener listener;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setListener(MovieListener listener) {
        this.listener = listener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_poster);
            imageView.setOnClickListener(this);
        }

        private void bind(int position) {
            Movie movie = movies.get(position);
            Picasso.with(imageView.getContext())
                    .load(movie.getPosterPath())
                    .into(imageView);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.iv_poster) {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            }
        }
    }
}
