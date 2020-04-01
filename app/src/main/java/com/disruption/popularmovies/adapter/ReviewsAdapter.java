package com.disruption.popularmovies.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies.R;
import com.disruption.popularmovies.model.review.Review;

public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ReviewViewHolder> {

    private static final DiffUtil.ItemCallback<Review> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Review>() {
                @Override
                public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                    return oldItem == newItem;
                }
            };

    public ReviewsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        Review review = getItem(position);
        assert review != null;
        holder.bind(review);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        final TextView author;
        final TextView content;

        ReviewViewHolder(View v) {
            super(v);
            author = v.findViewById(R.id.tv_review_author);
            content = v.findViewById(R.id.tv_review_content);
        }

        private void bind(Review review) {
            author.setText(review.getAuthor());
            content.setText(review.getContent());
        }
    }
}