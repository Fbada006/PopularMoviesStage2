package com.disruption.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.disruption.popularmovies.adapter.ReviewsAdapter;
import com.disruption.popularmovies.adapter.TrailersAdapter;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.utils.Constants;
import com.disruption.popularmovies.viewModel.FavouritesActivity;
import com.disruption.popularmovies.viewModel.details.DetailsViewModel;
import com.disruption.popularmovies.viewModel.details.DetailsViewModelFactory;
import com.like.LikeButton;
import com.like.OnLikeListener;

import static com.disruption.popularmovies.utils.Constants.IMAGE_URL_BASE_PATH;

public class DetailsActivity extends AppCompatActivity {
    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvRating;
    private Movie mMovie;
    private DetailsViewModel mDetailsViewModel;
    private LikeButton mLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvDate = findViewById(R.id.movie_date);
        tvTitle = findViewById(R.id.movie_title);
        tvDescription = findViewById(R.id.movie_synopsis);
        tvRating = findViewById(R.id.movie_vote);
        ivPoster = findViewById(R.id.movie_poster);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra(Constants.MOVIE_EXTRA))) {
            mMovie = intent.getParcelableExtra(Constants.MOVIE_EXTRA);
            assert mMovie != null;
            showMovieDetails(mMovie);
            setTitle(mMovie.getTitle());
            setUpFavsListener();

            DetailsViewModelFactory factory = new DetailsViewModelFactory(mMovie.getMovieId(), getApplication());
            mDetailsViewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);

            observeLikedState();
        } else {
            setTitle(getString(R.string.movie_details_label));
        }

        setUpTrailersRv();

        setUpReviewsRv();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favs) {
            startActivity(new Intent(this, FavouritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void observeLikedState() {
        mDetailsViewModel.isMovieInFavs(mMovie.getMovieId()).observe(this, movie -> {
            if (movie != null) {
                mLikeButton.setLiked(true);
            } else {
                mLikeButton.setLiked(false);
            }
        });
    }

    private void setUpFavsListener() {
        mLikeButton = findViewById(R.id.star_button);
        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mDetailsViewModel.insertMovieToFavourites(mMovie);
                Toast.makeText(DetailsActivity.this, getString(R.string.add_to_favs), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                mDetailsViewModel.deleteMovieFromFavourites(mMovie);
                Toast.makeText(DetailsActivity.this, getString(R.string.remove_from_favs), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showMovieDetails(Movie movie) {
        String imageUrl = IMAGE_URL_BASE_PATH + movie.getPosterPath();

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.movie_loading_animation)
                .error(R.drawable.ic_error_black_24dp)
                .into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        tvDate.setText(setMovieYear(movie.getReleaseDate()));
        tvRating.setText(getString(R.string.rating_text, movie.getVote()));
    }

    private String setMovieYear(String year) {
        try {
            return year.substring(0, 4);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "N/A";
        }
    }

    private void setUpTrailersRv() {
        ProgressBar progressBar = findViewById(R.id.trailer_progress_bar);
        ImageView errorImage = findViewById(R.id.trailer_error_image);
        RecyclerView recyclerView = findViewById(R.id.trailers_list);
        TrailersAdapter adapter = new TrailersAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        mDetailsViewModel.getTrailerResourceLiveData().observe(this, trailerResponseResource -> {
            switch (trailerResponseResource.status) {
                case SUCCESS:
                    assert trailerResponseResource.data != null;
                    if (!trailerResponseResource.data.getTrailers().isEmpty()) {
                        adapter.submitList(trailerResponseResource.data.getTrailers());
                        showData(progressBar, errorImage);
                    } else {
                        showError(progressBar, errorImage);
                    }
                    break;
                case ERROR:
                    showError(progressBar, errorImage);
                    break;
                case LOADING:
                    showProgressBar(progressBar, errorImage);
                    break;
            }
        });
    }

    private void setUpReviewsRv() {
        ProgressBar progressBar = findViewById(R.id.review_progress_bar);
        ImageView errorImage = findViewById(R.id.review_error_image);
        RecyclerView recyclerView = findViewById(R.id.reviews_list);
        ReviewsAdapter adapter = new ReviewsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDetailsViewModel.getReviewResourceLiveData().observe(this, reviewResponseResource -> {
            switch (reviewResponseResource.status) {
                case SUCCESS:
                    assert reviewResponseResource.data != null;
                    if (!reviewResponseResource.data.getReviews().isEmpty()) {
                        adapter.submitList(reviewResponseResource.data.getReviews());
                        showData(progressBar, errorImage);
                    } else {
                        showError(progressBar, errorImage);
                    }
                    break;
                case ERROR:
                    showError(progressBar, errorImage);
                    break;
                case LOADING:
                    showProgressBar(progressBar, errorImage);
                    break;
            }
        });
    }

    private void showProgressBar(ProgressBar progressBar, ImageView errorImage) {
        progressBar.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.GONE);
    }

    private void showError(ProgressBar progressBar, ImageView errorImage) {
        progressBar.setVisibility(View.GONE);
        errorImage.setVisibility(View.VISIBLE);
    }

    private void showData(ProgressBar progressBar, ImageView errorImage) {
        progressBar.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
    }
}
