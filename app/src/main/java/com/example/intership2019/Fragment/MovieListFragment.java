package com.example.intership2019.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intership2019.Constant;
import com.example.intership2019.Fragment.Adapter.ForecastAdapter;
import com.example.intership2019.Fragment.Adapter.MovieListAdapter;
import com.example.intership2019.Fragment.MovieList.ApiClientMovieList;
import com.example.intership2019.Fragment.MovieList.ApiInterfaceMovieList;
import com.example.intership2019.Fragment.MovieList.MovieDetail.DescriptionMovie;
import com.example.intership2019.Fragment.MovieList.MovieDetail.Genres;
import com.example.intership2019.Fragment.MovieList.MovieDetailActivity;
import com.example.intership2019.Fragment.MovieList.MovieListMain.ListOfMovie;
import com.example.intership2019.Fragment.MovieList.MovieListMain.MainInfoMovieList;
import com.example.intership2019.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {

    private RecyclerView recyclerViewMovieList;
    private MovieListAdapter movieListAdapter;
    private MainInfoMovieList mainInfoMovieList;
    private DescriptionMovie descriptionMovie;
    private List<ListOfMovie> movieList;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

//    private Activity mActivityMovieDetail;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance(String param1, String param2) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        recyclerViewMovieList = view.findViewById(R.id.recyclerView_MovieList);
//        recyclerViewMovieList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        recyclerViewMovieList.setItemAnimator(new SlideInUpAnimator());
        recyclerViewMovieList.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMovieList.setLayoutManager(layoutManager);
        loadDataMovie();
        return view;
    }

    private void initPreferences() {
        mSharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void loadDataMovie() {

        final ApiInterfaceMovieList apiInterfaceMovieList = ApiClientMovieList.getClient().create(ApiInterfaceMovieList.class);
        final String key = "ba22e944d75a4f64fdba15e60523251f";
        Call<MainInfoMovieList> call = apiInterfaceMovieList.getInfoMovieList(key);
        call.enqueue(new Callback<MainInfoMovieList>() {

            @Override
            public void onResponse(Call<MainInfoMovieList> call, Response<MainInfoMovieList> response) {
                mainInfoMovieList = response.body();
                movieList = response.body().getItems();
                Activity activityMovieDetail = getActivity();
                movieListAdapter = new MovieListAdapter(movieList, activityMovieDetail);
                for (final ListOfMovie movie : movieList) {
                    int movieId = movie.getId();

                    Call<DescriptionMovie> callDetail = apiInterfaceMovieList.getDescriptionMovie(movieId, key);
                    callDetail.enqueue(new Callback<DescriptionMovie>() {

                        @Override
                        public void onResponse(Call<DescriptionMovie> call, Response<DescriptionMovie> response) {

                            descriptionMovie = response.body();

                            if (descriptionMovie != null && descriptionMovie.getRuntime() != null) {
                                //gán duration bằng runtime
                                movie.setDuration(descriptionMovie.getRuntime());
                            }
                            movieListAdapter.notifyDataSetChanged();
                            Log.e(Constant.TAG, "loading API" + mainInfoMovieList.toString());
                        }

                        @Override
                        public void onFailure(Call<DescriptionMovie> call, Throwable t) {
                            Log.e(Constant.TAG, "error loading from API" + t.getMessage());
                        }
                    });
                }

                initPreferences();
                Gson gson = new Gson();
                String jsonMovie = gson.toJson(movieList);
                editor.putString("movie", jsonMovie);
                editor.commit();

                recyclerViewMovieList.setAdapter(movieListAdapter);
                movieListAdapter.notifyDataSetChanged();

                Log.e(Constant.TAG, "loading API" + mainInfoMovieList.toString());
            }

            @Override
            public void onFailure(Call<MainInfoMovieList> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Get data from local");
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initPreferences();
                        Gson gson = new Gson();
                        String jsonMovie = mSharedPreferences.getString("movie", "");
                        Type type = new TypeToken<List<ListOfMovie>>() {
                        }.getType();
                        movieList = gson.fromJson(jsonMovie, type);
                        Activity activityMovieDetail = getActivity();
                        movieListAdapter = new MovieListAdapter(movieList, activityMovieDetail);
                        recyclerViewMovieList.setAdapter(movieListAdapter);
                        movieListAdapter.notifyDataSetChanged();

                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Not internet not data", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                Log.e(Constant.TAG, "error loading from API" + t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}