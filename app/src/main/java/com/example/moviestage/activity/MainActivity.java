package com.example.moviestage.activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviestage.R;
import com.example.moviestage.adapter.MovieAdapter;
import com.example.moviestage.app.Constants;
import com.example.moviestage.db.MovieViewModel;
import com.example.moviestage.listener.MovieListener;
import com.example.moviestage.model.Movie;
import com.example.moviestage.presenter.MoviePresenter;
import com.example.moviestage.utils.GridItemDecoration;
import com.example.moviestage.view.MovieView;

import java.util.List;

public class MainActivity extends BaseActivity implements MovieView, MovieListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MoviePresenter presenter;

    private MovieAdapter movieAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView emptyView;

    private static final String KEY_CHECKED_MENU = "key_checked";

    private int checkedMenuId = R.id.popular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        presenter = new MoviePresenter(this);
        loadMovies();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CHECKED_MENU, checkedMenuId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkedMenuId = savedInstanceState.getInt(KEY_CHECKED_MENU);
    }

    private void init() {

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        emptyView = findViewById(R.id.emptyView);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.setListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridItemDecoration(2, dpToPx(), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(movieAdapter);
    }

    private int dpToPx() {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, resources.getDisplayMetrics()));
    }

    private void startLoading() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showContent (){
        if (movieAdapter.getItemCount() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }


    private void loadMovies() {
        if (checkedMenuId == R.id.popular) {
            presenter.fetchMovies(Constants.SORT_BY_POPULAR);
        } else {
            presenter.fetchMovies(Constants.SORT_BY_TOP_RATE);
        }
    }

    private void setupViewModel() {
        final MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(checkedMenuId == R.id.favorite){
                    movieAdapter.setMovies(movies);
                    showContent();
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(checkedMenuId).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        item.setChecked(true);
        checkedMenuId = id;
        if (id == R.id.favorite) {
            setupViewModel();
        } else {
            loadMovies();
        }
        return true;
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        movieAdapter.setMovies(movies);
        showContent();
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
        startLoading();
    }

    @Override
    public void hideProgress() {
        stopLoading();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra(MovieDetail.KEY_MOVIE, movie);
        startActivity(intent);

    }


}