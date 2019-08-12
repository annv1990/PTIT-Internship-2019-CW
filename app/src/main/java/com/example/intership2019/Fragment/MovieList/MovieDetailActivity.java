package com.example.intership2019.Fragment.MovieList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.MovieList.MovieDetail.DescriptionMovie;
import com.example.intership2019.Fragment.MovieList.MovieDetail.Genres;
import com.example.intership2019.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private DescriptionMovie descriptionMovie;

    int mMovieId, mMovieDuration;
    String mMovieName, mMovieOverView;
    private List<Genres> mGenres;

    private TextView textMovieName, textReleaseDuration, textGenres, textOverView;
    private ImageView imageBackgroundMovie, imageRateMovie1, imageRateMovie2, imageRateMovie3, imageRateMovie4, imageRateMovie5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();

        mMovieId = bundle.getInt("movie_Id", 0);
        mMovieName = bundle.getString("movie_Name");
        mMovieOverView = bundle.getString("movie_over_view");
        mMovieDuration = bundle.getInt("movie_duration");

        textMovieName = findViewById(R.id.textMovieName);
        textReleaseDuration = findViewById(R.id.textRelease_Duration);
        textOverView = findViewById(R.id.textOverView);
        textGenres = findViewById(R.id.textGenres);
        imageBackgroundMovie = findViewById(R.id.imageBackgroundMovie);
        imageRateMovie1 = findViewById(R.id.imageRateMovie1);
        imageRateMovie2 = findViewById(R.id.imageRateMovie2);
        imageRateMovie3 = findViewById(R.id.imageRateMovie3);
        imageRateMovie4 = findViewById(R.id.imageRateMovie4);
        imageRateMovie5 = findViewById(R.id.imageRateMovie5);

        textMovieName.setText(mMovieName);
        textReleaseDuration.setText(mMovieDuration + " minute");
        textOverView.setText(mMovieOverView);

        Log.d(Constant.TAG, "movieID ");

        loadDataMovieDetailActivity();
    }

    public void loadDataMovieDetailActivity() {

        final ApiInterfaceMovieList apiInterfaceMovieList = ApiClientMovieList.getClient().create(ApiInterfaceMovieList.class);
        final String key = "ba22e944d75a4f64fdba15e60523251f";
        Call<DescriptionMovie> callDescriptionMovie = apiInterfaceMovieList.getDescriptionMovie(mMovieId, key);
        callDescriptionMovie.enqueue(new Callback<DescriptionMovie>() {
            @Override
            public void onResponse(Call<DescriptionMovie> call, Response<DescriptionMovie> response) {
                descriptionMovie = response.body();

                mGenres = response.body().getGenres();

                String genres = "";
                if (mGenres != null) {
                    for (int i = 0; i < mGenres.size(); i++) {
                        genres = genres + mGenres.get(i).getName() + " ";
                    }
                    textGenres.setText(genres);
                } else textGenres.setText(null);

                Picasso.get().
                        load("https://image.tmdb.org/t/p/w500" + descriptionMovie.getBackdropPath())
                        .placeholder(R.drawable.ic_launcher_background).fit().into(imageBackgroundMovie);

                if (descriptionMovie.getVoteAverage() <= 2) {
                    imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
                } else if (descriptionMovie.getVoteAverage() > 2 && descriptionMovie.getVoteAverage() <= 4) {
                    imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
                } else if (descriptionMovie.getVoteAverage() > 4 && descriptionMovie.getVoteAverage() <= 6) {
                    imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
                } else if (descriptionMovie.getVoteAverage() > 6 && descriptionMovie.getVoteAverage() <= 8) {
                    imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie4.setImageResource(R.drawable.staryellow_16px);
                } else if (descriptionMovie.getVoteAverage() > 8 && descriptionMovie.getVoteAverage() <= 10) {
                    imageRateMovie1.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie2.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie3.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie4.setImageResource(R.drawable.staryellow_16px);
                    imageRateMovie5.setImageResource(R.drawable.staryellow_16px);
                }
                Log.e(Constant.TAG, "loading API");
            }

            @Override
            public void onFailure(Call<DescriptionMovie> call, Throwable t) {
                Log.e(Constant.TAG, "error loading from API" + t.getMessage());
            }
        });
    }
}


