package com.disruption.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies1.adapter.MoviesAdapter;
import com.disruption.popularmovies1.model.Movie;
import com.disruption.popularmovies1.utils.Constants;
import com.disruption.popularmovies1.viewModel.MovieViewModel;

public class MainActivity extends AppCompatActivity {
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mErrorTextView = findViewById(R.id.error_text);
        mProgressBar = findViewById(R.id.progressBar);

        mMoviesAdapter = new MoviesAdapter(this::onMovieClick);

        recyclerView.setAdapter(mMoviesAdapter);

        observeViewModelForMovies();
    }

    private void observeViewModelForMovies() {
        MovieViewModel movieViewModel =
                new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.mMovieResource.observe(this, movieResource -> {
            switch (movieResource.status) {
                case SUCCESS:
                    assert movieResource.data != null;
                    if (!movieResource.data.getResults().isEmpty()) {
                        mMoviesAdapter.submitList(movieResource.data.getResults());
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.GONE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.VISIBLE);
                        mErrorTextView.setText(getString(R.string.empty_result));
                    }
                    break;
                case ERROR:
                    mProgressBar.setVisibility(View.GONE);
                    mErrorTextView.setVisibility(View.VISIBLE);
                    if (movieResource.data != null) {
                        mErrorTextView.setText(getString(R.string.error_has_occurred, movieResource.data.getErrorMessage()));
                    }
                    break;
                case LOADING:
                    mProgressBar.setVisibility(View.VISIBLE);
                    mErrorTextView.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}
