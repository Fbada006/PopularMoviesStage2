package com.disruption.popularmovies1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies1.adapter.MoviesAdapter;
import com.disruption.popularmovies1.viewModel.MovieViewModel;

public class MainActivity extends AppCompatActivity {
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesAdapter = new MoviesAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setAdapter(mMoviesAdapter);

        observeViewModelForMovies();
    }

    private void observeViewModelForMovies() {
        MovieViewModel movieViewModel =
                new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovieListObservable().observe(this, movies -> {
            if (movies != null) {
                mMoviesAdapter.submitList(movies);
            }
        });
    }
}
