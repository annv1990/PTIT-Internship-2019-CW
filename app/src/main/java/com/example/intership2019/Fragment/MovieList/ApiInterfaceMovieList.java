package com.example.intership2019.Fragment.MovieList;

import com.example.intership2019.Fragment.MovieList.MovieDetail.DescriptionMovie;
import com.example.intership2019.Fragment.MovieList.MovieListMain.MainInfoMovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterfaceMovieList {
//@GET("/3/list/10?api_key=ba22e944d75a4f64fdba15e60523251f")
    @GET("/3/list/10")
    Call<MainInfoMovieList> getInfoMovieList(@Query("api_key") String apiKey);
    @GET("/3/movie/{movieId}")
    Call<DescriptionMovie> getDescriptionMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);


}
