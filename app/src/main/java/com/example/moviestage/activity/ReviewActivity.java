package com.example.moviestage.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviestage.R;
import com.example.moviestage.adapter.MovieAdapter;
import com.example.moviestage.adapter.ReviewsAdapter;
import com.example.moviestage.model.Review;
import com.example.moviestage.presenter.ReviewsPresenter;
import com.example.moviestage.utils.GridItemDecoration;
import com.example.moviestage.view.ReviewsView;

import java.util.List;

public class ReviewActivity extends BaseActivity implements ReviewsView {

    private static final String TAG = ReviewActivity.class.getSimpleName();

    public static final String KEY_MOVIE_ID = "key_movie_id";

    private ReviewsAdapter mReviewAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView emptyView;

    ReviewsPresenter  presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        Intent intent = getIntent();
        if (!intent.hasExtra(KEY_MOVIE_ID)) {
            finish();
        }

        int movieId = intent.getIntExtra(KEY_MOVIE_ID,0);

        presenter = new ReviewsPresenter(this);

        presenter.fetchReviews(movieId);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_review;
    }


    private void init() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.reviews_label);
        }
        mProgressBar = findViewById(R.id.progress);
        emptyView = findViewById(R.id.emptyView);

        mReviewAdapter = new ReviewsAdapter(this);
        mRecyclerView =  findViewById(R.id.recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mReviewAdapter);
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
        if (mReviewAdapter.getItemCount() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayReviews(List<Review> reviews) {
        mReviewAdapter.setReviews(reviews);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startLoading();
            }
        });

    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopLoading();
            }
        });

    }
}