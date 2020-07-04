package com.example.moviestage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestage.R;
import com.example.moviestage.listener.MovieListener;
import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Review;
import com.example.moviestage.utils.ImageLoader;

import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    private Context mContext;

    public ReviewsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (reviews == null){
            return 0;
        }
        return reviews.size();
    }

    public List<Review> getReviews() {
        return reviews;
    }


    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView txtAuthor;
        TextView txtContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtContent = itemView.findViewById(R.id.txtContent);
        }

        private void bind(int position) {
            Review review = reviews.get(position);
            if (review !=null){
                txtAuthor.setText(review.getAuthor());
                txtContent.setText(review.getContent());
            }
        }

    }
}
