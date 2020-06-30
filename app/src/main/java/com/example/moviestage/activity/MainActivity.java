package com.example.moviestage.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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

import com.example.moviestage.R;
import com.example.moviestage.adapter.MovieAdapter;
import com.example.moviestage.app.Commons;
import com.example.moviestage.listener.MovieListener;
import com.example.moviestage.model.Movie;
import com.example.moviestage.presenter.MoviePresenter;
import com.example.moviestage.utils.GridItemDecoration;
import com.example.moviestage.view.MovieView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieView, MovieListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MoviePresenter presenter;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    private static final String KEY_CHECKED_MENU = "key_checked";

    private int checkedMenuId = R.id.popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        presenter = new MoviePresenter(this);
        loadMovies();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CHECKED_MENU,checkedMenuId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkedMenuId = savedInstanceState.getInt(KEY_CHECKED_MENU);
    }

    private void init() {

        mProgressBar = (ProgressBar)findViewById(R.id.progress);

        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(movieList);
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

    private void showContent(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hidContent(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void loadMovies(){
        movieList.clear();
        if (checkedMenuId == R.id.popular){
            presenter.fetchMovies(Commons.SORT_BY_POPULAR);
        }else {
            presenter.fetchMovies(Commons.SORT_BY_TOP_RATE);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(checkedMenuId).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        item.setChecked(true);
        checkedMenuId = id;
        loadMovies();
        return true;
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        if (movies !=null && !movies.isEmpty()){
            movieList.addAll(movies);

        }
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDisplayError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAlertMessage("Failure!",error);
            }
        });

    }

    @Override
    public void showProgress() {
        hidContent();
    }

    @Override
    public void hideProgress() {
        showContent();
    }

    @Override
    public void onItemClick(int position) {
        Movie movie = movieList.get(position);
        Intent intent = new Intent(this,MovieDetail.class);
        intent.putExtra(MovieDetail.KEY_MOVIE,movie);
        startActivity(intent);

    }

    private void showAlertMessage(String title, String message){
        new AlertDialog
                .Builder(this)
                .setTitle("Failure!")
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}