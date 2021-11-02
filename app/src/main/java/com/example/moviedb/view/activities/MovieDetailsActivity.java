package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_detail_title, lbl_detail_overview, lbl_detail_rating;
    private String movie_id = "";
    private MovieViewModel viewModel;
    private ImageView img_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        lbl_detail_title = findViewById(R.id.lbl_title_movie_details);
        lbl_detail_overview = findViewById(R.id.lbl_overview_movie_details);
        lbl_detail_rating = findViewById(R.id.lbl_rating_movie_details);
        img_poster = findViewById(R.id.img_poster_movie_details);

        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);

    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(img_path).into(img_poster);
            lbl_detail_title.setText(movies.getTitle());
            lbl_detail_overview.setText(movies.getOverview());
            lbl_detail_rating.setText(""+movies.getVote_average());
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}