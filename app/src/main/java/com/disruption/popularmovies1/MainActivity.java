package com.disruption.popularmovies1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies1.adapter.MoviesAdapter;
import com.disruption.popularmovies1.model.Movie;
import com.disruption.popularmovies1.utils.Constants;
import com.disruption.popularmovies1.viewModel.MovieViewModel;

public class MainActivity extends AppCompatActivity {
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesAdapter = new MoviesAdapter(this::onMovieClick);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setAdapter(mMoviesAdapter);

        observeViewModelForMovies();
    }

    private void observeViewModelForMovies() {
        MovieViewModel movieViewModel =
                new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovieListObservable().observe(this, movies -> {
            if ((movies != null) && (!movies.isEmpty())) {
                mMoviesAdapter.submitList(movies);
            }
        });
    }

    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}
