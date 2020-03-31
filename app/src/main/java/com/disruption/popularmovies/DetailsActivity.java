package com.disruption.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.utils.Constants;

import static com.disruption.popularmovies.utils.Constants.IMAGE_URL_BASE_PATH;

public class DetailsActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvDate;
    private TextView tvRating;

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
            Movie movie = intent.getParcelableExtra(Constants.MOVIE_EXTRA);
            assert movie != null;
            showMovieDetails(movie);
            setTitle(movie.getTitle());
        } else {
            setTitle(getString(R.string.movie_details_label));
        }
    }

    private void showMovieDetails(Movie movie) {
        String imageUrl = IMAGE_URL_BASE_PATH + movie.getPosterPath();

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.movie_loading_animation)
                .error(R.drawable.ic_error_black_24dp)
                .into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        tvDate.setText(setMovieYear(movie.getReleaseDate()));
        tvRating.setText(movie.getVote());
    }

    private String setMovieYear(String year) {
        try {
            return year.substring(0, 4);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "N/A";
        }
    }
}
