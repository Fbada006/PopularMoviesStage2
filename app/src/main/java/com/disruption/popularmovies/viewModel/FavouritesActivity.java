package com.disruption.popularmovies.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies.DetailsActivity;
import com.disruption.popularmovies.R;
import com.disruption.popularmovies.adapter.MoviesAdapter;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.utils.Constants;
import com.disruption.popularmovies.viewModel.favs.FavsViewModel;
import com.disruption.popularmovies.viewModel.favs.FavsViewModelFactory;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        setTitle(getString(R.string.favourites));

        RecyclerView recyclerView = findViewById(R.id.rec_view);
        TextView emptyText = findViewById(R.id.empty_favs);

        MoviesAdapter moviesAdapter = new MoviesAdapter(this::onMovieClick);

        recyclerView.setAdapter(moviesAdapter);

        FavsViewModelFactory factory = new FavsViewModelFactory(getApplication());

        FavsViewModel favsViewModel = new ViewModelProvider(this, factory).get(FavsViewModel.class);

        favsViewModel.getFavsList().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                moviesAdapter.submitList(movies);
                emptyText.setVisibility(View.INVISIBLE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}
