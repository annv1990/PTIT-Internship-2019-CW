package com.example.intership2019;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.intership2019.Fragment.CurrentFragment;
import com.example.intership2019.Fragment.ForecastFragment;
import com.example.intership2019.Fragment.ForecastWeather.ForecastWeatherItem;
import com.example.intership2019.Fragment.MovieList.MovieListMain.MainInfoMovieList;
import com.example.intership2019.Fragment.MovieListFragment;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new CurrentFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_current:
                    fragment = new CurrentFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_forecast:
                    fragment = new ForecastFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_movie:
                    fragment = new MovieListFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;

        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}
