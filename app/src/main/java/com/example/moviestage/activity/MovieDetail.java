package com.example.moviestage.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestage.R;
import com.example.moviestage.app.AppExecutors;
import com.example.moviestage.app.Constants;
import com.example.moviestage.app.Endpoint;
import com.example.moviestage.db.AppDb;
import com.example.moviestage.db.MovieDetailViewModel;
import com.example.moviestage.db.MovieViewModelFactory;
import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Video;
import com.example.moviestage.presenter.MovieDetailPresenter;
import com.example.moviestage.utils.ImageLoader;
import com.example.moviestage.view.MovieDetailView;

import java.util.List;

public class MovieDetail extends BaseActivity implements MovieDetailView, View.OnClickListener {

    private static final String TAG = MovieDetail.class.getSimpleName();

    public static final String KEY_MOVIE = "key_movie";

    Movie movie;
    private ImageView ivPoster;
    private TextView txtTitle;
    private TextView txtReleaseDate;
    private TextView txtVoteAvg;
    private TextView txtOverView;
    private FloatingActionButton fabAddFavorite;
    private Button btnReviews;
    private Button btnShowTrailer;

    MovieDetailPresenter presenter;

    private AppDb mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDb.getInstance(getApplicationContext());

        init();

        Intent intent = getIntent();
        if (!intent.hasExtra(KEY_MOVIE)) {
            finish();
        }

        movie = intent.getParcelableExtra(KEY_MOVIE);

        updateUi();

        setupViewModel();

        presenter = new MovieDetailPresenter(this);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_movie_detaile;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_MOVIE, movie);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(KEY_MOVIE);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void init() {
        ivPoster = findViewById(R.id.iv_poster);
        txtTitle = findViewById(R.id.tv_title);
        txtReleaseDate = findViewById(R.id.tv_release_date);
        txtVoteAvg = findViewById(R.id.tv_vote_avg);
        txtOverView = findViewById(R.id.tv_overview);
        fabAddFavorite = findViewById(R.id.fabAddFavorite);
        btnReviews = findViewById(R.id.btnReviews);
        btnShowTrailer = findViewById(R.id.btnTrailer);

        fabAddFavorite.setOnClickListener(this);
        btnReviews.setOnClickListener(this);
        btnShowTrailer.setOnClickListener(this);

    }

    private void setupViewModel() {
        MovieViewModelFactory factory = new MovieViewModelFactory(mDb, movie.getMovieId());
        final MovieDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movieDb) {
                if (movieDb != null) {
                    setCheckedFab(movie.getMovieId() == movieDb.getMovieId());
                } else {
                    setCheckedFab(false);
                }
            }
        });

    }

    private void addMovie() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insert(movie);
            }
        });
    }

    private void removeMovie() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteOne(movie);
            }
        });
    }

    private void setCheckedFab(boolean isFavorite) {
        int drawableId = isFavorite ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite_empty;
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        fabAddFavorite.setImageDrawable(drawable);
        movie.setFavorite(isFavorite);
    }

    private void updateUi() {
        if (movie != null) {
            ImageLoader.loadPoster(ivPoster, movie.getBackdropPath());
            txtTitle.setText(movie.getOriginalTitle());
            txtReleaseDate.setText(movie.getReleaseDate());
            txtVoteAvg.setText(String.valueOf(movie.getVoteAverage()));
            txtOverView.setText(movie.getOverview());
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fabAddFavorite:
                handleFavorite();
                break;
            case R.id.btnReviews:
                handleReviews();
                break;
            case R.id.btnTrailer:
                presenter.getVideos(movie.getMovieId());
                break;
        }

    }

    private void handleFavorite() {
        if (movie.isFavorite()) {
            removeMovie();
        } else {
            addMovie();
        }
    }

    private void handleReviews() {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra(ReviewActivity.KEY_MOVIE_ID, movie.getMovieId());
        startActivity(intent);
    }

    private void handleUrl(Video video) {
        String urlString = Endpoint.YOUTUBE + video.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        startActivity(intent);
    }




    @Override
    public void onDisplayResult(List<Video> videoList) {
        //only get first video url
        if(videoList != null && videoList.size() > 0){
            handleUrl(videoList.get(0));
        }

    }


    @Override
    public void onDisplayError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlertMessage(getString(R.string.error_title_failur), error);
            }
        });
    }

    @Override
    public void showProgress() {


    }

    @Override
    public void hideProgress() {


    }
}