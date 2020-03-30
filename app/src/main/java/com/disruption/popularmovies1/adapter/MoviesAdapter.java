package com.disruption.popularmovies1.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.disruption.popularmovies1.R;
import com.disruption.popularmovies1.model.Movie;

import static com.disruption.popularmovies1.utils.Constants.IMAGE_URL_BASE_PATH;

public class MoviesAdapter extends ListAdapter<Movie, MoviesAdapter.MovieViewHolder> {
    private static final String TAG = "MoviesAdapter";

    public MoviesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        Movie movie = getItem(position);
        String imageUrl = IMAGE_URL_BASE_PATH + movie.getPosterPath();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.movie_loading_animation)
                .error(R.drawable.ic_error_black_24dp)
                .into(holder.movieImage);
        holder.movieTitle.setText(movie.getTitle());
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView movieImage;
        final TextView movieTitle;

        MovieViewHolder(View v) {
            super(v);
            movieImage = v.findViewById(R.id.movie_image);
            movieTitle = v.findViewById(R.id.movie_title);
        }
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getMovieId() == newItem.getMovieId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem == newItem;
                }
            };
}