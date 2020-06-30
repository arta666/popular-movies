package com.example.moviestage.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestage.R;
import com.example.moviestage.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    public static final String KEY_MOVIE = "key_movie";

    Movie movie;
    private ImageView ivPoster;
    private TextView txtTitle;
    private TextView txtReleaseDate;
    private TextView txtVoteAvg;
    private TextView txtOverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detaile);

        init();

        Intent intent = getIntent();
        if (!intent.hasExtra(KEY_MOVIE)) {
            finish();
        }

        movie = intent.getParcelableExtra(KEY_MOVIE);

        updateUi();


    }

    private void init() {
        ivPoster = findViewById(R.id.iv_poster);
        txtTitle = findViewById(R.id.tv_title);
        txtReleaseDate = findViewById(R.id.tv_release_date);
        txtVoteAvg = findViewById(R.id.tv_vote_avg);
        txtOverView = findViewById(R.id.tv_overview);
    }

    private void updateUi() {
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getBackdropPath())
                    .into(ivPoster);

            txtTitle.setText(movie.getOriginalTitle());
            txtReleaseDate.setText(movie.getReleaseDate());
            txtVoteAvg.setText(String.valueOf(movie.getVoteAverage()));
            txtOverView.setText(movie.getOverview());
        }
    }
}